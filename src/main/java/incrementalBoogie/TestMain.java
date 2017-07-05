package incrementalBoogie;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.Solver;

import difflib.DiffUtils;
import difflib.Patch;
import grammar.BoogieASTVisitor;
import grammar.BoogieASTVisitorInfoCollector;
import grammar.BoogieGrammar;
import grammar.ConcreteInput;
import grammar.ProcInfo;
import incrementalBoogie.grammar.BoogieLexer;
import incrementalBoogie.grammar.BoogieParser;
import parser.IncrementalParser;
import utils.ProcedureSolver;

public class TestMain {
	
	
	
	private class CodeVersion {
		private int versionNumber = -1;
		private HashSet<String> registeredMethods = new HashSet<String>();
		private List<ConcreteInput> sourceCode;
		
		CodeVersion(int versionNumber, String programCode) {
			this.versionNumber = versionNumber;
			sourceCode = generateSource(programCode);
		}
		
		private void printSource(){
			System.out.println("===================================================================");
			System.out.println("Source program version: "+versionNumber);
			System.out.println(sourceCode);
			System.out.println("===================================================================");
		}
		
		private void addProcedure(String method){
			registeredMethods.add(method);
		}
	}
	public static String version = "version0";
	public static BoogieASTVisitor astWalker = new BoogieASTVisitor();
	public static BoogieASTVisitorInfoCollector astInfoCollector = new BoogieASTVisitorInfoCollector();
	public static Context context = new Context();
	
	public static Solver solver; // probably move to nonstatic later
	public static boolean DEBUG = true;
	public static ArrayList<HashSet<String>> prevVersionModified = new ArrayList<HashSet<String>>();
	public static HashSet<String> currVersionModified = new HashSet<String>();
	
	
	

	private int currentIteration;
	private static ArrayList<CodeVersion> allCodeIterations;
	private IncrementalParser parser;
	private BoogieSidecarVisitor sidecarVisitor;
	public HashMap<String, ProcedureSolver> solvers = new HashMap<String, ProcedureSolver>();
	
	public TestMain() {
		currentIteration = -1;
		allCodeIterations = new ArrayList<CodeVersion>();
		
		BoogieGrammar.getInstance(); // not sure if needed
		parser = new IncrementalParser(BoogieGrammar.getInstance().getGrammar());
		sidecarVisitor = BoogieSidecarVisitor.newVisitor();
		sidecarVisitor.testSuite = this;
		parser.addSemanticVisitor(sidecarVisitor);
	}
	
	private BoolExpr[] getVersionImplications(int iteration) {
		ArrayList<BoolExpr> tempList = new ArrayList<BoolExpr>();
		

		for(int i=0;i<allCodeIterations.size()-1;i++) {
			tempList.add(context.mkNot(context.mkBoolConst("version"+i)));
		}
		tempList.add(context.mkBoolConst("version"+currentIteration));
		//TODO: Move the current part out to its own method so it can loop through the methods instead of verifying all of them.
		for(String procedure : allCodeIterations.get(currentIteration).registeredMethods) {
			tempList.add(context.mkBoolConst("proc_"+procedure));
		}
		return tempList.toArray(new BoolExpr[tempList.size()]);
	}
	
	public void registerProcedure(String method) {
		allCodeIterations.get(currentIteration).addProcedure(method);
	}
	
	
	public String getVersion() {
		return "version"+currentIteration;
	}
	
	public static List<ConcreteInput> generateSource(String input){

		ANTLRInputStream inputStream = new ANTLRInputStream(input);
		BoogieLexer lexer = new BoogieLexer(inputStream);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		BoogieParser parser = new BoogieParser(tokens);
		ParseTree tree = parser.program(); // begin parsing at rule 'r'
		if(DEBUG){
			System.out.println("------------PARSE TREE-------------");
			System.out.println(tree.toStringTree(parser)); // print LISP-style tree
			System.out.println("------------/PARSE TREE------------");
		}
		astInfoCollector.visit(tree);
		return astWalker.visit(tree);
	}
	
	
	private static void printSource(List<ConcreteInput> source) {
		System.out.println("-------");
		if(source == null) {
			System.out.println("Source code null");
		} else {
			System.out.println("Source code length: "+source.size());
			System.out.println(source); 
		}
		System.out.println("-------");
	}
	
