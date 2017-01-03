import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

import javax.imageio.ImageIO;

import HOCR.*;
import net.sourceforge.tess4j.TesseractException;

public class SlipProcessingMain {

	public static void main(String[] args) throws TesseractException, IOException {
		// TODO Auto-generated method stub
		
		
		
		String inputFolder = "C:/Users/Colin/Documents/PDF Conversion/Input Folder";
		//String outputFolder = "C:/Users/Colin/Documents/PDF Conversion/Output Folder";
		String PDFFileString = "C:/Users/Colin/Documents/PDF Conversion/Input Folder/Abcourt Mining 2016 Slip.pdf";
		

		//OpticalRecognitionMain OCRMain = new OpticalRecognitionMain();
		//OpticalRecognitionMain.OpticalProcess( inputFolder, outputFolder);
		
		
		//Convert PDF to text
		//Initialise class
		//OCRConversion OCRConversion = new OCRConversion();
		
		//Convert, comment out
		//String HOCROutput = OCRConversion.HOCROutput(inputFileString);
		//String OCROutput = OCRConversion.OCROutput(inputFileString);
		//Comment out
		
		String OCRFileString = "C:/Users/Colin/Documents/PDF Conversion/Input Folder/Abcourt Mining 2016 SlipOCR.txt";
		String HOCRFileString = "C:/Users/Colin/Documents/PDF Conversion/Input Folder/Abcourt Mining 2016 SlipHOCR.txt";
		
		String OCROutput,HOCROutput;
		
		OCROutput = new Scanner(new File(OCRFileString)).useDelimiter("\\Z").next();
		HOCROutput = new Scanner(new File(HOCRFileString)).useDelimiter("\\Z").next();
		
		
		//Print to text file, comment out below here
		/*
		//File imageFile = new File("S:\\Actuarial\\Projects\\Machine Learning\\ScannedPDF\\ScannedExample1Short.pdf");  
		String HOCRoutputTextFile = inputFileString.replaceAll(".pdf","HOCR.txt");
		String OCRoutputTextFile = inputFileString.replaceAll(".pdf","OCR.txt");
		
		try {
			//PrintWriter HOCROut = new PrintWriter(HOCROutputFile.toString());
			PrintWriter HOCROut = new PrintWriter(HOCRoutputTextFile);		
			HOCROut.println(HOCROutput);
			HOCROut.close();
			System.out.println("HOCR Output printed.");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			//PrintWriter OCROut = new PrintWriter(OCROutputFile.toString());
			PrintWriter OCROut = new PrintWriter(OCRoutputTextFile);		
			OCROut.println(OCROutput);
			OCROut.close();
			System.out.println("OCR Output printed.");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		//For printout to text file comment out above here
		
		
		RegexProcess regexProcess = new RegexProcess();
		String[][] regexArray = regexProcess.processText(OCROutput);
		
		/*for(int i=0; i<regexArray.length; i++){
			for(int j=0; j<regexArray[0].length; j++){
				System.out.println(regexArray[i][j]);
			}
		}*/
		
		//System.out.println(regexArray[0][1]);
		
		//Create HOCR Parser, get the array, use number to get relevant bbox, feed bbox into image extractor, 
		//look at getting word and image side by side on page and formatting correctly
		
		HOCRParser HOCRParser = new HOCRParser();
		
		String HOCRArray[][] = HOCR.HOCRParser.HOCRParseArray(HOCROutput);  
		
		//System.out.println(HOCRArray.length);
		//System.out.println(HOCRArray[0].length);
		
		//System.out.println(HOCRArray[1][1]);
		
		//Get column of words only
		String regexWordColumn[] = new String[HOCRArray.length];
		for(int i=0; i<HOCRArray.length; i++){		
			regexWordColumn[i] = HOCRArray[i][1];
			
		}
		/*for(int i=0; i<regexWordColumn.length; i++){		
			System.out.println(regexWordColumn[i]);
		}*/
		
		
		ImageExtraction imageExtraction = new ImageExtraction();
		
		double HOCRPageHeight = HOCR.HOCRParser.pageHeight(HOCROutput);
		double HOCRPageWidth = HOCR.HOCRParser.pageWidth(HOCROutput);
		
		double PDFPageHeight = imageExtraction.pageHeight(PDFFileString);
		double PDFPageWidth = imageExtraction.pageWidth(PDFFileString);
		
		double scaleFactor = HOCRPageHeight/PDFPageHeight;
		//double scaleFactor2 = HOCRPageWidth/PDFPageWidth;
		
		
		//System.out.println("ScaleFactor1 " + scaleFactor1);
		//System.out.println("ScaleFactor2 " + scaleFactor2);
		
		
		//System.out.println("Regex word is " + regexWordColumn[2]);
		
		//Position of match, can plug back into HOCR array to get bbox coordinates and scale factor
		//int regexIndex = Arrays.asList(regexWordColumn).indexOf(regexArray[0][1]);
		
		/*System.out.println("Regex index is " + regexIndex);
		System.out.println("Regex word is " + regexArray[0][1]);
		System.out.println("Bbox1 is " + HOCRArray[regexIndex][4]);
		*/
		//Bbox1Posn = 
		
		
		String wordPositionArray[][] = new String[23][6];
		
		//System.out.println("regexArray Length is " + regexArray.length);
		
		for(int i=0; i<regexArray[0].length-1; i++){
			
			
			//Write down all the array dimensions and fix this
			
//-------------------------------------------------------------------------------------------------
			/*System.out.println("regexArray 0 is " + regexArray[i-1][0]);
			System.out.println("regexArray 1 is " + regexArray[i-1][1]);
			System.out.println("regexArray 2 is " + regexArray[i-1][2]);
			System.out.println("regexArray 3 is " + regexArray[i-1][3]);
			System.out.println("regexArray 4 is " + regexArray[i-1][4]);
			*/
			
			wordPositionArray[i][0] = regexArray[0][i];
			//System.out.println("regexWord is " + regexArray[0][i-1]);
			int regexIndex = Arrays.asList(regexWordColumn).indexOf(regexArray[0][i]);
			//System.out.println("regexIndex is " + regexIndex);
			
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
		
		//System.out.println("Height" + wordPositionArray.length);
		//System.out.println("Width" + wordPositionArray[0].length);
		
		/*for(int i=0; i<wordPositionArray.length; i++){
			for(int j=0; j<wordPositionArray[0].length; j++){
				System.out.println(wordPositionArray[i][j]);
			}
		*/
		
		//Draw text image for title
		
		int y = 10;
		int textX = 10;
		
		
		
		//g.drawImage(null, 0, 0, Color.BLACK, null);
		//g.setColor(Color.WHITE);
		
		int HOCRX = 200;
		BufferedImage combinedImage = new BufferedImage(2500,3000,BufferedImage.TYPE_INT_ARGB);
		
		for(int i=0; i<wordPositionArray.length; i++){ 
		
			
		//System.out.println("Value 1 is" + wordPositionArray[i][1]);
		if (wordPositionArray[i][1] != null){
		int pageNumber = Integer.parseInt(wordPositionArray[i][1]);
		double bbox1 = Double.parseDouble(wordPositionArray[i][2]);
		double bbox2 = Double.parseDouble(wordPositionArray[i][3]);
		double bbox3 = Double.parseDouble(wordPositionArray[i][4]);
		double bbox4 = Double.parseDouble(wordPositionArray[i][5]);
		
		
		//Draw HOCR image
		//Get bbox coordinates from wordPosition array and get bboxPageHeight
		BufferedImage image = imageExtraction.Image(PDFFileString, pageNumber, bbox1, bbox2, bbox3, bbox4, HOCRPageHeight, scaleFactor);
		File yourImageFile = new File("C:/Users/Colin/Documents/PDF Conversion/Input Folder/Abcourt Mining 2016 Slip "+"picture_" + i + ".png");
        
		
		Graphics g = combinedImage.getGraphics();
		
		String text = "Test text";
		
		BufferedImage textImage = new BufferedImage(200, 50, BufferedImage.TYPE_INT_ARGB);
		Graphics h = textImage.getGraphics();
        Font font = new Font("Arial", Font.PLAIN, 100);
        h.setFont(font);
        //FontMetrics fm = h.getFontMetrics();
        //int textImageWidth = fm.stringWidth(text);
        //int textImageHeight = fm.getHeight();
        
        
        h = textImage.createGraphics();
        //g.drawString(text, 0, fm.getAscent());
        h.drawString(text, 20, 20);
        
        //g.drawImage(combinedImage, textX , y, null);
        

        g.drawImage(image, HOCRX+200, y, null);
		g.drawImage(textImage, HOCRX, y,null);
		//ImageIO.write(image, "png", yourImageFile);
		 y += 50;
		
		}
		
		
		}
		ImageIO.write(combinedImage,"png",new File("C:/Users/Colin/Documents/PDF Conversion/Input Folder/Abcourt Mining 2016 Slip Combined.png"));
		System.out.println("Completed");
	}	
}


