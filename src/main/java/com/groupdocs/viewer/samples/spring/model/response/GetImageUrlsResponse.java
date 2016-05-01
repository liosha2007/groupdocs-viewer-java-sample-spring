package com.groupdocs.viewer.samples.spring.model.response;

import java.util.List;

public class GetImageUrlsResponse {
    private List<String> imageUrls;

    public GetImageUrlsResponse(){}

    public GetImageUrlsResponse(List<String> imageUrls){
        this.imageUrls = imageUrls;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

}
