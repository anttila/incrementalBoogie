package grammar;
import java.util.HashMap;
import java.util.List;

import incrementalBoogie.grammar.BoogieBaseVisitor;
import incrementalBoogie.grammar.BoogieParser;
public class BoogieASTVisitorInfoCollector extends BoogieBaseVisitor<List<ConcreteInput>> {
	
	public static HashMap<String, ProcInfo> procs = new HashMap<String, ProcInfo>();
	private String currentProc;
	private boolean parsingArgs = false;
	private boolean parsingOutParameter = false;
	// Idea:
	// Store all preconditions and postconditions as their expr statements. Then when the other visitor gets to a call it adds the ensures/requires parts

	
//	@Override
//	public List<ConcreteInput> visitProc3(BoogieParser.Proc3Context ctx) {
////		System.out.println("Proc found: "+ctx.id1_.getText());
//		currentProc = ctx.id_.getText(); 
////		proc = ctx.id_.getText();
////		proc_p = ctx.specList();
//		procs.put(currentProc, new ProcInfo(currentProc));
//		visit(ctx.specList());
//		currentProc = null;
////		System.out.println("Proc ended: "+ctx.id_.getText());
//		return null;
//	}
	
	@Override
	public List<ConcreteInput> visitProcedureDecl(BoogieParser.ProcedureDeclContext ctx) {
//		System.out.println("ctxid:"+ctx.id_);
		currentProc = ctx.id_.getText(); 
		procs.put(currentProc, new ProcInfo(currentProc));
		parsingArgs = true;
		visit(ctx.pSig());
		parsingArgs = false;
		if(ctx.specList() != null) {
			visit(ctx.specList());
		}
		if(ctx.declList() != null) {
			visit(ctx.declList());
		}
		currentProc = null;
		return null;
	}
	
	@Override
	public List<ConcreteInput> visitIdType(BoogieParser.IdTypeContext ctx){
//		System.out.println("AddingIdType: "+ctx.id_.getText()+" of type "+ctx.type_.getText());
		if(currentProc != null && parsingArgs && !parsingOutParameter) {
			procs.get(currentProc).addArg(ctx.id_.getText(), ctx.type_.getText());
			
		} else if (currentProc != null && parsingOutParameter) {
			procs.get(currentProc).addOutParam(ctx.id_.getText(), ctx.type_.getText());
		}
		return null;
	}
	
	@Override
	public List<ConcreteInput> visitOutparameters1(BoogieParser.Outparameters1Context ctx) {
		parsingOutParameter = true;
		visit(ctx.idType());
		parsingOutParameter = false;
		return null;
	}
	
	@Override
	public List<ConcreteInput> visitSpecRequires(BoogieParser.SpecRequiresContext ctx){
		procs.get(currentProc).addPreconditions(ctx.expr());
		return null;
	}
	
	@Override
	public List<ConcreteInput> visitSpecRequiresList(BoogieParser.SpecRequiresListContext ctx){
		procs.get(currentProc).addPreconditions(ctx.expr());
		visit(ctx.specList());
		return null;
	}
	
	
	@Override
	public List<ConcreteInput> visitSpecEnsures(BoogieParser.SpecEnsuresContext ctx){
//		System.out.println("Postcond proc "+currentProc+":"+ctx.expr().toString());
		procs.get(currentProc).addPostconditions(ctx.expr());
		return null;
	}
	
	@Override
	public List<ConcreteInput> visitSpecEnsuresList(BoogieParser.SpecEnsuresListContext ctx){
//		System.out.println("Postcond proc "+currentProc+":"+ctx.expr().toString());

//		System.out.println("visitSpecEnsuresList"+ctx.expr().getText()+procs.get(currentProc));
		procs.get(currentProc).addPostconditions(ctx.expr());
		visit(ctx.specList());
		return null;
	}
	
	
//	specList:
//		'requires' expr ';' #SpecRequires
//		| 'requires' expr ';'specList #SpecRequiresList
//		| 'modifies' ID ';' #SpecModifies
//		| 'modifies' ID ';' specList #SpecModifiesList
//		| 'ensures' expr ';' #SpecEnsures
//		| 'ensures' expr ';' specList #SpecEnsuresList
//		;
	
}


