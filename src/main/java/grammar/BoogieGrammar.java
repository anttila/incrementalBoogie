package grammar;

public final class BoogieGrammar {
	
    private static BoogieGrammar instance = new BoogieGrammar();
    public final Terminal leftParanthesis = new Terminal("leftParanthesis");
    public final Terminal rightParanthesis = new Terminal("rightParanthesis");
    public final Terminal leftSquarebracket = new Terminal("[");
    public final Terminal rightSquarebracket = new Terminal("]");
    public final Terminal semicolon = new Terminal(";");
    public final Terminal colon = new Terminal(":");
    public final Terminal comma = new Terminal(",");
    public final Terminal axiom = new Terminal("axiom");
    public final Terminal const_ = new Terminal("const");
    public final Terminal var = new Terminal("var");
    public final Terminal function = new Terminal("function");
    public final Terminal leftCurlybracket = new Terminal("{");
    public final Terminal rightCurlybracket = new Terminal("}");
    public final Terminal procedure = new Terminal("procedure");
    public final Terminal returns = new Terminal("returns");
    public final Terminal requires = new Terminal("requires");
    public final Terminal modifies = new Terminal("modifies");
    public final Terminal ensures = new Terminal("ensures");
    public final Terminal assert_ = new Terminal("assert");
    public final Terminal assume_ = new Terminal("assume");
	public final Terminal havoc = new Terminal("havoc");
    public final Terminal call = new Terminal("call");
    public final Terminal while_ = new Terminal("while");
    public final Terminal break_ = new Terminal("break");
    public final Terminal return_ = new Terminal("return");
    public final Terminal assign = new Terminal(":=");
    public final Terminal if_ = new Terminal("if");
    public final Terminal else_ = new Terminal("else");
    public final Terminal invariant = new Terminal("invariant");
    public final Terminal true_ = new Terminal("true");
    public final Terminal false_ = new Terminal("false");
    public final Terminal equivop = new Terminal("<==>");
    public final Terminal implop = new Terminal("==>");
    public final Terminal orop= new Terminal("||");
    public final Terminal andop = new Terminal("&&");
    public final Terminal relopEq = new Terminal("==");
    public final Terminal relopNotEq = new Terminal("!=");
    public final Terminal relopLessThan = new Terminal("<");
    public final Terminal relopGreaterThan = new Terminal(">");
    public final Terminal relopEqOrLess = new Terminal("<=");
    public final Terminal relopEqOrGreater = new Terminal(">=");
    public final Terminal addopPlus = new Terminal("+");
    public final Terminal addopMinus = new Terminal("-");
    public final Terminal mulopMul = new Terminal("*");
    public final Terminal mulopDiv = new Terminal("/");
    public final Terminal unop = new Terminal("!");
    public final Terminal qopForall = new Terminal("forall");
    public final Terminal qopExists = new Terminal("exists");
    public final Terminal qsep = new Terminal("::");
    public final Terminal id = new Terminal("id");
    public final Terminal int_ = new Terminal("int");
    public final Terminal bool_ = new Terminal("bool");
    public final Terminal old = new Terminal("old");
    public final Terminal dot = new Terminal("dot");
    public final Terminal e_id = new Terminal("e_id");
    public final Terminal typeflag_int = new Terminal("typeflag_int");
    public final Terminal typeflag_boolean = new Terminal("typeflag_boolean");
    public final Terminal exprid = new Terminal("exprid");
    public final Terminal exprJump0 = new Terminal("exprJump0");
    public final Terminal exprJump1 = new Terminal("exprJump1");
    public final Terminal exprJump2 = new Terminal("exprJump2");
    public final Terminal exprJump3 = new Terminal("exprJump3");
    public final Terminal exprJump5 = new Terminal("exprJump5");
    public final Terminal exprJump6 = new Terminal("exprJump6");
    public final Terminal exprJump7 = new Terminal("exprJump7");
    public final Terminal exprJump8 = new Terminal("exprJump8");
    public final Terminal dotIfStmt = new Terminal("dotIfStmt");
    public final Terminal itedot = new Terminal("itedot");
    public final Terminal itedotend = new Terminal("itedotend");
    public final Terminal whiledot = new Terminal("whiledot");
    public final Terminal dotpostconditions = new Terminal("dotpostconditions");
    public final Terminal dotpreconditions = new Terminal("dotpreconditions");
    public final Terminal dotspecend = new Terminal("dotspecend");
    public final Terminal procdot = new Terminal("procdot");
    public final Terminal dotnull = new Terminal("dotnull");
    public final Terminal dotifelsedelimiter = new Terminal("dotifelsedelimiter");

