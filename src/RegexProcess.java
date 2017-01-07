import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.FilenameUtils;

public class RegexProcess {
		
	public String[][] processText(String OCRString){
		
		//Extra columns to allow for multiple guesses
		String regexArray[][] = new String[13][5];
		
		//Number of columns
		Integer regexWidth = regexArray.length;
		
		//Will add extra column to regexArray to allow for multiple searches if the first one fails. 
		
		//Placeholder
		regexArray[0][0] = "Blank";
		//Make this longer than 5 characters?
		regexArray[0][1] = "None";
		regexArray[0][2] = "None";
		regexArray[0][3] = "None";
		regexArray[0][4] = "None";
		
		
		regexArray[1][0] = "UMR";
		//Make this longer than 5 characters?
		//Leading space might be useful
		//regexArray[1][1] = "(UMR).*?(?:B[0-9][^\\s]*[A-Za-z][^\\s]*)|(?:B[A-Za-z][^\\s]*[0-9][^\\s]*)|(?:3[0-9][^\\s]*[A-Za-z][^\\s]*)|(?:3[A-Za-z][^\\s]*[0-9][^\\s]*)|(?:8[0-9][^\\s]*[A-Za-z][^\\s]*)|(?:8[A-Za-z][^\\s]*[0-9][^\\s]*)";
		regexArray[1][1] = "(?:B[0-9][^\\s]*[A-Za-z][^\\s]*)|(?:B[A-Za-z][^\\s]*[0-9][^\\s]*)";
		//regexArray[1][1] = "(?:B[0-9][^\\s]*[A-Za-z][^\\s]*)|(?:B[A-Za-z][^\\s]*[0-9][^\\s]*)|(?:3[0-9][^\\s]*[A-Za-z][^\\s]*)|(?:3[A-Za-z][^\\s]*[0-9][^\\s]*)|(?:8[0-9][^\\s]*[A-Za-z][^\\s]*)|(?:8[A-Za-z][^\\s]*[0-9][^\\s]*)";
		
		regexArray[1][2] = "(?:B[0-9][^\\s]*[A-Za-z][^\\s]*)|(?:B[A-Za-z][^\\s]*[0-9][^\\s]*)|(?:3[0-9][^\\s]*[A-Za-z][^\\s]*)|(?:3[A-Za-z][^\\s]*[0-9][^\\s]*)|(?:8[0-9][^\\s]*[A-Za-z][^\\s]*)|(?:8[A-Za-z][^\\s]*[0-9][^\\s]*)";
		regexArray[1][3] = "None";
		regexArray[1][4] = "None";
		
		//regexArray[1][2] = "(?:\\s3[0-9][^\\s]*[A-Za-z][^\\s]*)|(?:\\s3[A-Za-z][^\\s]*[0-9][^\\s]*)";
		
		
		//regexArray[0][2] = "(?:B[0-9][^\\s]*[A-Za-z][^\\s]*){5,}|(?:B[A-Za-z][^\\s]*[0-9][^\\s]*)";
		
		regexArray[2][0] = "Inception Day";
		regexArray[2][1] = "(?<=((?i)Period.(?-i)|(?i)Effecti(?-i)|(?i)Commenc(?-i))).*?\\s\\d{1,2}";		
		//If the first occurrence of the word does not work, the second one seems to be useful.
		regexArray[2][2] = "((?i)Period.(?-i)|(?i)Effecti(?-i)|(?i)Commenc(?-i)).*?(((?i)Period.(?-i)|(?i)Effecti(?-i)|(?i)Commenc(?-i)).*?\\s\\d{1,2})";
		regexArray[2][3] = "None";
		regexArray[2][4] = "None";
		
		regexArray[3][0] = "Inception Month";
		regexArray[3][1] =  "((?i)January(?-i)|(?i)February(?-i)|(?i)March(?-i)|(?i)April(?-i)|M(?i)ay(?-i)|(?i)June(?-i)|(?i)July(?-i)|(?i)August(?-i)|(?i)September(?-i)|(?i)October(?-i)|(?i)November(?-i)|(?i)December(?-i))";
		regexArray[3][2] =  "((?i)January(?-i)|(?i)February(?-i)|(?i)March(?-i)|(?i)April(?-i)|M(?i)ay(?-i)|(?i)June(?-i)|(?i)July(?-i)|(?i)August(?-i)|(?i)September(?-i)|(?i)October(?-i)|(?i)November(?-i)|(?i)December(?-i))";
		regexArray[3][3] = "None";
		regexArray[3][4] = "None";
		
		regexArray[4][0] = "Inception Year";
		regexArray[4][1] = "(?<=((?i)Period.(?-i)|(?i)Effecti(?-i)|(?i)Commenc(?-i))).*?20\\d{2}";
		regexArray[4][2] = "None";
		regexArray[4][3] = "None";
		regexArray[4][4] = "None";
		
		regexArray[5][0] = "Expiry Day";
		regexArray[5][1] = "(?<=((?i)Period.(?-i)|(?i)Effecti(?-i)|(?i)Commenc(?-i))).*?2\\d{3}.*?\\d{1,2}";
		regexArray[5][2] = "(?<=((?i)Period.(?-i)|(?i)Effecti(?-i)|(?i)Commenc(?-i))).*?2\\d{3}.*?\\d{1,2}";
		regexArray[5][3] = "None";
		regexArray[5][4] = "None";
		
		regexArray[6][0] = "Expiry Month";
		regexArray[6][1] = "(?:(?i)January(?-i)|(?i)February(?-i)|(?i)March(?-i)|(?i)April(?-i)|M(?i)ay(?-i)|(?i)June(?-i)|(?i)July(?-i)|(?i)August(?-i)|(?i)September(?-i)|(?i)October(?-i)|(?i)November(?-i)|(?i)December(?-i)).*?((?i)January(?-i)|(?i)February(?-i)|(?i)March(?-i)|(?i)April(?-i)|(?i)May(?-i)|(?i)June(?-i)|(?i)July(?-i)|(?i)August(?-i)|(?i)September(?-i)|(?i)October(?-i)|(?i)November(?-i)|(?i)December(?-i))";
		regexArray[6][2] = "None";
		regexArray[6][3] = "None";
		regexArray[6][4] = "None";
		
		//Test
		//regexArray[5][2] =  "(?:Jan(?:uary)?|Feb(?:ruary)?|Mar(?:ch)?|Apr(?:il)?|May|Jun(?:e)?|Jul(?:y)?|Aug(?:ust)?|Sep(?:tember)?|Oct(?:ober)?|Nov(?:ember)?|Dec(?:ember)?).*?(?:Jan(?:uary)?|Feb(?:ruary)?|Mar(?:ch)?|Apr(?:il)?|May|Jun(?:e)?|Jul(?:y)?|Aug(?:ust)?|Sep(?:tember)?|Oct(?:ober)?|Nov(?:ember)?|Dec(?:ember)?)";
		
		regexArray[7][0] = "Expiry Year";
		regexArray[7][1] = "(?<=((?i)Period.(?-i)|(?i)Effecti(?-i)|(?i)Commenc(?-i))).*?2\\d{3}.*?2\\d{3}";
		regexArray[7][2] = "None";
		regexArray[7][3] = "None";
		regexArray[7][4] = "None";
		
		regexArray[8][0] = "Insured Name";
		regexArray[8][1] = "None";
		regexArray[8][2] = "None";
		regexArray[8][3] = "None";
		regexArray[8][4] = "None";
		
		regexArray[9][0] = "Reinsured Name";
		regexArray[9][1] = "None";
		regexArray[9][2] = "None";
		regexArray[9][3] = "None";
		regexArray[9][4] = "None";
		
		regexArray[10][0] = "100% Premium";
		regexArray[10][1] = "(?<=PREMIUM).*?(\\d{4,})";
		regexArray[10][2] = "(?<=Premium).*?(\\d{4,})";
		regexArray[10][3] = "(?<=PREMIUM).*?100.*?(\\d{4,})";
		regexArray[10][4] = "(?<=Premium).*?100.*?(\\d{4,})";
		
		
		
		regexArray[11][0] = "Premium Currency";
		regexArray[11][1] = "(?<=(?i)PREMIUM(?-i)).*?([A-Z]{3})";
		regexArray[11][2] = "(?<=(?i)PREMIUM(?-i)).*?100.*?([A-Z]{3})";
		regexArray[11][3] = "None";
		regexArray[11][4] = "None";
				
		regexArray[12][0] = "Claims";
		regexArray[12][1] = "(?<=((?i)Loss Record.(?-i)|(?i)Loss History(?-i)))";		
		regexArray[12][2] = "None";
		regexArray[12][3] = "None";
		regexArray[12][4] = "None";
		
		/*regexArray[13][0] = "2006 Claims";
		regexArray[13][1] = "(?<=((?i)Loss Record.(?-i)|(?i)Loss History(?-i))).*?2006.*?(\\d+|(?i)Nil(?-i))";		
		regexArray[13][2] = "None";
		regexArray[13][3] = "None";
		regexArray[13][4] = "None";
		
		regexArray[14][0] = "2007 Claims";
		regexArray[14][1] = "(?<=((?i)Loss Record.(?-i)|(?i)Loss History(?-i))).*?2007.*?(\\d+|(?i)Nil(?-i))";		
		regexArray[14][2] = "None";
		regexArray[14][3] = "None";
		regexArray[14][4] = "None";
		
		regexArray[15][0] = "2008 Claims";
		regexArray[15][1] = "(?<=((?i)Loss Record.(?-i)|(?i)Loss History(?-i))).*?2008.*?(\\d+|(?i)Nil(?-i))";		
		regexArray[15][2] = "None";
		regexArray[15][3] = "None";
		regexArray[15][4] = "None";
		
		regexArray[16][0] = "2009 Claims";
		regexArray[16][1] = "(?<=((?i)Loss Record.(?-i)|(?i)Loss History(?-i))).*?2009.*?(\\d+|(?i)Nil(?-i))";		
		regexArray[16][2] = "None";
		regexArray[16][3] = "None";
		regexArray[16][4] = "None";
		
		regexArray[17][0] = "2010 Claims";
		regexArray[17][1] = "(?<=((?i)Loss Record.(?-i)|(?i)Loss History(?-i))).*?2010.*?(\\d+|(?i)Nil(?-i))";		
		regexArray[17][2] = "None";
		regexArray[17][3] = "None";
		regexArray[17][4] = "None";
		
		regexArray[18][0] = "2011 Claims";
		regexArray[18][1] = "(?<=((?i)Loss Record.(?-i)|(?i)Loss History(?-i))).*?2011.*?(\\d+|(?i)Nil(?-i))";		
		regexArray[18][2] = "None";
		regexArray[18][3] = "None";
		regexArray[18][4] = "None";
		
		regexArray[19][0] = "2012 Claims";
		regexArray[19][1] = "(?<=((?i)Loss Record.(?-i)|(?i)Loss History(?-i))).*?2012.*?(\\d+|(?i)Nil(?-i))";		
		regexArray[19][2] = "None";
		regexArray[19][3] = "None";
		regexArray[19][4] = "None";
		
		regexArray[20][0] = "2013 Claims";
		regexArray[20][1] = "(?<=((?i)Loss Record.(?-i)|(?i)Loss History(?-i))).*?2013.*?(\\d+|(?i)Nil(?-i))";		
		regexArray[20][2] = "None";
		regexArray[20][3] = "None";
		regexArray[20][4] = "None";
		
		regexArray[21][0] = "2014 Claims";
		regexArray[21][1] = "(?<=((?i)Loss Record.(?-i)|(?i)Loss History(?-i))).*?2014.*?(\\d+|(?i)Nil(?-i))";		
		regexArray[21][2] = "None";
		regexArray[21][3] = "None";
		regexArray[21][4] = "None";
				
		regexArray[22][0] = "2015 Claims";
		regexArray[22][1] = "(?<=((?i)Loss Record.(?-i)|(?i)Loss History(?-i))).*?2015.*?(\\d+|(?i)Nil(?-i))";		
		regexArray[22][2] = "None";
		regexArray[22][3] = "None";
		regexArray[22][4] = "None";
		
		regexArray[23][0] = "2016 Claims";
		regexArray[23][1] = "(?<=((?i)Loss Record.(?-i)|(?i)Loss History(?-i))).*?2016.*?(\\d+|(?i)Nil(?-i))";		
		regexArray[23][2] = "None";
		regexArray[23][3] = "None";
		regexArray[23][4] = "None";
		*/

		
		
		//String attempt = "(?<=((?i)LOSS RECORD(?-i)|(?i)LOSS HISTOR(?-i))).*?2014\s*(\d+)\s*2015"
		
		//File inputfiles = new File(inputPath);	    
	    //File [] files = inputfiles.listFiles();
	    
	    //Used for defining a set of ASCII characters for Regex search
	    //Charset charset = Charset.forName("ISO-8859-1");
		
	    //Used for calculating success rate of Regex search
	    //double success = 0;
	    
	    //Extra row for column header and extra column for filename
	    String[][] processText = new String[2][regexWidth];
	    
	    //Set column headings
	    /*processText[0][0] = "File Name";
	    processText[0][1] = "UMR";
	    processText[0][2] = "Inception Day";	
	    processText[0][3] = "Inception Month";
	    processText[0][4] = "Inception Year";
	    processText[0][5] = "Expiry Day";	
	    processText[0][6] = "Expiry Month";
	    processText[0][7] = "Expiry Year";
	    processText[0][8] = "Insured";
	    processText[0][9] = "Reinsured";
	    processText[0][10] = "100% Premium";
	    processText[0][11] = "Premium Currency";	    
	    processText[0][12] = "2005 Claims";
	    processText[0][13] = "2006 Claims";
	    processText[0][14] = "2007 Claims";
	    processText[0][15] = "2008 Claims";
	    processText[0][16] = "2009 Claims";
	    processText[0][17] = "2010 Claims";
	    processText[0][18] = "2011 Claims";
	    processText[0][19] = "2012 Claims";
	    processText[0][20] = "2013 Claims";
	    processText[0][21] = "2014 Claims";
	    processText[0][22] = "2015 Claims";
	    processText[0][23] = "2016 Claims";
	    */

		
		//System.out.println(FilenameUtils.getBaseName(files[i].toString()));
		
	    String[] OCRDelimited = OCRString.split(" ");
	    
	    for (int j = 0; j < regexWidth; j++) {
			RegexSearch regexSearch = new RegexSearch();
			
			processText[0][j] = regexSearch.searchRegex(OCRString, regexArray[j][1], regexArray[j][2],regexArray[j][3], regexArray[j][4], regexArray[j][0]);
			
			for (int k = 0; k < OCRDelimited.length; k++) {
		        if (OCRDelimited[k].equals(processText[0][j])) {
		            //System.out.println("The word " + processText[0][j] + " is in the position: " + k);
		            processText[1][j] = Integer.toString(k);
		        	break;            
		        }
		    }
			
			
			//System.out.println("Text is" + processText[1][j]);
	    } 
	    //System.out.println(processText[1][1]);
	    return processText;
	 	}
	    
