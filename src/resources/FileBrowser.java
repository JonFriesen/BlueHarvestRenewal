package resources;
/**
 * @author Jon Friesen
 * 
 * Basic map selection menu
 */
import java.io.File;
import java.io.FilenameFilter;


public class FileBrowser {
	private String dir = "src/resources/maps";
	public FileBrowser(){


	}
	public String getDir(){
		return dir;
	}
	/**
	 * Finds files that are maps (.tmx)
	 * @return returns list of maps
	 */
	public String[] fileFinder(){
		
		File maps = new File(dir);
		FilenameFilter ext = new ExtFilter();
		String[] s = new String[maps.list(ext).length]; 
		for(int i=0; i< maps.list(ext).length; i++){
			s[i] = maps.list(ext)[i];
		}
		return s;

	}
	public class ExtFilter implements FilenameFilter{
		public boolean accept(File dir, String name){
			return name.endsWith(".tmx");
		}
	}
}
