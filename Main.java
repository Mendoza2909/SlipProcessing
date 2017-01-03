import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.util.*;

import net.sourceforge.tess4j.*;


import org.slf4j.*;

import edu.stanford.nlp.*;

public class Main {

	
	public static void main(String args[]) {
		//System.out.print("Hello");
		int x;
		
			//InputFolder
			String InputFolder = "S:/Actuarial/Projects/Machine Learning/IPF/Slips Phase 2 Test/Output";
			
			System.out.println(InputFolder);
			//OutputFolder
			//S:\Actuarial\Projects\Machine Learning\IPF\Slips Phase 2 Test\Slips
			
			//File OutputFolder  = new File("S:/Actuarial/Projects/Machine Learning/IPF/Original Slips/");
			
			File OutputFolder  = new File("S:/Actuarial/Projects/Machine Learning/IPF/Slips Phase 2 Test/Slips");			
			
			File Folder= new File(InputFolder);
			/*File [] listOfFiles = Folder.listFiles();
		    
		    for (int i = 0; i < listOfFiles.length; i++) {
		        if (listOfFiles[i].isFile()) {
		          System.out.println("File " + listOfFiles[i].getName());
		        } else if (listOfFiles[i].isDirectory()) {
		          System.out.println("Directory " + listOfFiles[i].getName());
		        }
		      }
			*/
			
			//FileCopy FileCopy = new FileCopy();
			//FileCopy.DoCopy(IPFFolder,OutputFolder);
			
			
		    
		    
		    
		    OpticalRecognition OpticalRecognition = new OpticalRecognition();
		    String inputpath = "S:\\Actuarial\\Projects\\Machine Learning\\IPF\\Slips Phase 2 Test\\Slips";
		    String outputpath = "S:\\Actuarial\\Projects\\Machine Learning\\IPF\\Slips Phase 2 Test\\Output";
		    File inputfiles = new File(inputpath);
		    
		    File [] files = inputfiles.listFiles();
		    for (int i = 0; i < files.length; i++){
		        if (files[i].isFile()){ //this line weeds out other directories/folders
		            
		        	String outputfilename = files[i].getName();
		        	System.out.println(outputfilename);
		        	//Change filename so outputs as text file
		        	outputfilename = outputfilename.replaceAll(".pdf",".txt");
		        	//Need to account for when extension is .PDF
		        	
		        	System.out.println(outputfilename);
		        	Path outputfile = Paths.get(outputpath, outputfilename);
		        	x = OpticalRecognition.Optical(files[i],outputfile); 
		            
		        }
		    }
		
		
		
		
		//String filepath = null;
		//x = OpticalRecognition.Optical(filepath);
		//new TextReading();
		
		
	}

	
    }
        

