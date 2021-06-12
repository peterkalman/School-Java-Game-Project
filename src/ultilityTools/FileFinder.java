package ultilityTools;

import java.io.File;
import java.util.LinkedList;
/**
 * FileFinder is a class that searches files in a certain folder
 * @author Peter
 *
 */
public class FileFinder {
	/**
	 * picks all files in the given folder  where the name ends with a specific part
	 * @param folderPath - the folder path of the wanted folder
	 * @param _endingPart - the specific part to sort by 
	 * @return
	 */
	public static LinkedList<String> getAllFiles(String folderPath,String _endingPart){
		File f = new File(folderPath);
		LinkedList<String> finalFiles = new LinkedList<String>(); 
		File listOfFiles[] = f.listFiles();
		for(int i=0;i<listOfFiles.length;i++){
			if(listOfFiles[i].isFile() && listOfFiles[i].getName().endsWith(_endingPart))
				finalFiles.add(listOfFiles[i].getName());
		}
		return finalFiles;
		
	}
}
