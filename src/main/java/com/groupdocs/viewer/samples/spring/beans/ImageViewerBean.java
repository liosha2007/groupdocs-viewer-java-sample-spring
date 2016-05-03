package com.groupdocs.viewer.samples.spring.beans;

import com.groupdocs.viewer.converter.options.ImageOptions;
import com.groupdocs.viewer.domain.image.PageImage;
import com.groupdocs.viewer.handler.ViewerImageHandler;
import com.groupdocs.viewer.licensing.License;
import com.groupdocs.viewer.samples.spring.config.ViewerConfig;
import com.groupdocs.viewer.samples.spring.model.DocumentType;
import com.groupdocs.viewer.samples.spring.model.FileType;
import com.groupdocs.viewer.samples.spring.model.request.ViewDocumentRequest;
import com.groupdocs.viewer.samples.spring.model.response.RotatePageResponse;
import com.groupdocs.viewer.samples.spring.model.response.ViewDocumentResponse;

import java.util.List;

/**
 * The type Image viewer bean.
 * @author Aleksey Permyakov (01.05.2016).
 */
public class ImageViewerBean extends GenericViewerBean {
    /**
     * Instantiates a new Image viewer bean.
     * @param viewerConfig the viewer config
     */
    public ImageViewerBean(ViewerConfig viewerConfig) {
        super(viewerConfig);
    }

    /**
     * Render document view document response.
     * @param request the request
     * @return the view document response
     */
    @Override
    public ViewDocumentResponse renderDocument(ViewDocumentRequest request, String baseUrl) {

        // Create image handler
        ViewerImageHandler imageHandler = new ViewerImageHandler(viewerConfig);

        //Instantiate the ImageOptions object
        ImageOptions options = new ImageOptions();

        final ViewDocumentResponse viewDocumentResponse = new ViewDocumentResponse();
        final String path = request.getPath();
        final List<PageImage> pages = imageHandler.getPages(path, options);
        try {
            final FileType fileType = FileType.fromFileName(path);
            viewDocumentResponse.setDoc_type(DocumentType.getDocumentType(fileType));
            viewDocumentResponse.setDocumentDescription("{\"pages\":[],\"maxPageHeight\":792.0,\"widthForMaxHeight\":612.0}"); // TODO: Should be filled
            viewDocumentResponse.setFileType(fileType.name());
            viewDocumentResponse.setPath(path);
            viewDocumentResponse.setId("");
            for (int n = 0; n < pages.size(); n++) {
                StringBuilder builder = new StringBuilder(baseUrl)
                        .append("GetDocumentPageImageHandler").append("?")
                        .append("path=").append(path).append("&")
                        .append("pageIndex=").append(n).append("&")
                        .append("width=").append("1126").append("&")
                        .append("quality=").append("100").append("&")
                        .append("usePdf=").append("true").append("&")
                        .append("watermarkPosition=").append("&")
                        .append("watermarkFontSize=").append("0").append("&")
                        .append("useHtmlBasedEngine=").append(false).append("&")
                        .append("rotate=").append("true").append("&")
                        .append("isPrint=").append("false");
                viewDocumentResponse.getImageUrls().add(builder.toString());
            }
            viewDocumentResponse.setLic(License.isValidLicense());
            viewDocumentResponse.setName(path);
            viewDocumentResponse.setPage_count(pages.size());
            viewDocumentResponse.setPdfDownloadUrl(baseUrl + "GetFileHandler?path=" + path + "&getPdf=true");
            viewDocumentResponse.setPdfPrintUrl(baseUrl + "GetPdfWithPrintDialog?path=" + path);
            viewDocumentResponse.setToken("");
            viewDocumentResponse.setUrl(baseUrl + "GetFileHandler?path=" + path + "&getPdf=false");
            viewDocumentResponse.setPageCss(null);
            viewDocumentResponse.setPageHtml(null);
            viewDocumentResponse.setUrlForResourcesInHtml(null);
            viewDocumentResponse.setSharedCss("");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return viewDocumentResponse;
    }

    /**
     * Rotate document rotate page response.
     * @param path          the path
     * @param pageNumber    the page number
     * @param rotationAngle the rotation angle
     * @return the rotate page response
     */
    @Override
    public RotatePageResponse rotateDocument(String path, int pageNumber, int rotationAngle) {
        return null;
    }


}
