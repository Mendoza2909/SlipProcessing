package PDFCompression;

import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

public class Compression {
	
	public static void uncompressXRef(String src, String dest) throws IOException, DocumentException{
		
		PdfReader reader  = new PdfReader(src);
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
		stamper.close();
		reader.close();
	}
	
	
	public static void recompressXRef(String src, String dest) throws IOException, DocumentException{
		PdfReader reader  = new PdfReader(src);
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
		
		stamper.getWriter().setFullCompression();
		//stamper.close();
		//reader.close();
	}
	
	
	/*public void uncompressXRef(String src, String dest) throws IOException, DocumentException{
		PdfReader reader  = new PdfReader(src);
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
		stamper.close();
		reader.close();
	}
	
	
	public void recompressXRef(String src, String dest) throws IOException, DocumentException{
		PdfReader reader  = new PdfReader(src);
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
		
		stamper.getWriter().setFullCompression();
		stamper.close();
		reader.close();
	}*/
	
}
