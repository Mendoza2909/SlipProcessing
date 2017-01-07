import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class RegexMatchNew {
		
	
	
	String UMRRegex = "(?:B[0-9][^\\s]*[A-Za-z][^\\s]*)|(?:B[A-Za-z][^\\s]*[0-9][^\\s]*)";
	String PeriodRegex = "(?<=((?i)Period.(?-i)|(?i)Effecti(?-i)|(?i)Commenc(?-i)))";	
	String InsuredRegex = "None";
	String ReinsuredRegex = "None";
	String PremiumRegex3 = "(?<=(?i)PREMIUM(?-i)).*?(\\d{4,})";
	String PremiumRegex4 = "(?<=(?i)PREMIUM(?-i)).*?100.*?(\\d{4,})";
	String PremiumRegex1 = "(?<=(?i)PREMIUM(?-i)).*?100.*?([A-Z]{3})";
	String PremiumRegex2 = "(?<=(?i)PREMIUM(?-i)).*?([A-Z]{3})";
	String ClaimsRegex = "(?<=((?i)Loss Record.(?-i)|(?i)Loss History(?-i)))";
	
	
	String patternMatch = "";
	Pattern r1; 
	Pattern r2; 
	Pattern r3; 
	Pattern r4; 
	
	Matcher m1;
	Matcher m2;
	Matcher m3;
	Matcher m4;
	

	
	String UMRSearch(String searchText){
		
		String regexString = UMRRegex;
		r1 = Pattern.compile(regexString);
		Matcher m1 = r1.matcher(searchText);
		if (m1.find( )) {	
			patternMatch = m1.group(0);
		}
		return patternMatch;
	}
	
	String PeriodSearch(String searchText){
		
		String regexString = PeriodRegex;
		r1 = Pattern.compile(regexString);
		Matcher m1 = r1.matcher(searchText);
		if (m1.find()) {	
			patternMatch = m1.group(1);
			//System.out.println("PeriodMatch");
			//System.out.println(m1.group(1));
		}
		return patternMatch;
	}
	
	String InsuredSearch(String searchText){
			
			String regexString = InsuredRegex;
			r1 = Pattern.compile(regexString);
			Matcher m1 = r1.matcher(searchText);
			if (m1.find( )) {	
				patternMatch = m1.group(0);
			}
			return patternMatch;
		}

	String ReinsuredSearch(String searchText){
		
		String regexString = ReinsuredRegex;
		r1 = Pattern.compile(regexString);
		Matcher m1 = r1.matcher(searchText);
		if (m1.find( )) {	
			patternMatch = m1.group(0);
			//System.out.println("RIMatch");
		}
		return patternMatch;
	}
		
	String PremiumSearch(String searchText){
			
			String regexString1 = PremiumRegex1;
			r1 = Pattern.compile(regexString1);
			Matcher m1 = r1.matcher(searchText);
			String regexString2 = PremiumRegex2;
			r2 = Pattern.compile(regexString2);
			Matcher m2 = r2.matcher(searchText);
			String regexString3 = PremiumRegex3;
			r3 = Pattern.compile(regexString3);
			Matcher m3 = r3.matcher(searchText);
			String regexString4 = PremiumRegex4;
			r4 = Pattern.compile(regexString4);
			Matcher m4 = r4.matcher(searchText);
			
			if (m1.find( )) {	
				//System.out.println("PremMatch");
				patternMatch = m1.group(0);
				//System.out.println("1 " + m1.group(1));
			}
			else
			if (m2.find( )) {	
				//System.out.println("PremMatch");
				patternMatch = m2.group(0);
				//System.out.println("2 " + m2.group(1));
			}
			else	
			if (m3.find( )) {	
				//System.out.println("PremMatch");
				patternMatch = m3.group(0);
				//System.out.println("3 " + m3.group(0));
			}
			else
			if (m4.find( )) {	
				//System.out.println("PremMatch");
				patternMatch = m4.group(0);
				//System.out.println("4 " + m4.group(0));
			}			
			return patternMatch;
		}
	
	String ClaimsSearch(String searchText){
			
			String regexString = ClaimsRegex;
			r1 = Pattern.compile(regexString);
			Matcher m1 = r1.matcher(searchText);
			if (m1.find( )) {	
				patternMatch = m1.group(1);
			}
			return patternMatch;
		}
		
	
}
