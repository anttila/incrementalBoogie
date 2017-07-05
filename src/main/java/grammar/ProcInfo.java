package grammar;

import java.util.ArrayList;
import java.util.HashMap;

import incrementalBoogie.grammar.BoogieParser.ExprContext;

public class ProcInfo {
	public String procName;
	private ArrayList<ExprContext> preconditions;
	private ArrayList<ExprContext> postconditions;
	private ArrayList<String> args;
	private HashMap<String, String> argType;
	private ArrayList<String> outParams;
	private int numberOfArgs;
	
	public ProcInfo(String procName) {
		this.procName = procName;
		preconditions = new ArrayList<ExprContext>();
		postconditions = new ArrayList<ExprContext>();
		args = new ArrayList<String>();
		outParams = new  ArrayList<String>();
		argType = new HashMap<String, String>();
		numberOfArgs = 0;
	}
	
	public ArrayList<ExprContext> getPreconditions() {
		return preconditions;
	}
	
	public void addPreconditions (ExprContext expr) {
		preconditions.add(expr);
	}
	
	public ArrayList<ExprContext> getPostconditions() {
		return postconditions;
	}
	
	public void addPostconditions (ExprContext expr) {
		postconditions.add(expr);
	}
	
	public ArrayList<String> getArgs() {
		return args;
	}
	
	public void addArg (String arg, String type) {
		args.add(arg);
		argType.put(arg, type);
//		System.out.println("Adding: "+arg+" of type "+type);
		numberOfArgs++;
	}
	
	public String getType(String arg){
		return argType.get(arg);
	}
	public int getNumberOfArgs(){
		return numberOfArgs;
	}
	public void addOutParam (String param, String type) {
		outParams.add(param);
		argType.put(param, type);
	}
	public ArrayList<String> getOutParams () {
		return outParams;
	}
}
