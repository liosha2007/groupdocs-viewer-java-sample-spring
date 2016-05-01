package com.groupdocs.viewer.samples.spring.model;

import com.groupdocs.viewer.converter.options.HtmlOptions;
import com.groupdocs.viewer.converter.options.ImageOptions;
import com.groupdocs.viewer.domain.FileDescription;
import com.groupdocs.viewer.domain.Transformation;
import com.groupdocs.viewer.domain.WatermarkPosition;
import com.groupdocs.viewer.domain.containers.FileContainer;
import com.groupdocs.viewer.domain.containers.FileTreeContainer;
import com.groupdocs.viewer.domain.containers.RotatePageContainer;
import com.groupdocs.viewer.domain.html.HtmlResource;
import com.groupdocs.viewer.domain.html.PageHtml;
import com.groupdocs.viewer.domain.image.PageImage;
import com.groupdocs.viewer.domain.options.FileTreeOptions;
import com.groupdocs.viewer.domain.options.PdfFileOptions;
import com.groupdocs.viewer.handler.ViewerHandler;
import com.groupdocs.viewer.handler.ViewerHtmlHandler;
import com.groupdocs.viewer.handler.ViewerImageHandler;
import com.groupdocs.viewer.licensing.License;
import com.groupdocs.viewer.samples.spring.model.business.HtmlInfo;
import com.groupdocs.viewer.samples.spring.model.business.ImageInfo;
import com.groupdocs.viewer.samples.spring.model.request.GetImageUrlsRequest;
import com.groupdocs.viewer.samples.spring.model.request.ViewDocumentRequest;
import com.groupdocs.viewer.samples.spring.model.response.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

/**
 * The type View generator.
 * @author Aleksey Permyakov (21.04.2016).
 */
public class ViewGenerator {

