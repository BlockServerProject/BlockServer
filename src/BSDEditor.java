
import java.util.Arrays;

/**
 * @author jython234
 *
 */
public class BSDEditor {
	public static final String VERSION = "Pre-Alpha-0.1";
	public static final String IMPLEMENTVERSION = "Pre-Alpha-0.0.1";
	
	public static void main(String[] args) {
		if(args.length != 0){
			//Print out the correct usage
			System.out.println("Usage: BSDEditor [args]\nArgs:\n\t-e\tOpens the editor in 'edit' mode\n\t-r\tOpens the editor in 'read' mode\n\t-v\n\tDisplay version info");
			System.exit(0);
		}
		else{
			if(args[0] == "-e"){
				//Open up in "edit" mode
				startEdit();
			}
			else if(args[0] == "-r"){
				//Open up in "read" mode
				startRead();
			}
			else if(args[0] == "-v"){
				//Display version info
				System.out.println("BlockServer Data Editor version: "+ VERSION);
				System.out.println("This version is for BlockServer version: "+ IMPLEMENTVERSION);
				System.exit(0);
			}
			else{
				System.out.println("Usage: BSDEditor [args]\nArgs:\n\t-e\tOpens the editor in 'edit' mode\n\t-r\tOpens the editor in 'read' mode\n\t-v\n\tDisplay version info");
				System.exit(0);
			}
		}

	}
	
	private static void print(String msg){
		System.out.println(msg);
	}
	
	private static String getInput(String prompt){
		String response = null;
		
		return response;
	}
	
	private static void startEdit(){
		//TODO: Work on this
	}
	
	private static void startRead(){
		print("BlockServer Data Editor version: "+VERSION);
		
	}

}
