import java.io.File;
import java.math.BigInteger;
import java.nio.file.Path;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.log4j.Logger;

public class OCRConversion  {
	
	static Tesseract instance = new Tesseract();
	
	
	//String langData = "C:\\Program Files\\Java\\jdk1.8.0_101\\lib\\libraries\\Tess4J\\";
	static String langData = "C:\\Program Files\\Tess4J";
	
		//S:\Actuarial\Projects\Machine Learning\IPF\Slips Phase 2 Test\Slips
		//System.out.print(inputpath);
		//This class is used for scanned documents. 
		//It does not recognise 100% of the characters.
		//Work will take place on improving this class to improve % recognition.
		//File imageFile = new File("S:\\Actuarial\\Projects\\Machine Learning\\ScannedPDF\\ScannedExample1Short.pdf");  
		//String outputFileName = inputPath.replaceAll(".pdf",".txt");
		//Path outputFile = Paths.get(inputPath, outputFileName);
	      // JNA Interface Mapping   
	    // Tesseract1 instance = new Tesseract1(); // JNA Direct Mapping

	    //Look at getting cube files
	    //instance.setOcrEngineMode(0);
       //TessResultRenderer ResultRenderer = new TessResultRenderer();
	        
	        //	OEM_TESSERACT_ONLY	0	Run Tesseract only - fastest
	        //OEM_CUBE_ONLY	1	Run Cube only - better accuracy, but slower
	        //OEM_TESSERACT_CUBE_COMBINED	2	Run both and combine results - best accuracy
	        //OEM_DEFAULT	3 Specify this mode to indicate that any of the above modes should be automatically 
	        //inferred from the variables in the language-specific config, or 
	        //if not specified in any of the above should be set to the default OEM_TESSERACT_ONLY.
	       
	        //get rid of commas as they mess up CSV output and replace newlines with carriage returns
	        //result = result.replaceAll(",","");
	        

	public static String toHex(String arg) {
	    return String.format("%040x", new BigInteger(1, arg.getBytes(/*YOUR_CHARSET?*/)));
	}
	
	
	public static String HexToASCII(String arg) {
	     StringBuilder output = new StringBuilder();
	    for (int i = 0; i < arg.length(); i+=2) {
	        String str = arg.substring(i, i+2);
	        output.append((char)Integer.parseInt(str, 16));
	    }
		return output.toString();
	    }
	
	
	
	public static String OCROutput(Path inputPath) throws TesseractException{
		instance.setDatapath(langData);
		String result = "";
		instance.setHocr(false);
		File imageFile = new File(inputPath.toString());
		result = instance.doOCR(imageFile);		
		result = result.replaceAll("\n", "\r");      
        //Convert to hex, replace 0d with 0a for readability and convert back
        String hexVal = toHex(result);
        //System.out.print(hexval);
        hexVal = hexVal.replaceAll("0d", "0d0a");
        //System.out.print(hexval);
        String stringVal = HexToASCII(hexVal);
        return stringVal;
	}
	
	public static String HOCROutput(Path inputPath) throws TesseractException{
		instance.setDatapath(langData);
		String result = "";
		instance.setHocr(true);
		File imageFile = new File(inputPath.toString());
		result = instance.doOCR(imageFile);		
		result = result.replaceAll("\n", "\r");      
        //Convert to hex, replace 0d with 0a for readability and convert back
        String hexVal = toHex(result);
        //System.out.print(hexval);
        hexVal = hexVal.replaceAll("0d", "0d0a");
        //System.out.print(hexval);
        String stringVal = HexToASCII(hexVal);
        return stringVal;
	}
	
	
	
}
