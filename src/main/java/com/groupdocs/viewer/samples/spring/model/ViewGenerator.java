package com.groupdocs.viewer.samples.spring.model;

import com.groupdocs.viewer.converter.options.HtmlOptions;
import com.groupdocs.viewer.converter.options.ImageOptions;
import com.groupdocs.viewer.domain.FileDescription;
import com.groupdocs.viewer.domain.Transformation;
import com.groupdocs.viewer.domain.WatermarkPosition;
import com.groupdocs.viewer.domain.containers.FileContainer;
import com.groupdocs.viewer.domain.containers.FileTreeContainer;
import com.groupdocs.viewer.domain.html.PageHtml;
import com.groupdocs.viewer.domain.image.PageImage;
import com.groupdocs.viewer.domain.options.FileTreeOptions;
import com.groupdocs.viewer.domain.options.PdfFileOptions;
import com.groupdocs.viewer.handler.ViewerHandler;
import com.groupdocs.viewer.handler.ViewerHtmlHandler;
import com.groupdocs.viewer.handler.ViewerImageHandler;
import com.groupdocs.viewer.samples.spring.model.business.HtmlInfo;
import com.groupdocs.viewer.samples.spring.model.business.ImageInfo;
import com.groupdocs.viewer.samples.spring.model.request.ViewDocumentRequest;
import com.groupdocs.viewer.samples.spring.model.response.FileBrowserTreeDataNode;
import com.groupdocs.viewer.samples.spring.model.response.LoadFileBrowserTreeDataResponse;
import com.groupdocs.viewer.samples.spring.model.response.ViewDocumentResponse;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The type View generator.
 * @author Aleksey Permyakov (21.04.2016).
 */
public class ViewGenerator {

    private static com.groupdocs.viewer.config.ViewerConfig viewerConfig;

    /**
     * Init generator.
     * @param viewerConfig the viewer viewerConfig
     */
    public static void initGenerator(com.groupdocs.viewer.config.ViewerConfig viewerConfig) {
        ViewGenerator.viewerConfig = viewerConfig;
    }

