package Output;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelWrite {
	
	public static void writeToExcel(String fileName,  String [][] data)  throws FileNotFoundException, IOException {
	
		//Takes the array  of data and an Excel filepath and writes the data to the filepath
			
			
		    //Read in workbook		      
		      FileOutputStream fileOut = new FileOutputStream(fileName);
		      
		      //@SuppressWarnings("resource")
			  XSSFWorkbook xlsxBook = new XSSFWorkbook();
		      XSSFSheet sheet = xlsxBook.createSheet();
		          
		      //Create 2D Cell Array
		      //First row is for column header
		      Row[] row = new Row[data.length];
		      Cell[][] cell = new Cell[row.length][];
		      	      
		      //Define and Assign Cell Data from array
		      for(int i = 0; i < row.length; i ++)
		      {	  //System.out.println("Row length is" + row.length);
		    	  //System.out.println("i is" + i);
		          row[i] = sheet.createRow(i);
		          cell[i] = new Cell[data[i].length];
		          
		          //System.out.println("cell i length is " + cell[i].length);
		          for(int j = 0; j < cell[i].length; j ++)
		          {	        
		        	  //System.out.println (cell[i][0]+" i= " +i+", j= "+j);
		              cell[i][j] = row[i].createCell(j);
		              //System.out.println(data[i][j]);
		              cell[i][j].setCellValue(data[i][j]);
		          }

		      }

		      //Export Data
		      xlsxBook.write(fileOut);
		      
		      //Close workbook
		      fileOut.flush();
		      xlsxBook.close();
		      fileOut.close();
		      
		      
		      System.out.println("File exported successfully");
		  }
	
	
}
