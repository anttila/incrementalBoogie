package grammar;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import incrementalBoogie.grammar.BoogieBaseVisitor;
import incrementalBoogie.grammar.BoogieParser;
import incrementalBoogie.grammar.BoogieParser.StmtListContext;
public class BoogieASTVisitor extends BoogieBaseVisitor<List<ConcreteInput>> {
	
	int statementContextDepth = 0;
	HashMap<String, ConcreteInput> ciCache = new HashMap<String,ConcreteInput>();
	HashMap<String, ConcreteInput> idCache = new HashMap<String,ConcreteInput>();
	HashMap<String, ConcreteInput> e_idCache = new HashMap<String,ConcreteInput>();
	HashMap<String, ConcreteInput> currentScope; // todo: currently not used
	HashMap<String, ConcreteInput> globalVars = new HashMap<String, ConcreteInput>();
	HashMap<String, Integer> globalVarsCounters = new HashMap<String, Integer>();
	HashMap<String, Integer> variableSnapshot = new HashMap<String, Integer>(); //Used to save variables at a call for old
	
	HashSet<String> scopeUsedVariables = new HashSet<String>(); // todo: reset between scope changes
	HashSet<String> constants = new HashSet<String>();
	HashSet<String> variablesHavoced = new HashSet<String>();
	
	ArrayList<String> whileTarget = new ArrayList<String>(); //Stores the target of a while body 
	ArrayList<ConcreteInput> queuedRegistration = new ArrayList<ConcreteInput>();
	ArrayList<ConcreteInput> assignments = new ArrayList<ConcreteInput>();
	ArrayList<ArrayList<ConcreteInput>> queuedCallconditions = new ArrayList<ArrayList<ConcreteInput>>();
	ArrayList<StmtListContext> queuedStatements = new ArrayList<StmtListContext>();
	
	private boolean DEVEL = false;
	private boolean parsingEnsures = false;
	private boolean parsingCall = false;
	private boolean parsingCallPostconds = false;
	private boolean parsingWhile = false;
	private boolean parsingOld = false;
	
	private int argumentsParsed = 0;
	private String callingProc = null;
	private String currentProcedure = null;
	// preventLastStmtEmission is used to prevent the last statement things to be emitted when working on something that has an inner stmtlist, which is: 
	//  * if-cases
	//  * else-cases
	//  * while-loops
	private boolean preventLastStmtEmission = false;
	
	public boolean parseError = false;
		
	private void error(String message) {
		System.out.println(message);
		parseError = true;
	}
	
	private String toVarForm(String var, int refNumb) {
		return "_"+var+"!"+refNumb;
	}
	
	private void addConcreteInput(List<ConcreteInput> list, Terminal terminal, String text) {
		// Since e_id and id can colide there will be different maps for them:
		HashMap<String,ConcreteInput> cache;
		if(terminal == BoogieGrammar.getInstance().id) {
			cache = idCache;
		} else if(terminal == BoogieGrammar.getInstance().e_id) {
			cache = e_idCache;
		} else {
			cache = ciCache;
		}
		
		if(cache.containsKey(text)) {
			list.add(cache.get(text));
		} else {
			ConcreteInput ci = ConcreteInput.newConcreteInput(terminal, text);
			cache.put(text, ci);
			list.add(ci);	
		}
	}
	
	private ConcreteInput getConcreteInput(String type) {
//		addConcreteInput(list, BoogieGrammar.getInstance().int_, "int");
		if(type.equals("int")) {
			if(ciCache.containsKey(type)) {
				return ciCache.get("int");
			}
			ciCache.put("int", ConcreteInput.newConcreteInput(BoogieGrammar.getInstance().int_, "int"));
			return ciCache.get("int");
		} else if(type.equals("bool")) {
			if(ciCache.containsKey(type)) {
				return ciCache.get("bool");
			}
			ciCache.put("bool", ConcreteInput.newConcreteInput(BoogieGrammar.getInstance().bool_, "bool"));
			return ciCache.get("bool");
		}
		return null;
	}
	
	private void registerVar(String name, ConcreteInput type) {
//		globalVars.put(idtype.get(0).getRepresentation(), idtype.get(2));
//		globalVarsCounters.put(idtype.get(0).getRepresentation(), 0);
		if(!globalVars.containsKey(name)) {
			globalVars.put(name, type);
		}
		if(!globalVarsCounters.containsKey(name)) {
			globalVarsCounters.put(name, 0);
		}
//		System.out.println("Registervar: "+name+type);
	}
	
	private static <T> void addAllNonNull(List<T> list, Collection<? extends T> additionalItems) {
		if(additionalItems != null) {
//			System.out.print("Old size: " +list.size());
			list.addAll(additionalItems);
//			System.out.println(" new size: " +list.size()+" input: "+additionalItems);
		} else {
//			System.out.println("Tried adding null");
		}
	}
	
	private void havocWhileVars(){
		for (String id : whileTarget){
			globalVarsCounters.put(id, globalVarsCounters.get(id)+1);
		}
	}

	//id ':' type;
	@Override
	public List<ConcreteInput> visitIdType(BoogieParser.IdTypeContext ctx) {
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addAllNonNull(list, visit(ctx.id_));
		addConcreteInput(list, BoogieGrammar.getInstance().colon, "colon");
		addAllNonNull(list, visit(ctx.type_));
		return list;
	}
	
	@Override
	public List<ConcreteInput> visitVarDecl_(BoogieParser.VarDecl_Context ctx) {
		List<ConcreteInput> idtype = visit(ctx.idt);
//		globalVars.put(idtype.get(0).getRepresentation(), idtype.get(2));
//		globalVarsCounters.put(idtype.get(0).getRepresentation(), 0);
		registerVar(idtype.get(0).getRepresentation(), idtype.get(2));
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addConcreteInput(list, BoogieGrammar.getInstance().var, "var");
		addAllNonNull(list, idtype);
		addConcreteInput(list, BoogieGrammar.getInstance().semicolon, "semicolon");
		
		return list;
	}
	
	@Override
	public List<ConcreteInput> visitVarDeclList(BoogieParser.VarDeclListContext ctx) {
		List<ConcreteInput> idtype = visit(ctx.idt);
//		globalVars.put(idtype.get(0).getRepresentation(), idtype.get(2));
		registerVar(idtype.get(0).getRepresentation(), idtype.get(2));
		
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addConcreteInput(list, BoogieGrammar.getInstance().var, "var");
		addAllNonNull(list, idtype);

		addConcreteInput(list, BoogieGrammar.getInstance().semicolon, "semicolon");
		addAllNonNull(list, visit(ctx.list));
		return list;
	}
	
	@Override
	public List<ConcreteInput> visitAxiomList(BoogieParser.AxiomListContext ctx) {

//		System.out.println("AxiomList found");
		
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addConcreteInput(list, BoogieGrammar.getInstance().axiom, "axiom");
		addAllNonNull(list, visit(ctx.e));
		addConcreteInput(list, BoogieGrammar.getInstance().semicolon, "semicolon");
		addAllNonNull(list, visit(ctx.list));
		
		return list;
	}
//
	@Override
	public List<ConcreteInput> visitAxiom(BoogieParser.AxiomContext ctx) {

//		System.out.println("Axiom found");
		
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addConcreteInput(list, BoogieGrammar.getInstance().axiom, "axiom");
		addAllNonNull(list, visit(ctx.expr()));
		addConcreteInput(list, BoogieGrammar.getInstance().semicolon, "semicolon");
		
		return list;
	}

	/*
	 * **********************************************************************************
	 * EXPRESSIONS
	 * **********************************************************************************
	 */
	@Override
	public List<ConcreteInput> visitExpr(BoogieParser.ExprContext ctx) {
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();

		addConcreteInput(list, BoogieGrammar.getInstance().exprid, "exprid");
		addAllNonNull(list, visit(ctx.e0()));
		return list;
	}
	// E0 expr
	@Override
	public List<ConcreteInput> visitE0toE1(BoogieParser.E0toE1Context ctx) {
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addConcreteInput(list, BoogieGrammar.getInstance().exprJump0, "exprJump0");
		addAllNonNull(list, visit(ctx.e1()));
		return list;
	}
	@Override
	public List<ConcreteInput> visitEEquiv(BoogieParser.EEquivContext ctx) {
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addAllNonNull(list, visit(ctx.left));
		addConcreteInput(list, BoogieGrammar.getInstance().equivop, "<==>");
		addAllNonNull(list, visit(ctx.right));
//		System.out.println("Equiv found: "+list);
		return list;
	}
	
	
	// E1 expr	
	@Override
	public List<ConcreteInput> visitE1toE2(BoogieParser.E1toE2Context ctx) {
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addConcreteInput(list, BoogieGrammar.getInstance().exprJump1, "exprJump1");
		addAllNonNull(list, visit(ctx.e2()));
		return list;
	}
	@Override
	public List<ConcreteInput> visitEImpl(BoogieParser.EImplContext ctx) {
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addAllNonNull(list, visit(ctx.e2()));
		addConcreteInput(list, BoogieGrammar.getInstance().implop, "==>");
		addAllNonNull(list, visit(ctx.e1()));
		return list;
	}
	
	// E2 expr
	@Override
	public List<ConcreteInput> visitE2toE3(BoogieParser.E2toE3Context ctx) {
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addConcreteInput(list, BoogieGrammar.getInstance().exprJump2, "exprJump2");
		addAllNonNull(list, visit(ctx.e3()));
		return list;
	}
	@Override
	public List<ConcreteInput> visitEOr(BoogieParser.EOrContext ctx) {
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addAllNonNull(list, visit(ctx.left));
		addConcreteInput(list, BoogieGrammar.getInstance().orop, "||");
		addAllNonNull(list, visit(ctx.right));
		return list;
	}
	@Override
	public List<ConcreteInput> visitEAnd(BoogieParser.EAndContext ctx) {
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addAllNonNull(list, visit(ctx.left));
		addConcreteInput(list, BoogieGrammar.getInstance().andop, "&&");
		addAllNonNull(list, visit(ctx.right));
		return list;
	}
	
