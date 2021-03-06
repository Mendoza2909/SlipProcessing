import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import org.apache.pdfbox.io.*;
import org.apache.pdfbox.pdmodel.*;
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
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import javax.swing.*;
import javax.imageio.*;
import java.awt.image.*;


//437 454 1731 501
public class ImageMain {
    public int setup(String filepath, String pdfFileName) throws IOException {
        // load a pdf from a byte buffer
        File file = new File(pdfFileName);
        RandomAccessFile raf = new RandomAccessFile(file, "r");
        FileChannel channel = raf.getChannel();
        ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
        PDFFile pdffile = new PDFFile(buf);
        int numPgs = pdffile.getNumPages();
        for (int i = 0; i < 2; i++) {
            // draw the first page to an image
            PDFPage page = pdffile.getPage(i);
            // get the width and height for the doc at the default zoom
            //Rectangle rect = new Rectangle(0, 0, (int) page.getBBox().getWidth(), (int) page.getBBox().getHeight());
            double pageWidth = page.getBBox().getWidth();
            double pageHeight = page.getBBox().getHeight();
            
            
            
            System.out.println("Page width is "+ pageWidth);
            System.out.println("Page height is "+ pageHeight);
            
            //Rectangle rect = new Rectangle(100, 400, Width,Height);
            //This is correct Rectangle rect = new Rectangle(100, 700, 180, 20);
             
            //Page width is 595.08
            //Page height is 841.6800000000001
            
            double scaleFactor = 4.166666;
            
            
            double bboxpageWidth = 2480;
            double bboxpageHeight = 3507;
            
            double x_size = 60.35482;
            
            
            
            //UMR
            double bbox1 = 438;
            double bbox2 = 526;
            double bbox3 = 1092;
            double bbox4 = 579;
            
            
            /*
            //MARKET REFORM CONTRACT
            double bbox1 = 467;
            double bbox2 = 877;
            double bbox3 = 1509;
            double bbox4 = 952;
            */
            
            /*
            double bbox1 = 429;
            double bbox2 = 1155;
            double bbox3 = 699;
            double bbox4 = 1182;
            */
            
            
            
            //rectx = 
            
            Rectangle rect = new Rectangle((int) (bbox1/scaleFactor), (int) ((bboxpageHeight - bbox4)/scaleFactor), (int) ((bbox3-bbox1)/scaleFactor)+10, (int) ((bbox4-bbox2)/scaleFactor)+10);
            
            
            
            
            /*System.out.println("x is "+ rect.getX());
            System.out.println("y is "+ rect.getX());
            System.out.println("width is "+ rect.width);
            System.out.println("height is "+ rect.height);
            */
            // generate the image
            Image img = page.getImage(rect.width, rect.height, // width & height
                    rect, // clip rect
                    null, // null for the ImageObserver
                    true, // fill background with white
                    true // block until drawing is done
                    );
            
            
            Rectangle2D pdfpagebox = page.getBBox();
            
            System.out.println("bbox is "+ pdfpagebox.toString());
           
            //System.out.println("y is "+ rect.getX());
            //System.out.println("width is "+ rect.width);
            //System.out.println("height is "+ rect.height);
            // save it as a file
            BufferedImage bImg = toBufferedImage(img);
            File yourImageFile = new File(filepath+"page_" + i + ".png");
            ImageIO.write(bImg, "png", yourImageFile);
        }
        
        return 0;
        
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

    /*public static void main(final String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    ImageMain.setup();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }*/
}