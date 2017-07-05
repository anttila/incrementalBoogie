package utils;

import java.util.ArrayList;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Solver;
import com.microsoft.z3.Status;

import incrementalBoogie.TestMain;

public class ProcedureSolver {
	Solver solver;
	public boolean hasBeenChanged;
	public String procedure;
	private int version = 0;
	public ProcedureSolver(String procedure){
		solver = TestMain.context.mkSolver();
		hasBeenChanged = false;
		this.procedure = procedure;
	}
	
	public void addInput(BoolExpr input) {
		hasBeenChanged = true;
		solver.add(TestMain.context.mkImplies(TestMain.context.mkBoolConst("version"+version), input));
//		solver.add(input);
	}
	
	public boolean hasBeenChanged() {
		return hasBeenChanged;
	}
	
	public void setChanged(boolean newstatus){
		hasBeenChanged = newstatus;
	}

	private ArrayList<BoolExpr> generateAssumptions() {
		ArrayList<BoolExpr> assumptions = new ArrayList<BoolExpr>();
		for(int i=0;i<version;i++) {
			assumptions.add(TestMain.context.mkNot(TestMain.context.mkBoolConst("version"+i)));
		}
		assumptions.add(TestMain.context.mkBoolConst("version"+version));
		return assumptions;
	}
	
	public boolean solve() {
		return solve(false);
	}
	
	
	public boolean solve(boolean printSolver) {
		boolean status;
		hasBeenChanged = false;
		if(printSolver) {
			System.out.println();
			System.out.println(solver);
		}
		ArrayList<BoolExpr> assumptionsList = generateAssumptions();
		BoolExpr[] assumptions = assumptionsList.toArray(new BoolExpr[assumptionsList.size()]);
		System.out.print("Solving procedure: "+ procedure+" version: "+version +" using assumptions: ");
		for(BoolExpr assumption : assumptions) {
			System.out.print(assumption);
		}
		System.out.println();
		long stop, start = System.nanoTime();
		if(solver.check(assumptions) == Status.SATISFIABLE) {
			stop = System.nanoTime();
			System.out.print(" Result: INVALID");
			status = false;
		} else {
			stop = System.nanoTime();
			System.out.print(" Result: VALID");
			status = true;
		}
		System.out.println(" result time: "+(stop-start));
		version++;
		return status;
//		System.out.println("Nodes visited: "+sidecarVisitor.nodesVisited);
	}
}