	// E3 expr
	@Override
	public List<ConcreteInput> visitE3toE5(BoogieParser.E3toE5Context ctx) {
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addConcreteInput(list, BoogieGrammar.getInstance().exprJump3, "exprJump3");
		addAllNonNull(list, visit(ctx.e5()));
		return list;
	}
	@Override
	public List<ConcreteInput> visitEEq(BoogieParser.EEqContext ctx) {
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addAllNonNull(list, visit(ctx.left));
		addConcreteInput(list, BoogieGrammar.getInstance().relopEq, "==");
		addAllNonNull(list, visit(ctx.right));
		return list;
	}
	@Override
	public List<ConcreteInput> visitELt(BoogieParser.ELtContext ctx) {
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addAllNonNull(list, visit(ctx.left));
		addConcreteInput(list, BoogieGrammar.getInstance().relopLessThan, "<");
		addAllNonNull(list, visit(ctx.right));
		return list;
	}
	@Override
	public List<ConcreteInput> visitEGt(BoogieParser.EGtContext ctx) {
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addAllNonNull(list, visit(ctx.left));
		addConcreteInput(list, BoogieGrammar.getInstance().relopGreaterThan, ">");
		addAllNonNull(list, visit(ctx.right));
		return list;
	}
	@Override
	public List<ConcreteInput> visitENotEq(BoogieParser.ENotEqContext ctx) {
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addAllNonNull(list, visit(ctx.left));
		addConcreteInput(list, BoogieGrammar.getInstance().relopNotEq, "!=");
		addAllNonNull(list, visit(ctx.right));
		return list;
	}
	@Override
	public List<ConcreteInput> visitEEqLt(BoogieParser.EEqLtContext ctx) {
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addAllNonNull(list, visit(ctx.left));
		addConcreteInput(list, BoogieGrammar.getInstance().relopEqOrLess, "<=");
		addAllNonNull(list, visit(ctx.right));
		return list;
	}

	@Override
	public List<ConcreteInput> visitEEqGt(BoogieParser.EEqGtContext ctx) {
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addAllNonNull(list, visit(ctx.left));
		addConcreteInput(list, BoogieGrammar.getInstance().relopEqOrGreater, ">=");
		addAllNonNull(list, visit(ctx.right));
		return list;
	}
	
	// E5 expr
	@Override
	public List<ConcreteInput> visitE5toE6(BoogieParser.E5toE6Context ctx) {
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addConcreteInput(list, BoogieGrammar.getInstance().exprJump5, "exprJump5");
		addAllNonNull(list, visit(ctx.e6()));
		return list;
	}
	@Override
	public List<ConcreteInput> visitEAdd(BoogieParser.EAddContext ctx) {
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addAllNonNull(list, visit(ctx.e5()));
		addConcreteInput(list, BoogieGrammar.getInstance().addopPlus, "+");
		addAllNonNull(list, visit(ctx.e6()));
		return list;
	}
	@Override
	public List<ConcreteInput> visitESub(BoogieParser.ESubContext ctx) {
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addAllNonNull(list, visit(ctx.e5()));
		addConcreteInput(list, BoogieGrammar.getInstance().addopMinus, "-");
		addAllNonNull(list, visit(ctx.e6()));
		return list;
	}
	
	// E6 expr
	@Override
	public List<ConcreteInput> visitE6toE7(BoogieParser.E6toE7Context ctx) {
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addConcreteInput(list, BoogieGrammar.getInstance().exprJump6, "exprJump6");
		addAllNonNull(list, visit(ctx.e7()));
		return list;
	}
	@Override
	public List<ConcreteInput> visitEMul(BoogieParser.EMulContext ctx) {
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addAllNonNull(list, visit(ctx.e6()));
		addConcreteInput(list, BoogieGrammar.getInstance().mulopMul, "*");
		addAllNonNull(list, visit(ctx.e7()));
		return list;
	}
	@Override
	public List<ConcreteInput> visitEDiv(BoogieParser.EDivContext ctx) {
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addAllNonNull(list, visit(ctx.e6()));
		addConcreteInput(list, BoogieGrammar.getInstance().mulopDiv, "/");
		addAllNonNull(list, visit(ctx.e7()));
		return list;
	}
	
	// E7 expr
	@Override
	public List<ConcreteInput> visitE7toE8(BoogieParser.E7toE8Context ctx) {
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addConcreteInput(list, BoogieGrammar.getInstance().exprJump7, "exprJump7");
		addAllNonNull(list, visit(ctx.e8()));
		return list;
	}
	@Override
	public List<ConcreteInput> visitENot(BoogieParser.ENotContext ctx) {
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addConcreteInput(list, BoogieGrammar.getInstance().unop, "!");
		addAllNonNull(list, visit(ctx.e8()));
		return list;
	}
	
	// E8 expr
	@Override
	public List<ConcreteInput> visitE8toE9(BoogieParser.E8toE9Context ctx) {
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addConcreteInput(list, BoogieGrammar.getInstance().exprJump8, "exprJump8");
		addAllNonNull(list, visit(ctx.e9()));
		return list;
	}
	@Override
	public List<ConcreteInput> visitEArray1(BoogieParser.EArray1Context ctx) {
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addAllNonNull(list, visit(ctx.e9()));
		addConcreteInput(list, BoogieGrammar.getInstance().leftSquarebracket, "[");
		addAllNonNull(list, visit(ctx.firstExpr));
		addConcreteInput(list, BoogieGrammar.getInstance().assign, ":=");
		addAllNonNull(list, visit(ctx.secondExpr));
		addConcreteInput(list, BoogieGrammar.getInstance().rightSquarebracket, "]");
		return list;
	}
	@Override
	public List<ConcreteInput> visitEArray2(BoogieParser.EArray2Context ctx) {
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addAllNonNull(list, visit(ctx.e9()));
		addConcreteInput(list, BoogieGrammar.getInstance().leftSquarebracket, "[");
		addAllNonNull(list, visit(ctx.expr()));
		addConcreteInput(list, BoogieGrammar.getInstance().rightSquarebracket, "]");
		return list;
	}
	
	// E9 expr
	@Override
	public List<ConcreteInput> visitEParanthesis(BoogieParser.EParanthesisContext ctx) {
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addConcreteInput(list, BoogieGrammar.getInstance().leftParanthesis, "(");
		addAllNonNull(list, visit(ctx.expr()));
		addConcreteInput(list, BoogieGrammar.getInstance().rightParanthesis, ")");
		return list;
	}
	@Override
	public List<ConcreteInput> visitEFalse(BoogieParser.EFalseContext ctx) {
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addConcreteInput(list, BoogieGrammar.getInstance().false_, "false");
		return list;
	}
	@Override
	public List<ConcreteInput> visitETrue(BoogieParser.ETrueContext ctx) {
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addConcreteInput(list, BoogieGrammar.getInstance().true_, "true");
		return list;
	}
	@Override
	public List<ConcreteInput> visitEInt(BoogieParser.EIntContext ctx) {
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addConcreteInput(list, BoogieGrammar.getInstance().int_, ctx.val.getText());
		return list;
	}
	@Override
	public List<ConcreteInput> visitEId(BoogieParser.EIdContext ctx) {
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		if(globalVars.containsKey(ctx.id().getText())) {
			ConcreteInput var = globalVars.get(ctx.id().getText());
			if(var.getCorrespondingTerminal().equals(BoogieGrammar.getInstance().int_)) {
				addConcreteInput(list, BoogieGrammar.getInstance().typeflag_int, "typeflag_int");
			} else if(var.getCorrespondingTerminal().equals(BoogieGrammar.getInstance().bool_)) {
				addConcreteInput(list, BoogieGrammar.getInstance().typeflag_boolean, "typeflag_boolean");
			} else {
				error("Error in BoogieASTVisit:visitEId");
			}
		} else {
			// TODO: Do something real here
			error("Parser error (visitEId): Can not find global variable: "+ctx.id().getText());
			error("Global vars: "+globalVars+globalVars.containsKey("y"));
		}
		addConcreteInput(list, BoogieGrammar.getInstance().e_id, getVarRefRhs(ctx.id().getText()));
		return list;
	}
	@Override
	public List<ConcreteInput> visitEOld(BoogieParser.EOldContext ctx) {
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		boolean prevOldVal = parsingOld;
		parsingOld = true;
		addConcreteInput(list, BoogieGrammar.getInstance().leftParanthesis, "(");
		addAllNonNull(list, visit(ctx.expr()));
		addConcreteInput(list, BoogieGrammar.getInstance().rightParanthesis, ")");
		parsingOld = prevOldVal;
//		String varName = ctx.id().getText();
//		addConcreteInput(list, BoogieGrammar.getInstance().old, "old");
//		addConcreteInput(list, BoogieGrammar.getInstance().leftParanthesis, "(");
//		if(globalVars.containsKey(varName)) {
//			Terminal type = globalVars.get(ctx.id().getText()).getCorrespondingTerminal();
//			if(type.equals(BoogieGrammar.getInstance().int_)) {
//				addConcreteInput(list, BoogieGrammar.getInstance().typeflag_int, "typeflag_int");
//			} else if(type.equals(BoogieGrammar.getInstance().bool_)) {
//				addConcreteInput(list, BoogieGrammar.getInstance().typeflag_boolean, "typeflag_boolean");
//			} else {
//				System.out.println("Error in BoogieASTVisitor EOld: unknown type terminal: "+ type);
//			}
//		} else {
//			// TODO: Do something real here
//			System.out.println("Parser error (visitEOld): Can not find global variable: "+ctx.id().getText());
//			System.out.println("Global vars: " + globalVars + globalVars.containsKey("y"));
//		}
//		if (variableSnapshot.containsKey(varName)){
//			int num = variableSnapshot.get(varName);
//			addConcreteInput(list, BoogieGrammar.getInstance().e_id, "_"+ varName +"!"+num);
//		} else {
//			System.out.println("Error (visitEOld) Variable: "+varName+" not found in scope snapshot.");
//		}
//		addConcreteInput(list, BoogieGrammar.getInstance().rightParanthesis, ")");
		return list;
	}
	
	// Some todo, see "entered into antlr visitor.txt" for whats missing. 
	// Currently not needed and requires multiple additional rules to work so not worth it atm
	
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	



	@Override
	public List<ConcreteInput> visitId(BoogieParser.IdContext ctx) {
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addConcreteInput(list, BoogieGrammar.getInstance().id, ctx.id_.getText());
		return list;
	}
	
	@Override
	public List<ConcreteInput> visitTypeInt(BoogieParser.TypeIntContext ctx) {
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addConcreteInput(list, BoogieGrammar.getInstance().int_, "int");
//		System.out.println("IDTINT"+list);
		return list;
	}
	