    public final NonTerminal ntProgram = new NonTerminal("Program");
    public final NonTerminal ntDecl = new NonTerminal("Decl");
    public final NonTerminal ntDeclList = new NonTerminal("DeclList"); // for Decl*
    public final NonTerminal ntAxiomDecl = new NonTerminal("AxiomDecl");
    public final NonTerminal ntConstantDecl = new NonTerminal("ConstantDecl");
    public final NonTerminal ntFunctionDecl = new NonTerminal("ntFunctionDecl");
    public final NonTerminal ntIdType = new NonTerminal("IdType");
    public final NonTerminal ntIdTypeCommaList = new NonTerminal("ntIdTypeCommaList");
    public final NonTerminal ntIdCommaList = new NonTerminal("ntIdCommaList");
    public final NonTerminal ntVarDecl = new NonTerminal("VarDecl");
    
    // public final NonTerminal ntVarDecls = new NonTerminal("VarDecls");
    public final NonTerminal ntProcedureDecl = new NonTerminal("ProcedureDecl");
    public final NonTerminal ntFSig= new NonTerminal("FSig");
    public final NonTerminal ntFArg = new NonTerminal("FArg");
    public final NonTerminal ntFArgList = new NonTerminal("FArgList");
    public final NonTerminal ntPSig = new NonTerminal("PSig");
    public final NonTerminal ntOutParameters = new NonTerminal("OutParameters");
    public final NonTerminal ntSpec = new NonTerminal("Spec");
    public final NonTerminal ntType = new NonTerminal("Type");
    public final NonTerminal ntTypeAtom = new NonTerminal("TypeAtom");
    public final NonTerminal ntMapType = new NonTerminal("MapType");
    public final NonTerminal ntBody = new NonTerminal("Body");
    public final NonTerminal ntLocalVarDecl = new NonTerminal("LocalVarDecl");
    public final NonTerminal ntLocalVarDeclList = new NonTerminal("LocalVarDeclList");
    public final NonTerminal ntStmtList = new NonTerminal("StmtList");
    public final NonTerminal ntStmt = new NonTerminal("Stmt");
    public final NonTerminal ntLhs = new NonTerminal("Lhs");
    public final NonTerminal ntCallLhs = new NonTerminal("CallLhs");
    public final NonTerminal ntBlockStmt = new NonTerminal("BlockStmt");
    public final NonTerminal ntIfStmt = new NonTerminal("IfStmt");
    public final NonTerminal ntElse = new NonTerminal("Else");
    public final NonTerminal ntLoopInv = new NonTerminal("LoopInv");
    public final NonTerminal ntExprCommaList = new NonTerminal("ExprCommaList");
    public final NonTerminal ntExpr = new NonTerminal("Expr");
    public final NonTerminal ntE0 = new NonTerminal("E0");
    public final NonTerminal ntE1 = new NonTerminal("E1");
    public final NonTerminal ntE2 = new NonTerminal("E2");
    public final NonTerminal ntE3 = new NonTerminal("E3");
 //   public final NonTerminal ntEOr = new NonTerminal("EOr");
    public final NonTerminal ntEOrList = new NonTerminal("EOrList");
    //public final NonTerminal ntEAnd = new NonTerminal("EAnd");
    public final NonTerminal ntEAndList = new NonTerminal("EAndList");
    public final NonTerminal ntE5 = new NonTerminal("E5");
    public final NonTerminal ntE6 = new NonTerminal("E6");
    public final NonTerminal ntE7 = new NonTerminal("E7");
    public final NonTerminal ntE8 = new NonTerminal("E8");
 //   public final NonTerminal ntMapOp = new NonTerminal("MapOp");
 //   public final NonTerminal ntMapOpList = new NonTerminal("MapOpList");
 //   public final NonTerminal ntMapUpdate = new NonTerminal("MapUpdate");
    public final NonTerminal ntE9 = new NonTerminal("E9");
    public final NonTerminal ntFuncApplication = new NonTerminal("FuncApplication");
    public final NonTerminal ntNumber = new NonTerminal("Number");
    public final NonTerminal ntEquivOp = new NonTerminal("EquivOp");
    public final NonTerminal ntImplOp = new NonTerminal("ImplOp");
    public final NonTerminal ntOrOp = new NonTerminal("OrOp");
    public final NonTerminal ntAndOp = new NonTerminal("AndOp");
    public final NonTerminal ntRelOp = new NonTerminal("RelOp");
    public final NonTerminal ntAddOp = new NonTerminal("AddOp");
    public final NonTerminal ntMulOp = new NonTerminal("MulOp");
    public final NonTerminal ntUnOpList = new NonTerminal("ntUnOpList");
    // public final NonTerminal ntUnOp = new NonTerminal("UnOp");
    public final NonTerminal ntQOp = new NonTerminal("QOp");
    public final NonTerminal ntQSep = new NonTerminal("QSep");
    public final NonTerminal ntTriggerList = new NonTerminal("TriggerList");
    public final NonTerminal ntTrigger = new NonTerminal("Trigger");
    public final NonTerminal ntIte = new NonTerminal("ITE");
    public final NonTerminal ntCallSpec = new NonTerminal("CallSpec");
    