	    //Initialise regexSearch	    
	    
	    /*for (int i = 0; i < files.length; i++){
	    	
	        if (files[i].isFile()){ //this line weeds out other directories/folders
	        	//System.out.println(files[i]);
	        	//Set file name to put in column 1 of Excel file
	        	processText[i+1][0] = FilenameUtils.getBaseName(files[i].toString());
	        			
	        	for (int j = 1; j < regexWidth; j++)
				try {
					
					
					String testFileString = files[i].getPath();
					String testFile = readFile(testFileString,charset);
					
					//System.out.println(FilenameUtils.getBaseName(files[i].toString()));
					
					regexSearch regexSearch = new regexSearch();
					processText[i+1][j] = regexSearch.searchRegex(testFile, regexArray[j][1], regexArray[j][2],regexArray[j][3], regexArray[j][4], regexArray[j][0]);
			    
					
				    }			         			
				 catch (IOException e) {
					e.printStackTrace();
				} 
		
	        }
	    }*/
				
	    //Calculate success rate of files processed. Match may not be correct!
	    //System.out.println("rate = " + success/files.length);

	    //Return output array				
	 
		
		//Function to read file to one long string for purposes of Regex matching
		static String readFile(String path, Charset encoding) 
		  throws IOException 
		{
		  byte[] encoded = Files.readAllBytes(Paths.get(path));
		  return new String(encoded, encoding);
		}

}