	private CodeVersion newIteration(String program) {

		// Add in everything and patch
		currentIteration++;
		if(DEBUG) {
			System.out.println("New iteration started: "+currentIteration);
		}
		CodeVersion version = new CodeVersion(currentIteration, program);
		if(astWalker.parseError) {
			System.out.println("Error in ASTVisitor");
			return null;
		}
		allCodeIterations.add(version);
		
		
		for(ProcInfo proc : astInfoCollector.procs.values()) {
			if(!solvers.containsKey(proc.procName)) {
				solvers.put(proc.procName, new ProcedureSolver(proc.procName));
			}
			// Todo: do something here that removes old procedures as well
		}
		
		Patch<ConcreteInput> patch;
		if(currentIteration == 0) {
			patch = generatePatch(null, version);
		} else {
			patch = generatePatch(allCodeIterations.get(currentIteration-1), version);
		}
		parser.edit(patch);
		if(DEBUG) {
//			System.out.println(solver.toString());
		}
		boolean programValid = true;
		for(ProcedureSolver solver : solvers.values()) {
			if(solver.hasBeenChanged()) {
				programValid &= solver.solve(DEBUG);
			}
		}
		System.out.println("Program is "+ (programValid ? "VALID" : "INVALID"));
		System.out.println();
		
		

//		// Solve:
//		BoolExpr[] assumptions = getVersionImplications(currentIteration);
//		if(DEBUG) {
//			System.out.print("Using the following assumptions: ");
//			for(BoolExpr foo : assumptions) {
//				System.out.print(" "+foo);
//			}
//			System.out.println();
//		}
//		
//		System.out.print("Solving version: "+currentIteration);
//		long stop, start = System.nanoTime();
//		if(solver.check(assumptions) == Status.SATISFIABLE) {
//			stop = System.nanoTime();
//			System.out.print(" Result: INVALID");
//		} else {
//			stop = System.nanoTime();
//			System.out.print(" Result: VALID");
//		}
//		System.out.println(" result time: "+(stop-start));
//		System.out.println("Nodes visited: "+sidecarVisitor.nodesVisited);
//
//		sidecarVisitor.nodesVisited = 0;
//		solver.check();
		
		return version;
	}

