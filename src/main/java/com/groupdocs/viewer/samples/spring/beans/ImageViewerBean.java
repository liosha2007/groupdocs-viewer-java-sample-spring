package com.groupdocs.viewer.samples.spring.beans;

import com.groupdocs.viewer.samples.spring.config.ViewerConfig;
import com.groupdocs.viewer.samples.spring.model.request.ViewDocumentRequest;
import com.groupdocs.viewer.samples.spring.model.response.RotatePageResponse;
import com.groupdocs.viewer.samples.spring.model.response.ViewDocumentResponse;

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
    public ViewDocumentResponse renderDocument(ViewDocumentRequest request) {
        return null;
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
