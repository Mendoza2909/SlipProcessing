import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.file.Path;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.*;
import net.sourceforge.tess4j.util.*;
import net.sourceforge.tess4j.ITessAPI.*;
import net.sourceforge.tess4j.ITesseract.RenderedFormat;
import net.sourceforge.tess4j.ITesseract.*;


import org.slf4j.*;

import edu.stanford.nlp.*;

public class OpticalRecognition  {
	
	public int Optical(File inputpath,Path outputfile){
		
		//S:\Actuarial\Projects\Machine Learning\IPF\Slips Phase 2 Test\Slips
		//System.out.print(inputpath);
		//This class is used for scanned documents. 
		//It does not recognise 100% of the characters.
		//Most work will take place on improving this class to improve % recognition.
		//File imageFile = new File("S:\\Actuarial\\Projects\\Machine Learning\\ScannedPDF\\ScannedExample1Short.pdf");
	    
		File imageFile = inputpath;
	    Tesseract instance = new Tesseract();  // JNA Interface Mapping
	    
	    {
	    // Tesseract1 instance = new Tesseract1(); // JNA Direct Mapping
	    
	    instance.setDatapath("C:\\Program Files\\Java\\jdk1.8.0_101\\lib\\libraries\\Tess4J\\");
	    instance.setHocr(false);
    	
	    
	    //Look at getting cube files
	    instance.setOcrEngineMode(0);
	    
	    try {
	    	
	    	//for (ITesseract.RenderedFormat c : ITesseract.RenderedFormat.values())
	    	    //System.out.println(c);
	    	
	    	
	        String result = instance.doOCR(imageFile);
	        //instance.
	       
	        //TessResultRenderer ResultRenderer = new TessResultRenderer();
	        
	        //	OEM_TESSERACT_ONLY	0	Run Tesseract only - fastest
	        //OEM_CUBE_ONLY	1	Run Cube only - better accuracy, but slower
	        //OEM_TESSERACT_CUBE_COMBINED	2	Run both and combine results - best accuracy
	        //OEM_DEFAULT	3 Specify this mode to indicate that any of the above modes should be automatically 
	        //inferred from the variables in the language-specific config, or 
	        //if not specified in any of the above should be set to the default OEM_TESSERACT_ONLY.
	        
	        
	        
	        
	        
	        //ResultRenderer(result);
	        
	        //String upToNCharacters = result.substring(0, Math.min(result.length(), 100));
	        //System.out.println(upToNCharacters);
	        //PrintWriter out = new PrintWriter("S:\\Actuarial\\Projects\\Machine Learning\\ScannedPDF\\ScannedExample1word.docx");
	        
	        
	        //get rid of commas as they mess up CSV output and replace newlines with carriage returns
	        result = result.replaceAll(",","");
	        result = result.replaceAll("\n", "\r");
	        
	        //out.println(result);
	        //out.close();
	        
	        
	        //Convert to hex, replace 0d with 0a for readability and convert back
	        String hexval = toHex(result);
	        //System.out.print(hexval);
	        hexval = hexval.replaceAll("0d", "0d0a");
	        //System.out.print(hexval);
	        String stringval = HexToASCII(hexval);
	        
	        out.println(stringval);
	        out.close();
	        
		    } catch (TesseractException e) {
		        System.err.println(e.getMessage());
		    } catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		
	    }
	    return 0;
	    }
	}


	public String toHex(String arg) {
	    return String.format("%040x", new BigInteger(1, arg.getBytes(/*YOUR_CHARSET?*/)));
	}
	
	
	public String HexToASCII(String arg) {
	     StringBuilder output = new StringBuilder();
	    for (int i = 0; i < arg.length(); i+=2) {
	        String str = arg.substring(i, i+2);
	        output.append((char)Integer.parseInt(str, 16));
	    }
		return output.toString();
	    }
	
}