	@Override
	public List<ConcreteInput> visitTypeBool(BoogieParser.TypeBoolContext ctx) {
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addConcreteInput(list, BoogieGrammar.getInstance().bool_, "bool");
//		System.out.println("IDTBOOL"+list);
		return list;
	}
	
//	@Override
//	public List<ConcreteInput> visitProc3(BoogieParser.Proc3Context ctx) {
//		currentScope = new HashMap<String, ConcreteInput>();
//		System.out.println("Global vars: "+globalVars+globalVars.containsKey("y"));
//		// 'procedure' id_=ID pSig ')' specList '{' stmtList '}' #proc3
//		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
//		addConcreteInput(list, BoogieGrammar.getInstance().procedure, "procedure");
//		addConcreteInput(list, BoogieGrammar.getInstance().id, ctx.id_.getText());
//		addAllNonNull(list, visit(ctx.pSig()));
//		addConcreteInput(list, BoogieGrammar.getInstance().rightParanthesis, ")");
//		addConcreteInput(list, BoogieGrammar.getInstance().dot, "dot");
//		addAllNonNull(list, visit(ctx.specList())); // TODO: Maybe readd back or remove
//		addConcreteInput(list, BoogieGrammar.getInstance().leftCurlybracket, "{");
//		addAllNonNull(list, visit(ctx.stmtList()));
//		// Add assignments to referenced variables, so last reference end up being refered to here
//		// This should probably be moved to a method later when more procs are added
////		for(String var : scopeUsedVariables) {
////			System.out.println("USED VAR: "+var);
////			generateAssign(list, "_"+var+"!"+"0", getVarRefRhs(var));
////		}
////		addAllNonNull(list, visit(BoogieASTVisitorInfoCollector.proc_p)); // DEBUG
//		addConcreteInput(list, BoogieGrammar.getInstance().rightCurlybracket, "}");
//		addAllNonNull(list, visit(ctx.specList()));
//		addConcreteInput(list, BoogieGrammar.getInstance().procdot, "procdot");
//		currentScope = null;
//		scopeUsedVariables = new HashSet<String>();
//		globalVarsCounters = new HashMap<String, Integer>();
//		return list;
//	}
	
	@Override
	public List<ConcreteInput> visitConstantDecl(BoogieParser.ConstantDeclContext ctx) {
		List<ConcreteInput> var = visit(ctx.idType());
		String varName = var.get(0).getRepresentation();
//		System.out.println("Constant: "+ varName + " type: "+var.get(2));
		if(constants.contains(varName)) {
			error("Constant already exists with name: "+varName);
			return null;
		} else if (globalVars.containsKey(varName)) {
			error("Variable already exists with name: "+varName);
			return null;
		}
		constants.add(varName);
		registerVar(varName, var.get(2));
		return ctx.declList() == null ? null : visit(ctx.declList());
	}
	
	@Override
	public List<ConcreteInput> visitProcedureDecl(BoogieParser.ProcedureDeclContext ctx) {
		variableSnapshot = new HashMap<String, Integer>(globalVarsCounters);
		currentScope = new HashMap<String, ConcreteInput>();
		currentProcedure = ctx.id_.getText();
		ProcInfo procInfo = BoogieASTVisitorInfoCollector.procs.get(currentProcedure);
		for(String var : procInfo.getArgs()) {
			String type = procInfo.getType(var);
			registerVar(var, getConcreteInput(type));
		}
		for(String var : procInfo.getOutParams()) {
			String type = procInfo.getType(var);
			registerVar(var, getConcreteInput(type));
		}
		
		List<ConcreteInput> pSig = visit(ctx.pSig());
		// TODO: Add 
		// Speclist is covered by procInfos pre/post conditions
		
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addConcreteInput(list, BoogieGrammar.getInstance().procedure, "procedure");
		addConcreteInput(list, BoogieGrammar.getInstance().id, ctx.id_.getText());
		addAllNonNull(list, pSig);
		addConcreteInput(list, BoogieGrammar.getInstance().rightParanthesis, ")");
		addConcreteInput(list, BoogieGrammar.getInstance().dot, "dot"); 
//		addAllNonNull(list, visit(ctx.specList()));
		if(procInfo.getPreconditions().isEmpty()) {
			addConcreteInput(list, BoogieGrammar.getInstance().dotnull, "dotnull");
		} else {
			for(BoogieParser.ExprContext precond : procInfo.getPreconditions()) {
				addConcreteInput(list, BoogieGrammar.getInstance().requires, "requires");
				addAllNonNull(list, visit(precond));
				addConcreteInput(list, BoogieGrammar.getInstance().semicolon, "semicolon");
			}
		}
		
		addConcreteInput(list, BoogieGrammar.getInstance().leftCurlybracket, "{");
		
		List<ConcreteInput> body = visit(ctx.stmtList());
		
		if(assignments.isEmpty()) {
			addConcreteInput(list, BoogieGrammar.getInstance().dotnull, "dotnull"); 
		} else {
			addAllNonNull(list, assignments);
			assignments.clear();
		}
		
		addConcreteInput(list, BoogieGrammar.getInstance().dot, "dot"); 

		finishLastStatment(body);
		if(body.isEmpty()) {
			addConcreteInput(list, BoogieGrammar.getInstance().dotnull, "dotnull"); 
		} else {
			addAllNonNull(list, body);
		}
		
			
		addConcreteInput(list, BoogieGrammar.getInstance().rightCurlybracket, "}");
		

		// Insert preconditions here for any calls that was done in expressions:
		// Insert postconditions here for any calls that was done in expressions:
		
		
//		addAllNonNull(list, visit(ctx.declList())); // TODO: This being commented out might be temp, it should at least create a new solver
		currentScope = null;
		currentProcedure = null;
		globalVarsCounters.clear();
		variableSnapshot.clear();
		if(ctx.declList() != null) {
			addAllNonNull(list, visit(ctx.declList()));
		}
		return list;
	}
	
	@Override
	public List<ConcreteInput> visitPsig1(BoogieParser.Psig1Context ctx) {
		// '(' ')' outParameters #psig2
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addConcreteInput(list, BoogieGrammar.getInstance().leftParanthesis, "("); 
		addConcreteInput(list, BoogieGrammar.getInstance().rightParanthesis, ")");
		addAllNonNull(list, visit(ctx.outParameters()));
		return list;
	}
	
	
	@Override
	public List<ConcreteInput> visitPsig2(BoogieParser.Psig2Context ctx) {
		// '(' ')' outParameters #psig2
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addConcreteInput(list, BoogieGrammar.getInstance().leftParanthesis, "("); 
		addConcreteInput(list, BoogieGrammar.getInstance().rightParanthesis, ")");
		addAllNonNull(list, visit(ctx.outParameters()));
		return list;
	}
	
	@Override
	public List<ConcreteInput> visitOutparameters1(BoogieParser.Outparameters1Context ctx) {
		// 'returns' '(' idType
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addConcreteInput(list, BoogieGrammar.getInstance().returns, "returns");
		addConcreteInput(list, BoogieGrammar.getInstance().leftParanthesis, "(");
		addAllNonNull(new ArrayList<ConcreteInput>(), visit(ctx.idType()));
		return list;
	}
	
	@Override
	public List<ConcreteInput> visitOutparameters2(BoogieParser.Outparameters2Context ctx) {
		// 'returns' '('  #outparameters2
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addConcreteInput(list, BoogieGrammar.getInstance().returns, "returns");
		addConcreteInput(list, BoogieGrammar.getInstance().leftParanthesis, "(");
		return list;
	}
	
	@Override
	public List<ConcreteInput> visitSpecRequires(BoogieParser.SpecRequiresContext ctx) {
		// 'ensures' expr ';' specList #SpecEnsuresList
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addConcreteInput(list, BoogieGrammar.getInstance().requires, "requires");
		addAllNonNull(list, visit(ctx.expr()));
		addConcreteInput(list, BoogieGrammar.getInstance().semicolon, ";");
		return list;
	}
	
	@Override
	public List<ConcreteInput> visitSpecRequiresList(BoogieParser.SpecRequiresListContext ctx) {
		// 'ensures' expr ';' specList #SpecEnsuresList
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addConcreteInput(list, BoogieGrammar.getInstance().requires, "requires");
		addAllNonNull(list, visit(ctx.expr()));
		addConcreteInput(list, BoogieGrammar.getInstance().semicolon, ";");
		addAllNonNull(list, visit(ctx.specList()));
		return list;
	}
	
	@Override
	public List<ConcreteInput> visitSpecEnsures(BoogieParser.SpecEnsuresContext ctx) {
		// 'ensures' expr ';' specList #SpecEnsuresList
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
//		parsingEnsures = true;
		addConcreteInput(list, BoogieGrammar.getInstance().ensures, "ensures");
		addAllNonNull(list, visit(ctx.expr()));
		addConcreteInput(list, BoogieGrammar.getInstance().semicolon, ";");
//		parsingEnsures = false;
		return list;
	}
	
	@Override
	public List<ConcreteInput> visitSpecEnsuresList(BoogieParser.SpecEnsuresListContext ctx) {
		// 'ensures' expr ';' specList #SpecEnsuresList
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addConcreteInput(list, BoogieGrammar.getInstance().ensures, "ensures");
		addAllNonNull(list, visit(ctx.expr()));
		addConcreteInput(list, BoogieGrammar.getInstance().semicolon, ";");
		addAllNonNull(list, visit(ctx.specList()));
		return list;
	}
	
	@Override
	public List<ConcreteInput> visitSpecModifies(BoogieParser.SpecModifiesContext ctx) {
		// 'modifies' ID ';' #SpecModifies
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addConcreteInput(list, BoogieGrammar.getInstance().modifies, "modifies");
		addConcreteInput(list, BoogieGrammar.getInstance().id, ctx.ID().getText());
		addConcreteInput(list, BoogieGrammar.getInstance().semicolon, ";");
		return list;
	}
	
	@Override
	public List<ConcreteInput> visitSpecModifiesList(BoogieParser.SpecModifiesListContext ctx) {
		// 'modifies' ID ';' #SpecModifies
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addConcreteInput(list, BoogieGrammar.getInstance().modifies, "modifies");
		addConcreteInput(list, BoogieGrammar.getInstance().id, ctx.ID().getText());
		addConcreteInput(list, BoogieGrammar.getInstance().semicolon, ";");
		addAllNonNull(list, visit(ctx.specList()));
		return list;
	}
	
