import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class RegexMatch {
		
	//Set Regex search strings here.
	
	String UMRRegex1 = "\\s((?:B[0-9][^\\s]*[A-Za-z][^\\s]*)|(?:B[A-Za-z][^\\s]*[0-9][^\\s]*))";
	String UMRRegex2 = "\\s((?:3[0-9][^\\s]*[A-Za-z][^\\s]*)|(?:3[A-Za-z][^\\s]*[0-9][^\\s]*))";
	String UMRRegex3 = "\\s((?:8[0-9][^\\s]*[A-Za-z][^\\s]*)|(?:8[A-Za-z][^\\s]*[0-9][^\\s]*))";
	
	String PeriodRegex = "((?i)Period.(?-i)|(?i)Effecti(?-i)|(?i)Commenc(?-i))(\\S+)?";	
	
	String InsuredRegex = "I(?i)nsured(?i)(\\S+)?";
	
	String ReinsuredRegex = "R(?i)einsured(?i)(\\S+)?";
	
	String DeductiblesRegex = "D(?i)eductible(?i)(\\S+)?";
	
	String LimitRegex = "L(?i)imit(?i)(\\s+)|L(?i)imits(?i)(\\s+)|L(?i)imits(?i)";
	
	String SublimitsRegex = "S(?i)ublimit(?i)(\\S+)?|S(?i)ub-limit(?i)(\\S+)?";
	
	/*String PremiumRegex3 = "(?<=P(?i)REMIUM(?-i)).*?(\\d{4,})";
	String PremiumRegex4 = "(?<=P(?i)REMIUM(?-i)).*?100.*?(\\d{4,})";
	String PremiumRegex1 = "(?<=P(?i)REMIUM(?-i)).*?100.*?([A-Z]{3})";
	String PremiumRegex2 = "(?<=P(?i)REMIUM(?-i)).*?([A-Z]{3})";
	*/
	String PremiumRegex4 = "P(?i)REMIUM(?-i).*?(\\d{4,})(\\S+)?";
	//String PremiumRegex4 = "(P(?i)REMIUM(?-i).*?100.*?(\\d{4,}))\\s";
	//String PremiumRegex1 = "(P(?i)REMIUM(?-i).*?100.*?([A-Z]{3}))\\s";
	String PremiumRegex1 = "P(?i)REMIUM(?-i).*?100(\\S+)?";
	String PremiumRegex2 = "P(?i)REMIUM(?-i).*?100.*?(\\d{4,})(\\S+)?";
	String PremiumRegex3 = "P(?i)REMIUM(?-i).*?([A-Z]{3})(\\S+)?";
	
	String ClaimsRegex1 = "(?i)Loss Recor(?-i)\\S+|(?i)Loss Histo(?-i)\\S+|(?i)Claims Histo(?-i)\\S+|(?i)Claims recor(?-i)\\S+|(?i)Claims detai(?-i)\\S+";
	String ClaimsRegex2 = "LOSSE\\S+";

	
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
		String patternMatch = "";
		//Need to initialise in each method otherwise value is remembered.
		String regexString1 = UMRRegex1;
		r1 = Pattern.compile(regexString1);
		Matcher m1 = r1.matcher(searchText);
		String regexString2 = UMRRegex2;
		r2 = Pattern.compile(regexString2);
		Matcher m2 = r2.matcher(searchText);
		String regexString3 = UMRRegex3;
		r3 = Pattern.compile(regexString3);
		Matcher m3 = r3.matcher(searchText);
		

		if (m1.find( )) {	
			patternMatch = m1.group(1);
		}
		else
		if (m2.find( )) {	
			//System.out.println("PremMatch");
			patternMatch = m2.group(1);
			//System.out.println("2 " );
		}
		else	
		if (m3.find( )) {	
			//System.out.println("PremMatch");
			patternMatch = m3.group(1);
			//System.out.println("3 ");
		}
		
		return patternMatch;
	}
	
	String PeriodSearch(String searchText){
		String patternMatch = "";
		String regexString = PeriodRegex;
		r1 = Pattern.compile(regexString);
		Matcher m1 = r1.matcher(searchText);
		if (m1.find()) {	
			patternMatch = m1.group(0);

		}
		return patternMatch;
	}
	
	String InsuredSearch(String searchText){
			String patternMatch = "";
			String regexString = InsuredRegex;
			r1 = Pattern.compile(regexString);
			Matcher m1 = r1.matcher(searchText);
			if (m1.find( )) {	
				patternMatch = m1.group(0);
			}
			return patternMatch;
		}

	String ReinsuredSearch(String searchText){
		String patternMatch = "";
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
			String patternMatch = "";
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
				//System.out.println("1 ");
			}
			else
			if (m2.find( )) {	
				//System.out.println("PremMatch");
				patternMatch = m2.group(0);
				//System.out.println("2 " );
			}
			else	
			if (m3.find( )) {	
				//System.out.println("PremMatch");
				patternMatch = m3.group(0);
				//System.out.println("3 ");
			}
			else
			if (m4.find( )) {	
				//System.out.println("PremMatch");
				patternMatch = m4.group(0);
				//System.out.println("4 ");
			}			
			//System.out.println(patternMatch);
			return patternMatch;
		}
	
	String ClaimsSearch(String searchText){
			String patternMatch = "";
			String regexString1 = ClaimsRegex1;
			r1 = Pattern.compile(regexString1);
			Matcher m1 = r1.matcher(searchText);
			String regexString2 = ClaimsRegex2;
			r2 = Pattern.compile(regexString2);
			Matcher m2 = r2.matcher(searchText);
			if (m1.find( )) {	
				patternMatch = m1.group(0);
			}
			else
			if (m2.find( )) {	
				patternMatch = m2.group(0);
			}
			//System.out.println("Pattern " + patternMatch);
			return patternMatch;
		}
	
	String LimitSearch(String searchText){
		String patternMatch = "";
		String regexString = LimitRegex;
		r1 = Pattern.compile(regexString);
		Matcher m1 = r1.matcher(searchText);
		if (m1.find( )) {	
			patternMatch = m1.group(0);
		}
		return patternMatch;
	}
	
	String SublimitSearch(String searchText){
		String patternMatch = "";
		String regexString = SublimitsRegex;
		r1 = Pattern.compile(regexString);
		Matcher m1 = r1.matcher(searchText);
		if (m1.find( )) {	
			patternMatch = m1.group(0);
		}
		return patternMatch;
	}
	
	String DeductiblesSearch(String searchText){
		String patternMatch = "";
		String regexString = DeductiblesRegex;
		r1 = Pattern.compile(regexString);
		Matcher m1 = r1.matcher(searchText);
		if (m1.find( )) {	
			patternMatch = m1.group(0);
		}
		return patternMatch;
	}
		
	
}
