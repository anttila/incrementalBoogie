package incrementalBoogie;
import java.util.List;
import java.util.concurrent.Semaphore;

import com.microsoft.z3.ArithExpr;
import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Expr;
import com.microsoft.z3.Solver;

import grammar.BoogieGrammar;
import grammar.ConcreteInput;
import grammar.Symbol;
import parser.ASTNode;
import semantics.SemanticEvaluationException;
import semantics.SemanticVisitor;

public class BoogieSidecarVisitor extends SemanticVisitor<Expr> { // probably switch String to some VC representation later


	private boolean DEVEL = false;
	private final Semaphore semaphore = new Semaphore(1);
	private static Solver solver;
	public int nodesVisited = 0;
	
	public TestMain testSuite = null;
	
	public BoogieSidecarVisitor(int visitorId) {
		super(visitorId);
		if(TestMain.solver == null) {
			TestMain.solver = TestMain.context.mkSolver();
		}
		solver = TestMain.solver;
	}
	
	
	private String extractVariable(ASTNode node) { 
		// A bit of a hack since computeInitialValue cant be used to extract variable names, I'll just use this instead from representation()
		int splitpos = node.representation().indexOf('[');
		return node.representation().substring(0,splitpos);
	}
	
	public static BoogieSidecarVisitor newVisitor(){
		return new BoogieSidecarVisitor(SemanticVisitor.getUniqueVisitorId());
	}

	@Override
	protected Expr computeInitialValue(ConcreteInput arg0) throws SemanticEvaluationException {
		if(arg0.getCorrespondingTerminal().equals(BoogieGrammar.getInstance().int_)){
			// A bit of a hack...should probably change grammar. Now this can be triggered from "int" and numbers requiring this hack
			// Not sure what to do with "int" atm
			if(arg0.getRepresentation().matches("\\d+")) {
				return TestMain.context.mkInt(Integer.parseInt(arg0.getRepresentation()));
			}
		}
		return null;
//		return "[!!!Undefined computeInitialValue!!! "+arg0.getRepresentation()+"]";
	}
	