	/*
	 * **********************************************************************************
	 * STATEMENTS
	 * **********************************************************************************
	 */
	
	@Override
	public List<ConcreteInput> visitStmtAssert(BoogieParser.StmtAssertContext ctx) {
		// 'assert' expr ';' #StmtAssert
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addConcreteInput(list, BoogieGrammar.getInstance().assert_, "assert");
		addAllNonNull(list, visit(ctx.expr()));
		addConcreteInput(list, BoogieGrammar.getInstance().semicolon, ";");
		finishCallStatements(list);
		return list;
	}
	
	@Override
	public List<ConcreteInput> visitStmtAssertList(BoogieParser.StmtAssertListContext ctx) {
		// 'assert' expr ';' stmtList #StmtAssertList
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addConcreteInput(list, BoogieGrammar.getInstance().assert_, "assert");
		addAllNonNull(list, visit(ctx.expr()));
		addConcreteInput(list, BoogieGrammar.getInstance().semicolon, ";");
		finishCallStatements(list);
		addAllNonNull(list, visit(ctx.stmtList()));
		return list;
	}
	
	
	@Override
	public List<ConcreteInput> visitStmtAssume(BoogieParser.StmtAssumeContext ctx) {
		// 'assume' expr ';' #StmtAssume
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addConcreteInput(list, BoogieGrammar.getInstance().assume_, "assume");
		addAllNonNull(list, visit(ctx.expr()));
		addConcreteInput(list, BoogieGrammar.getInstance().semicolon, ";");
		finishCallStatements(list);
//		finishLastStatment(list); // last statment
		return list;
	}
	
	@Override
	public List<ConcreteInput> visitStmtAssumeList(BoogieParser.StmtAssumeListContext ctx) {
		// 'assume' expr ';' stmtList #StmtAssumeList
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addConcreteInput(list, BoogieGrammar.getInstance().assume_, "assume");
		addAllNonNull(list, visit(ctx.expr()));
		addConcreteInput(list, BoogieGrammar.getInstance().semicolon, ";");
		finishCallStatements(list);
		addAllNonNull(list, visit(ctx.stmtList()));
		return list;
	}
	
	
	@Override
	public List<ConcreteInput> visitStmtAssign(BoogieParser.StmtAssignContext ctx) {
		// lhs ':=' expr ';' #StmtAssign
		// Visit rhs first so its variable references are correct:
		List<ConcreteInput> rhs = visit(ctx.expr());
		List<ConcreteInput> lhs = visit(ctx.lhs());
		
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		if(!parsingWhile) {
			addAllNonNull(assignments, lhs);
			addConcreteInput(assignments, BoogieGrammar.getInstance().assign, ":=");
			addAllNonNull(assignments, rhs);
			addConcreteInput(assignments, BoogieGrammar.getInstance().semicolon, ";");
			finishCallStatements(list);
		}
		
//		finishLastStatment(list); // last statment
//		assignments.addAll(list);
		return list;
	}
		
	@Override
	public List<ConcreteInput> visitStmtAssignList(BoogieParser.StmtAssignListContext ctx) {
		// lhs ':=' expr ';' stmtList #StmtAssignList
		// Visit rhs first so its variable references are correct:
		List<ConcreteInput> rhs = visit(ctx.expr());
		List<ConcreteInput> lhs = visit(ctx.lhs());

//		System.out.println("LHSFOO:::"+lhs+lhs.contains(ciCache.get("typeflag_int"))); // might be used to id calls
		
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		if(!parsingWhile) {
			addAllNonNull(assignments, lhs);
			addConcreteInput(assignments, BoogieGrammar.getInstance().assign, ":=");
			addAllNonNull(assignments, rhs);
			addConcreteInput(assignments, BoogieGrammar.getInstance().semicolon, ";");
			finishCallStatements(list);
		}
//		
		addAllNonNull(list, visit(ctx.stmtList()));
		return list;
//		assignments.addAll(list);
//		return null;
	}
	
	@Override
	public List<ConcreteInput> visitLhsId(BoogieParser.LhsIdContext ctx) {
		// id #Lhs
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		String varName = ctx.id().getText();
		
		if(parsingWhile){
			if(!whileTarget.contains(varName)){
				whileTarget.add(varName);
				if (DEVEL){
					System.out.println(varName + " added as target of while loop");
				}
			}
			return list;
		} else{
			
			if(globalVars.containsKey(varName)) {
				ConcreteInput var = globalVars.get(varName);
				if(var.getCorrespondingTerminal().equals(BoogieGrammar.getInstance().int_)) {
					addConcreteInput(list, BoogieGrammar.getInstance().typeflag_int, "typeflag_int");
				} else if(var.getCorrespondingTerminal().equals(BoogieGrammar.getInstance().bool_)) {
					addConcreteInput(list, BoogieGrammar.getInstance().typeflag_boolean, "typeflag_boolean");
				} else {
					error("Error in BoogieASTVisit:visitLhsId");
				}
			} else {
				error("Parser error (visitLhsId): Can not find global variable: "+ctx.id().getText());
				error("Global vars: "+globalVars+globalVars.containsKey("y"));
			}
			if (parsingWhile){
				globalVarsCounters.put(varName, globalVarsCounters.get(varName));
			}
			addConcreteInput(list, BoogieGrammar.getInstance().id, getVarRefLhs(ctx.id().getText()));
			return list;
		}	
	}
	
	@Override
	public List<ConcreteInput> visitLhsArray(BoogieParser.LhsArrayContext ctx) {
		// id '[' expr ']' #lhsArray
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addConcreteInput(list, BoogieGrammar.getInstance().id, ctx.id().getText());
		addConcreteInput(list, BoogieGrammar.getInstance().leftSquarebracket, "[");
		addAllNonNull(list, visit(ctx.expr()));
		addConcreteInput(list, BoogieGrammar.getInstance().rightSquarebracket, "]");
		return list;
	}
	
	@Override
	public List<ConcreteInput> visitStmtIf(BoogieParser.StmtIfContext ctx) {
		//  ifStmt #StmtIf
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addConcreteInput(list, BoogieGrammar.getInstance().dotIfStmt, "dotIfStmt");
		addAllNonNull(list, visit(ctx.ifStmt()));
		return list;
	}
//	// 'if' '(' expr ')' '{' stmtList '}' #StmtIf1
//	@Override
//	public List<ConcreteInput> visitStmtIf1(BoogieParser.StmtIf1Context ctx) {
//		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
//		addConcreteInput(list, BoogieGrammar.getInstance().if_, "if");
//		addConcreteInput(list, BoogieGrammar.getInstance().leftParanthesis, "(");
//		List<ConcreteInput> cond = visit(ctx.expr());
//		addConcreteInput(list, BoogieGrammar.getInstance().rightParanthesis, ")");
//		addConcreteInput(list, BoogieGrammar.getInstance().leftCurlybracket, "{");
//		
//		addConcreteInput(list, BoogieGrammar.getInstance().rightCurlybracket, "}");
//		return list;
//	}
//	 'if' '(' expr ')' '{' stmtList '}' #StmtIf1
	@Override
	public List<ConcreteInput> visitStmtIf1(BoogieParser.StmtIf1Context ctx) {
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		if (parsingWhile){
			addAllNonNull(list, visit(ctx.stmtList()));
			
		} else{
			ArrayList<ConcreteInput> stmts = new ArrayList<ConcreteInput>();
			addConcreteInput(list, BoogieGrammar.getInstance().if_, "if");
			addConcreteInput(list, BoogieGrammar.getInstance().leftParanthesis, "(");
			List<ConcreteInput> cond = visit(ctx.expr());
			addAllNonNull(list, cond);
			addConcreteInput(list, BoogieGrammar.getInstance().rightParanthesis, ")");
			addConcreteInput(list, BoogieGrammar.getInstance().leftCurlybracket, "{");

			HashSet<String> prevScopeUsedVariables = scopeUsedVariables;
			scopeUsedVariables = new HashSet<String>();
			HashMap<String, Integer> globalVarsCountersBeforeNewScope = new HashMap<String, Integer>(globalVarsCounters);

			addAllNonNull(stmts, visit(ctx.stmtList()));
			if(stmts.isEmpty()) {
				addConcreteInput(list, BoogieGrammar.getInstance().dotnull, "dotnull");
			} else {
				addAllNonNull(list, stmts);
			}
			addConcreteInput(list, BoogieGrammar.getInstance().rightCurlybracket, "}");
			for(String var : scopeUsedVariables) {
				generateIteIfCase(list, globalVarsCountersBeforeNewScope, cond, var);
				//			String alt1 = toVarForm(var, globalVarsCounters.get(var));
				//			String alt2 = toVarForm(var, globalVarsCountersBeforeNewScope.get(var));
				//			System.out.println("LTE: "+cond+" alt1: "+alt1+" alt2: "+alt2);
				//			generateAssignIte(list, cond, getVarRefLhs(var), alt1, alt2);
				prevScopeUsedVariables.add(var);
			}
			scopeUsedVariables = prevScopeUsedVariables;
		}
		return list;
	}
	
