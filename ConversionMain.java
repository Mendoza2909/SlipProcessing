import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.io.*;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

//Converts pdf to png

public class ConversionMain {

	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String pdfFilename = "S:/Actuarial/Projects/Machine Learning/IPF/Slips Phase 2 Test/Slips/Abcourt Mining 2016 Slip";
		
		File file = new File(pdfFilename);
		PDDocument document;
		try {
			document = PDDocument.load(file);
		
		PDFRenderer pdfRenderer = new PDFRenderer(document);
		for (int page = 0; page < document.getNumberOfPages(); ++page)
		{ 
		    BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);

		    // suffix in filename will be used as the file format
		    ImageIOUtil.writeImage(bim, pdfFilename + "-" + (page+1) + ".png", 300);
		}
		document.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