    /**
     * Render simple document in html representation
     * @param request          File name
     * @param DocumentPassword Optional
     * @return the list
     * @throws Exception the exception
     */
    public static ViewDocumentResponse renderDocumentAsHtml(final ViewDocumentRequest request, String DocumentPassword) {

        // Create html handler
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(viewerConfig);

        //Instantiate the HtmlOptions object
        HtmlOptions options = new HtmlOptions();

        //to get html representations of pages with embedded resources
        options.setResourcesEmbedded(true);

        // Set password if document is password protected.
        if (DocumentPassword != null && !DocumentPassword.isEmpty()) {
            options.setPassword(DocumentPassword);
        }
        final ViewDocumentResponse viewDocumentResponse = new ViewDocumentResponse();
        try {
            //Get document pages in html form
            final List<PageHtml> pages = htmlHandler.getPages(request.getPath(), options);
            final FileType fileType = FileType.fromFileName(request.getFileDisplayName());
            viewDocumentResponse.setDoc_type(DocumentType.getDocumentType(fileType));
            viewDocumentResponse.setFileType(fileType.name());
            viewDocumentResponse.setPath(request.getPath());
            viewDocumentResponse.setLic(false);
            viewDocumentResponse.setName(request.getFileDisplayName());
            viewDocumentResponse.setPage_count(pages.size());
            viewDocumentResponse.setPageHtml(new String[pages.size()]);
            viewDocumentResponse.setPageCss(new String[pages.size()]);
            StringBuilder builder = new StringBuilder("{\"pages\":[");
            for (int n = 0; n < pages.size(); n++) {
                final PageHtml pageHtml = pages.get(n);
                viewDocumentResponse.getPageHtml()[n] = pageHtml.getHtmlContent();
//                    getPageCss()[n] = pages.get(n).getHtmlResources();
                if (n > 0) {
                    builder.append(",");
                }
                builder.append("{\"w\":").append(800)
                        .append(",\"h\":").append(600)
                        .append(",\"number\":").append(pageHtml.getPageNumber())
                        .append("}");
            }
            builder.append("],\"maxPageHeight\":1000,\"widthForMaxHeight\":800}");
            viewDocumentResponse.setDocumentDescription(builder.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return viewDocumentResponse;
    }

    /**
     * Render document in html representation with watermark
     * @param DocumentName     file/document name
     * @param WatermarkText    watermark text
     * @param WatermarkColor   System.Drawing.Color
     * @param WatermarkWidth   width of watermark as integer. it is optional Parameter default value is 100
     * @param DocumentPassword Password Parameter is optional
     * @return the list
     * @throws Exception the exception
     */
    public static List<HtmlInfo> renderDocumentAsHtml(String DocumentName, String WatermarkText, Color WatermarkColor, int WatermarkWidth, String DocumentPassword) throws Exception {
        // Guid implies that unique document name
        // Create html handler
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(viewerConfig);
        //Instantiate the HtmlOptions object
        HtmlOptions options = new HtmlOptions();
        options.setResourcesEmbedded(false);
        // Set password if document is password protected.
        if (DocumentPassword != null && !DocumentPassword.isEmpty()) {
            options.setPassword(DocumentPassword);
        }
        // Call AddWatermark and pass the reference of HtmlOptions object as 1st parameter
        Utilities.PageTransformations.addWatermark(options, WatermarkText, WatermarkColor, WatermarkPosition.Diagonal, WatermarkWidth);
        //Get document pages in html form
        List<PageHtml> pages = htmlHandler.getPages(DocumentName, options);

        return getHtmlInfos(pages);
        //ExEnd:RenderAsHtmlWithWaterMark
    }

    /**
     * Rotate document as html list.
     * @param DocumentName     the document name
     * @param pageNumber       the page number
     * @param RotationAngle    the rotation angle
     * @param DocumentPassword the document password
     * @return the list
     * @throws Exception the exception
     */
    public static List<HtmlInfo> rotateDocumentAsHtml(String DocumentName, int pageNumber, int RotationAngle, String DocumentPassword) throws Exception {
        // Guid implies that unique document name
        // Create image handler
        ViewerHandler handler = new ViewerHtmlHandler(viewerConfig);
        //Initialize ImageOptions Object and setting Rotate Transformation
        HtmlOptions options = new HtmlOptions();
        options.setTransformations(Transformation.Rotate);

        // Set password if document is password protected.
        if (DocumentPassword != null && !DocumentPassword.isEmpty()) {
            options.setPassword(DocumentPassword);
        }

        //Call RotatePages to apply rotate transformation to a page
        Utilities.PageTransformations.rotatePages(handler, DocumentName, pageNumber, RotationAngle);

        //down cast the handler(ViewerHandler) to viewerHtmlHandler
        ViewerHtmlHandler htmlHandler = (ViewerHtmlHandler) handler;

        //Get document pages in image form
        List<PageHtml> pages = htmlHandler.getPages(DocumentName, options);

        return getHtmlInfos(pages);
        //ExEnd:RenderAsImageWithRotationTransformation
    }

    /**
     * document in html representation and reorder a page
     * @param DocumentName      file/document name
     * @param CurrentPageNumber Page existing order number
     * @param NewPageNumber     Page new order number
     * @param DocumentPassword  Password Parameter is optional
     * @return the list
     * @throws Exception the exception
     */
    public static List<HtmlInfo> renderDocumentAsHtml(String DocumentName, int CurrentPageNumber, int NewPageNumber, String DocumentPassword) throws Exception {
        // Guid implies that unique document name
        // Cast ViewerHtmlHandler class object to its base class(ViewerHandler).
        ViewerHandler handler = new ViewerHtmlHandler(viewerConfig);
        //Instantiate the HtmlOptions object with setting of Reorder Transformation
        HtmlOptions options = new HtmlOptions();
        options.setTransformations(Transformation.Reorder);

        //to get html representations of pages with embedded resources
        options.setResourcesEmbedded(true);

        // Set password if document is password protected.
        if (DocumentPassword != null && !DocumentPassword.isEmpty()) {
            options.setPassword(DocumentPassword);
        }

        //Call ReorderPage and pass the reference of ViewerHandler's class  parameter by reference.
        Utilities.PageTransformations.reorderPage(handler, DocumentName, CurrentPageNumber, NewPageNumber);

        //down cast the handler(ViewerHandler) to viewerHtmlHandler
        ViewerHtmlHandler htmlHandler = (ViewerHtmlHandler) handler;

        //Get document pages in html form
        List<PageHtml> pages = htmlHandler.getPages(DocumentName, options);

        return getHtmlInfos(pages);
        //ExEnd:RenderAsHtmlAndReorderPage
    }

    private static List<HtmlInfo> getHtmlInfos(List<PageHtml> pages) {
        List<HtmlInfo> contents = new ArrayList<HtmlInfo>();

        for (PageHtml page : pages) {
            HtmlInfo htmlInfo = new HtmlInfo();
            htmlInfo.setHtmlContent(page.getHtmlContent());
            htmlInfo.setPageNmber(page.getPageNumber());
            contents.add(htmlInfo);
        }
        return contents;
    }

    /**
     * Render a document in html representation whom located at web/remote location.
     * @param DocumentURL      URL of the document
     * @param DocumentPassword Password Parameter is optional
     * @throws Exception the exception
     */
    public static void renderDocumentAsHtml(URI DocumentURL, String DocumentPassword) throws Exception {
        //ExStart:RenderRemoteDocAsHtml
        //Get Configurations


        // Create html handler
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(viewerConfig);

        //Instantiate the HtmlOptions object
        HtmlOptions options = new HtmlOptions();

        if (DocumentPassword != null && !DocumentPassword.isEmpty()) {
            options.setPassword(DocumentPassword);
        }

        //Get document pages in html form
        List<PageHtml> pages = htmlHandler.getPages(DocumentURL, options);

        for (PageHtml page : pages) {
            //Save each page at disk
            Utilities.saveAsHtml(page.getPageNumber() + "_" + FilenameUtils.getName(DocumentURL.getPath()), page.getHtmlContent());
        }
        //ExEnd:RenderRemoteDocAsHtml
    }

    /**
     * Render simple document in image representation
     * @param documentName     File name
     * @param DocumentPassword Optional
     * @return the list
     */
    public static List<ImageInfo> renderDocumentAsImages(String documentName, String DocumentPassword) {
        //ExStart:RenderAsImage
        //Get Configurations


        // Create image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(viewerConfig);

        //Initialize ImageOptions Object
        ImageOptions options = new ImageOptions();

        // Set password if document is password protected.
        if (DocumentPassword != null && !DocumentPassword.isEmpty()) {
            options.setPassword(DocumentPassword);
        }

        //Get document pages in image form
        List<PageImage> Images = imageHandler.getPages(documentName, options);

        List<ImageInfo> contents = new ArrayList<ImageInfo>();

        for (PageImage image : Images) {
            String imgname = image.getPageNumber() + "_" + FilenameUtils.getName(documentName);
            imgname = imgname.replace("\\s+", "_");

            Utilities.saveAsImage(viewerConfig.getTempPath(), imgname, image.getStream());

            ImageInfo imageInfo = new ImageInfo();
            imageInfo.setImageUrl("/Uploads/images/" + FilenameUtils.getBaseName(imgname) + ".jpg?" + UUID.randomUUID().toString());
            imageInfo.setPageNmber(image.getPageNumber());
            imageInfo.setHtmlContent("<div class='image_page'><img src='" + imageInfo.getImageUrl() + "' /></div>");
            contents.add(imageInfo);
        }

        return contents;
        //ExEnd:RenderAsImage

    }

    /**
     * Render document in image representation with watermark
     * @param DocumentName     file/document name
     * @param WatermarkText    watermark text
     * @param WatermarkColor   System.Drawing.Color
     * @param WatermarkWidth   width of watermark as integer. it is optional Parameter default value is 100
     * @param DocumentPassword Password Parameter is optional
     * @return the list
     */
    public static List<ImageInfo> renderDocumentAsImages(String DocumentName, String WatermarkText, Color WatermarkColor, int WatermarkWidth, String DocumentPassword) {
        // Guid implies that unique document name
        // Create image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(viewerConfig);
        //Initialize ImageOptions Object
        ImageOptions options = new ImageOptions();

        // Set password if document is password protected.
        if (DocumentPassword != null && !DocumentPassword.isEmpty()) {
            options.setPassword(DocumentPassword);
        }

        // Call AddWatermark and pass the reference of ImageOptions object as 1st parameter
        Utilities.PageTransformations.addWatermark(options, WatermarkText, WatermarkColor, WatermarkPosition.Diagonal, WatermarkWidth);

        //Get document pages in image form
        List<PageImage> images = imageHandler.getPages(DocumentName, options);

        return getImageInfos(DocumentName, images);
        //ExEnd:RenderAsImageWithWaterMark
    }

    private static List<ImageInfo> getImageInfos(String DocumentName, List<PageImage> images) {
        List<ImageInfo> contents = new ArrayList<ImageInfo>();

        for (PageImage image : images) {
            String imgname = image.getPageNumber() + "_" + FilenameUtils.getBaseName(DocumentName);
            imgname = imgname.replace("\\s+", "_");

            Utilities.saveAsImage(viewerConfig.getTempPath(), imgname, image.getStream());

            ImageInfo imageInfo = new ImageInfo();
            imageInfo.setImageUrl("/Uploads/images/" + imgname + ".jpg?" + UUID.randomUUID().toString());
            imageInfo.setPageNmber(image.getPageNumber());
            imageInfo.setHtmlContent("<div class='image_page'><img src='" + imageInfo.getImageUrl() + "' /></div>");
            contents.add(imageInfo);
        }
        return contents;
    }

    /**
     * Render the document in image form and set the rotation angle to rotate the page while display.
     * @param DocumentName     the document name
     * @param pageNumber       the page number
     * @param RotationAngle    rotation angle in digits
     * @param DocumentPassword the document password
     * @return the list
     * @throws Exception the exception
     */
    public static List<ImageInfo> rotateDocumentAsImages(String DocumentName, int pageNumber, int RotationAngle, String DocumentPassword) throws Exception {
        // Guid implies that unique document name
        // Create image handler
        ViewerHandler handler = new ViewerImageHandler(viewerConfig);
        //Initialize ImageOptions Object and setting Rotate Transformation
        ImageOptions options = new ImageOptions();
        options.setTransformations(Transformation.Rotate);

        // Set password if document is password protected.
        if (DocumentPassword != null && !DocumentPassword.isEmpty()) {
            options.setPassword(DocumentPassword);
        }

        //Call RotatePages to apply rotate transformation to a page
        Utilities.PageTransformations.rotatePages(handler, DocumentName, pageNumber, RotationAngle);

        //down cast the handler(ViewerHandler) to viewerHtmlHandler
        ViewerImageHandler imageHandler = (ViewerImageHandler) handler;

        //Get document pages in image form
        List<PageImage> Images = imageHandler.getPages(DocumentName, options);

        return getImageInfos(DocumentName, Images);
    }

    /**
     * document in image representation and reorder a page
     * @param DocumentName      file/document name
     * @param CurrentPageNumber Page existing order number
     * @param NewPageNumber     Page new order number
     * @param DocumentPassword  Password Parameter is optional
     * @return the list
     * @throws Exception the exception
     */
    public static List<ImageInfo> renderDocumentAsImages(String DocumentName, int CurrentPageNumber, int NewPageNumber, String DocumentPassword) throws Exception {
        // Guid implies that unique document name
        // Cast ViewerHtmlHandler class object to its base class(ViewerHandler).
        ViewerHandler handler = new ViewerImageHandler(viewerConfig);
        //Initialize ImageOptions Object and setting Reorder Transformation
        ImageOptions options = new ImageOptions();
        options.setTransformations(Transformation.Reorder);

        // Set password if document is password protected.
        if (DocumentPassword != null && !DocumentPassword.isEmpty()) {
            options.setPassword(DocumentPassword);
        }

        //Call ReorderPage and pass the reference of ViewerHandler's class  parameter by reference.
        Utilities.PageTransformations.reorderPage(handler, DocumentName, CurrentPageNumber, NewPageNumber);

        //down cast the handler(ViewerHandler) to viewerHtmlHandler
        ViewerImageHandler imageHandler = (ViewerImageHandler) handler;

        //Get document pages in image form
        List<PageImage> images = imageHandler.getPages(DocumentName, options);

        return getImageInfos(DocumentName, images);
        //ExEnd:RenderAsImageAndReorderPage
    }

    /**
     * Render a document in image representation whom located at web/remote location.
     * @param DocumentURL      URL of the document
     * @param DocumentPassword Password Parameter is optional
     */
    public static void renderDocumentAsImages(URI DocumentURL, String DocumentPassword) {
        // Create image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(viewerConfig);

        //Initialize ImageOptions Object
        ImageOptions options = new ImageOptions();

        // Set password if document is password protected.
        if (DocumentPassword != null && !DocumentPassword.isEmpty()) {
            options.setPassword(DocumentPassword);
        }

        //Get document pages in image form
        List<PageImage> Images = imageHandler.getPages(DocumentURL, options);

//        for (PageImage image : Images) {
        //Save each image at disk
        // Utilities.SaveAsImage(image.getPageNumber() + "_" + Path.GetFileName(DocumentURL.LocalPath), image.Stream);
//        }
    }

    /**
     * Render a document as it is (original form)
     * @param DocumentName the document name
     */
    public static void renderDocumentAsOriginal(String DocumentName) {
        //ExStart:RenderOriginal
        // Create image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(viewerConfig);

        // Guid implies that unique document name

        // Get original file
        FileContainer container = imageHandler.getFile(DocumentName);

        //Save each image at disk
        // Utilities.SaveAsImage(DocumentName, container.Stream);
        //ExEnd:RenderOriginal

    }

    /**
     * Render a document in PDF Form
     * @param DocumentName the document name
     */
    public static void renderDocumentAsPDF(String DocumentName) {
        //ExStart:RenderAsPdf
        // Create/initialize image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(viewerConfig);

        //Initialize PdfFileOptions object
        PdfFileOptions options = new PdfFileOptions();

        // Guid implies that unique document name
        options.setGuid(DocumentName);

        // Call GetPdfFile to get FileContainer type object which contains the stream of pdf file.
        FileContainer container = imageHandler.getPdfFile(options);

        //Change the extension of the file and assign to a String type variable filename
        String filename = FilenameUtils.getBaseName(DocumentName) + ".pdf";

        //Save each image at disk
        Utilities.saveFile(filename, container.getStream());
    }

    /**
     * Load directory structure as file tree
     * @param path the path
     * @return the load file browser tree data response
     */
    public static LoadFileBrowserTreeDataResponse loadFileTree(final String path) {
        // Create/initialize image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(viewerConfig);

        // Load file tree list for custom path
        FileTreeOptions options = new FileTreeOptions(path);

        // Load file tree list for ViewerConfig.StoragePath
        FileTreeContainer container = imageHandler.loadFileTree(options);

        final List<FileDescription> fileTree = container.getFileTree();
        final LoadFileBrowserTreeDataResponse response = new LoadFileBrowserTreeDataResponse();
        final int[] count = {1};
        for (final FileDescription fileDescription : fileTree) {
            response.getNodes().add(new FileBrowserTreeDataNode() {{
                setGuid(fileDescription.getGuid());
                setDocType(fileDescription.isDirectory() ? null : fileDescription.getDocumentType());
                setExt(fileDescription.isDirectory() ? "[DIRECTORY_TYPE]" : fileDescription.getExtension().toLowerCase());
                setFileType(fileDescription.isDirectory() ? "[DIRECTORY_TYPE]" : fileDescription.getFileType());
                setModifyTime(fileDescription.getLastModificationDate().getTime());
                setName(fileDescription.getName());
                setPath(fileDescription.getName());
                setIsKnown(false);
                setIsShared(false);
                setType(fileDescription.isDirectory() ? "folder" : "file");
                setSize(fileDescription.getSize());
                setId(count[0]++);
            }});
        }
        response.setCount(fileTree.size());
        return response;
    }

    /**
     * Load page image byte [ ].
     * @param filename the filename
     * @return the byte [ ]
     * @throws IOException the io exception
     */
    public static byte[] loadPageImage(String filename) throws IOException {
        final File imagePath = Utilities.makeImagePath(viewerConfig.getTempPath(), filename);
        return FileUtils.readFileToByteArray(imagePath);
    }
}