	@Override
	public List<ConcreteInput> visitStmtIf3(BoogieParser.StmtIf3Context ctx) {
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		if (parsingWhile){
			addAllNonNull(list, visit(ctx.stmt1));
			addAllNonNull(list, visit(ctx.stmt2));
			
		} else {
			ArrayList<ConcreteInput> stmts = new ArrayList<ConcreteInput>();
			addConcreteInput(list, BoogieGrammar.getInstance().if_, "if");
			addConcreteInput(list, BoogieGrammar.getInstance().leftParanthesis, "(");
			List<ConcreteInput> cond = visit(ctx.expr());
			addAllNonNull(list, cond);
			addConcreteInput(list, BoogieGrammar.getInstance().rightParanthesis, ")");
			addConcreteInput(list, BoogieGrammar.getInstance().leftCurlybracket, "{");
			
			HashSet<String> prevScopeUsedVariables = scopeUsedVariables;
			scopeUsedVariables = new HashSet<String>();
			HashMap<String, Integer> globalVarsCountersBeforeNewScope = new HashMap<String, Integer>(globalVarsCounters);
//			addAllNonNull(list, visit(ctx.stmt1));
			addAllNonNull(stmts, visit(ctx.stmt1));
			if(stmts.isEmpty()) {
				addConcreteInput(list, BoogieGrammar.getInstance().dotnull, "dotnull");
			} else {
				addAllNonNull(list, stmts);
			}
			addConcreteInput(list, BoogieGrammar.getInstance().rightCurlybracket, "}");
			for(String var : scopeUsedVariables) {
				generateIteIfCase(list, globalVarsCountersBeforeNewScope, cond, var);
				prevScopeUsedVariables.add(var);
//				String alt1 = toVarForm(var, globalVarsCounters.get(var));
//				if(globalVarsCountersBeforeNewScope.containsKey(var)) {
//					String alt2 = toVarForm(var, globalVarsCountersBeforeNewScope.get(var));
//					System.out.println("LTE: "+cond+" alt1: "+alt1+" alt2: "+alt2);
//					generateAssignIte(list, cond, getVarRefLhs(var), alt1, alt2);
//				} else {
//					if(globalVars.get(var).getCorrespondingTerminal().equals(BoogieGrammar.getInstance().int_)) {
//						System.out.println("LTE: "+cond+" alt1: "+alt1+" alt2: "+0);
//						generateAssignIteInt(list, cond, getVarRefLhs(var), alt1, 0);
//					}
//				}
			}
			scopeUsedVariables = prevScopeUsedVariables;
			addAllNonNull(list, visit(ctx.stmt2));
		}
		
		return list;
	}
	
	@Override
	public List<ConcreteInput> visitStmtIf2Else1(BoogieParser.StmtIf2Else1Context ctx) {
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		if (parsingWhile){
			addAllNonNull(list, visit(ctx.stmt1));
			addAllNonNull(list, visit(ctx.stmt2));
			
		} else{
//			System.out.println("IF2ELSE1");
			ArrayList<ConcreteInput> stmts = new ArrayList<ConcreteInput>();
			addConcreteInput(list, BoogieGrammar.getInstance().if_, "if");
			addConcreteInput(list, BoogieGrammar.getInstance().leftParanthesis, "(");
			List<ConcreteInput> cond = visit(ctx.expr());
			addAllNonNull(list, cond);
			addConcreteInput(list, BoogieGrammar.getInstance().rightParanthesis, ")");
			addConcreteInput(list, BoogieGrammar.getInstance().leftCurlybracket, "{");
			
			
			
			HashSet<String> prevScopeUsedVariables = scopeUsedVariables;
			HashSet<String> scopeTrueUsedVariables, scopeFalseUsedVariables;
			HashSet<String> allUsedVariables = new HashSet<String>();
			scopeUsedVariables = new HashSet<String>(); // Rest for true-case
			
			HashMap<String, Integer> globalCountersBeforeIfCase= new HashMap<String, Integer>(globalVarsCounters);
			addAllNonNull(stmts, visit(ctx.stmt1));
			if(stmts.isEmpty()) {
				addConcreteInput(list, BoogieGrammar.getInstance().dotnull, "dotnull");
			} else {
				addAllNonNull(list, stmts);
			}
			scopeTrueUsedVariables = scopeUsedVariables;
			
			scopeUsedVariables = new HashSet<String>(); // Rest for else-case
			addConcreteInput(list, BoogieGrammar.getInstance().rightCurlybracket, "}");
			addConcreteInput(list, BoogieGrammar.getInstance().else_, "else");
			addConcreteInput(list, BoogieGrammar.getInstance().leftCurlybracket, "{");
			HashMap<String, Integer> globalCountersForTrueCase = new HashMap<String, Integer>(globalVarsCounters);
			scopeFalseUsedVariables = scopeUsedVariables;
			stmts = new ArrayList<ConcreteInput>();
			addAllNonNull(stmts, visit(ctx.stmt2));
			if(stmts.isEmpty()) {
				addConcreteInput(list, BoogieGrammar.getInstance().dotnull, "dotnull");
			} else {
				addAllNonNull(list, stmts);
			}
			addConcreteInput(list, BoogieGrammar.getInstance().rightCurlybracket, "}");
			// TODO: Insert ite cond lastFromTrue lastFromFalse
			allUsedVariables.addAll(scopeTrueUsedVariables);
			allUsedVariables.addAll(scopeFalseUsedVariables);
			for(String var : allUsedVariables) {
//				String altTrue = toVarForm(var, glo)
				// Cases: 1. Var used in true and false paths
				//        2. Var used in true but not false path
				//        3. Var used in false but not true path
				prevScopeUsedVariables.add(var);
				if(scopeTrueUsedVariables.contains(var) && scopeFalseUsedVariables.contains(var)) {
					// Case 1: Generate ite cond varTrue varFalse
//					System.out.println("visitStmtIf2: case1 for var: "+var);
					String alt1 = getVarRefRhs(globalCountersForTrueCase, var);
					String alt2 = getVarRefRhs(globalVarsCounters, var);
					generateAssignIte(list, cond, getVarRefLhs(var), alt1, alt2);
				} else if (scopeTrueUsedVariables.contains(var) && !scopeFalseUsedVariables.contains(var)) {
					// Case 2: Generate ite cond varTrue 0/true
					String alt1 = getVarRefRhs(globalCountersForTrueCase, var);
					String alt2 = getVarRefRhs(globalCountersBeforeIfCase, var);
					generateAssignIte(list, cond, getVarRefLhs(var), alt1, alt2);
//					System.out.println("visitStmtIf2: case2 for var: "+var);
				} else if (!scopeTrueUsedVariables.contains(var) && scopeFalseUsedVariables.contains(var)) {
					// Case 3: Generate ite cond 0/true varFalse
					String alt1 = getVarRefRhs(globalCountersBeforeIfCase, var);
					String alt2 = getVarRefRhs(globalVarsCounters, var);
					generateAssignIte(list, cond, getVarRefLhs(var), alt1, alt2);
//					System.out.println("visitStmtIf2: case3 for var: "+var);
				} else {
					error("ERROR: Case ELSE occurs in BoogieASTVisitor : visitStmtIf2 - SHOULD NOT HAPPEN");
				}
			}
			scopeUsedVariables = prevScopeUsedVariables;

		}
		return list;
	} 
	
	@Override
	public List<ConcreteInput> visitStmtIf2Else2(BoogieParser.StmtIf2Else2Context ctx) {
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		if (parsingWhile){
			addAllNonNull(list, visit(ctx.stmt1));
			addAllNonNull(list, visit(ctx.stmt2));
			addAllNonNull(list, visit(ctx.stmt3));
			
		} else{
//			System.out.println("IF2ELSE1");
			ArrayList<ConcreteInput> stmts = new ArrayList<ConcreteInput>();
			addConcreteInput(list, BoogieGrammar.getInstance().if_, "if");
			addConcreteInput(list, BoogieGrammar.getInstance().leftParanthesis, "(");
			List<ConcreteInput> cond = visit(ctx.expr());
			addAllNonNull(list, cond);
			addConcreteInput(list, BoogieGrammar.getInstance().rightParanthesis, ")");
			addConcreteInput(list, BoogieGrammar.getInstance().leftCurlybracket, "{");
			
			
			
			HashSet<String> prevScopeUsedVariables = scopeUsedVariables;
			HashSet<String> scopeTrueUsedVariables, scopeFalseUsedVariables;
			HashSet<String> allUsedVariables = new HashSet<String>();
			scopeUsedVariables = new HashSet<String>(); // Rest for true-case
			
			HashMap<String, Integer> globalCountersBeforeIfCase= new HashMap<String, Integer>(globalVarsCounters);
			addAllNonNull(stmts, visit(ctx.stmt1));
			if(stmts.isEmpty()) {
				addConcreteInput(list, BoogieGrammar.getInstance().dotnull, "dotnull");
			} else {
				addAllNonNull(list, stmts);
			}
			scopeTrueUsedVariables = scopeUsedVariables;
			
			scopeUsedVariables = new HashSet<String>(); // Rest for else-case
			addConcreteInput(list, BoogieGrammar.getInstance().rightCurlybracket, "}");
			addConcreteInput(list, BoogieGrammar.getInstance().else_, "else");
			addConcreteInput(list, BoogieGrammar.getInstance().leftCurlybracket, "{");
			HashMap<String, Integer> globalCountersForTrueCase = new HashMap<String, Integer>(globalVarsCounters);
			scopeFalseUsedVariables = scopeUsedVariables;
			
			stmts = new ArrayList<ConcreteInput>();
			addAllNonNull(stmts, visit(ctx.stmt2));
			if(stmts.isEmpty()) {
				addConcreteInput(list, BoogieGrammar.getInstance().dotnull, "dotnull");
			} else {
				addAllNonNull(list, stmts);
			}
			addConcreteInput(list, BoogieGrammar.getInstance().rightCurlybracket, "}");
			// TODO: Insert ite cond lastFromTrue lastFromFalse
			allUsedVariables.addAll(scopeTrueUsedVariables);
			allUsedVariables.addAll(scopeFalseUsedVariables);
			for(String var : allUsedVariables) {
//				String altTrue = toVarForm(var, glo)
				// Cases: 1. Var used in true and false paths
				//        2. Var used in true but not false path
				//        3. Var used in false but not true path
				prevScopeUsedVariables.add(var);
				if(scopeTrueUsedVariables.contains(var) && scopeFalseUsedVariables.contains(var)) {
					// Case 1: Generate ite cond varTrue varFalse
//					System.out.println("visitStmtIf2: case1 for var: "+var);
					String alt1 = getVarRefRhs(globalCountersForTrueCase, var);
					String alt2 = getVarRefRhs(globalVarsCounters, var);
					generateAssignIte(list, cond, getVarRefLhs(var), alt1, alt2);
				} else if (scopeTrueUsedVariables.contains(var) && !scopeFalseUsedVariables.contains(var)) {
					// Case 2: Generate ite cond varTrue 0/true
					String alt1 = getVarRefRhs(globalCountersForTrueCase, var);
					String alt2 = getVarRefRhs(globalCountersBeforeIfCase, var);
					generateAssignIte(list, cond, getVarRefLhs(var), alt1, alt2);
//					System.out.println("visitStmtIf2: case2 for var: "+var);
				} else if (!scopeTrueUsedVariables.contains(var) && scopeFalseUsedVariables.contains(var)) {
					// Case 3: Generate ite cond 0/true varFalse
					String alt1 = getVarRefRhs(globalCountersBeforeIfCase, var);
					String alt2 = getVarRefRhs(globalVarsCounters, var);
					generateAssignIte(list, cond, getVarRefLhs(var), alt1, alt2);
//					System.out.println("visitStmtIf2: case3 for var: "+var);
				} else {
					error("ERROR: Case ELSE occurs in BoogieASTVisitor : visitStmtIf2 - SHOULD NOT HAPPEN");
				}
			}
			scopeUsedVariables = prevScopeUsedVariables;
			addAllNonNull(list, visit(ctx.stmt3));

		}
		return list;
	} 
	