	private void run(String[] programs) {

		
		for(int i=0;i<programs.length;i++){
			if(programs[i].startsWith("--")) {
				continue;
			}
			String program = "";
			Scanner scanner;
			try {
				scanner = new Scanner(new File(programs[i]));
				while(scanner.hasNextLine()) {
					program += scanner.nextLine() + '\n';
				}
			} catch (FileNotFoundException e) {
				System.out.println("File not found: "+programs[i]);
				e.printStackTrace();
			}

//			System.out.println(program);
			newIteration(program);
		}
		
//		 Used for testing empty programs: newIteration("var x : int; var y : int; var z : int; procedure P() returns ()  modifies z; { x := 3;  }");
//		newIteration("var x : int; var y : int; var z : int; procedure P() returns () ensures x == 5;  modifies x; { x:=1;if(x == 2) {  x := 5; } else { x := 5;}}");
//		newIteration("var x : int; var y : int; var z : int; procedure FOO() returns ()  modifies z; {  x := 3; havoc x;  }");
		
		// Setup:
//		newIteration("var x : int; var y : int; var z : int; procedure P(i : int, k : bool) returns () ensures x == 1;  { x := 222+2000;  call x:= Q(3); x := x-3;} procedure Q(arg : int) returns (out : int) ensures out == 4; { x := arg; } ");
//		newIteration("axiom 1234;").printSource();
//		newIteration("axiom 12345;").printSource();
//		newIteration("var x : int; var y : int; var z : int; procedure P() returns () ensures y == 1; modifies z; { x := 3; y := 3; z := 9; }");

//		printSource(newIteration("var x : int; var y : int; var z : int; procedure P() returns () ensures x == 1;  { x := 1; x := 3; x:=9; }").sourceCode);
//		newIteration("var x : int; var y : int; var z : int; procedure P() returns ()  modifies x; { assert x == 3;  z:=3;}");
//		newIteration("var x : int; var y : int; var z : int; procedure P() returns () ensures x == 1; modifies x; { x := 1; x := 3; x:=9;  x := 1; if (x == 1) { assert 1 == 1;  } }");
//		newIteration("var x : int; var y : int; var z : int; procedure P() returns () ensures x == 999;  {x := 1; if(x == 1) { x := 3; if(x == 3) { x := 555; if (x == 555) { x:=999; } else { x := 100;} z := 0;}  else { x := 777;} z := 333;} else { x := 4;  z:= 999;} z:= 44;}");
//		newIteration("var x : int; var y : int; var z : int; procedure P() returns () ensures x == 1;  { if(x == 1) { x := 3; if(x == 1) { x := 555;} else { x := 777;} z := 333;} else { x := 1;  z:= 999;} z:= 44;}");
		// Working if-else: newIteration("var x : int; var y : int; var z : int; procedure P(i : int, k : bool) returns () ensures x == 1;  { if(1==1) { x := 3; }  else { x := 3; } } ");
//		newIteration("var x : int; var y : int; var z : int; procedure P(i : int, k : bool) returns () ensures x == 1;  { if(1==2) { x := 3; } z := 99; if(1==1) { x := 1; } } ");
//		newIteration("var x : int; var y : int; var z : int; procedure P(i : int, k : bool) returns () { if(1==1) {assert x==3;}} ");
//		newIteration("var x : int; var y : int; var z : int; procedure P(i : int, g:bool) returns () ensures x == 3;   { x := 222+2000; i := 3; z:=Q(x+1); x := z+x; } "
//				+ "procedure Q(foo : int) returns (rets : int ) requires foo == x+1; ensures x == 2; ensures rets == 1; { rets := 1; } ");
//		newIteration("var x : int; var y : int; var z : int; procedure P(i : int, g:bool) returns () ensures x == 3;  { x := 222+2000; i := 3; call Q(1,3,x+1); x:= Q(1,3,3+1); x:=1;} "
//				+ "procedure Q(arg : int, args: int, foo : int) returns (rets : int ) requires foo == x+1; ensures x == 4; { x := arg; } ");
//		newIteration("var x : int; var y : int; var z : int; procedure P(i : int, g:bool) returns () ensures x == 3;  { x := 222+2000; i := 3; call Q(1,3,1); x:= Q(1,3,1); x:=1;} procedure Q(arg : int, args: int, foo : int) returns (rets : int ) requires foo == 1; ensures x == 4; { x := arg; } ");
//		newIteration("var x : int; var y : int; var z : int; procedure P(i : int, g:bool) returns () ensures x == 1;  { x := 222+2000; i := 3; call Q(1,3,2); x:=1;} procedure Q(arg : int, args: int, foo : int) returns (rets : int ) requires foo == 2; ensures x == 4; { x := arg; } ");

//		newIteration("var x : int; var y : int; var z : int; procedure P(i : int, k : bool) returns () ensures x == 1;  { x := 222+2000;  call Q(1,3,x+1); } procedure Q(arg : int, args: int, foo : int) returns () requires foo == x+1; ensures x == 4; { x := arg; } ");
//		newIteration("var x : int; var y : int; var z : int; procedure P(i : int, kork : bool) returns ()  ensures x == 9+9; { call x := Q(99,2,77); call y := Q(33,2,77);   x := x + y;} procedure Q(arg : int, args: int, fooo : int) returns (rets : int) requires fooo == 77;  ensures rets == 9; { x := arg; } ");
		
//		newIteration("var x : int; var y : int; var z : int; procedure P(i : int, kork : bool) returns () ensures x == 1;  { x:=9; x := 222+2000;  call Q(1,3,x+1); } procedure Q(arg : int, args: int, fooo : int) returns () requires fooo == 77;  { x := arg; } ");
//		newIteration("var x : int; var y : int; var z : int; procedure P(i : int, kork : bool) returns () ensures x == 1;  { x := 222+2000;  call Q(1,3,x+1); } procedure Q(arg : int, args: int, fooo : int) returns () requires fooo == 77;  { x := arg; } ");
//		newIteration("var x : int;  procedure P(z:bool) returns () ensures x == 1;  { x := 1; }");
//		System.out.println(astInfoCollector.procs.size());
//		System.out.println(astInfoCollector.procs.get("P").getPostconditions().size());

		//Mul/divtest
//		newIteration("var x : int; var y : int; var z : int; procedure P() returns () ensures z == 25; {x := 5; y := 5; z := x * y;}"); //Sat
//		newIteration("var x : int; var y : int; var z : int; procedure P() returns () ensures z == 50; {x := 5; y := 5; z := x * x + y * 5;}"); //Sat
//		newIteration("var x : int; var y : int; var z : int; procedure P() returns () ensures z == 51; {x := 5; y := 5; z := x * x + y * 5 + 125 / 5 / 5 / x;}"); //Sat
//		newIteration("var x : int; var y : int; var z : int; procedure P() returns () ensures z == 15; {x := 5; y := 2; z:= x*y+x;}"); //Sat
//		newIteration("var x : int; var y : int; var z : int; procedure P() returns () ensures z == 35; {x := 5; y := 2; z:= x*y+x;}"); //Unsat
//		newIteration("var x : int; var y : int; var z : int; procedure P() returns () ensures z == 35; {x := 5; y := 2; z:= x*(y+x);}"); //Sat
//		newIteration("var x : int; var y : int; var z : int; procedure P() returns () ensures z == 15; {x := 5; y := 2; z:= x+y*x;}"); //Sat
//		newIteration("var x : int; var y : int; var z : int; procedure P() returns () ensures z == 35; {x := 5; y := 2; z:= x+y*x;}"); //Unsat
//		newIteration("var x : int; var y : int; var z : int; procedure P() returns () ensures z == 35; {x := 5; y := 2; z:= (x+y)*x;}"); //Sat
//		newIteration("var x : int; var y : int; var z : int; procedure P() returns () ensures z == 7; {x := 6; y := 2; z:= x/y+4;}"); //Sat
//		newIteration("var x : int; var y : int; var z : int; procedure P() returns () ensures z == 1; {x := 6; y := 2; z:= x/y+4;}"); //Unsat
//		newIteration("var x : int; var y : int; var z : int; procedure P() returns () ensures z == 1; {x := 6; y := 2; z:= x/(y+4);}"); //Sat
//		newIteration("var x : int; var y : int; var z : int; procedure P() returns () ensures z == 7; {x := 6; y := 2; z:= x+y/2;}"); //Sat
//		newIteration("var x : int; var y : int; var z : int; procedure P() returns () ensures z == 4; {x := 6; y := 2; z:= x+y/2;}"); //Unsat
//		newIteration("var x : int; var y : int; var z : int; procedure P() returns () ensures z == 4; {x := 6; y := 2; z:= (x+y)/2;}"); //Sat
		
		
		//BasicMathTests
//		newIteration("var x : int; var y : int; var z : int; procedure P() returns () ensures x == 35; ensures y == 0; {x := 10 + 5*5; y := 10 - 5*2; }"); //Sat
//		newIteration("var x : int; var y : int; var z : int; procedure P() returns () ensures x == 26; ensures y == 25; {x := 10 + 5*5; y := x - 5*2; x := y+5/5; }"); //Sat
//		newIteration("var x : int; var y : int; var z : int; procedure P() returns () ensures x == 20; ensures y == 49; {x := 5*5-5; y := x - 5*2 + 10/2 - 3*3/3 + x*2 - 3; }"); //Sat
//		newIteration("var x : int; var y : int; var z : int; procedure P() returns () ensures x == 4; ensures y == 13; {x := 10/5-5+7/7*7; y := x/x+x*x-x; }"); //Sat
						
		
		//RelOptests
//		newIteration("var x : int; var y : int; procedure P() returns () ensures x != y;{x := 5; y := 5; x := x + y;}"); //Sat
//		newIteration("var x : int; var y : int; procedure P() returns () ensures x != y;{x := 5; y := 5;}"); //UnSat
//		newIteration("var x : int; var y : int; procedure P() returns () ensures x != y;{x := 10; y := 5;}"); //Sat
//		newIteration("var x : int; var y : int; procedure P() returns () ensures y > x;{x := 10; y := 5;}"); //Unsat
//		newIteration("var x : int; var y : int; procedure P() returns () ensures x > y;{x := 10; y := 5;}"); //Sat
//		newIteration("var x : int; var y : int; procedure P() returns () ensures x < y;{x := 10; y := 5;}"); //Unsat
//		newIteration("var x : int; var y : int; procedure P() returns () ensures y < x;{x := 10; y := 5;}"); //Sat
//		newIteration("var x : int; var y : int; procedure P() returns () ensures y >= x;{x := 10; y := 5;}"); //Unsat
//		newIteration("var x : int; var y : int; procedure P() returns () ensures y >= x;{x := 5; y := 5;}"); //Sat
//		newIteration("var x : int; var y : int; procedure P() returns () ensures x >= y;{x := 5; y := 5;}"); //Sat
//		newIteration("var x : int; var y : int; procedure P() returns () ensures x >= y;{x := 10; y := 5;}"); //Sat
//		newIteration("var x : int; var y : int; procedure P() returns () ensures y <= x;{x := 10; y := 5;}"); //sat
//		newIteration("var x : int; var y : int; procedure P() returns () ensures x <= y;{x := 10; y := 5;}"); //Unsat
//		newIteration("var x : int; var y : int; procedure P() returns () ensures x <= y;{x := 5; y :=5;}"); //Sat
//		newIteration("var x : int; var y : int; procedure P() returns () ensures y <= x;{x := 5; y :=5;}"); //Sat
//		newIteration("var x : int; var y : int; var z : int; procedure P() returns () ensures x==z; ensures x!=y; ensures x <= z; ensures x >= z; ensures x > y; ensures y > z; ensures y<=z; ensures x>=y;  {x := 5; y:= 4; z :=5;}"); //Sat
//		newIteration("var x : int; var y : int; var z : int; procedure P() returns () ensures x==z; ensures x!=y; ensures x <= z; ensures x >= z; ensures x > y; ensures y < z; ensures y<=z; ensures x>=y;  {x := 5; y:= 4; z :=5;}"); //Sat
		
		//NotTest
//		newIteration("var x : int; var y : int; procedure P() returns () ensures !(x != y);{x := 5; y := 5; x := x + y;}"); //Unsat
//		newIteration("var x : int; var y : int; procedure P() returns () ensures !(x != y);{x := 5; y := 5;}"); //Sat
//		newIteration("var x : int; var y : int; procedure P() returns () ensures !(x != y);{x := 10; y := 5;}"); //Unsat
//		newIteration("var x : int; var y : int; procedure P() returns () ensures !(y > x);{x := 10; y := 5;}"); //Sat
		
		//And/OrTests
//		newIteration("var x : int; var y : int; var z : int; procedure P() returns () ensures x==z && x != y && x <= z && x >= z && x > y && y > z && y <= z && x >= y;  {x := 5; y:= 4; z :=5;}"); //Sat
//		newIteration("var x : int; var y : int; var z : int; procedure P() returns () ensures x==z && x!=y && x <= z && x >= z && x > y && y < z && y <= z && x >= y;  {x := 5; y:= 4; z :=5;}"); //Sat
//		newIteration("var x : int; var y : int; procedure P() returns () ensures x == 5 && y == 5;{x := 5; y := 5;}"); //Sat
//		newIteration("var x : int; var y : int; procedure P() returns () ensures x == 6 && y == 5;{x := 5; y := 5;}"); //Unsat
//		newIteration("var x : int; var y : int; procedure P() returns () ensures x == 5 && y == 6;{x := 5; y := 5;}"); //Unsat
//		newIteration("var x : int; var y : int; procedure P() returns () ensures x == 6 && y == 6;{x := 5; y := 5;}"); //Unsat
//		newIteration("var x : int; var y : int; procedure P() returns () ensures x == 5 || y == 5;{x := 5; y := 5;}"); //Sat
//		newIteration("var x : int; var y : int; procedure P() returns () ensures x == 5 || y == 6;{x := 5; y := 5;}"); //Sat
//		newIteration("var x : int; var y : int; procedure P() returns () ensures x == 6 || y == 5;{x := 5; y := 5;}"); //Sat
//		newIteration("var x : int; var y : int; procedure P() returns () ensures x == 6 || y == 6;{x := 5; y := 5;}"); //Unsat
//		newIteration("var x : int; var y : int; procedure P() returns () ensures x == 5 || (x == 5 && y == 5);{x := 5; y := 5;}"); //Sat 
//		newIteration("var x : int; var y : int; procedure P() returns () ensures x == 6 || (x == 5 && y == 5);{x := 5; y := 5;}"); //Sat
//		newIteration("var x : int; var y : int; procedure P() returns () ensures x == 5 || (x == 6 && y == 5);{x := 5; y := 5;}"); //Sat
//		newIteration("var x : int; var y : int; procedure P() returns () ensures x == 6 || (x == 6 && y == 5);{x := 5; y := 5;}"); //Unsat
//		newIteration("var x : int; var y : int; procedure P() returns () ensures (x == 5 && y == 5) || (x == 5 && y == 5);{x := 5; y := 5;}"); //Sat
//		newIteration("var x : int; var y : int; procedure P() returns () ensures (x == 6 && y == 5) || (x == 5 && y == 5);{x := 5; y := 5;}"); //Sat
//		newIteration("var x : int; var y : int; procedure P() returns () ensures (x == 5 && y == 5) || (x == 5 && y == 6);{x := 5; y := 5;}"); //Sat
//		newIteration("var x : int; var y : int; procedure P() returns () ensures (x == 5 && y == 6) || (x == 5 && y == 6);{x := 5; y := 5;}"); //Unsat
		
		//While-tests
//		newIteration("var x : int; var y : int; var z : int; procedure P() returns () ensures x == 5;{x := 0; y := 0; z := 3; while (y <= z) invariant x-y == 0 && x >= 0 && x <= 5; {x := z+1; y := y+1;} y:=10; }"); //Unsat
//		newIteration("var x : int; var y : int; var z : int; procedure P() returns () ensures x == 5;{x := 0; y := 0; z := 4; while (y <= z) invariant x-y == 0 && x >= 0 && x <= 5; {x := x+1; y := y+1;} y:=10; }"); //Sat
//		newIteration("var x : int; procedure P() returns () ensures x == 5;{x := 0; while (x == 0) invariant x >= 0 && x <= 1; {x := 1 +incx(4);} x := 5;  } procedure incx(foo : int) returns (foo : int) requires x == 4444; ensures x == 999999; { x := x+1;}"); //Sat 
//		newIteration("var x : int; procedure P() returns () ensures x == 5;{x := 0; while (x == 0) invariant x >= 0 && x <= 1; {x := 2;} x := 5; }"); //Unsat
//		newIteration("var x : int; var y : int; var z : int; procedure P() returns () ensures x == 5;{x := 0; while (x == 0) invariant x >= 0 && x <= 1; {havoc z; x := 1;} x := 5; }"); //Unsat

//		newIteration("var x : int; procedure P() returns () ensures x == 5; modifies x;{x := 0; while (x == 0) invariant x >= 0 && x <= 1; { x := 1; assert 1 == 1;}  assert 1==1;x := 5; }"); //Unsat
		
		//Havoc-tests
//		newIteration("var x : int; var y : int; var z : int; procedure P() returns () ensures x == 5; { x:= 5; havoc x, y, z;}"); //Unval
//		newIteration("var x : int; var y : int; var z : int; procedure P() returns () ensures x == 5; { x:= 5; havoc x, y, z;}"); //
//		newIteration("var x : int; var y : int; var z : int; procedure P() returns () ensures x == 5; { x:= 5; havoc x, y, z;}");
//		newIteration("var x : int; var y : int; var z : int; procedure P() returns () ensures x == 5; { x:= 5; havoc x, y, z;}");
//		newIteration("var x : int; var y : int; var z : int; procedure P() returns () ensures x == 5; { x:= 5; havoc x, y, z;}");
		
		//Assume-tests
//		newIteration("var x : int; var y : int; var z : int; procedure P() returns () ensures x == 5; {assume x == 5;}"); //Val
//		newIteration("var x : int; var y : int; var z : int; procedure P() returns () ensures x == 7; {assume x == 5;}"); //Unval
//		newIteration("var x : int; var y : int; var z : int; procedure P() returns () ensures x == 5; {assume x == 5; x:=7;}"); //Unval
//		newIteration("var x : int; var y : int; var z : int; procedure P() returns () ensures x == 5; {assume x == 7; x:=5;}"); //Val
//		newIteration("var x : int; var y : int; var z : int; procedure P() returns () ensures x + y == 14; {assume x == 7; assume y == 7;}"); //Val
//		newIteration("var x : int; var y : int; var z : int; procedure P() returns () ensures x + y == 15; {assume x == 7; assume y == 7;}"); //UnVal
//		newIteration("var x : int; var y : int; var z : int; procedure P() returns () ensures x + y == 14; {assume x == 7; assume y == 7; x:=5;}"); //UnVal
//		newIteration("var x : int; var y : int; var z : int; procedure P() returns () ensures x + y == 15; {assume x == 7; assume y == 7; y:=5;}"); //UnVal
		
		//Old test
//		newIteration("var x : int; procedure main () returns () requires x == 12; ensures x==old(x)-1; { x:= 10; x := incx(); } procedure incx() returns (bork : int) ensures x == old(x +1); { x := x+1;}"); //UnVal
//		newIteration("var x : int; procedure main () returns () requires x == 1; ensures x==12; modifies x; { x := 1; x := old(x+old(x))+x+11; } ");
		
//		newIteration("var x : int; var y : int; var z : int; procedure P() returns () ensures y == 1; modifies z; { x := 4; } procedure Foo() returns () ensures y == 1; modifies z; { z := 9; }");
//		newIteration("var x : int; var y : int; var z : int; procedure P() returns () ensures y == 1; modifies z; { x := 3; y := 3; z := 10; } procedure Foo() returns () ensures y == 1; modifies z; { x := 3; y := 3; z := 9; }");

		
		// Larger programs:
//		newIteration("var x : int; var y : int; procedure P() returns () ensures y == 1; modifies p; { x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3;  x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;}");
//		newIteration("var x : int; var y : int; procedure P() returns () ensures y == 1; modifies p; { x := 33; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3;  x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;}");
//		newIteration("var x : int; var y : int; procedure P() returns () ensures y == 1; modifies p; { x := 33; y := 34; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3;  x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3; x := 3; y := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; if(x == 3) { y := 100; } y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3;x := 3; y := 3; x := 3; y:=1;}");
//		CodeVersion version0 = new CodeVersion(0, program0);
	}
	
