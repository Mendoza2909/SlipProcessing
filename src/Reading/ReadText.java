package Reading;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadText {
	
	public static String FileRead(String inputFilePath) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader("file.txt"));
		try {
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		    String everything = sb.toString();
		} finally {
		    br.close();
		}
	}
	
}
