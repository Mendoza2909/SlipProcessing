import java.io.IOException;

public class SubImageExtractionMain {
	public static void main(String args[]) {
		//System.out.print("Hello");
		
		String outputFilePath = "S:/Actuarial/Projects/Machine Learning/IPF/Slips Phase 2 Test/Output/";
		
		String pdfFileName = "S:/Actuarial/Projects/Machine Learning/IPF/Slips Phase 2 Test/Slips/Abcourt Mining 2016 Slip.pdf";
		
		ImageMain ImageMain = new ImageMain();
		try {
			ImageMain.setup(outputFilePath,pdfFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