    public final OperatorsGrammar grammar;
    
    public static BoogieGrammar getInstance() {
        return instance;
    }

    private BoogieGrammar(){
        super(); //??
        
        GrammarBuilderHelper gbh = new GrammarBuilderHelper();
        setupTerminals(gbh);
        setupNonTerminals(gbh);
        newSetupProductions(gbh);
        //gbh.setAxiom(ntProgram); // unsure about this
        this.grammar = gbh.getOperatorGrammar();
//        System.out.println("Grammar set up");
    }

    private void setupTerminals(GrammarBuilderHelper gbh) {
        gbh.addTerminals(leftParanthesis,rightParanthesis,leftSquarebracket,rightSquarebracket,semicolon, colon, 
                         comma, axiom, const_, var, function, leftCurlybracket, rightCurlybracket, procedure, returns, 
                         requires, modifies, ensures, assert_, assume_, havoc, call, while_, break_, return_, assign, if_, 
                         else_, invariant, true_, false_, equivop, implop, orop, andop, relopEq, relopNotEq, relopLessThan, 
                         relopGreaterThan, relopEqOrLess, relopEqOrGreater, addopPlus, addopMinus, mulopMul, mulopDiv, 
                         unop, qopForall, qopExists, qsep, id, int_, bool_, old, dot, e_id, typeflag_int, typeflag_boolean, 
                         exprid, exprJump0, exprJump1, exprJump2, exprJump3, exprJump5, exprJump6, exprJump7, exprJump8, 
                         dotIfStmt, itedot, itedotend, dotpostconditions, dotpreconditions, dotspecend, procdot, dotnull, dotifelsedelimiter, whiledot);
    }

    private void setupNonTerminals(GrammarBuilderHelper gbh) {
         gbh.addNonTerminals(ntProgram, 
            ntDecl, 
            ntDeclList, 
            ntAxiomDecl, 
            ntConstantDecl, 
            ntFunctionDecl, 
            ntIdType, 
            ntIdTypeCommaList, 
            ntVarDecl, 
            // ntVarDecls, 
            ntProcedureDecl, 
            ntFSig, 
            ntFArg, 
            ntFArgList, 
            ntPSig, 
            ntOutParameters, 
            ntSpec, 
            ntType, 
            ntTypeAtom, 
            ntMapType, 
            ntBody, 
            ntLocalVarDecl, 
            ntLocalVarDeclList, 
            ntStmtList, 
            ntStmt, 
            ntLhs, 
            ntCallLhs, 
            ntBlockStmt, 
            ntIfStmt, 
            ntElse, 
            ntLoopInv, 

            ntExprCommaList, 
            ntExpr, 
            ntE0, 
            ntE1, 
            ntE2, 
 //           ntEOr, 
            ntEOrList, 
 //           ntEAnd, 
            ntEAndList, 
            ntE3, 
            ntE5, 
            ntE6, 
            ntE7, 
            ntE8, 
 //           ntMapOp, 
 //           ntMapOpList, 
 //           ntMapUpdate, 
            ntE9, 
            ntFuncApplication, 
            ntNumber, 
            ntEquivOp, 
            ntImplOp, 
            ntOrOp, 
            ntAndOp, 
            ntRelOp, 
            ntAddOp, 
            ntMulOp, 
            // ntUnOp, 
            ntUnOpList, 
            ntQOp, 
            ntQSep, 
            ntTriggerList, 
            ntTrigger, 
            ntIdCommaList,
            ntIte,
            ntCallSpec
            );

    }

