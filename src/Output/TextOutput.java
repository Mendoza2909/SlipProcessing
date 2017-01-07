package Output;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class TextOutput { 
	
	public static void Output(String inputFileContent, String outputFilePath){
		
		try {
			//PrintWriter HOCROut = new PrintWriter(HOCROutputFile.toString());
			System.out.println(outputFilePath);
			
			PrintWriter OCROut = new PrintWriter(outputFilePath);		
			OCROut.println(inputFileContent);
			OCROut.close();
			System.out.println(outputFilePath + " OCR Output printed.");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
