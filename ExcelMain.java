import java.io.File;
import java.io.IOException;

public class ExcelMain {

	public static void main(String[] args) {

		long startTime = System.currentTimeMillis();

		


		
		//Location of text files to be searched
		String inputPath = "S:/Actuarial/Projects/Machine Learning/IPF/Parsed Slips";	
		File inputFiles = new File(inputPath);	    
	    File [] files = inputFiles.listFiles();
	    int fileLength = files.length;
		
	    //Create array to store variables
	    //Extra row is for column header
		String[][] outputDataArray = new String [fileLength + 1][2];
		
		;
		//Create new text processing routine
		TextProcess TextProcess = new TextProcess();
		
		//Start searching text files for relevant data, store results to variable output array
		outputDataArray = TextProcess.ProcessText(inputPath);
		
		//Start excel writing routine
		new ExcelWrite();		
		//Store outputarray to Excel using given file path
		String outputFilePath = "S:/Actuarial/Projects/Machine Learning/IPF/Java Output.xlsx";
		
		try {
			ExcelWrite.writeToExcel(outputFilePath,outputDataArray);
		} catch (IOException e) {
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("That took " + (endTime - startTime) + " milliseconds");

			
}
}