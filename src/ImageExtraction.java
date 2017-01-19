import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Transparency;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import javax.swing.*;
import javax.imageio.*;
import java.awt.image.*;


public class ImageExtraction {
    public BufferedImage Image(String pdfFileName, int pageNumber, double bbox1, double bbox2, double bbox3, double bbox4, double bboxPageHeight, double scaleFactor) throws IOException {
        // load a pdf from a byte buffer
        File file = new File(pdfFileName);
        RandomAccessFile raf = new RandomAccessFile(file, "r");
        FileChannel channel = raf.getChannel();
        ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
        PDFFile pdffile = new PDFFile(buf);
        int numPgs = pdffile.getNumPages();
        
            // draw the first page to an image
            PDFPage page = pdffile.getPage(pageNumber);
            // get the width and height for the doc at the default zoom
            //Rectangle rect = new Rectangle(0, 0, (int) page.getBBox().getWidth(), (int) page.getBBox().getHeight());
            double pageWidth = page.getBBox().getWidth();
            double pageHeight = page.getBBox().getHeight();
            
            
            //Rectangle Parameters
            //Defines the rectangle that will be extracted from the given pageNumber
            int x1 = (int) (bbox1/scaleFactor);
            int y1 = (int) ((bboxPageHeight - bbox4)/scaleFactor);
            //10 pixel buffer on x2 and y2. I should probably put those buffers somewhere else
            int x2 = (int) ((bbox3-bbox1)/scaleFactor); 
            int y2 = (int) ((bbox4-bbox2)/scaleFactor);
            
            Rectangle rect = new Rectangle(x1, y1, x2, y2);
            //Rectangle rect = new Rectangle((int) (bbox1/scaleFactor), (int) ((bboxPageHeight - bbox4)/scaleFactor), (int) ((bbox3-bbox1)/scaleFactor)+10, (int) ((bbox4-bbox2)/scaleFactor)+10);
            
            // generate the image
            Image img = page.getImage(rect.width, rect.height, // width & height
                    rect, // clip rect
                    null, // null for the ImageObserver
                    true, // fill background with white
                    true // block until drawing is done
                    );
                 
            //Rectangle2D pdfpagebox = page.getBBox();
            
            // save it as a file
            BufferedImage bImg = toBufferedImage(img);
            //File yourImageFile = new File(filepath+"page_" + i + ".png");
            //ImageIO.write(bImg, "png", yourImageFile);
               
        return bImg;
        
    }

    // This method returns a buffered image with the contents of an image
    public static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }
        // This code ensures that all the pixels in the image are loaded
        image = new ImageIcon(image).getImage();
        // Determine if the image has transparent pixels; for this method's
        // implementation, see e661 Determining If an Image Has Transparent
        // Pixels
        boolean hasAlpha = hasAlpha(image);
        // Create a buffered image with a format that's compatible with the
        // screen
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            // Determine the type of transparency of the new buffered image
            int transparency = Transparency.OPAQUE;
            if (hasAlpha) {
                transparency = Transparency.BITMASK;
            }
            // Create the buffered image
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(image.getWidth(null), image.getHeight(null), transparency);
        } catch (HeadlessException e) {
            // The system does not have a screen
        }
        if (bimage == null) {
            // Create a buffered image using the default color model
            int type = BufferedImage.TYPE_INT_RGB;
            if (hasAlpha) {
                type = BufferedImage.TYPE_INT_ARGB;
            }
            bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
        }
        // Copy image to buffered image
        Graphics g = bimage.createGraphics();
        // Paint the image onto the buffered image
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return bimage;
    }

    public static boolean hasAlpha(Image image) {
        // If buffered image, the color model is readily available
        if (image instanceof BufferedImage) {
            BufferedImage bimage = (BufferedImage) image;
            return bimage.getColorModel().hasAlpha();
        }
        // Use a pixel grabber to retrieve the image's color model;
        // grabbing a single pixel is usually sufficient
        PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
        try {
            pg.grabPixels();
        } catch (InterruptedException e) {
        }
        // Get the image's color model
        ColorModel cm = pg.getColorModel();
        return cm.hasAlpha();
    }

    public double pageWidth(String pdfFileName) throws IOException{
    	//Method to get pageWidth of pdf
    	//Compare with pageWidth given by bbox param in HOCR output to get scale factor
    	File file = new File(pdfFileName);
        RandomAccessFile raf = new RandomAccessFile(file, "r");
        FileChannel channel = raf.getChannel();
        ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
        PDFFile pdffile = new PDFFile(buf);
        PDFPage page = pdffile.getPage(1);
    	double pageWidth = page.getBBox().getWidth();
    	return pageWidth;
    }
    
    public double pageHeight(String pdfFileName) throws IOException{
    	//Method to get pageHeight of pdf
    	//Compare with pageHeight given by bbox param in HOCR output to get scale factor
    	File file = new File(pdfFileName);
        RandomAccessFile raf = new RandomAccessFile(file, "r");
        FileChannel channel = raf.getChannel();
        ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
        PDFFile pdffile = new PDFFile(buf);
        PDFPage page = pdffile.getPage(1);
    	double pageHeight = page.getBBox().getHeight();
    	return pageHeight;
    }
   
}