	private Patch<ConcreteInput> generatePatch(CodeVersion originalVersion, CodeVersion newVersion) {
		if(newVersion == null) {
			System.out.println("generatePatch requires newVersion to not be null");
		}
		Patch<ConcreteInput> patch = DiffUtils.diff(originalVersion == null ? new ArrayList<ConcreteInput>() : originalVersion.sourceCode, newVersion.sourceCode);
		if(DEBUG) {
			System.out.println("Patch size: " + patch.getDeltas().size());
			System.out.println(patch.getDeltas());
		}
//		return null;
		return patch;
	}
	
	public static void main(String[] args) {

		boolean incremental = true;
		for(String arg : args) {
			if(arg.equals("--incrementalityOff")) {
				incremental = false;
			}
		}

		// TEST PROGRAM 1:
		System.out.println("===========================================================================================");
		System.out.println("=======================================TEST PROGRAM========================================");
		System.out.println("===========================================================================================");
		System.out.println("Test suite for incrementalBoogie");
		System.out.println("Solving "+(!incremental ? "non" : "")+"incrementaly");
		System.out.println();
		
		BoogieGrammar.getInstance();
		
		if(incremental) {
			new TestMain().run(args);
		} else {
			
			for(String arg : args) {
				String[] program = new String[1];
				program[0] = arg;
				new TestMain().run(program);
			}
		}
		
	}
}
