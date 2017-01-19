package UnusedPackages;
import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexSearch {

	String searchRegex(String searchText, String regexString1, String regexString2, String regexString3, String regexString4,String regexFlag){
		
		//Takes the text to be searched and the regex search string and returns the required information from the text
				
		String patternMatch = "";
		
		//Replace EOL with EOL plus space to make regex matching easier
		//Not sure if regex matches over EOL. Seems inconsistent.
		//The other replacements are for strings in format 2013/14. I'm sure there's a better way than doing 1 by 1 replacements
		
		
		/*searchText = searchText.replaceAll("UMR 3", "UMR B");
		searchText = searchText.replaceAll("\n", " ");
		searchText = searchText.replaceAll("\r", " ");
		searchText = searchText.replaceAll("100%", " ");
		searchText = searchText.replaceAll("100 %", " ");
		searchText = searchText.replaceAll("%", " ");
		//searchText = searchText.replaceAll("Ist", "1st");
		//searchText = searchText.replaceAll("lst", "1st");
		searchText = searchText.replaceAll("2\\s?006/07", "2006");
		searchText = searchText.replaceAll("2007/08", "2007");
		searchText = searchText.replaceAll("2008/09", "2008");
		searchText = searchText.replaceAll("2009/10", "2009");
		searchText = searchText.replaceAll("2010/11", "2010");
		searchText = searchText.replaceAll("2011/12", "2011");
		searchText = searchText.replaceAll("2012/13", "2012");
		searchText = searchText.replaceAll("2013/14", "2013");
		searchText = searchText.replaceAll("201\\s?4/15", "2014");
		searchText = searchText.replaceAll("2015/16", "2015");
		searchText = searchText.replaceAll("2016/17", "2016");
		searchText = searchText.replaceAll("2006/2007", "2006");
		searchText = searchText.replaceAll("2007/2008", "2007");
		searchText = searchText.replaceAll("2008/2009", "2008");
		searchText = searchText.replaceAll("2009/2010", "2009");
		searchText = searchText.replaceAll("2010/2011", "2010");
		searchText = searchText.replaceAll("2011/2012", "2011");
		searchText = searchText.replaceAll("2012/2013", "2012");
		searchText = searchText.replaceAll("2013/2014", "2013");
		searchText = searchText.replaceAll("2014/2015", "2014");
		searchText = searchText.replaceAll("2015/2016", "2015");
		searchText = searchText.replaceAll("2016/2017", "2016");
		*/
		
		String patternFlag;
		Pattern r1 = Pattern.compile(regexString1);
		Matcher m1 = r1.matcher(searchText);
		//System.out.println(m1);
		
		Pattern r2 = Pattern.compile(regexString2);
		Matcher m2 = r2.matcher(searchText);
		Pattern r3 = Pattern.compile(regexString3);
		Matcher m3 = r3.matcher(searchText);
		Pattern r4 = Pattern.compile(regexString4);
		Matcher m4 = r4.matcher(searchText);
		
		
		
		if (m1.find( )) {	
			patternMatch = m1.group(0);
			//System.out.println(regexFlag + " match position is " + m1.start());
			//System.out.println(patternMatch);
			//System.out.println(m1.groupCount());
			patternFlag = "Pattern1";			
		}				
		else if (m2.find()){
					patternMatch = m2.group(0);
					patternFlag = "Pattern2";
				}
		else if (m3.find()){
					patternMatch = m3.group(0);
					patternFlag = "Pattern3";
				}
		else if (m4.find()){
					patternMatch = m4.group(0);
					patternFlag = "Pattern4";
				}
		else {patternMatch = "-1";
		return patternMatch;
		}
		
		
		
			
			//If match is found then assign match to array.
			  
		    //If looking at a year, month or day field then have to do extra string manipulation 
			//to remove the non-numeric characters
			//Not sure full Regex even possible here
			
			if (regexFlag.equals("UMR")){	
				
				/*if (m1.find()){
						patternMatch = m1.group(0);
						System.out.println("Pattern1");
					}
				else if (m2.find()){
						patternMatch = m2.group(0);
						System.out.println("Pattern2");
					}
				else{
					patternMatch = "-1";
					return patternMatch;
				}*/
			
				
				//Remove random punctuation marks
				patternMatch = patternMatch.replaceAll("\\p{P}", "");	
				//Replace first character with B
				//System.out.println(patternMatch);
				patternMatch = "B"+patternMatch.substring(1);
				//System.out.println(patternMatch);
				//Common problems caused by OCR misreading. "O" only occurs if in the middle of the string "BOW" or end of string "BD0".
				patternMatch = patternMatch.replace("O", "0");
				patternMatch = patternMatch.replace("o", "0");
				patternMatch = patternMatch.replace("B0W", "BOW");				
				patternMatch = patternMatch.replace("BD0", "BDO");
				patternMatch = patternMatch.replace("8D0", "BDO");
				
				
				//1 sometimes read as lower case l or |, I may occur elsewhere so cant be replaced. I3 may come through instead of B
				
				patternMatch = patternMatch.replace("Z", "2");
				
				patternMatch = patternMatch.replace("I3", "B");
				patternMatch = patternMatch.replace("I<", "k");
				patternMatch = patternMatch.replace("l<", "k");
				
				patternMatch = patternMatch.replace("|", "1");
				patternMatch = patternMatch.replace("l", "1");
				
				patternMatch = patternMatch.replace("BOWP1", "BOWPI");	
				
				patternMatch = patternMatch.replace("B0Q01", "B0901");			
				
				patternMatch = patternMatch.replace("B0S0Q", "B0509");
				patternMatch = patternMatch.replace("BDS0Q", "B0509");
				patternMatch = patternMatch.replace("B050Q", "B0509");
				patternMatch = patternMatch.replace("B0E0Q", "B0509");
				
				
				patternMatch = patternMatch.replace("B0S72", "B0572");	
				
				patternMatch = patternMatch.replace("B070Z", "B0702");	
				
				patternMatch = patternMatch.replace("B0TS0", "B0750");	
				patternMatch = patternMatch.replace("B07S0", "B0750");
				patternMatch = patternMatch.replace("B0T50", "B0750");
				
				patternMatch = patternMatch.replace("B0BTB", "B0878");	
				patternMatch = patternMatch.replace("B0BTS", "B0878");
				patternMatch = patternMatch.replace("B0STS", "B0878");
				patternMatch = patternMatch.replace("B0S78", "B0878");
				
				//System.out.println(patternMatch);
				
				
				
			}
				
			else if (regexFlag.equals("Inception Day")){				
				//If first match is outside the range [1,31] then try second match, else 1
				if (m1.find()){
				
				//System.out.println(patternMatch.length());
				//System.out.println("Yes");
				//System.out.println(patternMatch);
				patternMatch = patternMatch.substring(Math.max(0, patternMatch.length() - 2));		
				//patternMatch = patternMatch1;
				
				if (Integer.valueOf(patternMatch.trim()) < 1 || Integer.valueOf(patternMatch.trim()) > 31){
					if(m2.find()){
					patternMatch = m2.group(0).substring(Math.max(0, m2.group(0).length() - 2));
				}
					else{ 
					patternMatch = "-1";
					}				
				}
				}
				}
			else if (regexFlag.equals("Inception Month") ){													         	  				      
				if(patternFlag.equals("Pattern1")){
					patternMatch = m1.group(1);
				}
				else{
					patternMatch = m2.group(1);
				}							         	  				      				
			} 
			else if (regexFlag.equals("Inception Year") ){
				if (m1.find()){
				patternMatch = m1.group(0).substring(Math.max(0, m1.group(0).length() - 4));								         	  				      
				}
				else{
				patternMatch = "-1";	
				}
			} 
			
			else if (regexFlag.equals("100% Premium") ){
				//System.out.println("100% Premium");
				if(patternFlag.equals("Pattern1")){
					//
					patternMatch = m1.group(1);				
					if (Long.parseLong(patternMatch)<10000){	
						if( m2.find()){						
							patternMatch = m2.group(1);
							if (Long.parseLong(patternMatch)<10000){
								if(m3.find()){
									patternMatch = m3.group(1);
									if (Long.parseLong(patternMatch)<10000){
										if(m4.find()){
											patternMatch = m4.group(1);
										}
											else{
												patternMatch = "-1";
											}
										}
									}
								}
							}
							
						}
						
						
				
					
				
				}
											         	  				      
			} 
			
			
			
			else if (regexFlag.equals("Premium Currency") ){				
				if(patternFlag.equals("Pattern1")){
					patternMatch = m1.group(1);			
				}
				else{
					patternMatch = m2.group(1);
				}								         	  				      
			} 
			
			else if (regexFlag.equals("Expiry Day") ){
				if (m1.find()){
				patternMatch = patternMatch.substring(Math.max(0, patternMatch.length() - 2)).trim();									         	  				      
				} 
				else{
					patternMatch = "-1";
				}
				}
			else if (regexFlag.equals("Expiry Month") ){													         	  				      
				if(patternFlag.equals("Pattern1")){
					patternMatch = m1.group(1);
				}
				else{
					patternMatch = m2.group(1);
				}							         	  				      				
			} 
			else if (regexFlag.equals("Expiry Year") ){	
				if (m1.find()){
				patternMatch = patternMatch.substring(Math.max(0, patternMatch.length() - 4)).trim();									         	  				      
				} 
				else{
					patternMatch = "-1";
				}
			}
			else if (regexFlag.toLowerCase().contains("claims") ){
				
				if(patternFlag.equals("Pattern1")){
					patternMatch = m1.group(1);
				}
				else{
					//patternMatch = m2.group(2);
					patternMatch = "Nil";
				}			
				if ((patternMatch.toUpperCase().equals("NIL"))||(patternMatch.matches("20\\d\\d"))||patternMatch.matches("\\d")) {
					patternMatch = "0";
				}										
				
			
			
		      //processText[i+1][1] = m1.group(0).replaceAll("[^\\d.]", "");	
		      
		  }
		
	
		
	
		
		public String UMRSearch(patternMatch){
			patternMatch = m1.group(0);
			return patternMatch;
		}
		
	}
	
	
}

