grammar Boogie;


program:   declList
	// specList
	//lhs ':=' expr ';'
	;

declList: axiomDecl
	| constantDecl
	| varDecl
	| functionDecl
	| procedureDecl
	;

axiomDecl: 
	'axiom' expr ';' #axiom
	| 'axiom' e=expr ';' list=declList #axiomList
	;

constantDecl:
	'const' idType ';'
	| 'const' idType ';' declList
	;

idType:
	id_=id ':' type_=type;

functionDecl:
	'function' ID fSig '{' expr '}'
	| 'function' ID fSig '{' expr '}' declList
	;

fSig:
	'(' fArgList ')' 'returns' ')' fArg ')'
	| '(' ')' 'returns' '(' fArg ')'
	;

fArgList:
	fArgList ',' fArg
	| fArg;

fArg:
	idType;

varDecl: 
	'var' idt=idType ';' #varDecl_ 
	| 'var' idt=idType ';' list=declList #varDeclList
	; 

procedureDecl:
	'procedure' id_=ID pSig ')' specList '{' localVarDeclList ';' stmtList '}' 
	| 'procedure' id_=ID pSig ')' specList '{' localVarDeclList ';' stmtList '}' declList 
	| 'procedure' id_=ID pSig ')' specList '{' stmtList '}'
	| 'procedure' id_=ID pSig ')' specList '{' stmtList '}' declList 
	| 'procedure' id_=ID pSig ')' '{' localVarDeclList ';' stmtList '}' 
	| 'procedure' id_=ID pSig ')' '{' localVarDeclList ';' stmtList '}' declList
	| 'procedure' id_=ID pSig ')' '{' stmtList '}' 
	| 'procedure' id_=ID pSig ')' '{' stmtList '}' declList 
	| 'procedure' id_=ID pSig ')' '{'  '}'
	;

pSig: 
	'(' idTypeCommaList ')' outParameters #psig1
	| '(' ')' outParameters #psig2
	;

outParameters:
	'returns' '(' idType #outparameters1 
	| 'returns' '('  #outparameters2
	;
	
localVarDeclList:
	'var' idType
	| 'var' idType ';' localVarDeclList
	;

idTypeCommaList:
	idTypeCommaList ',' idType
	| idType;

specList:
	'requires' expr ';' #SpecRequires
	| 'requires' expr ';'specList #SpecRequiresList
	| 'modifies' ID ';' #SpecModifies
	| 'modifies' ID ';' specList #SpecModifiesList
	| 'ensures' expr ';' #SpecEnsures
	| 'ensures' expr ';' specList #SpecEnsuresList
	;
	
idCommaList:
	idCommaList ',' id 
	| id 
	;

type:
	mapType
	| typeAtom
	;

typeAtom:
	'int' #TypeInt
	| 'bool' #TypeBool
	;
	
mapType:
	'[' 'int' ']' type
	;

exprCommaList: 
	expr ',' exprCommaList
	| expr
	;
expr: e0 
;

stmtList:
	'assert' expr ';' #StmtAssert
	| 'assert' expr ';' stmtList #StmtAssertList
	| 'assume' expr ';' #StmtAssume
	| 'assume' expr ';' stmtList #StmtAssumeList
	| 'havoc' idCommaList ';' #StmtHavoc
	| 'havoc' idCommaList ';' stmtList #StmtHavocList
	| lhs ':=' expr ';' #StmtAssign
	| lhs ':=' expr ';' stmtList #StmtAssignList
	| callStmt #StmtCall
	| ifStmt #StmtIf
	| 'while' '(' expr ')' loopInv '{' stmtList '}' #StmtWhile
	| 'while' '(' expr ')' loopInv '{' stmtList1=stmtList '}' stmtList2=stmtList #StmtWhileList
	| 'break' ';' #StmtBreak
	| 'break' ';' stmtList #StmtBreakList
	| 'return' ';' #StmtReturn
	| 'return' ';' stmtList #StmtReturnList
	;

