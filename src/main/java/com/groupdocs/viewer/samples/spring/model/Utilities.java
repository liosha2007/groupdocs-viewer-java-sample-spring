package com.groupdocs.viewer.samples.spring.model;

import com.groupdocs.viewer.converter.options.HtmlOptions;
import com.groupdocs.viewer.converter.options.ImageOptions;
import com.groupdocs.viewer.domain.Watermark;
import com.groupdocs.viewer.domain.WatermarkPosition;
import com.groupdocs.viewer.domain.containers.RotatePageContainer;
import com.groupdocs.viewer.domain.options.ReorderPageOptions;
import com.groupdocs.viewer.domain.options.RotatePageOptions;
import com.groupdocs.viewer.handler.ViewerHandler;
import com.groupdocs.viewer.licensing.License;
import com.groupdocs.viewer.samples.spring.config.SpringConfig;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * The type Utilities.
 * @author Aleksey Permyakov (21.04.2016).
 */
public class Utilities {

    private static List<String> EXT_ARR = Arrays.asList("doc", "docx", "xls", "xlsx", "pdf", "ppt", "pptx", "html", "xml", "bmp", "jpg", "gif");


    /**
     * Check extenstion boolean.
     * @param ext the ext
     * @return the boolean
     */
    public static boolean checkExtenstion(String ext) {
        return EXT_ARR.contains(ext);
    }

    /**
     * Gets upload path.
     * @param dropwizardConfig the dropwizard config
     * @return the upload path
     */
    public static String getUploadPath(SpringConfig dropwizardConfig) {
        return dropwizardConfig.getStoragePath() + "/";
    }

    /**
     * The type Page transformations.
     */
    public static class PageTransformations {
        /**
         * Rotate a Page before rendering
         * @param handler    the handler
         * @param guid       the guid
         * @param pageNumber the page number
         * @param angle      the angle
         * @return the rotate page container
         * @throws Exception the exception
         */
        public static RotatePageContainer rotatePages(ViewerHandler handler, String guid, int pageNumber, int angle) throws Exception {
            //ExStart:rotationAngle
            // Set the property of handler's rotate Page
            return handler.rotatePage(new RotatePageOptions(guid, pageNumber, angle));
            //ExEnd:rotationAngle
        }

        /**
         * Reorder a page before rendering
         * @param viewerHandler     Base class of handlers
         * @param guid              File name
         * @param currentPageNumber Existing number of page
         * @param newPageNumber     New number of page
         * @throws Exception the exception
         */
        public static void reorderPage(ViewerHandler viewerHandler, String guid, int currentPageNumber, int newPageNumber) throws Exception {
            //ExStart:reorderPage
            //Initialize the ReorderPageOptions object by passing guid as document name, current Page Number, new page number
            ReorderPageOptions options = new ReorderPageOptions(guid, currentPageNumber, newPageNumber);
            // call ViewerHandler's Reorder page function by passing initialized ReorderPageOptions object.
            viewerHandler.reorderPage(options);
            //ExEnd:reorderPage
        }

        /**
         * add a watermark text to all rendered images.
         * @param options  HtmlOptions by reference
         * @param text     Watermark text
         * @param color    System.Drawing.Color
         * @param position the position
         * @param width    the width
         */
        public static void addWatermark(ImageOptions options, String text, Color color, WatermarkPosition position, float width) {
            Watermark watermark = createWatermark(text, color, position, width);
            //Assign intialized and populated watermark object to ImageOptions or HtmlOptions objects
            options.setWatermark(watermark);
            //ExEnd:AddWatermark
        }

        /**
         * add a watermark text to all rendered Html pages.
         * @param options  HtmlOptions by reference
         * @param text     Watermark text
         * @param color    System.Drawing.Color
         * @param position the position
         * @param width    the width
         */
        public static void addWatermark(HtmlOptions options, String text, Color color, WatermarkPosition position, float width) {
            Watermark watermark = createWatermark(text, color, position, width);
            options.setWatermark(watermark);
        }

    }

    private static Watermark createWatermark(String text, Color color, WatermarkPosition position, float width) {
        //Initialize watermark object by passing the text to display.
        Watermark watermark = new Watermark(text);
        //Apply the watermark color by assigning System.Drawing.Color.
        watermark.setColor(color);
        //Set the watermark's position by assigning an enum WatermarkPosition's value.
        watermark.setPosition(position);
        //set an integer value as watermark width
        watermark.setWidth(width);
        return watermark;
    }

    /**
     * Set product's license
     * @param licensePath the license path
     */
    public static void applyLicense(String licensePath) {
        License lic = new License();
        lic.setLicense(licensePath);
    }

    /**
     * Save file in html form
     * @param filename Save as provided string
     * @param content  Html contents in String form
     */
    public static void saveAsHtml(String filename, String content) {
        try {
            //ExStart:SaveAsHTML
            // set an html file name with absolute path
            // String fname = Path.Combine(Path.GetFullPath(OutputHtmlPath), Path.GetFileNameWithoutExtension(filename) + ".html");

            // create a file at the disk
            // System.IO.File.WriteAllText(fname, content);
            //ExEnd:SaveAsHTML
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * Save the rendered images at disk
     * @param path         the path
     * @param imageName    Save as provided string
     * @param imageContent stream of image contents
     */
    public static void saveAsImage(String path, String imageName, InputStream imageContent) {
        try {
            //ExStart:SaveAsImage
            // extract the image from stream
            BufferedImage img = ImageIO.read(imageContent);
            //save the image in the form of jpeg
            ImageIO.write(img, "png", Utilities.makeImagePath(path, imageName));
            //ExEnd:SaveAsImage
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Make image path file.
     * @param path      the path
     * @param imageName the image name
     * @return the file
     */
    public static File makeImagePath(String path, String imageName) {
        final File directory = new File(path + "\\images\\");
        if (!directory.exists() && !directory.mkdirs()) {
            System.out.println("Can't create directory for images! " + directory.getAbsolutePath());
        }
        return new File(directory.getAbsolutePath() + File.separator + FilenameUtils.getBaseName(imageName) + ".png");
    }

    /**
     * Save file in any format
     * @param filename Save as provided string
     * @param content  Stream as content of a file
     */
    public static void saveFile(String filename, InputStream content) {
        try {
            //ExStart:SaveAnyFile
            //Create file stream
            //FileStream fileStream = File.Create(Path.Combine(Path.GetFullPath(OutputPath), filename), (int)content.Length);

            // Initialize the bytes array with the stream length and then fill it with data
            //byte[] bytesInStream = new byte[content.Length];
            //content.Read(bytesInStream, 0, bytesInStream.Length);

            // Use write method to write to the file specified above
            //fileStream.Write(bytesInStream, 0, bytesInStream.Length);
            //ExEnd:SaveAnyFile
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
