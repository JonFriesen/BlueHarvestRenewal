package resources;
/**
 * @author Jon Friesen
 * 
 * Easy to use log output system
 */
public class Log {
	public static boolean verbose = true;
	public static String map = "";
	
	public static void msg(String str){
		System.out.println("> "+str);
	}
	public static void err(String str){
		System.err.println("ERR> "+str);
	}
}
