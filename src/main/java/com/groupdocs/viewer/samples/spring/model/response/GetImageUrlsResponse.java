package com.groupdocs.viewer.samples.spring.model.response;

import java.util.List;

/**
 * The type Get image urls response.
 */
public class GetImageUrlsResponse {
    private List<String> imageUrls;

    /**
     * Instantiates a new Get image urls response.
     */
    public GetImageUrlsResponse() {
    }

    /**
     * Instantiates a new Get image urls response.
     * @param imageUrls the image urls
     */
    public GetImageUrlsResponse(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    /**
     * Gets image urls.
     * @return the image urls
     */
    public List<String> getImageUrls() {
        return imageUrls;
    }

    /**
     * Sets image urls.
     * @param imageUrls the image urls
     */
    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

}
