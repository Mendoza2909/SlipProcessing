import java.awt.Color;
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
import java.util.concurrent.TimeUnit;

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

//1434 24 items
//186mb


//1201 
//74 items
//565MB
public class SlipProcessingMain {
	//public static String inputFolder = "C:\\Users\\Colin\\Documents\\PDF Conversion\\Slips";
	
	public static void main(String[] args) throws TesseractException, IOException, DocumentException, ParserConfigurationException, SAXException {
		// TODO Auto-generated method stub
		
		BasicConfigurator.configure();
		
		/*
		String inputFolder = "C:\\Users\\Colin\\Documents\\Testing\\Slips";
		String OCROutputFolder = "C:\\Users\\Colin\\Documents\\Testing\\OCR Text Files\\";
		String HOCROutputFolder = "C:\\Users\\Colin\\Documents\\Testing\\HOCR Text Files\\";
		String imageOutputFolder = 	"C:\\Users\\Colin\\Documents\\Testing\\Output\\";
		*/
		
		
		//String inputFolder = "C:\\Users\\Colin\\Documents\\PDF Conversion\\SlipsConvert";
		String inputFolder = "C:\\Users\\Colin\\Documents\\PDF Conversion\\Slips";
		String OCROutputFolder = "C:\\Users\\Colin\\Documents\\PDF Conversion\\OCR Text Files\\";
		String HOCROutputFolder = "C:\\Users\\Colin\\Documents\\PDF Conversion\\HOCR Text Files\\";
		String imageOutputFolder = 	"C:\\Users\\Colin\\Documents\\PDF Conversion\\Output\\";
		
		//GET OCR OUTPUT
		File inputFiles = new File(inputFolder);
		File [] files = inputFiles.listFiles();
/*
//----------------------------------------------------
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
		
//---------------------------------------------		
*/
		//For printout to text file comment out above here
			
			
		//Create HOCR Parser, get the array, use number to get relevant bbox, feed bbox into image extractor, 
		//HOCRParser HOCRParser = new HOCRParser();

		//-----------------------	
		File HOCRFiles = new File(HOCROutputFolder);
		File [] hocrfiles = inputFiles.listFiles();
		for (int a = 0; a < files.length; a++){
			//Loop through each file, dont process files that aren't pdfs or have compression issues		
			String inputFile = files[a].getName();
			//System.out.println(inputFile);
			String testString = inputFile.substring(inputFile.length() - 3);
			
			if (!testString.equalsIgnoreCase("pdf")){
				//Only want to look at pdf files, loop to next if not pdf
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
	    	
	    	PdfReader pdfReader = new PdfReader(PDFFileString);
	    	
	    	boolean compressFlag = pdfReader.isNewXrefType();
	    	//System.out.println(compressFlag);
	    	
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
			

			//if(compressFlag){
	    	//	Compression.recompressXRef(PDFFileString,PDFFileString);
	    	//	System.out.println("Recompressed " + inputFile);
	    	//}
			
			// UMR - 0
			// Period - 1
			// Insured - 2
			// Reinsured - 3
			// Premium - 4
			// Deductible - 5
			// Claims - 6
			
			int imageOutputCount = 9;
			
			//Set the titles
			String imageTitle[] = new String [imageOutputCount];
			
			imageTitle[0] = "UMR";
			imageTitle[1] = "Period";
			imageTitle[2] = "Insured";
			imageTitle[3] = "Reinsured";
			imageTitle[4] = "Premium";
			imageTitle[5] = "Claims";
			imageTitle[6] = "Limit";
			imageTitle[7] = "Sublimit";
			imageTitle[8] = "Deductibles";
			
			String wordPositionArray[][] = new String[imageOutputCount][6];
			
			//Define bbox adjustments for different images
			Double bboxAdjustment[][] = new Double[imageOutputCount][4];
			
			//UMR
			bboxAdjustment[0][0] = (double) -2000;
			bboxAdjustment[0][1] = (double) -20;
			bboxAdjustment[0][2] = (double) 2500;
			bboxAdjustment[0][3] = (double) 300;
			
			//Period
			bboxAdjustment[1][0] = (double) -2000;
			bboxAdjustment[1][1] = (double) -50;
			bboxAdjustment[1][2] = (double) 2500;
			bboxAdjustment[1][3] = (double) 200;
					
			//Insured
			bboxAdjustment[2][0] = (double) -2000;
			bboxAdjustment[2][1] = (double) -20;
			bboxAdjustment[2][2] = (double) 2500;
			bboxAdjustment[2][3] = (double) 150;
	
			//Reinsured
			bboxAdjustment[3][0] = (double) -2000;
			bboxAdjustment[3][1] = (double) -20;
			bboxAdjustment[3][2] = (double) 2500;
			bboxAdjustment[3][3] = (double) 100;
	
			//Premium
			bboxAdjustment[4][0] = (double) -2000;
			bboxAdjustment[4][1] = (double) -150;
			bboxAdjustment[4][2] = (double) 2500;
			bboxAdjustment[4][3] = (double) 200;
	
			//Claims
			bboxAdjustment[5][0] = (double) -2000;
			bboxAdjustment[5][1] = (double) -20;
			bboxAdjustment[5][2] = (double) 2500;
			bboxAdjustment[5][3] = (double) 2000;
			
			//Limit
			bboxAdjustment[6][0] = (double) -2000;
			bboxAdjustment[6][1] = (double) -200;
			bboxAdjustment[6][2] = (double) 2500;
			bboxAdjustment[6][3] = (double) 300;
			
			//Sublimit
			bboxAdjustment[7][0] = (double) -2000;
			bboxAdjustment[7][1] = (double) -200;
			bboxAdjustment[7][2] = (double) 2500;
			bboxAdjustment[7][3] = (double) 1000;
			
			//Deductible
			bboxAdjustment[8][0] = (double) -2000;
			bboxAdjustment[8][1] = (double) -20;
			bboxAdjustment[8][2] = (double) 2500;
			bboxAdjustment[8][3] = (double) 500;
	
			RegexMatch regexMatch = new RegexMatch();
			
			String matchedWords[] = new String[imageOutputCount]; 
			
			for(int d = 0; d < imageOutputCount; d++){
				matchedWords[d] = "";
			}
			
			matchedWords[0] = regexMatch.UMRSearch(OCROutput).replace("[\\u0000-\\u001f]", "\\s");
			matchedWords[1] = regexMatch.PeriodSearch(OCROutput).replace("[\\u0000-\\u001f]", "\\s");
			matchedWords[2] = regexMatch.InsuredSearch(OCROutput).replace("[\\u0000-\u001f]", "\\s");
			matchedWords[3] = regexMatch.ReinsuredSearch(OCROutput).replace("[\\u0000-\\u001f]", "\\s");
			matchedWords[4] = regexMatch.PremiumSearch(OCROutput).replace("[\\u0000-\\u001f]", "\\s");
			matchedWords[5] = regexMatch.ClaimsSearch(OCROutput).replace("[\\u0000-\\u001f]", "\\s");
			matchedWords[6] = regexMatch.LimitSearch(OCROutput).replace("[\\u0000-\\u001f]", "\\s");
			matchedWords[7] = regexMatch.SublimitSearch(OCROutput).replace("[\\u0000-\\u001f]", "\\s");
			matchedWords[8] = regexMatch.DeductiblesSearch(OCROutput).replace("[\\u0000-\\u001f]", "\\s");
			
			
			int matchedWordsLength[] = new int[imageOutputCount];
			
			for (int x = 0;x<imageOutputCount;x++){
				matchedWordsLength[x] = matchedWords[x].split(" ").length; 	
				//System.out.println("Word is " + matchedWords[x]);
			}
			
			int regexIndex = 0;
			
			for(int i=0; i < wordPositionArray.length; i++){	
	
				regexIndex = Arrays.asList(regexWordColumn).indexOf(matchedWords[i]);	
				
				regexIndex = Collections.indexOfSubList(Arrays.asList(regexWordColumn), Arrays.asList(matchedWords[i].split(" ")));
				//System.out.println(regexIndex);

				if (regexIndex > 0){		
					//Set page number so know which page to extract image from
					wordPositionArray[i][1] = HOCRArray[regexIndex][2];		
					
					//Bbox coordinates	
					wordPositionArray[i][2] = HOCRArray[regexIndex][4];
					wordPositionArray[i][3] = HOCRArray[regexIndex][5];
					wordPositionArray[i][4] = HOCRArray[regexIndex][6];
					wordPositionArray[i][5] = HOCRArray[regexIndex][7];
				}
			}
	
			//Draw text image for title
		
			int y = 100;
			int textX = 10;
			int HOCRX = 20;
			BufferedImage combinedImage = new BufferedImage(1500,1500,BufferedImage.TYPE_INT_ARGB);
			
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
	
				//String text = "Test text";
				BufferedImage textImage = new BufferedImage(150, 50, BufferedImage.TYPE_INT_ARGB);
				
				
				
				Font font = new Font("Arial Black", Font.PLAIN, 200);
				
				Graphics h = textImage.getGraphics();        
				h.setColor(Color.WHITE);
				h.fillRect(textX, y, 1000, 50);
				h = textImage.createGraphics();
				h.setColor(Color.BLACK);
		        
		        
				h.setFont(font);
		       
		        //System.out.println(h.getColor());
		        //h.setColor(Color.WHITE);
		        h = textImage.createGraphics();
		        h.drawString(imageTitle[i], 20, 20);
		        
		        //h.drawString(text + " " + i, 20, 20);
		        
		        Graphics g = combinedImage.getGraphics();
		           
		        //g.drawLine(arg0, arg1, arg2, arg3);
		        
		        g.drawImage(image, HOCRX + 100, y, null);
				g.drawImage(textImage, HOCRX, y,null);
				//ImageIO.write(image, "png", yourImageFile);
				 //y += 20 + bboxAdjustment[i][3];
				y+= 50 + bboxAdjustment[i][3]/scaleFactor;
			}
			
			}
			ImageIO.write(combinedImage,"png",new File(imageOutputFolder + imageFilename));
			System.out.println("Completed");
			
		}	
			
		else{
			System.out.println("PDF in compressed format. Image unable to be extracted.");
		}
	    	
		}

//		---------------------
 
 
}
}