package com.groupdocs.viewer.samples.spring.beans;

import com.groupdocs.viewer.converter.options.HtmlOptions;
import com.groupdocs.viewer.domain.Transformation;
import com.groupdocs.viewer.domain.containers.RotatePageContainer;
import com.groupdocs.viewer.domain.html.PageHtml;
import com.groupdocs.viewer.handler.ViewerHandler;
import com.groupdocs.viewer.handler.ViewerHtmlHandler;
import com.groupdocs.viewer.licensing.License;
import com.groupdocs.viewer.samples.spring.config.ViewerConfig;
import com.groupdocs.viewer.samples.spring.model.DocumentType;
import com.groupdocs.viewer.samples.spring.model.FileType;
import com.groupdocs.viewer.samples.spring.model.Utilities;
import com.groupdocs.viewer.samples.spring.model.request.ViewDocumentRequest;
import com.groupdocs.viewer.samples.spring.model.response.RotatePageResponse;
import com.groupdocs.viewer.samples.spring.model.response.ViewDocumentResponse;

import java.util.List;

/**
 * The type Html viewer bean.
 * @author Aleksey Permyakov (01.05.2016).
 */
public class HtmlViewerBean extends GenericViewerBean {
    /**
     * Instantiates a new Html viewer bean.
     * @param viewerConfig the viewer config
     */
    public HtmlViewerBean(ViewerConfig viewerConfig) {
        super(viewerConfig);
    }

    /**
     * Render document view document response.
     * @param request the request
     * @return the view document response
     */
    @Override
    public ViewDocumentResponse renderDocument(ViewDocumentRequest request, String baseUrl) {

        // Create html handler
        ViewerHtmlHandler htmlHandler = new ViewerHtmlHandler(viewerConfig);

        //Instantiate the HtmlOptions object
        HtmlOptions options = new HtmlOptions();

        //to get html representations of pages with embedded resources
        options.setResourcesEmbedded(true);

        final ViewDocumentResponse viewDocumentResponse = new ViewDocumentResponse();
        try {
            //Get document pages in html form
            final String path = request.getPath();
            final List<PageHtml> pages = htmlHandler.getPages(path, options);
            final FileType fileType = FileType.fromFileName(path);
            viewDocumentResponse.setDoc_type(DocumentType.getDocumentType(fileType));
            viewDocumentResponse.setFileType(fileType.name());
            viewDocumentResponse.setPath(path);
            viewDocumentResponse.setLic(License.isValidLicense());
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
//                saveDocumentHtmlResources(request.getFileDisplayName(), htmlHandler, pageHtml, path);
            }
            builder.append("],\"maxPageHeight\":1000,\"widthForMaxHeight\":800}");
            viewDocumentResponse.setDocumentDescription(builder.toString());

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
        RotatePageResponse response = new RotatePageResponse();
        try {
            // Create image handler
            ViewerHandler handler = new ViewerHtmlHandler(viewerConfig);
            //Initialize ImageOptions Object and setting Rotate Transformation
            HtmlOptions options = new HtmlOptions();
            options.setTransformations(Transformation.Rotate);

            //Call RotatePages to apply rotate transformation to a page
            final RotatePageContainer rotatePageContainer = Utilities.PageTransformations.rotatePages(handler, path, pageNumber, rotationAngle);

            response.setResultAngle(rotatePageContainer.getCurrentRotationAngle());
            response.setSuccess(true);
        } catch (Exception e) {
            response.setSuccess(false);
            e.printStackTrace();
        }
        return response;
    }
}