    public static final String UPLOAD_DIRECTORY_NAME = "upload";
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
            final String path = request.getPath();
            final List<PageHtml> pages = htmlHandler.getPages(path, options);
            final FileType fileType = FileType.fromFileName(path);
            viewDocumentResponse.setDoc_type(DocumentType.getDocumentType(fileType));
            viewDocumentResponse.setFileType(fileType.name());
            viewDocumentResponse.setPath(path);
            viewDocumentResponse.setLic(false);
            viewDocumentResponse.setName(path);
            viewDocumentResponse.setPage_count(pages.size());
            viewDocumentResponse.setPageHtml(new String[pages.size()]);
            viewDocumentResponse.setPageCss(new String[pages.size()]);
            viewDocumentResponse.setLic(License.isValidLicense());
            viewDocumentResponse.setPdfDownloadUrl("/GetFileHandler?path=" + path + "&getPdf=true");
            viewDocumentResponse.setPdfPrintUrl("/GetPdfWithPrintDialog?path=" + path);
            viewDocumentResponse.setUrl("/GetFileHandler?path=" + path + "&getPdf=false");
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
                saveDocumentHtmlResources(request.getFileDisplayName(), htmlHandler, pageHtml, path);
            }
            builder.append("],\"maxPageHeight\":1000,\"widthForMaxHeight\":800}");
            viewDocumentResponse.setDocumentDescription(builder.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return viewDocumentResponse;
    }

    private static void saveDocumentHtmlResources(String fileDisplayName, ViewerHtmlHandler htmlHandler, PageHtml pageHtml, String path) {
        try {
            final List<HtmlResource> htmlResources = pageHtml.getHtmlResources();
            for (HtmlResource htmlResource : htmlResources) {
                final int documentPageNumber = htmlResource.getDocumentPageNumber();
                final String resourceName = htmlResource.getResourceName();

                File resourceFile = getHtmlResourcePath(fileDisplayName, documentPageNumber, resourceName);
                if (!resourceFile.getParentFile().exists()) {
                    if (!resourceFile.getParentFile().mkdirs()) {
                        System.out.println("Can't create directory for resource: " + resourceFile.getParent());
                    }
                } else if (resourceFile.exists() && !resourceFile.delete()) {
                    System.out.println("Can't delete previous resource: " + resourceFile.getAbsolutePath());
                }
                final InputStream inputStream = htmlHandler.getResource(path, htmlResource);
                System.out.println("Write resource file: " + resourceFile.getAbsolutePath());
                FileUtils.writeByteArrayToFile(resourceFile, IOUtils.toByteArray(inputStream));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static File getHtmlResourcePath(String fileDisplayName, int documentPageNumber, String resourceName) {
        final String tempPath = viewerConfig.getTempPath();
        return new File(tempPath
                + File.separator
                + fileDisplayName
                + File.separator
                + documentPageNumber
                + File.separator
                + resourceName);
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
     * @param rotationAngle    the rotation angle
     * @param DocumentPassword the document password
     * @return the list
     * @throws Exception the exception
     */
    public static RotatePageResponse rotateDocumentAsHtml(String DocumentName, int pageNumber, int rotationAngle, String DocumentPassword) {
        RotatePageResponse response = new RotatePageResponse();
        try {
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
            final RotatePageContainer rotatePageContainer = Utilities.PageTransformations.rotatePages(handler, DocumentName, pageNumber, rotationAngle);

            response.setResultAngle(rotatePageContainer.getCurrentRotationAngle());
            response.setSuccess(true);
        } catch (Exception e) {
            response.setSuccess(false);
            e.printStackTrace();
        }
        return response;
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
                setPath(path == null || path.isEmpty() ? fileDescription.getName() : path + "/" + fileDescription.getName());
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

    public static GetImageUrlsResponse createImageUrls(GetImageUrlsRequest request) {
        final String path = request.getPath();
        final Integer firstPage = request.getFirstPage();
        Integer pageCount = request.getPageCount();
        final Integer width = request.getWidth();
        final Integer quality = request.getQuality();
        final Boolean usePdf = request.isUsePdf();
        final List<String> imageUrls = new ArrayList<String>();
        if (pageCount == null) { // For printing
            ViewerImageHandler imageHandler = new ViewerImageHandler(viewerConfig);
            pageCount = imageHandler.getPages(path).size();
        }
        for (int n = firstPage; n < firstPage + pageCount; n++) {
            StringBuilder builder = new StringBuilder("/GetDocumentPageImageHandler?")
                    .append("path=").append(path).append("&")
                    .append("pageIndex=").append(n).append("&")
                    .append("width=").append(width == null ? 800 : width);
            imageUrls.add(builder.toString());
        }
        return new GetImageUrlsResponse() {{
            setImageUrls(imageUrls);
        }};
    }

    public static byte[] renderThumbnailForDocumentPage(String path, Integer width, Integer pageIndex) {
        ViewerImageHandler imageHandler = new ViewerImageHandler(viewerConfig);

        //Initialize ImageOptions Object
        ImageOptions options = new ImageOptions();
        if (width != null) {
            options.setWidth((int) (width * 1.7));
            options.setHeight(width * 2);
        }
        options.setPageNumber(pageIndex + 1);
        options.setCountPagesToConvert(1);

        //Get document pages in image form
        synchronized (ViewGenerator.class) {
            List<PageImage> images = imageHandler.getPages(path, options);
            try {
                if (images.size() == 1) {
                    return StreamUtils.copyToByteArray(images.get(0).getStream());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static byte[] readFileData(String path, boolean getPdf) {
        ViewerImageHandler imageHandler = new ViewerImageHandler(viewerConfig);
        final FileContainer file = imageHandler.getFile(path);
        try {
            return IOUtils.toByteArray(file.getStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String uploadFile(MultipartFile file) {
        final String originalFilename = file.getOriginalFilename();
        File uploadPath = new File(viewerConfig.getStoragePath() + File.separator + UPLOAD_DIRECTORY_NAME);
        File uploadFilePath =
                new File(uploadPath.getAbsolutePath()
                        + File.separator
                        + FilenameUtils.getBaseName(originalFilename) + "-"
                        + GregorianCalendar.getInstance().getTimeInMillis() + "."
                        + FilenameUtils.getExtension(originalFilename));
        try {
            final InputStream inputStream = file.getInputStream();
            if (!uploadPath.exists() && !uploadPath.mkdirs()) {
                throw new Exception("Can't create upload directory!");
            }
            FileUtils.writeByteArrayToFile(uploadFilePath, IOUtils.toByteArray(inputStream));
            System.out.println("File uploaded: " + uploadFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return UPLOAD_DIRECTORY_NAME + "/" + uploadFilePath.getName();
    }
}