	@Override
	protected Expr visit(ASTNode arg0) throws SemanticEvaluationException {
		nodesVisited++;
		try {
			semaphore.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("Crashing for node: "+arg0.representation());
			e.printStackTrace();
		}
		BoogieGrammar bg = BoogieGrammar.getInstance();
		Symbol symbol = arg0.getSymbol();
		List<ASTNode> children = arg0.getChildren();
		Expr result = null;
		
		if (symbol.equals(bg.ntVarDecl)) {
			Expr var = getValue(children.get(1));
			result = var;
		} else if(symbol.equals(bg.ntDecl)){
//			System.out.println("NTDECL");
			result = getValue(children.get(0));

		} else if (symbol.equals(bg.ntStmtList)){

			if(children.get(0).getSymbol().equals(bg.ntLhs)) {
				// Assignment
				Expr lhs = getValue(children.get(0));
				Expr rhs = getValue(children.get(2));
				BoolExpr eq = TestMain.context.mkEq(lhs, rhs);
				// BoolExpr eqNot = TestMain.context.mkNot(eq);
				BoolExpr versionControl = eq; // used to be eq
				result = versionControl;
				// TestMain.solver.add(versionControl);
				if (children.size() != 4 && getValue(children.get(4)) != null) {
					BoolExpr andNext = TestMain.context.mkAnd(versionControl, (BoolExpr) (getValue(children.get(4))));
					result = andNext;

				}

			} else if (children.get(1).getSymbol().equals(bg.ntIfStmt)) {
				result = getValue(children.get(1));
			} else if (children.get(0).getSymbol().equals(bg.call)) {
				// gbh.addProduction(ntStmtList, call, id, call,
				// leftParanthesis, rightParanthesis, ntCallSpec, semicolon); //
				// todo: add ntExprCommaList
				ASTNode conditions = children.get(5);
				BoolExpr preconds = (BoolExpr) getValue(conditions.getChildren().get(1));
				BoolExpr postconds = (BoolExpr) getValue(conditions.getChildren().get(3));
//				result = preconds AND postconds => next
				result = preconds;
				if (children.size() == 8) {
					// If there is a statement after:
					result = (BoolExpr) getValue(children.get(7));
					if(postconds != null) {
//						System.out.println("Postconds not null");
						result = TestMain.context.mkImplies(postconds, (BoolExpr) result);
					}
					if(preconds != null) {
//						System.out.println("Preconds not null");
						result = TestMain.context.mkAnd(preconds, (BoolExpr)result);
					}
					
//					result = TestMain.context.mkAnd((BoolExpr) result, next);
				}
			} else if (children.get(0).getSymbol().equals(bg.assert_)) {
				result = getValue(children.get(1));
				if (children.size() == 4) {
					BoolExpr next = (BoolExpr) getValue(children.get(3));
					if(next == null) {
						System.out.println("NEXTNULL"+children.get(3).representation());
					}
					result = TestMain.context.mkAnd((BoolExpr) result, next);
				}

			} else if (children.get(0).getSymbol().equals(bg.assume_)){
				if(children.size() == 3){
					BoolExpr implLhs = (BoolExpr) getValue(children.get(1));
					BoolExpr implRhs = TestMain.context.mkTrue();
					result = TestMain.context.mkImplies(implLhs, implRhs);
				} else{
					BoolExpr implLhs = (BoolExpr) getValue(children.get(1));
					BoolExpr implRhs = (BoolExpr) getValue(children.get(3));
					result = TestMain.context.mkImplies(implLhs, implRhs);
				}
			} else if (children.get(0).getSymbol().equals(bg.while_)) {
				if (children.size() == 16) {
					
					BoolExpr initiation = (BoolExpr) getValue(children.get(1));

					BoolExpr implLhsCons = TestMain.context.mkAnd((BoolExpr) getValue(children.get(5)),
							(BoolExpr) getValue(children.get(3)));
					BoolExpr whileBodyEvaluation;
					if(children.get(7).getSymbol().equals(bg.dotnull)){
						whileBodyEvaluation = TestMain.context.mkTrue();
					} else {
						whileBodyEvaluation = (BoolExpr) getValue(children.get(7));
					}
					BoolExpr postBodyInvarEval = (BoolExpr) getValue(children.get(9));
					BoolExpr implRhsCons = (BoolExpr) TestMain.context.mkAnd(whileBodyEvaluation, postBodyInvarEval);
					BoolExpr consecution = TestMain.context.mkImplies(implLhsCons, implRhsCons);

					BoolExpr implLhsCont = TestMain.context.mkAnd((BoolExpr) getValue(children.get(11)),
							TestMain.context.mkNot((BoolExpr) getValue(children.get(13))));
					BoolExpr implRhsCont = (BoolExpr) getValue(children.get(15));
					BoolExpr continuation = TestMain.context.mkImplies(implLhsCont, implRhsCont);

					BoolExpr whileEvalation = TestMain.context.mkAnd(initiation, consecution, continuation);

					result = whileEvalation;
				} else {
					BoolExpr initiation = (BoolExpr) getValue(children.get(1));

					BoolExpr implLhsCons = TestMain.context.mkAnd((BoolExpr) getValue(children.get(5)),
							(BoolExpr) getValue(children.get(3)));
					BoolExpr whileBodyEvaluation;
					BoolExpr postBodyInvarEval = (BoolExpr) getValue(children.get(9));
					if(children.get(7).getSymbol().equals(bg.dotnull)){
						whileBodyEvaluation = TestMain.context.mkTrue();
					} else {
						whileBodyEvaluation = (BoolExpr) getValue(children.get(7));
					}
					
					BoolExpr implRhsCons = (BoolExpr) TestMain.context.mkAnd(whileBodyEvaluation, postBodyInvarEval);
					BoolExpr consecution = TestMain.context.mkImplies(implLhsCons, implRhsCons);

					BoolExpr implLhsCont = TestMain.context.mkAnd((BoolExpr) getValue(children.get(11)),
							TestMain.context.mkNot((BoolExpr) getValue(children.get(13))));
					BoolExpr implRhsCont = TestMain.context.mkTrue();
					BoolExpr continuation = TestMain.context.mkImplies(implLhsCont, implRhsCont);
					
					BoolExpr whileEvalation = TestMain.context.mkAnd(initiation, consecution, continuation);

					result = whileEvalation;
				}

			} else {
				System.out.println("UNKNOWN ntStmtList " + children.get(0));
			}
		} else if (symbol.equals(bg.ntCallSpec)){
			// gbh.addProduction(ntCallSpec, dotpostconditions, ntSpec, dotpreconditions, ntSpec, dotspecend);
			for(ASTNode child : children) {
				if(child.getSymbol().equals(bg.ntSpec)) {
					if(result == null) {
						result = getValue(child);
						break; // bit of a hack, only do the preconds for now
					} else {
						result = TestMain.context.mkAnd((BoolExpr) result, (BoolExpr) getValue(child)); 
					}
				}
			}
	
		} else if (symbol.equals(bg.ntLoopInv)){
			Expr expr = getValue(children.get(1)); 
			result = expr;
		} else if (symbol.equals(bg.ntIte)) {
			// gbh.addProduction(ntIte, itedot, ntExpr, id, id, itedotend);
			BoolExpr cond = (BoolExpr)getValue(children.get(1));

			Expr arg1 = TestMain.context.mkIntConst(extractVariable(children.get(2))); //TODO: Make more general
			Expr arg2;
			if(children.get(3).getSymbol().equals(bg.id)) {
				arg2 = TestMain.context.mkIntConst(extractVariable(children.get(3)));
			} else {
				arg2 = getValue(children.get(3));
			}
//			System.out.println("NTITE c:" +cond);
//			System.out.println("NTITE 1:" +arg2);
//			System.out.println("NTITE 2:" +extractVariable(children.get(3)));
			result = TestMain.context.mkITE(cond, arg1, arg2);
			
		} else if (symbol.equals(bg.ntIfStmt)) {
//			System.out.println("IFSTMT, size: "+children.size());
			// OLD: 'if' '(' expr ')' '{' stmtList '}' #StmtIf1, size
			// gbh.addProduction(ntIfStmt, 0 if_,  1 leftParanthesis, 2 ntExpr, 3 rightParanthesis, 4 leftCurlybracket, 5 ntStmtList, 6 rightCurlybracket);
			semaphore.release();
			BoolExpr cond = (BoolExpr)getValue(children.get(2));
			BoolExpr bodyTrue = (BoolExpr)getValue(children.get(5));
			BoolExpr leftHalf;
			try {
				semaphore.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(bodyTrue != null){
				leftHalf = TestMain.context.mkImplies(cond, bodyTrue);
				result = leftHalf;
			} else {
				leftHalf = TestMain.context.mkImplies(cond, TestMain.context.mkTrue());
				result = leftHalf;
			}
			if(children.size() >= 8 && children.get(7).getSymbol().equals(bg.ntStmtList)) {
				BoolExpr rest = (BoolExpr)getValue(children.get(7)); 
				result = TestMain.context.mkAnd(leftHalf,rest);
			} else if(children.size() >= 8) {
				// Must be an else case:
				BoolExpr bodyFalse = (BoolExpr)getValue(children.get(9));
				BoolExpr rightHalf;
				if(bodyFalse != null) {
					rightHalf = TestMain.context.mkImplies(TestMain.context.mkNot(cond), bodyFalse);
				} else {
					rightHalf = TestMain.context.mkImplies( TestMain.context.mkNot(cond), TestMain.context.mkTrue());
					
				}
				BoolExpr fullIf = TestMain.context.mkAnd(leftHalf, rightHalf);
				result = fullIf;
				if(children.size() == 12) {
					BoolExpr next = (BoolExpr)getValue(children.get(11));
					result = TestMain.context.mkAnd((BoolExpr)result, next);
				}
			}

			// 'if' '(' expr ')' '{' stmtList '}' else_ #StmtIf2 Has not been tested properly
			// 'if' '(' expr ')' '{' stmtList '}' stmtList #StmtIf3
		} else if (symbol.equals(bg.ntProcedureDecl)) {
//	    	gbh.addProduction(ntProcedureDecl, procedure, id, ntPSig, dot, dotnull, leftCurlybracket, ntStmtList, rightCurlybracket, ntSpec, procdot); 
//	    	gbh.addProduction(ntProcedureDecl, procedure, id, ntPSig, dot, ntSpec, leftCurlybracket, ntStmtList, rightCurlybracket, ntSpec, procdot);
//			System.out.println("PROCDECL "+children.get(1).representation());
			String procName = extractVariable(children.get(1));
//			testSuite.registerProcedure(procName);
			BoolExpr version = TestMain.context.mkBoolConst(testSuite.getVersion());
			
			BoolExpr preconditions = (BoolExpr) getValue(children.get(4));
			BoolExpr assignments = (BoolExpr) getValue(children.get(6));
			if (assignments != null ) {
//				assignments = TestMain.context.mkImplies(version, assignments);
//				solver.add(assignments);
				testSuite.solvers.get(procName).addInput(assignments);
			} 
			BoolExpr body = (BoolExpr) getValue(children.get(8));
			if(preconditions != null){
				body = TestMain.context.mkImplies(preconditions, body);
			}
			if(body != null) {
				body = TestMain.context.mkNot(body);
//				body = TestMain.context.mkImplies(version, body);
				testSuite.solvers.get(procName).addInput(body);
//				solver.add(body);
			} else {
				// Bit of a hack, for programs with only assignments this causes the program to return 'UNSAT' which means its valid
				BoolExpr true_ = TestMain.context.mkTrue(); 
				true_ = TestMain.context.mkNot(true_);
//				true_ = TestMain.context.mkImplies(version, true_);
//				solver.add(true_);
				testSuite.solvers.get(procName).addInput(true_);
			}
		} else if (symbol.equals(bg.ntSpec)) {
			
			Symbol type = children.get(0).getSymbol();
			
			if(type.equals(bg.requires) || type.equals(bg.ensures)) {
				result = getValue(children.get(1));

				if(children.size() == 4) {
					BoolExpr nextSpec = (BoolExpr) getValue(children.get(3));
					if(nextSpec != null) {
						result = TestMain.context.mkAnd((BoolExpr)result, nextSpec);
					}
				}

//				BoolExpr version = TestMain.context.mkBoolConst(testSuite.getVersion());
//				result = TestMain.context.mkImplies(version, (BoolExpr)result);
			}
		} else if (symbol.equals(bg.ntLhs)) {
			if(children.size() == 2) {
				String varname = extractVariable(children.get(1));
				Expr var;

				// We only support int and bool
				if(children.get(0).getSymbol().equals(bg.typeflag_int)) {
					var = TestMain.context.mkIntConst(varname);
				} else {
					var = TestMain.context.mkBoolConst(varname);
				}
				result = var;
			} else {
				System.out.println("LHSERROR");
			}
		}  
		
		
		
		
//		}
//		
//		
//		//
//		// EXPRESSIONS
//		//
		else if (symbol.equals(bg.ntExpr)) {
			if(arg0.getChildren().size() == 2) {
				result = getValue(children.get(1));
			}
		} else if (symbol.equals(bg.ntE0)) {
			if(arg0.getChildren().size() == 2) {
				result = getValue(children.get(1));
			}
		} else if (symbol.equals(bg.ntE1)) {
			if(arg0.getChildren().size() == 2) {
				result = getValue(children.get(1));
			} else{
				BoolExpr lhs = (BoolExpr) getValue(children.get(0));
				BoolExpr rhs = (BoolExpr) getValue(children.get(2));
				result = TestMain.context.mkImplies(lhs, rhs);
			}
		} else if (symbol.equals(bg.ntE2)) {
			if(arg0.getChildren().size() == 2) {
				result = getValue(children.get(1));
			} else if (children.get(1).getSymbol().equals(bg.orop)){
				BoolExpr left = (BoolExpr)getValue(children.get(0));
				BoolExpr right = (BoolExpr)getValue(children.get(2));
				result = TestMain.context.mkOr(left, right);
			} else if (children.get(1).getSymbol().equals(bg.andop)){
				BoolExpr left = (BoolExpr)getValue(children.get(0));
				BoolExpr right = (BoolExpr)getValue(children.get(2));
				result = TestMain.context.mkAnd(left, right);
			}
		} else if (symbol.equals(bg.ntE3)){
			if(arg0.getChildren().size() == 2) {
				result = getValue(children.get(1));
			} else if (children.get(1).getSymbol().equals(bg.relopEq)) {
				result = TestMain.context.mkEq(getValue(children.get(0)), getValue(children.get(2)));
				
			} else if (children.get(1).getSymbol().equals(bg.relopLessThan)) {
				result = TestMain.context.mkLt((ArithExpr)getValue(children.get(0)), (ArithExpr)getValue(children.get(2)));
				
			} else if (children.get(1).getSymbol().equals(bg.relopGreaterThan)) {
				result = TestMain.context.mkGt((ArithExpr)getValue(children.get(0)), (ArithExpr)getValue(children.get(2)));
				
			} else if (children.get(1).getSymbol().equals(bg.relopNotEq)) {
				result =TestMain.context.mkEq(getValue(children.get(0)), getValue(children.get(2)));
				result = TestMain.context.mkNot((BoolExpr) result);
				
			} else if (children.get(1).getSymbol().equals(bg.relopEqOrLess)) {
				result = TestMain.context.mkLe((ArithExpr)getValue(children.get(0)), (ArithExpr)getValue(children.get(2)));
				
			} else if (children.get(1).getSymbol().equals(bg.relopEqOrGreater)) {
				result = TestMain.context.mkGe((ArithExpr)getValue(children.get(0)), (ArithExpr)getValue(children.get(2)));
				
			} else{
				System.out.println("RELOPUNKNOWN  "+children.get(1).getSymbol());
			}
		} else if (symbol.equals(bg.ntE5)){
			if(children.size() == 2) {
				result = getValue(children.get(1));
			} else if (children.get(1).getSymbol().equals(bg.addopPlus)){
				ArithExpr left = (ArithExpr)getValue(children.get(0));
				ArithExpr right = (ArithExpr)getValue(children.get(2));
				result = TestMain.context.mkAdd(left, right);
			} else if (children.get(1).getSymbol().equals(bg.addopMinus)){
				ArithExpr left = (ArithExpr)getValue(children.get(0));
				ArithExpr right = (ArithExpr)getValue(children.get(2));
				result = TestMain.context.mkSub(left, right);
			}
		} else if (symbol.equals(bg.ntE6)) {
			if(arg0.getChildren().size() == 2) {
				result = getValue(children.get(1));
			} else if (children.get(1).getSymbol().equals(bg.mulopMul)){
				ArithExpr left = (ArithExpr)getValue(children.get(0));
				ArithExpr right = (ArithExpr)getValue(children.get(2));
				result = TestMain.context.mkMul(left, right);
			} else if (children.get(1).getSymbol().equals(bg.mulopDiv)){
				ArithExpr left = (ArithExpr)getValue(children.get(0));
				ArithExpr right = (ArithExpr)getValue(children.get(2));
				result = TestMain.context.mkDiv(left, right);
			}
		} else if (symbol.equals(bg.ntE7)) {
			if(arg0.getChildren().get(0).getSymbol().equals(bg.exprJump7)) {
				result = getValue(children.get(1));
			} else if(children.get(0).getSymbol().equals(bg.unop)){
				result = TestMain.context.mkNot((BoolExpr) getValue(children.get(1)));
			}
		} else if (symbol.equals(bg.ntE8)) {
			if(arg0.getChildren().size() == 2) {
				result = getValue(children.get(1));
			} 
		} else if (symbol.equals(bg.ntE9)){
			if (children.get(0).getSymbol().equals(bg.int_)) {
				//	System.out.println("Flippin int "+getValue(children.get(0)));
				result = getValue(children.get(0));
			} else if (children.get(0).getSymbol().equals(bg.typeflag_int)) {
				//	result = getValue(children.get(2));
//					System.out.println("EID: "+extractVariable(children.get(1)));
				result = TestMain.context.mkIntConst(extractVariable(children.get(1)));
			} else if (children.get(0).getSymbol().equals(bg.typeflag_boolean)) {
//				System.out.println("EBOOL: "+extractVariable(children.get(1)));
				result = TestMain.context.mkBoolConst(extractVariable(children.get(1)));
			} else if(children.get(0).getSymbol().equals(bg.leftParanthesis) && children.get(2).getSymbol().equals(bg.rightParanthesis)){
				result = getValue(children.get(1));
				//result = TestMain.context.mkBoolConst(extractVariable(children.get(1))); //TODO What is this? 
			} else if (children.get(0).getSymbol().equals(bg.old)){
				if(children.get(2).getSymbol().equals(bg.typeflag_int)){
					result = TestMain.context.mkIntConst(extractVariable(children.get(3)));
				} else if (children.get(2).getSymbol().equals(bg.typeflag_boolean)){
					result = TestMain.context.mkBoolConst(extractVariable(children.get(3)));
				}
			} else if (children.get(0).getSymbol().equals(bg.true_)){
				result = TestMain.context.mkTrue();
			} else if (children.get(0).getSymbol().equals(bg.false_)){
				result = TestMain.context.mkFalse();
			} else {
				if(DEVEL) {
					System.out.println("SiDECAR: Missing parsing for: "+children.get(0).getSymbol());
				}
			}
		} else if (symbol.equals(bg.ntIdType)) {
			String varname = extractVariable(children.get(0));
			Expr intVar = TestMain.context.mkIntConst(varname); // TODO: change from intvar later to more general
//			System.out.println(varname+" registered as "+varname);
			result =  intVar;
		} else {
			if(DEVEL){
				System.out.println("[!!!Undefined visit: "+arg0.getSymbol()+"]");
			}
		}
		
		semaphore.release();
		return result;
	} 

}