callStmt:
	'call' assign=id ':=' ID '(' exprCommaList ')' ';' 
	| 'call' assign=id ':=' ID '(' exprCommaList ')' ';' stmtList 
	| 'call' ID '(' exprCommaList ')' ';'
	| 'call' ID '(' exprCommaList ')' ';' stmtList 
	| 'call' assign=id ':=' ID '('  ')' ';' 
	| 'call' assign=id ':=' ID '('  ')' ';' stmtList 
	| 'call' ID '('  ')' ';'
	| 'call' ID '('  ')' ';' stmtList 
	;


lhs:
	id '[' expr ']' #LhsArray
	| id #LhsId
	;


ifStmt: 
	'if' '(' expr ')' '{' stmtList '}' #StmtIf1
	| 'if' '(' expr ')' '{' stmt1=stmtList '}' 'else' '{' stmt2=stmtList '}' #StmtIf2Else1
	| 'if' '(' expr ')' '{' stmt1=stmtList '}'  'else' '{' stmt2=stmtList '}' stmt3=stmtList #StmtIf2Else2
	| 'if' '(' expr ')' '{' stmt1=stmtList '}' stmt2=stmtList #StmtIf3
	;



loopInv: 
	'invariant' expr ';'
	;

// Expr: 
// E0;
/*
expr:
	 expr '<==>' expr
	| expr '==>' expr
	| expr '||' expr 
	| expr '&&' expr 
	| expr '==' expr
	| expr '<' expr
	| expr '>' expr
	| expr '!=' expr
	| expr'<=' expr
	| expr '>=' expr 
	| expr '+' expr
	| expr '-' expr
	| expr '*' expr
	| expr '/' expr
	| '!' expr
	| e9 '[' expr ':=' expr ']'
	| e9 '[' expr ']' 
	 ; */
e0:
	e1	#E0toE1
	| left=e1 '<==>' right=e0 #EEquiv
	;

e1:
	e2	#E1toE2
	| left=e2 '==>' right=e1 #EImpl
	;

e2: 
	e3 #E2toE3
	| left=e2 '||' right=e3 #EOr 
	| left=e2 '&&' right=e3  #EAnd
	;

e3:
	e5 #E3toE5
	| left=e5 '==' right=e5 #EEq
	| left=e5 '<' right=e5 #ELt
	| left=e5  '>' right=e5 #EGt
	| left=e5 '!=' right=e5 #ENotEq
	| left=e5'<=' right=e5 #EEqLt
	| left=e5 '>=' right=e5  #EEqGt
	;

e5:
	e6 #E5toE6
	| e5 '+' e6 #EAdd
	| e5 '-' e6 #ESub
	;

e6:
	e7 #E6toE7
	| e6 '*' e7 #EMul
	| e6 '/' e7 #EDiv
	;

e7: 
	e8 #E7toE8
	| '!' e8 #ENot
	;

e8:
	e9  #E8toE9
	| e9 '[' firstExpr=expr ':=' secondExpr=expr ']' #EArray1
	| e9 '[' expr ']'  #EArray2
	;

e9:
	'false'	#EFalse
	| 'true' #ETrue
	| val=integer #EInt
	| id #EId
	| id funcApplication #EIdFuncApp
	| 'old' '(' expr ')' #EOld
	| '(' 'forall' idTypeCommaList '::' expr ')' #EForall
	| '(' 'exists' idTypeCommaList '::' expr ')' #EExists
	| '(' 'forall' idTypeCommaList '::' triggerList '}' expr ')' #EForAllTrigger
	| '(' 'exists' idTypeCommaList '::' triggerList '}' expr ')' #EExistsTrigger
	| '(' expr ')' #EParanthesis
	;

triggerList:
	'{' exprCommaList
	| '{' exprCommaList '}' triggerList  
	; 

funcApplication:
	'(' exprCommaList ')' #funcAppExpr
	| '(' ')' #funcAppEmpty
	;

id:
	id_=ID
	;



integer: DIGIT+;

DIGIT: ('0'..'9');
 // temp
ID : [a-zA-Z]+ ;             // match lower-case identifiers
WS : [ \t\r\n]+ -> skip ; // skip spaces, tabs, newlines
LineComment: 
	'//' ~[\r\n]* -> skip
	;
BlockComment:
	'/*' .*? '*/' -> skip
;
/*


r  : 'hello' ID ;         // match keyword hello followed by an identifier


 */