	// Idea for new function to handle above mess for future methods:
	private void generateIteIfCase(List<ConcreteInput> list, HashMap<String, Integer> previousScopeCounters, List<ConcreteInput> cond, String var) {
		String alt1 = toVarForm(var, globalVarsCounters.get(var));
		if(previousScopeCounters.containsKey(var)) {
			String alt2 = toVarForm(var, previousScopeCounters.get(var));
//			System.out.println("LTE: "+cond+" alt1: "+alt1+" alt2: "+alt2);
			generateAssignIte(list, cond, getVarRefLhs(var), alt1, alt2);
		} else {
			if(globalVars.get(var).getCorrespondingTerminal().equals(BoogieGrammar.getInstance().int_)) {
//				System.out.println("LTE: "+cond+" alt1: "+alt1+" alt2: "+0);
				generateAssignIteInt(list, cond, getVarRefLhs(var), alt1, 0);
			}
		}
	}
	
	private void generateIteIfElseCase(List<ConcreteInput> list, HashMap<String, Integer> previousScopeCounters, List<ConcreteInput> cond, String var) {
		String alt1 = toVarForm(var, globalVarsCounters.get(var));
		if(previousScopeCounters.containsKey(var)) {
			String alt2 = toVarForm(var, previousScopeCounters.get(var));
//			System.out.println("LTE: "+cond+" alt1: "+alt1+" alt2: "+alt2);
			
			generateAssignIte(list, cond, getVarRefLhs(var), alt1, alt2);
		} else {
			if(globalVars.get(var).getCorrespondingTerminal().equals(BoogieGrammar.getInstance().int_)) {
//				System.out.println("LTE: "+cond+" alt1: "+alt1+" alt2: "+0);
				generateAssignIteInt(list, cond, getVarRefLhs(var), alt1, 0);
			}
		}
	}
	
	
	
	// 'else' ifStmt #else1
//	@Override
//	public List<ConcreteInput> visitElse1(BoogieParser.Else1Context ctx) {
//		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
//		addConcreteInput(list, BoogieGrammar.getInstance().else_, "else");
//		addAllNonNull(list, visit(ctx.ifStmt()));
//		return list;
//	}
//	// 'else' '{' stmtList '}' #else2 
//	@Override
//	public List<ConcreteInput> visitElse2(BoogieParser.Else2Context ctx) {
//		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
//		addConcreteInput(list, BoogieGrammar.getInstance().else_, "else");
//		addConcreteInput(list, BoogieGrammar.getInstance().leftCurlybracket, "{");
//		addAllNonNull(list, visit(ctx.stmtList()));
//		addConcreteInput(list, BoogieGrammar.getInstance().rightCurlybracket, "}");
//		return list;
//	}
//	@Override
//	public List<ConcreteInput> visitElse2(BoogieParser.Else2Context ctx) {
//		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
//		queuedStatements.add(null);
//		System.out.println("ELSE2");
//		System.out.println("This else branch contains an if: "+ifFinder.visit(ctx.stmtList()));
//		addAllNonNull(list, visit(ctx.stmtList()));
//		if(!ifFinder.visit(ctx.stmtList())) {
//			for(int i = queuedStatements.size()-1;i>=0;i--) {
//				if(queuedStatements.get(i) != null) {
//					System.out.println("Running: "+queuedStatements.get(i).getText());
//					addAllNonNull(list, visit(queuedStatements.get(i)));
//					
//				}
//			}
//
//			finishLastStatment(list);
//		}
//		
//		dealtWithPostCondsFromIfStmt = true;
//		return list;
//	}
//	// 'else' '{' stmtList '}' stmtList #else3
//	@Override
//	public List<ConcreteInput> visitElse3(BoogieParser.Else3Context ctx) {
//		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
//		queuedStatements.add(ctx.stmt2);
//		System.out.println("ELSE3");
//		System.out.println("This else branch contains an if: "+ifFinder.visit(ctx.stmt1));
//		addAllNonNull(list, visit(ctx.stmt1));
//		if(!ifFinder.visit(ctx.stmt1)) {
//			for(int i = queuedStatements.size()-1;i>=0;i--) {
//				if(queuedStatements.get(i) != null) {
//					System.out.println("Running: "+queuedStatements.get(i).getText());
//					addAllNonNull(list, visit(queuedStatements.get(i)));
//					
//				}
//			}
//
//			finishLastStatment(list);
//		}
//		
//		dealtWithPostCondsFromIfStmt = true;
//		return list;
//	}
	
	@Override
	public List<ConcreteInput> visitExprCommaList(BoogieParser.ExprCommaListContext ctx) {
		if(parsingCall) {
			ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
			// Bit of a hack. Use exprcommalist to generate assignments for calls
			ProcInfo proc = BoogieASTVisitorInfoCollector.procs.get(callingProc);
			String argName = proc.getArgs().get(argumentsParsed);
			String type = proc.getType(argName);
			registerVar(argName, getConcreteInput(type));
			String lhs = parsingCallPostconds ? getVarRefRhs(argName) : getVarRefLhs(argName);
//			System.out.println("1: "+argName +" : "+type +" as: "+getVarRefRhs(argName)+" assigned to "+getVarRefLhs(ctx.expr().getText()));
//			System.out.println("CALLING "+callingProc+ " USING: "+argName +" : "+type +" REAL:"+getConcreteInput(type)+" as: "+lhs+" assigned to "+visit(ctx.expr()));
			if(type.equals("int")) { // TODO: Make work for bool as well
//				addConcreteInput(list, BoogieGrammar.getInstance().typeflag_int, "typeflag_int");
//				addConcreteInput(list, BoogieGrammar.getInstance().id, lhs);
//				addConcreteInput(list, BoogieGrammar.getInstance().assign, "assign");
//				addAllNonNull(list, visit(ctx.expr()));
//				addConcreteInput(list, BoogieGrammar.getInstance().semicolon, "semicolon");
				addConcreteInput(assignments, BoogieGrammar.getInstance().typeflag_int, "typeflag_int");
				addConcreteInput(assignments, BoogieGrammar.getInstance().id, lhs);
				addConcreteInput(assignments, BoogieGrammar.getInstance().assign, "assign");
				addAllNonNull(assignments, visit(ctx.expr()));
				addConcreteInput(assignments, BoogieGrammar.getInstance().semicolon, "semicolon");
//				
			}
			
			// getVarRefRhs(proc.get);
			argumentsParsed++;
			
			if(ctx.exprCommaList() != null) {
				addAllNonNull(list, visit(ctx.exprCommaList()));
			}
//			System.out.println("COMMALIST"+list);
			return list;
		}
		return null;
	}
	// CALLS:
	@Override
	public List<ConcreteInput> visitCallStmt(BoogieParser.CallStmtContext ctx) {
		
		
		/*
		 * This assignment structure will not make use of all the sidecar calls.
		 * This is because the call: call t := foo(1) where foo has the type:
		 * foo(a : int) requires b == 1; ensures c == 2; returns (d : int)
		 *  gets translated to:
		 * 
		 * t := d;
		 * a == 1;
		 * b == 1;
		 * c == 2;
		 * call() foo(a);
		 */
		HashMap<String, Integer> prevVariableSnapshot = variableSnapshot;
		variableSnapshot = new HashMap<String, Integer>(globalVarsCounters);
//		variableSnapshot.putAll(globalVarsCounters);
		
		HashMap<String, ConcreteInput> currentGlobalVars = new HashMap<String, ConcreteInput>(globalVars);
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		ProcInfo calledProc = BoogieASTVisitorInfoCollector.procs.get(ctx.ID().getText());
		
		// Register the outparameters for the called method:
		for(String outparam : calledProc.getOutParams()) {
//			System.out.println("OUTPARAM: "+outparam+" of type "+calledProc.getType(outparam));
			String type = calledProc.getType(outparam);
			registerVar(outparam, getConcreteInput(type));
		}
		
	
		// Assign the arguments for preconditions:
		// Added above the call so it assigns everything related
		if(ctx.exprCommaList() != null){
			parsingCall = true;
			callingProc = ctx.ID().getText();
			addAllNonNull(list, visit(ctx.exprCommaList()));
			argumentsParsed = 0;
			callingProc = null;
			parsingCall = false;
		}

		ArrayList<ConcreteInput> preconds = new ArrayList<ConcreteInput>();
		ArrayList<ConcreteInput> pondconds = new ArrayList<ConcreteInput>();
		for(BoogieParser.ExprContext expr : calledProc.getPreconditions()) {
			addConcreteInput(preconds, BoogieGrammar.getInstance().requires, "requires");
			addAllNonNull(preconds, visit(expr));
			addConcreteInput(preconds, BoogieGrammar.getInstance().semicolon, "semicolon");
		}
		
		
		// We calculate the postconds up here, this is due to the havoc part, otherwise it will mess up the referals for the assignment
		for(BoogieParser.ExprContext expr : calledProc.getPostconditions()) {
			addConcreteInput(pondconds, BoogieGrammar.getInstance().ensures, "ensures");
			parsingEnsures = true; // essentially havoc
			HashSet<String> prevVariablesHavoced = variablesHavoced;
			variablesHavoced = new HashSet<String>();
			addAllNonNull(pondconds, visit(expr));
			parsingEnsures = false;
			variablesHavoced = prevVariablesHavoced;
			addConcreteInput(pondconds, BoogieGrammar.getInstance().semicolon, "semicolon");
		}
 
		
		// Assign the arguments for preconditions:
		// Added above the call so it assigns everything related
		if(ctx.exprCommaList() != null){
			parsingCall = true;
			parsingCallPostconds = true;
			callingProc = ctx.ID().getText();
			addAllNonNull(list, visit(ctx.exprCommaList()));
			argumentsParsed = 0;
			parsingCallPostconds = false;
			callingProc = null;
			parsingCall = false;
		}

		
		// Make the assignment for so that t gets mapped to the outparameter of foo() in: call t := foo()
		if(ctx.assign != null) {
			// Assign the variable to the output of the called function:
			String outparam = calledProc.getOutParams().get(0);
			generateAssign(list, getVarRefLhs(ctx.assign.getText()), getVarRefRhs(outparam), calledProc.getType(outparam)); // get(0) since we only support one outparam
		}
		addConcreteInput(list, BoogieGrammar.getInstance().call, "call");
		addConcreteInput(list, BoogieGrammar.getInstance().id, ctx.ID().getText());
		addConcreteInput(list, BoogieGrammar.getInstance().call, "call");
		addConcreteInput(list, BoogieGrammar.getInstance().leftParanthesis, "leftParanthesis");
		
		
		
		addConcreteInput(list, BoogieGrammar.getInstance().rightParanthesis, "rightParanthesis");
		addConcreteInput(list, BoogieGrammar.getInstance().dotpreconditions, "dotpreconditions");
		
		
		// Add preconditions:
		if(calledProc.getPreconditions().isEmpty()) {
			addConcreteInput(list, BoogieGrammar.getInstance().dotnull, "dotnull");
		} else {
			addAllNonNull(list, preconds);
		}
		
		
		// Add postconditions:
		addConcreteInput(list, BoogieGrammar.getInstance().dotpostconditions, "dotpostconditions");
		if(calledProc.getPostconditions().isEmpty()) {
			addConcreteInput(list, BoogieGrammar.getInstance().dotnull, "dotnull");
		} else {
			addAllNonNull(list, pondconds);
		}
		addConcreteInput(list, BoogieGrammar.getInstance().dotspecend, "dotspecend");
		
		// Restore globalvars to prevent the new ones from being used again:
		globalVars = currentGlobalVars;
		if(ctx.stmtList() != null) {
			addAllNonNull(list, visit(ctx.stmtList()));
		}
		
//		if(ctx.stmtList() == null) {
//			finishLastStatment(list); // last statment
//		}
		variableSnapshot = prevVariableSnapshot;
		return list;
	}
	