    private void newSetupProductions(GrammarBuilderHelper gbh) {
    	


//
//    constantDecl:
//    	'const' idType ';'
//    	| 'const' idType ';' declList
//    	;
//
//    idType:
//    	id_=id ':' type_=type;
//
//    functionDecl:
//    	'function' ID fSig '{' expr '}'
//    	| 'function' ID fSig '{' expr '}' declList
//    	;
//
//    fSig:
//    	'(' fArgList ')' 'returns' ')' fArg ')'
//    	| '(' ')' 'returns' '(' fArg ')'
//    	;
//
//    fArgList:
//    	fArgList ',' fArg
//    	| fArg;
//
//    fArg:
//    	idType;
//

//

//    	;
//

//

//    	
//    localVarDeclList:
//    	'var' idType
//    	| 'var' idType ';' localVarDeclList
//    	;
//
//    idTypeCommaList:
//    	idTypeCommaList ',' idType
//    	| idType;
//
//    	
//    idCommaList:
//    	idCommaList ',' id
//    	| id
//    	;
//
//    type:
//    	mapType
//    	| typeAtom
//    	;
//
//    typeAtom:
//    	'int' #TypeInt
//    	| 'bool' #TypeBool
//    	;
//    	
//    mapType:
//    	'[' 'int' ']' type
//    	;
//
//    exprCommaList: 
//    	| exprCommaList ',' expr
//    	| expr
//    	;

    	
    	
    	
    	
    	// Since we can not set up precedence levels of the same type, we need to 
    	// have every permutation it seems. Ie axiom needs to work for E1..E9, not
    	// just Expr, even though E1..E9 is derivated from Expr.
    	gbh.setAxiom(ntDecl);
    	// 	declList: axiomDecl | constantDecl | varDecl | functionDecl | procedureDecl
    	gbh.addProduction(ntDecl, ntAxiomDecl);
    	gbh.addProduction(ntDecl, ntVarDecl);
    	gbh.addProduction(ntDecl, ntProcedureDecl);
//    	gbh.addProduction(ntDecl, ntStmtList); // debug
    	

    	// axiomdecl ::=  "axiom" Expr ';' | "axiom" Expr ';' DeclList
    	gbh.addProduction(ntAxiomDecl, axiom, ntExpr, semicolon);
    	gbh.addProduction(ntAxiomDecl, axiom, ntExpr, semicolon, ntAxiomDecl);
    	gbh.addProduction(ntAxiomDecl, axiom, ntExpr, semicolon, ntConstantDecl);
    	gbh.addProduction(ntAxiomDecl, axiom, ntExpr, semicolon, ntVarDecl);
    	gbh.addProduction(ntAxiomDecl, axiom, ntExpr, semicolon, ntProcedureDecl);

    	// varDecl = "var" idType ';' | "var" idType ';' declList 
    	gbh.addProduction(ntVarDecl, var, ntIdType, semicolon);
    	gbh.addProduction(ntVarDecl, var, ntIdType, semicolon, ntAxiomDecl);
    	gbh.addProduction(ntVarDecl, var, ntIdType, semicolon, ntConstantDecl);
    	gbh.addProduction(ntVarDecl, var, ntIdType, semicolon, ntVarDecl);
    	gbh.addProduction(ntVarDecl, var, ntIdType, semicolon, ntFunctionDecl);
    	gbh.addProduction(ntVarDecl, var, ntIdType, semicolon, ntProcedureDecl);
    	
    	
    	gbh.addProduction(ntIdType, id, colon, int_); // todo: remove int and replace with real rule
    	gbh.addProduction(ntIdType, id, colon, bool_);
    	
    	// Outparameters have been inserted into PSig due to sidecar quirk where it had issues parsing both otherwise,
    	// A rightparanthesis was also added (not shown in commented grammar)
//      outParameters ::='returns' '(' idType  | 'returns' '(' 
//      pSig::= '(' idTypeCommaList ')' outParameters TODO not added 
    	// | '(' ')' outParameters
    	
    	gbh.addProduction(ntPSig, leftParanthesis, rightParanthesis, returns, leftParanthesis, rightParanthesis); // psig2
//    	gbh.addProduction(ntPSig, leftParanthesis, rightParanthesis, returns, leftParanthesis, ntIdType, rightParanthesis);
    	
    	
    	
    	// Procedure decl has been slightly changed from the antlr grammar listed bellow. The right parenthesis has been moved to
    	// outparameters due to quirk with sidecar and outparameters has then been removed and moved, in procedure 
    	// a dot has been placed between PSig and Spec nt's
//      procedureDecl:
//    	'procedure' ID pSig ')' specList '{' localVarDeclList ';' stmtList '}' #proc1
//    	| 'procedure' ID pSig ')' specList '{' localVarDeclList ';' stmtList '}' declList #proc2
//    	| 'procedure' ID pSig ')' specList '{' stmtList '}' #proc3
    	// With assignments and logic:
    	gbh.addProduction(ntProcedureDecl, procedure, id, ntPSig, dot, ntSpec, leftCurlybracket, ntStmtList, dot, ntStmtList, rightCurlybracket); // NB: dot added to separate nt and t
    	gbh.addProduction(ntProcedureDecl, procedure, id, ntPSig, dot, dotnull, leftCurlybracket, ntStmtList, dot, ntStmtList, rightCurlybracket);
    	gbh.addProduction(ntProcedureDecl, procedure, id, ntPSig, dot, ntSpec, leftCurlybracket, ntStmtList, dot, ntStmtList, rightCurlybracket);
    	gbh.addProduction(ntProcedureDecl, procedure, id, ntPSig, dot, dotnull, leftCurlybracket, ntStmtList, dot, ntStmtList, rightCurlybracket);
    	// With no assignments:
    	gbh.addProduction(ntProcedureDecl, procedure, id, ntPSig, dot, ntSpec, leftCurlybracket, dotnull, dot, ntStmtList, rightCurlybracket); 
    	gbh.addProduction(ntProcedureDecl, procedure, id, ntPSig, dot, dotnull, leftCurlybracket, dotnull, dot, ntStmtList, rightCurlybracket);
    	gbh.addProduction(ntProcedureDecl, procedure, id, ntPSig, dot, ntSpec, leftCurlybracket, dotnull, dot, ntStmtList, rightCurlybracket);
    	gbh.addProduction(ntProcedureDecl, procedure, id, ntPSig, dot, dotnull, leftCurlybracket, dotnull, dot, ntStmtList, rightCurlybracket);
    	// With no code other than assignments:
    	gbh.addProduction(ntProcedureDecl, procedure, id, ntPSig, dot, ntSpec, leftCurlybracket, ntStmtList, dot, dotnull, rightCurlybracket); // NB: dot added to separate nt and t
    	gbh.addProduction(ntProcedureDecl, procedure, id, ntPSig, dot, dotnull, leftCurlybracket, ntStmtList, dot, dotnull, rightCurlybracket);
    	gbh.addProduction(ntProcedureDecl, procedure, id, ntPSig, dot, ntSpec, leftCurlybracket, ntStmtList, dot, dotnull, rightCurlybracket);
    	gbh.addProduction(ntProcedureDecl, procedure, id, ntPSig, dot, dotnull, leftCurlybracket, ntStmtList, dot, dotnull, rightCurlybracket);
    	// We do not support empty procedures, so those where both are null are not considered
    	
    	// Make a copy of all above
    	gbh.addProduction(ntProcedureDecl, procedure, id, ntPSig, dot, dotnull, leftCurlybracket, ntStmtList, dot, ntStmtList, rightCurlybracket, ntProcedureDecl);
    	gbh.addProduction(ntProcedureDecl, procedure, id, ntPSig, dot, ntSpec, leftCurlybracket, ntStmtList, dot, ntStmtList, rightCurlybracket, ntProcedureDecl);
//    	| 'procedure' ID pSig ')' specList '{' stmtList '}' declList #proc4
//    	| 'procedure' ID pSig ')' '{' localVarDeclList ';' stmtList '}' #proc5
//    	| 'procedure' ID pSig ')' '{' localVarDeclList ';' stmtList '}' declList #proc6
//    	| 'procedure' ID pSig ')' '{' stmtList '}' #proc7
//    	| 'procedure' ID pSig ')' '{' stmtList '}' declList #proc8
//    	| 'procedure' ID pSig ')' '{'  '}' #proc9
    	
    	setupProductionStmt(gbh);
    	setupProductionSpec(gbh);
//    	gbh.addProduction(ntSpec, ensures, ntE3, semicolon, ntSpec);
//    	gbh.addProduction(ntSpec, modifies, id, semicolon);
//    	gbh.addProduction(ntE3, ntE9, relopEq, ntE9);

    	/*
    	 Expressions:
    	 */
    	setupProductionExpr(gbh);
    	
    	// E2 ::= E2 orop E3
    	
    }
    
    
    private void setupProductionStmt(GrammarBuilderHelper gbh) {
    	/* 
    	 TODO:
    	 Have not done havoc since I haven't done idCommaList yet
    	| 'havoc' idCommaList ';' #StmtHavoc
    	| 'havoc' idCommaList ';' stmtList #StmtHavocList
    	| 
    	 Have not done call since I haven't done exprCmmaList yet
    	| 'call' callLhs ID '(' exprCommaList ')' ';' #StmtCall1
    	| 'call' callLhs ID '(' exprCommaList ')' ';' stmtList #StmtCall1List
    	| 
    	| 'call' ID '(' exprCommaList ')' ';' stmtList #StmtCall2List

    	| 'while' '(' expr ')' loopInv '{' stmtList '}' #StmtWhile
    	| 'while' '(' expr ')' loopInv '{' stmtList '}' stmtList #StmtWhileList
    	| 'break' ';' #StmtBreak
    	| 'break' ';' stmtList #StmtBreakList
    	| 'return' ';' #StmtReturn
    	| 'return' ';' stmtList #StmtReturnList
    	*/
    	// 'assert' expr ';' #StmtAssert
    	gbh.addProduction(ntStmtList, assert_, ntExpr, semicolon);
    	
    	// 'assert' expr ';' stmtList #StmtAssertList
    	gbh.addProduction(ntStmtList, assert_, ntExpr, semicolon, ntStmtList);
    	
    	// 'assume' expr ';' #StmtAssume
    	gbh.addProduction(ntStmtList, assume_, ntExpr, semicolon);
    	
    	// 'assume' expr ';' stmtList #StmtAssumeList
    	gbh.addProduction(ntStmtList, assume_, ntExpr, semicolon, ntStmtList);
    	
    	//Havoc
    	gbh.addProduction(ntStmtList, havoc, ntIdCommaList, semicolon);
    	gbh.addProduction(ntStmtList, havoc, ntIdCommaList, semicolon, ntStmtList);
    	
    	//IdCommaList
    	gbh.addProduction(ntIdCommaList, ntIdCommaList, comma, id);
    	gbh.addProduction(ntIdCommaList, id);
    	
    	
    	// lhs ':=' expr ';' #StmtAssign
    	gbh.addProduction(ntStmtList, ntLhs, assign, ntExpr, semicolon);
    	
    	// lhs ':=' expr ';' stmtList #StmtAssignList
    	gbh.addProduction(ntStmtList, ntLhs, assign, ntExpr, semicolon, ntStmtList);
    	
    	gbh.addProduction(ntStmtList,  ntLhs, assign, ntIte, semicolon);
    	gbh.addProduction(ntStmtList,  ntLhs, assign, ntIte, semicolon, ntStmtList);
    	
    	// Test thing for dealing with branches:
    	gbh.addProduction(ntIte, itedot, ntExpr, id, id, itedotend);
    	gbh.addProduction(ntIte, itedot, ntExpr, id, ntExpr, itedotend); // Probably add so either can be expressions or id as well
    	
    	// LHS ::= id '[' expr ']' |id
    	gbh.addProduction(ntLhs, typeflag_int, id);
    	gbh.addProduction(ntLhs, typeflag_boolean, id);
    	gbh.addProduction(ntLhs, typeflag_int, leftSquarebracket, ntExpr, rightSquarebracket);
    	gbh.addProduction(ntLhs, typeflag_boolean, leftSquarebracket, ntExpr, rightSquarebracket);
    	
    	// ifStmt #StmtIf
    	gbh.addProduction(ntStmtList, dotIfStmt, ntIfStmt);
    	
//    	gbh.addProduction(ntIfStmt, if_, leftParanthesis, ntExpr, rightParanthesis, leftCurlybracket, ntStmtList, dotifelsedelimiter, ntStmtList, rightCurlybracket); // for else-cases
    	// Modification: 
// Commented out version, trying new version
    	// 'if' '(' expr ')' '{' stmtList '}' #StmtIf1
    	gbh.addProduction(ntIfStmt, if_, leftParanthesis, ntExpr, rightParanthesis, leftCurlybracket, ntStmtList, rightCurlybracket);
    	gbh.addProduction(ntIfStmt, if_, leftParanthesis, ntExpr, rightParanthesis, leftCurlybracket, dotnull, rightCurlybracket);
  	
    	// 'if' '(' expr ')' '{' stmtList '}' else_ #StmtIf2
    	// NOT TESTED PROPERLY!!!! TODO
    	gbh.addProduction(ntIfStmt, if_, leftParanthesis, ntExpr, rightParanthesis, leftCurlybracket, ntStmtList, rightCurlybracket, else_, leftCurlybracket, ntStmtList, rightCurlybracket);
    	gbh.addProduction(ntIfStmt, if_, leftParanthesis, ntExpr, rightParanthesis, leftCurlybracket, ntStmtList, rightCurlybracket, else_, leftCurlybracket, ntStmtList, rightCurlybracket, ntStmtList);
    	// Empty true
    	gbh.addProduction(ntIfStmt, if_, leftParanthesis, ntExpr, rightParanthesis, leftCurlybracket, dotnull, rightCurlybracket, else_, leftCurlybracket, ntStmtList, rightCurlybracket);
    	gbh.addProduction(ntIfStmt, if_, leftParanthesis, ntExpr, rightParanthesis, leftCurlybracket, ntStmtList, rightCurlybracket, else_, leftCurlybracket, dotnull, rightCurlybracket, ntStmtList);
    	gbh.addProduction(ntIfStmt, if_, leftParanthesis, ntExpr, rightParanthesis, leftCurlybracket, dotnull, rightCurlybracket, else_, leftCurlybracket, dotnull, rightCurlybracket, ntStmtList);
    	
    	// 'if' '(' expr ')' '{' stmtList '}' stmtList #StmtIf3
    	gbh.addProduction(ntIfStmt, if_, leftParanthesis, ntExpr, rightParanthesis, leftCurlybracket, ntStmtList, rightCurlybracket, ntStmtList);
    	gbh.addProduction(ntIfStmt, if_, leftParanthesis, ntExpr, rightParanthesis, leftCurlybracket, dotnull, rightCurlybracket, ntStmtList);

       	/*      	
    	// 'else' ifStmt #else1
    	gbh.addProduction(ntElse, else_, ntIfStmt); // might need Q after
    	// 'else' '{' stmtList '}' #else2
    	gbh.addProduction(ntElse, else_, leftCurlybracket, ntStmtList, rightCurlybracket);
    	// 'else' '{' stmtList '}' stmtList #else3
    	gbh.addProduction(ntElse, else_, leftCurlybracket, ntStmtList, rightCurlybracket, ntStmtList);
    	    	
    	 */
    	// While
    	gbh.addProduction(ntStmtList, while_, ntLoopInv, leftParanthesis, ntExpr, rightParanthesis, ntLoopInv, leftCurlybracket, ntStmtList, rightCurlybracket, ntLoopInv, whiledot, ntLoopInv, whiledot, ntExpr);
    	gbh.addProduction(ntStmtList, while_, ntLoopInv, leftParanthesis, ntExpr, rightParanthesis, ntLoopInv, leftCurlybracket, ntStmtList, rightCurlybracket, ntLoopInv, whiledot, ntLoopInv, whiledot, ntExpr, whiledot, ntStmtList);
    	//Empty/Only assign bodies
    	gbh.addProduction(ntStmtList, while_, ntLoopInv, leftParanthesis, ntExpr, rightParanthesis, ntLoopInv, leftCurlybracket, dotnull, rightCurlybracket, ntLoopInv, whiledot, ntLoopInv, whiledot, ntExpr);
    	gbh.addProduction(ntStmtList, while_, ntLoopInv, leftParanthesis, ntExpr, rightParanthesis, ntLoopInv, leftCurlybracket, dotnull, rightCurlybracket, ntLoopInv, whiledot, ntLoopInv, whiledot, ntExpr, whiledot, ntStmtList);
    	//LoopInv
    	gbh.addProduction(ntLoopInv, invariant, ntExpr, semicolon);
   	
    	
    	// Calls:
    	// In these rules the terminal call is added twice, this is due to some unknown reduction error in sidecar
    	// 'call' ID '(' exprCommaList ')' ';' #StmtCall2
    	gbh.addProduction(ntStmtList, call, id, call, leftParanthesis, rightParanthesis, ntCallSpec, dotspecend); // todo: add ntExprCommaList
    	gbh.addProduction(ntStmtList, call, id, call, leftParanthesis, rightParanthesis, ntCallSpec, dotspecend, ntStmtList); // todo: add ntExprCommaList
    	gbh.addProduction(ntCallSpec, dotpreconditions, ntSpec, dotpostconditions, ntSpec);
    	gbh.addProduction(ntCallSpec, dotpreconditions, dotnull, dotpostconditions, ntSpec);
    	gbh.addProduction(ntCallSpec, dotpreconditions, ntSpec, dotpostconditions, dotnull);
    	gbh.addProduction(ntCallSpec, dotpreconditions, dotnull, dotpostconditions, dotnull);
    }
    
