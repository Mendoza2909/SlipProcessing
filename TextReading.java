
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.util.*;


public class TextReading {
	
	//This class works for word documents that have been converted directly to PDF. 
	//It does not work for scanned documents.
	
	
	
	String input;
	PDDocument pdDoc;
	PDFTextStripper pdfStripper;
	{
	try{
	input = ("S:\\Actuarial\\Projects\\Machine Learning\\ScannedPDF\\ReservingDoc.pdf");		//File output = new File("S:\\Actuarial\\Projects\\Machine Learning\\ScannedPDF\\WindsongText.txt"); // The text file where you are going to store the extracted data
		
	
	pdDoc = PDDocument.load(new File(input));
	pdfStripper = new PDFTextStripper();
	
   
    pdfStripper = new PDFTextStripper();

    String Text = pdfStripper.getText(pdDoc);
    //Needs to be set based on the number of pages.
    pdfStripper.setStartPage(1);
    pdfStripper.setEndPage(5);
    
    PrintWriter out = new PrintWriter("S:\\Actuarial\\Projects\\Machine Learning\\ScannedPDF\\ReservingDocText.txt");
    //out.println(Text);
    out.close();
    PrintWriter outxl = new PrintWriter("S:\\Actuarial\\Projects\\Machine Learning\\ScannedPDF\\ReservingDocXL.xls");
    //outxl.println(Text);
    outxl.close();
    
    
    
    //String Text = pdfStripper.getText(pdDoc);
    //return Text;
    //String parsedText = pdfStripper.getText(pdDoc);
    System.out.println("Output is" + Text);
} catch (IOException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
} 
	
}}