	@Override
	public List<ConcreteInput> visitFuncAppExpr(BoogieParser.FuncAppExprContext ctx) {
		return visit(ctx.exprCommaList());
	}
	
	@Override
	public List<ConcreteInput> visitEIdFuncApp(BoogieParser.EIdFuncAppContext ctx) { 
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		error("Function calls not supported");
		if(1==1) {
			return null; // remove if functions are implemented, this is implemented for calling procedures, which turns out you cant do from boogie
		}
		if(parsingWhile) {
			return list;
		}
		HashMap<String, ConcreteInput> currentGlobalVars = new HashMap<String, ConcreteInput>(globalVars);

		HashMap<String, Integer> prevVariableSnapshot = variableSnapshot;
		variableSnapshot = new HashMap<String, Integer>(globalVarsCounters);
		
//		System.out.println("EXPR CALL FOR "+ctx.id().getText());
		ProcInfo calledProc = BoogieASTVisitorInfoCollector.procs.get(ctx.id().getText());
		
		// Queue arg registration:
		// Assign the arguments:
		// Added above the call so it assigns everything related
		parsingCall = true;
		callingProc = ctx.id().getText();
		addAllNonNull(queuedRegistration, visit(ctx.funcApplication()));
		argumentsParsed = 0;
		callingProc = null;
		parsingCall = false;
//		System.out.println("EXPR CALL: "+queuedRegistration);
		
		
		String outparam = null;
		String outparamtype = null;
		// Register the outparameters for the called method:
		if(!calledProc.getOutParams().isEmpty()) {
			outparam = calledProc.getOutParams().get(0);
			outparamtype = calledProc.getType(outparam);
			registerVar(outparam, getConcreteInput(outparamtype));
		} else {
			error("Call to procedure: "+ctx.id().getText()+" is invalid, does not contain a return value");
		}
		

		
		
		// Queue postconditions:
		// Add preconditions:
		for(BoogieParser.ExprContext expr : calledProc.getPreconditions()) {
			ArrayList<ConcreteInput> temp = new ArrayList<ConcreteInput>();
			addAllNonNull(temp, visit(expr));
			queuedCallconditions.add(temp);
		}
		// Add postconditions:
		for(BoogieParser.ExprContext expr : calledProc.getPostconditions()) {
			ArrayList<ConcreteInput> temp = new ArrayList<ConcreteInput>();
			parsingEnsures = true; // essentially havoc
			HashSet<String> prevVariablesHavoced = variablesHavoced;// todo: bit of a hack, make something better
			variablesHavoced = new HashSet<String>();
			addAllNonNull(temp, visit(expr));
			queuedCallconditions.add(temp);
			variablesHavoced = prevVariablesHavoced;
			parsingEnsures = false;
		}
		
		// Output a reference to the outparameter:
		if(outparamtype != null) {
			if(outparamtype.equals("int")) {
				addConcreteInput(list, BoogieGrammar.getInstance().typeflag_int, "typeflag_int"); 
			} else {
				addConcreteInput(list, BoogieGrammar.getInstance().typeflag_boolean, "typeflag_boolean"); 
			}
			addConcreteInput(list, BoogieGrammar.getInstance().e_id, getVarRefRhs(outparam));			
		}
		globalVars = currentGlobalVars;
		variableSnapshot = prevVariableSnapshot;
		return list;
	}
	

	@Override
	public List<ConcreteInput> visitStmtWhileList(BoogieParser.StmtWhileListContext ctx){ //TODO Mark
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		ArrayList<ConcreteInput> stmts = new ArrayList<ConcreteInput>();
		
		parsingWhile = true;
		visit(ctx.stmtList1);				
		parsingWhile = false;
		
		//Initiation
		addConcreteInput(list, BoogieGrammar.getInstance().while_, "while");
		addAllNonNull(list, visit(ctx.loopInv()));
		
		//Consecution
		havocWhileVars();
		addConcreteInput(list, BoogieGrammar.getInstance().leftParanthesis, "(");
		addAllNonNull(list, visit(ctx.expr()));
		addConcreteInput(list, BoogieGrammar.getInstance().rightParanthesis, ")");
		addAllNonNull(list, visit(ctx.loopInv()));
		addConcreteInput(list, BoogieGrammar.getInstance().leftCurlybracket, "{");
		addAllNonNull(stmts, visit(ctx.stmtList1));
		if(stmts.isEmpty()){
			addConcreteInput(list, BoogieGrammar.getInstance().dotnull, "dotnull");
		} else{
			addAllNonNull(list, stmts);
		}
		addConcreteInput(list, BoogieGrammar.getInstance().rightCurlybracket, "}");
		addAllNonNull(list, visit(ctx.loopInv()));
		addConcreteInput(list, BoogieGrammar.getInstance().whiledot, "whiledot");
		
		//Continuation
		havocWhileVars();
		whileTarget.clear();
		addAllNonNull(list, visit(ctx.loopInv()));
		addConcreteInput(list, BoogieGrammar.getInstance().whiledot, "whiledot");
		addAllNonNull(list, visit(ctx.expr()));
		addConcreteInput(list, BoogieGrammar.getInstance().whiledot, "whiledot");
		addAllNonNull(list, visit(ctx.stmtList2));
		

		
//		System.out.println(list);
		
		return list;
	}
	@Override
	public List<ConcreteInput> visitStmtWhile(BoogieParser.StmtWhileContext ctx){
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		ArrayList<ConcreteInput> stmts = new ArrayList<ConcreteInput>();
		
		parsingWhile = true;
		visit(ctx.stmtList());
		parsingWhile = false;
		
		//Initiation
		addConcreteInput(list, BoogieGrammar.getInstance().while_, "while");
		addAllNonNull(list, visit(ctx.loopInv()));
		
		//Consecution
		havocWhileVars();
		addConcreteInput(list, BoogieGrammar.getInstance().leftParanthesis, "(");
		addAllNonNull(list, visit(ctx.expr()));
		addConcreteInput(list, BoogieGrammar.getInstance().rightParanthesis, ")");
		addAllNonNull(list, visit(ctx.loopInv()));
		addConcreteInput(list, BoogieGrammar.getInstance().leftCurlybracket, "{");
		addAllNonNull(stmts, visit(ctx.stmtList()));
		if(stmts.isEmpty()){
			addConcreteInput(list, BoogieGrammar.getInstance().dotnull, "dotnull");
		} else{
			addAllNonNull(list, stmts);
		}
		addConcreteInput(list, BoogieGrammar.getInstance().rightCurlybracket, "}");
		addAllNonNull(list, visit(ctx.loopInv()));
		addConcreteInput(list, BoogieGrammar.getInstance().whiledot, "whiledot");
		
		//Continuation
		havocWhileVars();
		whileTarget.clear();
		addAllNonNull(list, visit(ctx.loopInv()));
		addConcreteInput(list, BoogieGrammar.getInstance().whiledot, "whiledot");
		addAllNonNull(list, visit(ctx.expr()));

		addConcreteInput(list, BoogieGrammar.getInstance().whiledot, "whiledot");
		
		return list;
	}
	
	@Override
	public List<ConcreteInput> visitLoopInv(BoogieParser.LoopInvContext ctx){
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addConcreteInput(list, BoogieGrammar.getInstance().invariant, "invariant");
		addAllNonNull(list, visit(ctx.expr()));
		addConcreteInput(list, BoogieGrammar.getInstance().semicolon, ";");
		return list;
	}
	
	@Override
	public List<ConcreteInput> visitStmtHavoc(BoogieParser.StmtHavocContext ctx) {
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addAllNonNull(list, visit(ctx.idCommaList()));
		for (ConcreteInput var : list){
			if (!(var.getRepresentation().equals(","))){
				if(constants.contains(var.getRepresentation())) {
					error("Constants can not be havoced: '"+var.getRepresentation()+"'");
				}
				if (!parsingWhile){
					if (globalVarsCounters.containsKey(var.getRepresentation())){
						globalVarsCounters.put(var.getRepresentation(), globalVarsCounters.get(var.getRepresentation())+1);
					} else{
						globalVarsCounters.put(var.getRepresentation(), 0);
					}	
				} else{
					if(!whileTarget.contains(var.getRepresentation())){
						whileTarget.add(var.getRepresentation());
						if (DEVEL){
							System.out.println(var.getRepresentation() + " added as target of while loop");
						}
					}
				}
				
			}
		}
		list.clear();
		return list;
	}
	
