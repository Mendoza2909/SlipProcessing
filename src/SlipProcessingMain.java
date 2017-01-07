import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.fonts.otf.TableHeader;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfReader;

import Output.TextOutput;
import PDFCompression.Compression;
import net.sourceforge.tess4j.TesseractException;


public class SlipProcessingMain {
	
	
	public static void main(String[] args) throws TesseractException, IOException, DocumentException, ParserConfigurationException, SAXException {
		// TODO Auto-generated method stub
		
		BasicConfigurator.configure();
		
		String inputFolder = "C:/Users/Colin/Documents/PDF Conversion/Slips";
		String OCROutputFolder = "C:\\Users\\Colin\\Documents\\PDF Conversion\\OCR Text Files\\";
		String HOCROutputFolder = "C:\\Users\\Colin\\Documents\\PDF Conversion\\HOCR Text Files\\";
		String imageOutputFolder = 	"C:\\Users\\Colin\\Documents\\PDF Conversion\\Output\\";
		//GET OCR OUTPUT
		File inputFiles = new File(inputFolder);
		File [] files = inputFiles.listFiles();
		
/*----------------------------------------------------
		for (int i = 0; i < files.length; i++){
	        if (files[i].isFile()){ //this line weeds out other directories/folders
	            
	        	String inputFile = files[i].getName();
	        	System.out.println(inputFile);
	        	//Change filename so outputs as text file
	        	String OCROutputFilename = inputFile.replaceAll(".pdf"," OCR.txt");
	        	String HOCROutputFilename = inputFile.replaceAll(".pdf"," HOCR.txt");
	        	
	        	//Need to account for when extension is .PDF
	        
	        	Path inputFilePath = Paths.get(inputFolder, inputFile);
	        	
	        	
	        	String OCROutput = OCRConversion.OCROutput(inputFilePath);
	        	String HOCROutput = OCRConversion.HOCROutput(inputFilePath);
	        	
	        	String OCROutputFilePath = OCROutputFolder + OCROutputFilename;
	        	String HOCROutputFilePath = HOCROutputFolder + HOCROutputFilename;
	        	
	        	
	        	TextOutput.Output(OCROutput,OCROutputFilePath);
	        	TextOutput.Output(HOCROutput,HOCROutputFilePath);
	        	
	            
	        }
	    }
		*/
//---------------------------------------------		
		
		//For printout to text file comment out above here
			
			
		//Create HOCR Parser, get the array, use number to get relevant bbox, feed bbox into image extractor, 
		//HOCRParser HOCRParser = new HOCRParser();
	
		File HOCRFiles = new File(HOCROutputFolder);
		File [] hocrfiles = inputFiles.listFiles();
		for (int a = 0; a < files.length; a++){
		//Loop through each file, dont process files that aren't pdfs or have compression issues
			
		String inputFile = files[a].getName();
		//System.out.println(inputFile);
		String testString = inputFile.substring(inputFile.length() - 3);
		
		if (!testString.equalsIgnoreCase("pdf")){
			//Only want to look at pdf files
			continue;
		}
		String OCRFilename = inputFile.replaceAll(".pdf"," OCR.txt");
    	String HOCRFilename = inputFile.replaceAll(".pdf"," HOCR.txt");
    	String imageFilename = inputFile.replaceAll(".pdf"," Processed.png");
    	
    	Path inputFilePath = Paths.get(inputFolder, inputFile);	
    	String PDFFileString = inputFilePath.toString();
    	System.out.println(PDFFileString);
    	String OCROutput = new String(Files.readAllBytes(Paths.get(OCROutputFolder + OCRFilename)));
    	String HOCROutput = new String(Files.readAllBytes(Paths.get(HOCROutputFolder + HOCRFilename)));
    	
    	//System.out.println("Type is " + Files.probeContentType(inputFilePath));
    	

    	
    	PdfReader pdfReader = new PdfReader(PDFFileString);
    	
    	boolean compressFlag = pdfReader.isNewXrefType();
    	System.out.println(compressFlag);
    	
    	if(!compressFlag){
    		//Compression.uncompressXRef(PDFFileString,PDFFileString);
    		//System.out.println("Recompressed " + inputFile);
    	
    	//Scanner OCROutputScan = new Scanner(new FileReader(OCROutputFolder + OCRFilename));
    	//Scanner HOCROutputScan = new Scanner(new FileReader(HOCROutputFolder + HOCRFilename));
		
    	
    	//String OCROutput = OCROutputScan.toString();
		//String HOCROutput = HOCROutputScan.toString();
		
		String HOCRArray[][] = HOCR.HOCRParser.HOCRParseArray(HOCROutput);  
		
		//Get column of words only
		String regexWordColumn[] = new String[HOCRArray.length];
		
		System.out.println(regexWordColumn.length);
		for(int i=0; i<HOCRArray.length; i++){		
			regexWordColumn[i] = HOCRArray[i][1];	
		}
			
		ImageExtraction imageExtraction = new ImageExtraction();
		
		double HOCRPageHeight = HOCR.HOCRParser.pageHeight(HOCROutput);
		//double HOCRPageWidth = HOCR.HOCRParser.pageWidth(HOCROutput);	
		double PDFPageHeight = imageExtraction.pageHeight(PDFFileString);
		//double PDFPageWidth = imageExtraction.pageWidth(PDFFileString);
		//bbox coordinates need to be scaled
		double scaleFactor = HOCRPageHeight/PDFPageHeight;
		
		
		
		/*if(compressFlag){
    		Compression.recompressXRef(PDFFileString,PDFFileString);
    		System.out.println("Recompressed " + inputFile);
    	}*/
		
		// UMR - 0
		// Period - 1
		// Insured - 2
		// Reinsured - 3
		// Premium - 4
		// Claims - 5
		
		int imageOutputCount = 6;
		String wordPositionArray[][] = new String[imageOutputCount][6];
		
		//Define bbox adjustments for different images
		Double bboxAdjustment[][] = new Double[imageOutputCount][4];
		
		//UMR
		bboxAdjustment[0][0] = (double) 0;
		bboxAdjustment[0][1] = (double) 0;
		bboxAdjustment[0][2] = (double) 0;
		bboxAdjustment[0][3] = (double) 0;
		
		//Period
		bboxAdjustment[1][0] = (double) 0;
		bboxAdjustment[1][1] = (double) 0;
		bboxAdjustment[1][2] = (double) 2000;
		bboxAdjustment[1][3] = (double) 200;
				
		//Insured
		bboxAdjustment[2][0] = (double) 0;
		bboxAdjustment[2][1] = (double) 0;
		bboxAdjustment[2][2] = (double) 0;
		bboxAdjustment[2][3] = (double) 0;

		//Reinsured
		bboxAdjustment[3][0] = (double) 0;
		bboxAdjustment[3][1] = (double) 0;
		bboxAdjustment[3][2] = (double) 0;
		bboxAdjustment[3][3] = (double) 0;

		//Premium
		bboxAdjustment[4][0] = (double) -100;
		bboxAdjustment[4][1] = (double) -20;
		bboxAdjustment[4][2] = (double) 100;
		bboxAdjustment[4][3] = (double) 20;

		//Claims
		bboxAdjustment[5][0] = (double) -1000;
		bboxAdjustment[5][1] = (double) 0;
		bboxAdjustment[5][2] = (double) 1500;
		bboxAdjustment[5][3] = (double) 2000;

		RegexMatchNew regexMatch = new RegexMatchNew();
		
		String matchedWords[] = new String[imageOutputCount]; 
		matchedWords[0] = regexMatch.UMRSearch(OCROutput).replace("[\\u0000-\\u001f]", "\\s");
		matchedWords[1] = regexMatch.PeriodSearch(OCROutput).replace("[\\u0000-\\u001f]", "\\s");;
		matchedWords[2] = regexMatch.InsuredSearch(OCROutput).replace("[\\u0000-\u001f]", "\\s");;
		matchedWords[3] = regexMatch.ReinsuredSearch(OCROutput).replace("[\\u0000-\\u001f]", "\\s");;
		matchedWords[4] = regexMatch.PremiumSearch(OCROutput).replace("[\\u0000-\\u001f]", "\\s");;
		matchedWords[5] = regexMatch.ClaimsSearch(OCROutput).replace("[\\u0000-\\u001f]", "\\s");;
		
		//oldString.replaceAll("[\u0000-\u001f]", "");
		//matchedWords[1] = matchedWords[1].replaceAll("\\s","");
		
		int matchedWordsLength[] = new int[imageOutputCount];
		for (int x = 0;x<imageOutputCount;x++){
			
		matchedWordsLength[x] = matchedWords[x].split(" ").length; 
			
		//System.out.println("Length is " + matchedWordsLength[x]);
		
		
		}
		
	//int matchedWordsPosition[] = new int[imageOutputCount];
	//String[] OCRDelimited = OCROutput.split("");
		//for (int j = 0; j < matchedWordsPosition.length; j++){
			//for (int k = 0; k < OCRDelimited.length; k++) {
		      //  if (OCRDelimited[k].equals(matchedWords[j])) {
		            //System.out.println("The word " + processText[0][j] + " is in the position: " + k);
		        //	System.out.println("Word is " + matchedWords[j]);
		        	//matchedWordsPosition[j] = k;
		        	//System.out.println(j + " Position" + k);
		        	//break;            
		        //}   
			//}
		//}
		
		int regexIndex = 0;
		
		for(int i=0; i < wordPositionArray[0].length; i++){	
			//System.out.println(i  + " " + matchedWords[i]);	
			
			
			
			
			/*for (int b = 0; b < regexWordColumn.length - matchedWordsLength[i] ; b++){
				for (int f = 0; f < matchedWordsLength[i]; f++){
					
					String[] testArray = Arrays.copyOfRange(regexWordColumn, i, i+f);
					
					for (int e = 0; e < testArray.length; e++){
						System.out.println("entry is " + testArray[e]);
					}
					
					if(	matchedWords[i+f].split("\\s+")[f].equals(regexWordColumn[i+f])){
						regexIndex = i;
						System.out.println("Index 1 " + regexIndex);
						break;
					}
				}
			}*/
			
		regexIndex = Arrays.asList(regexWordColumn).indexOf(matchedWords[i]);	
		
		regexIndex = Collections.indexOfSubList(Arrays.asList(regexWordColumn), Arrays.asList(matchedWords[i].split(" ")));
		
		System.out.println("Index 2 " + regexIndex);
			if (regexIndex > 0){
				
			//Page number
			wordPositionArray[i][1] = HOCRArray[regexIndex][2];		
			
			//Bbox coordinates	
			wordPositionArray[i][2] = HOCRArray[regexIndex][4];
			wordPositionArray[i][3] = HOCRArray[regexIndex][5];
			wordPositionArray[i][4] = HOCRArray[regexIndex][6];
			wordPositionArray[i][5] = HOCRArray[regexIndex][7];
			}
		}

		//Draw text image for title
	
		int y = 10;
		int textX = 10;
		int HOCRX = 200;
		BufferedImage combinedImage = new BufferedImage(1500,1200,BufferedImage.TYPE_INT_ARGB);
		
		for(int i=0; i<wordPositionArray.length; i++){ 
		
			//Set parameters for bbox extraction
			if (wordPositionArray[i][1] != null){
			int pageNumber = Integer.parseInt(wordPositionArray[i][1]);
			double bbox1 = Double.parseDouble(wordPositionArray[i][2]);
			double bbox2 = Double.parseDouble(wordPositionArray[i][3]);
			double bbox3 = Double.parseDouble(wordPositionArray[i][4]);
			double bbox4 = Double.parseDouble(wordPositionArray[i][5]);
			
			bbox1 = bbox1 + bboxAdjustment[i][0]; 
			bbox2 = bbox2 + bboxAdjustment[i][1];
			bbox3 = bbox3 + bboxAdjustment[i][2];
			bbox4 = bbox4 + bboxAdjustment[i][3];
			
			//Different images have different requirements, variables needed to adjust the bbox parameters
			
			
			//Draw HOCR image
			//Get bbox coordinates from wordPosition array and get bboxPageHeight
			BufferedImage image = imageExtraction.Image(PDFFileString, pageNumber, java.lang.Math.max(bbox1,0), java.lang.Math.max(bbox2,0), java.lang.Math.max(bbox3,0), java.lang.Math.max(bbox4,0), HOCRPageHeight, scaleFactor);
			//File yourImageFile = new File("C:/Users/Colin/Documents/PDF Conversion/Input Folder/Abcourt Mining 2016 Slip "+"picture_" + i + ".png");
	        	
			Graphics g = combinedImage.getGraphics();
			
			String text = "Test text";
			BufferedImage textImage = new BufferedImage(200, 50, BufferedImage.TYPE_INT_ARGB);
			Graphics h = textImage.getGraphics();
	        Font font = new Font("Arial", Font.PLAIN, 100);
	        h.setFont(font);
	        h = textImage.createGraphics();
	        h.drawString(text + " " + i, 20, 20);
	        g.drawImage(image, HOCRX+200, y, null);
			g.drawImage(textImage, HOCRX, y,null);
			//ImageIO.write(image, "png", yourImageFile);
			 y += 50;
			
		}
		
		}
		ImageIO.write(combinedImage,"png",new File(imageOutputFolder + imageFilename));
		System.out.println("Completed");

	
	}	
		
		else{
			System.out.println("Image unable to be extracted");
		}
    	}

	}

}