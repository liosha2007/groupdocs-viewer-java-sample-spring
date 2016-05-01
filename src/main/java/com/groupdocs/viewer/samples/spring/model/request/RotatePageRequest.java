package com.groupdocs.viewer.samples.spring.model.request;

public class RotatePageRequest {
    private int pageNumber;
    private String path;
    private int rotationAmount;

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getRotationAmount() {
        return rotationAmount;
    }

    public void setRotationAmount(int rotationAmount) {
        this.rotationAmount = rotationAmount;
    }
    
}
