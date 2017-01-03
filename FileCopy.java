import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;

public class FileCopy {
	public void DoCopy(String inputfolder,File copytofolder){
		//System.out.println(inputfolder);
		String extension = "/2016/Slip/";
		File file = new File(inputfolder);
		String[] names = file.list();

		for(String name : names)
		{
			System.out.print("Absolute " + inputfolder + name);
			if (new File(inputfolder + name+extension).isDirectory())
		    {		    	
		    	Path riskinput = Paths.get(inputfolder, name, extension);
		    	
		    	
		    	
		    	//String riskinputstring = riskinput.toString().replaceAll("\\\\", "/")+"/";
		    	
		    	File riskinputfolder  = new File(riskinput.toString());
		    	//System.out.print("Absolute " + riskinputstring);
		    	//System.out.print("\n");
		    	
		    	//if(!riskinputfolder.isDirectory()){
		        File [] files = riskinputfolder.listFiles();		        		       
		        for (int i = 0; i < files.length; i++){
			        if (files[i].isFile()){ //this line weeds out other directories/folders
			        	try {
							//copyFileUsingApacheCommonsIO(files[i], copytofolder);
							FileUtils.copyFileToDirectory(files[i], copytofolder);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        
			        }          
			        			            
			
		        
		}}
		}
	}

	private static void copyFileUsingApacheCommonsIO(File source, File dest) throws IOException {
	    FileUtils.copyFile(source, dest);
	}


}