	@Override
	public List<ConcreteInput> visitStmtHavocList(BoogieParser.StmtHavocListContext ctx) {
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		addAllNonNull(list, visit(ctx.idCommaList()));
		for (ConcreteInput var : list){
			System.out.println("Concrete input ID: "+var.getConcreteInputId()+" Representation: "+var.getRepresentation()+" ToString: "+var.toString());
			if (!(var.getRepresentation().equals(","))){
				if(constants.contains(var.getRepresentation())) {
					error("Constants can not be havoced: '"+var.getRepresentation()+"'");
				}
				if (!parsingWhile){
					if (globalVarsCounters.containsKey(var.getRepresentation())){
						globalVarsCounters.put(var.getRepresentation(), globalVarsCounters.get(var.getRepresentation())+1);
					} else{
						globalVarsCounters.put(var.getRepresentation(), 0);
					}
						
				} else{
					if(!whileTarget.contains(var.getRepresentation())){
						whileTarget.add(var.getRepresentation());
						if (DEVEL){
							System.out.println(var.getRepresentation() + " added as target of while loop");
						}
					}
				}
			}
		}
		
		list.clear();
		
		return visit(ctx.stmtList());
	}
	
	@Override
	public List<ConcreteInput> visitIdCommaList(BoogieParser.IdCommaListContext ctx) {
		ArrayList<ConcreteInput> list = new ArrayList<ConcreteInput>();
		if(!(ctx.idCommaList() == null)){
			addAllNonNull(list, visit(ctx.idCommaList()));
			addConcreteInput(list, BoogieGrammar.getInstance().comma, ",");
		}
		
		addAllNonNull(list, visit(ctx.id()));
		return list;
	}
	
	
	
	
		

	/**
	 * Generates a new variable reference for the left hand side
	 * @param variable 
	 * @return
	 */
	private String getVarRefLhs(String variable) {
		if(constants.contains(variable)) {
			error("Can not assign a value to '"+variable+"' since it is a constant");
			return null;
		}
		if(currentScope.containsKey(variable)) {
			// Todo: fetch from local vars
		} else if (globalVars.containsKey(variable)) {
			// Mark for reference at end:
			scopeUsedVariables.add(variable);
			if(!globalVarsCounters.containsKey(variable)) {
				// If variable hasn't been refered to yet
				globalVarsCounters.put(variable, 0); 
			}
			int num = globalVarsCounters.get(variable);
			globalVarsCounters.put(variable, num+1);
//			System.out.println("VARREFLHS: _"+variable+"!"+(num+1));
			return "_"+variable+"!"+(num+1);
		} else {
			error("Error: Can not find variable: "+variable);
		}
			
		return null;
	}
	
	/**
	 * Generates a variable reference for the right hand side using existing variables.
	 * Used for expressions.
	 * @param variable 
	 * @return
	 */
	private String getVarRefRhs(HashMap<String,Integer> counters, String variable) {
		if(parsingEnsures && !parsingOld && !variablesHavoced.contains(variable)) {
			variablesHavoced.add(variable);
			getVarRefLhs(variable);
		}
		if(parsingOld) {
			if(variableSnapshot.containsKey(variable)) {
				return "_"+variable+"!"+variableSnapshot.get(variable);
			} else {
				return "_"+variable+"!"+0;
			}
		}
		if(currentScope.containsKey(variable)) {
			// Todo: fetch from local vars
		} else if (globalVars.containsKey(variable)) {
			// Mark for reference at end:
			scopeUsedVariables.add(variable);
			if(!counters.containsKey(variable)) {
				// If variable hasn't been refered to yet
				counters.put(variable, 0); 
			}
			int num = counters.get(variable);
//			System.out.println("VARREFRHS: _"+variable+"!"+num);
			return "_"+variable+"!"+num;
		} else {
			System.out.println("Error: Can not find variable: "+variable);
		}
		return null;
	}
	
	private String getVarRefRhs(String variable) {
		return getVarRefRhs(globalVarsCounters, variable);
	}
	
	
	private void generateAssign(List<ConcreteInput> list, String lhs, String rhs, String type) {
		if(type.equals("int")) {
			addConcreteInput(assignments, BoogieGrammar.getInstance().typeflag_int, "typeflag_int"); 
		} else {
			addConcreteInput(assignments, BoogieGrammar.getInstance().typeflag_boolean, "typeflag_bool"); 
		}
		addConcreteInput(assignments, BoogieGrammar.getInstance().id, lhs);
		addConcreteInput(assignments, BoogieGrammar.getInstance().assign, "assign");
		addConcreteInput(assignments, BoogieGrammar.getInstance().exprid, "exprid");
		addConcreteInput(assignments, BoogieGrammar.getInstance().exprJump0, "exprJump0");
		addConcreteInput(assignments, BoogieGrammar.getInstance().exprJump1, "exprJump1");
		addConcreteInput(assignments, BoogieGrammar.getInstance().exprJump2, "exprJump2");
		addConcreteInput(assignments, BoogieGrammar.getInstance().exprJump3, "exprJump3");
		addConcreteInput(assignments, BoogieGrammar.getInstance().exprJump5, "exprJump5");
		addConcreteInput(assignments, BoogieGrammar.getInstance().exprJump6, "exprJump6");
		addConcreteInput(assignments, BoogieGrammar.getInstance().exprJump7, "exprJump7");
		addConcreteInput(assignments, BoogieGrammar.getInstance().exprJump8, "exprJump8");
		addConcreteInput(assignments, BoogieGrammar.getInstance().typeflag_int, "typeflag_int");
		addConcreteInput(assignments, BoogieGrammar.getInstance().e_id, rhs);
		addConcreteInput(assignments, BoogieGrammar.getInstance().semicolon, "semicolon");
	}
	
	private void generateAssignIte(List<ConcreteInput> list, List<ConcreteInput> cond, String lhs, String alt1, String alt2) {
		addConcreteInput(assignments, BoogieGrammar.getInstance().typeflag_int, "typeflag_int"); // todo: also make work for bool
		addConcreteInput(assignments, BoogieGrammar.getInstance().id, lhs);
		addConcreteInput(assignments, BoogieGrammar.getInstance().assign, "assign");
		addConcreteInput(assignments, BoogieGrammar.getInstance().itedot, "itedot");
		addAllNonNull(assignments, cond);
		addConcreteInput(assignments, BoogieGrammar.getInstance().id, alt1);
		addConcreteInput(assignments, BoogieGrammar.getInstance().id, alt2);
		addConcreteInput(assignments, BoogieGrammar.getInstance().itedotend, "itedotend");
		addConcreteInput(assignments, BoogieGrammar.getInstance().semicolon, "semicolon");
	}
	
	private void generateAssignIteInt(List<ConcreteInput> list, List<ConcreteInput> cond, String lhs, String alt1, int alt2) {
		addConcreteInput(assignments, BoogieGrammar.getInstance().typeflag_int, "typeflag_int"); // todo: also make work for bool
		addConcreteInput(assignments, BoogieGrammar.getInstance().id, lhs);
		addConcreteInput(assignments, BoogieGrammar.getInstance().assign, "assign");
		addConcreteInput(assignments, BoogieGrammar.getInstance().itedot, "itedot");
		addAllNonNull(assignments, cond);
		addConcreteInput(assignments, BoogieGrammar.getInstance().id, alt1);
		addConcreteInput(assignments, BoogieGrammar.getInstance().exprid, "exprid");
		addConcreteInput(assignments, BoogieGrammar.getInstance().exprJump0, "exprJump0");
		addConcreteInput(assignments, BoogieGrammar.getInstance().exprJump1, "exprJump1");
		addConcreteInput(assignments, BoogieGrammar.getInstance().exprJump2, "exprJump2");
		addConcreteInput(assignments, BoogieGrammar.getInstance().exprJump3, "exprJump3");
		addConcreteInput(assignments, BoogieGrammar.getInstance().exprJump5, "exprJump5");
		addConcreteInput(assignments, BoogieGrammar.getInstance().exprJump6, "exprJump6");
		addConcreteInput(assignments, BoogieGrammar.getInstance().exprJump7, "exprJump7");
		addConcreteInput(assignments, BoogieGrammar.getInstance().exprJump8, "exprJump8");
//		addConcreteInput(list, BoogieGrammar.getInstance().typeflag_int, "typeflag_int");
		addConcreteInput(assignments, BoogieGrammar.getInstance().int_, Integer.toString(alt2));
		addConcreteInput(assignments, BoogieGrammar.getInstance().itedotend, "itedotend");
		addConcreteInput(assignments, BoogieGrammar.getInstance().semicolon, "semicolon");
	}
	
	/**
	 * Finishes a call statement. This is queued up when a call is used in an expression, as such
	 * any method that deals with expressions should call this method after the expression is finished
	 */
	private void finishCallStatements(List<ConcreteInput> list) {
		// Insert arg-registration here
		list.addAll(queuedRegistration);
		queuedRegistration.clear();

		
		// Insert pre/postconditions for any calls here using assert.
		for(List<ConcreteInput> conds : queuedCallconditions) {
			addConcreteInput(list, BoogieGrammar.getInstance().assert_, "assert");
			addAllNonNull(list, conds);
			addConcreteInput(list, BoogieGrammar.getInstance().semicolon, "semicolon");
		}
	}
	
	private void finishLastStatment(List<ConcreteInput> list) {
		if(preventLastStmtEmission) {
			return;
		}
		ProcInfo procInfo = BoogieASTVisitorInfoCollector.procs.get(currentProcedure);
		if(procInfo.getPostconditions().isEmpty()) {
//			addConcreteInput(list, BoogieGrammar.getInstance().dotnull, "dotnull");
		} else {			
			// Insert this procedures prerequisites:
			for(BoogieParser.ExprContext postcond : procInfo.getPostconditions()) {
				addConcreteInput(list, BoogieGrammar.getInstance().assert_, "assert");
				addAllNonNull(list, visit(postcond));
				addConcreteInput(list, BoogieGrammar.getInstance().semicolon, "semicolon");
			}
		}
	}
}


