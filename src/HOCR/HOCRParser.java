package HOCR;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HOCRParser {

	public static String[][] HOCRParseArray(String inputTextFile) throws IOException {
		
				
		//String inputFileStringText = "C:/Users/Colin/Documents/PDF Conversion/Input Folder/Abcourt Mining 2016 Slip HOCR Output.txt";
		//String inputFileStringPDF = "C:/Users/Colin/Documents/PDF Conversion/Input Folder/Abcourt Mining 2016 Slip.pdf";
		
		String html = inputTextFile;
	    
		Document doc = Jsoup.parse(html);
		
		Elements wordElements = (Elements) doc.getElementsByClass("ocrx_word");
			//System.out.println(wordElements.toString()); 
		//Sample output is <span class="ocrx_word" id="word_17_109" title="bbox 2199 1575 2267 1606; x_wconf 88">JLT</span>
		//Use element.ownText to return "JLT"
		//Use regex matches below to return the page number, word number on page, 4 bbox components
		
		//Brackets required as I dont know how to get full match instead of group
		
		//String pageDimRegex = "\\s0\\s0\\s(\\d{1,5})\\s(\\d{1,5})";
			
		String pageNumRegex = "(\\d{1,3})";
		String wordNumRegex = "(\\d_(\\d{1,5}))";
		String bboxRegex1 = "^(?:[^ ]*\\ ){4}([^ ]\\d{1,5})";
		String bboxRegex2 = "^(?:[^ ]*\\ ){5}([^ ]\\d{1,5})";
		String bboxRegex3 = "^(?:[^ ]*\\ ){6}([^ ]\\d{1,5})";
		String bboxRegex4 = "^(?:[^ ]*\\ ){7}([^ ]\\d{1,5})";

		//Get word count
		int docWordCount=0;
		for (Element element : wordElements) {		
			   //System.out.println(element.ownText());
			   docWordCount++;
		}	
		
		//Array of page number, total word count, word count on page, 4 x bbox coordinates 
		String[][] wordAttributes = new String[docWordCount+1][8]; 
		
		wordAttributes[0][0] = "Word Count";
		wordAttributes[0][1] = "Word";
		wordAttributes[0][2] = "Page Number";
		wordAttributes[0][3] = "Page Word Count";
		wordAttributes[0][4] = "bbox1";
		wordAttributes[0][5] = "bbox2";
		wordAttributes[0][6] = "bbox3";
		wordAttributes[0][7] = "bbox4";
		
		int arrayCounter = 1;
		for (Element element : wordElements) {	

			String searchText = element.toString();

			Pattern pageNumPatt = Pattern.compile(pageNumRegex);
			Pattern wordNumPatt = Pattern.compile(wordNumRegex);
			Pattern bboxPatt1 = Pattern.compile(bboxRegex1);
			Pattern bboxPatt2 = Pattern.compile(bboxRegex2);
			Pattern bboxPatt3 = Pattern.compile(bboxRegex3);
			Pattern bboxPatt4 = Pattern.compile(bboxRegex4);
			
			Matcher pageNumMatch = pageNumPatt.matcher(searchText);
			Matcher wordNumMatch = wordNumPatt.matcher(element.toString());
			Matcher bboxMatch1 = bboxPatt1.matcher(element.toString());
			Matcher bboxMatch2 = bboxPatt2.matcher(element.toString());
			Matcher bboxMatch3 = bboxPatt3.matcher(element.toString());
			Matcher bboxMatch4 = bboxPatt4.matcher(element.toString());
					
			wordAttributes[arrayCounter][0] = Integer.toString(arrayCounter);
			wordAttributes[arrayCounter][1] = element.text();			
			
			if (pageNumMatch.find( )) {	
				//patternMatch = pageNumMatch.group(1);
				//System.out.println(patternMatch);
				//System.out.println(pageNumMatch.groupCount());
				wordAttributes[arrayCounter][2] = pageNumMatch.group(1);			
			}			
			if (wordNumMatch.find( )) {	
				wordAttributes[arrayCounter][3] = wordNumMatch.group(2);		
			}			
			if (bboxMatch1.find( )) {	
				wordAttributes[arrayCounter][4] = bboxMatch1.group(1);		
			}
			if (bboxMatch2.find( )) {	
				wordAttributes[arrayCounter][5] = bboxMatch2.group(1);		
			}
			if (bboxMatch3.find( )) {	
				wordAttributes[arrayCounter][6] = bboxMatch3.group(1);		
			}
			if (bboxMatch4.find( )) {	
				wordAttributes[arrayCounter][7] = bboxMatch4.group(1);		
			}
	
			arrayCounter++;
		}
		return wordAttributes;
		}
	


public static double pageHeight(String inputHOCRText) throws FileNotFoundException{
	
	//System.out.println(inputHOCRText);
	String pageDimRegex = "\\s0\\s0\\s(\\d{1,5})\\s(\\d{1,5})";
	String html;
	//html = new Scanner(new File(fileName)).useDelimiter("\\Z").next();    
	html = inputHOCRText;
	Document doc = Jsoup.parse(html);
	Element pageDim = (Element) doc.getElementById("page_1");	
	Pattern pageDimPatt = Pattern.compile(pageDimRegex);
	Matcher pageDimMatch = pageDimPatt.matcher(pageDim.toString());		
	
	if (pageDimMatch.find( )){
		double PageHeight = Double.parseDouble(pageDimMatch.group(2));	
		return PageHeight;
	}
	else{
		return 0;
	}
}



public static double pageWidth(String inputHOCRText) throws FileNotFoundException{
	
	String pageDimRegex = "\\s0\\s0\\s(\\d{1,5})\\s(\\d{1,5})";
	String html;
	//html = new Scanner(new File(fileName)).useDelimiter("\\Z").next();    
	html = inputHOCRText;
	Document doc = Jsoup.parse(html);
	Element pageDim = (Element) doc.getElementById("page_1");	
	Pattern pageDimPatt = Pattern.compile(pageDimRegex);
	Matcher pageDimMatch = pageDimPatt.matcher(pageDim.toString());		
	
	if (pageDimMatch.find( )){
		double PageWidth = Double.parseDouble(pageDimMatch.group(1));	
		return PageWidth;
	}
	else{
		return 0;
	}
}



}