    private void setupProductionExpr(GrammarBuilderHelper gbh) {
    	gbh.addProduction(ntExpr, exprid, ntE0);
    	// e0: e1 	| e1 '<==>' e0 
    	gbh.addProduction(ntE0, exprJump0, ntE1);
    	gbh.addProduction(ntE0, ntE1, equivop, ntE0);

    	// e1: e2 | e2 '==>' e1 #EImpl
    	gbh.addProduction(ntE1, exprJump1, ntE2);
    	gbh.addProduction(ntE1, ntE2, implop, ntE1);

    	// e2: e3 | e2 '||' e3 #EOr | e2 '&&' e3  #EAnd
    	gbh.addProduction(ntE2, exprJump2, ntE3);
    	gbh.addProduction(ntE2, ntE2, orop, ntE3);
    	gbh.addProduction(ntE2, ntE2, andop, ntE3);
    	
    	
    	
    	
    	
    	

		//  e3: e5 #E3toE5
		//    		| left=e5 '==' right=e5 #EEq
		//    		| left=e5 '<' right=e5 #ELt
		//    		| left=e5  '>' right=e5 #EGt
		//    		| left=e5 '!=' right=e5 #ENotEq
		//    		| left=e5'<=' right=e5 #EqLt
		//    		| left=e5 '>=' right=e5  #EqGt
    	gbh.addProduction(ntE3, exprJump3, ntE5);
    	gbh.addProduction(ntE3, ntE5, relopEq, ntE5);
    	gbh.addProduction(ntE3, ntE5, relopLessThan, ntE5);
    	gbh.addProduction(ntE3, ntE5, relopGreaterThan, ntE5);
    	gbh.addProduction(ntE3, ntE5, relopNotEq, ntE5);
    	gbh.addProduction(ntE3, ntE5, relopEqOrLess, ntE5);
    	gbh.addProduction(ntE3, ntE5, relopEqOrGreater, ntE5);
		
		// e5: e6 #E5toE6 | e5 '+' e6 #EAdd | e5 '-' e6 #ESub
    	gbh.addProduction(ntE5, exprJump5, ntE6);
    	gbh.addProduction(ntE5, ntE5, addopPlus, ntE6);
    	gbh.addProduction(ntE5, ntE5, addopMinus, ntE6);
    	
    	// e6: e7 #E6toE7 | e6 '*' e7 #EMul | e6 '/' e7 #EDiv
    	gbh.addProduction(ntE6, exprJump6, ntE7);
    	gbh.addProduction(ntE6, ntE6, mulopMul, ntE7);
    	gbh.addProduction(ntE6, ntE6, mulopDiv, ntE7);
    	
		// e7:e8 #E7toE8 | '!' e8 #ENot
    	gbh.addProduction(ntE7, exprJump7, ntE8);
    	gbh.addProduction(ntE7, unop, ntE8);
		
		// e8: e9  #E8toE9
		//    		| e9 '[' expr ':=' expr ']' #EArray1
		//    		| e9 '[' expr ']'  #EArray2
    	gbh.addProduction(ntE8, exprJump8, ntE9);
    	gbh.addProduction(ntE8, ntE9, leftSquarebracket, ntExpr, assign, ntExpr, rightSquarebracket);
    	gbh.addProduction(ntE8, ntE9, leftSquarebracket, ntExpr, rightSquarebracket);

		// e9:'false' #EFalse
		//    		| 'true' #ETrue
		//    		| integer #EInt
		//    		| id #EId
		//    		| id funcApplication #EIdFuncApp
		//    		| 'old' '(' expr ')' #EOld
		//    		| '(' 'forall' idTypeCommaList '::' expr ')' #EForall
		//    		| '(' 'exists' idTypeCommaList '::' expr ')' #EExists
		//    		| '(' 'forall' idTypeCommaList '::' triggerList '}' expr ')' #EForAllTrigger
		//    		| '(' 'exists' idTypeCommaList '::' triggerList '}' expr ')' #EExistsTrigger
    	gbh.addProduction(ntE9, false_);
    	gbh.addProduction(ntE9, true_);
    	gbh.addProduction(ntE9, int_);
    	gbh.addProduction(ntE9, typeflag_int, e_id);
    	gbh.addProduction(ntE9, typeflag_boolean, e_id);
    	gbh.addProduction(ntE9, leftParanthesis, ntExpr, rightParanthesis);
    	gbh.addProduction(ntE9, old, leftParanthesis, typeflag_int, e_id, rightParanthesis);
    	gbh.addProduction(ntE9, old, leftParanthesis, typeflag_boolean, e_id, rightParanthesis);
    	
    }    

    
    private void setupProductionSpec(GrammarBuilderHelper gbh) {
		// spec ::= 'requires' expr '; #SpecRequire
		gbh.addProduction(ntSpec, requires, ntExpr, semicolon);
		// spect ::= 'requires' expr ';'specList #SpecRequiresList
		gbh.addProduction(ntSpec, requires, ntExpr, semicolon, ntSpec);
		// 'modifies' ID ';' #SpecModifies
		gbh.addProduction(ntSpec, modifies, id, semicolon);
		// 'modifies' ID ';' specList #SpecModifiesList
		gbh.addProduction(ntSpec, modifies, id, semicolon, ntSpec);
		// 'ensures' expr ';' specList #SpecEnsuresList
		gbh.addProduction(ntSpec, ensures, ntExpr, semicolon);
		// 'ensures' expr ';' specList #SpecEnsuresList
		gbh.addProduction(ntSpec, ensures, ntExpr, semicolon, ntSpec);
		
    }
    
    public final OperatorsGrammar getGrammar() {
        return grammar;
    }
}
