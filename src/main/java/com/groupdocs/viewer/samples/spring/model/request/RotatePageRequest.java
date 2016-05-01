package com.groupdocs.viewer.samples.spring.model.request;

/**
 * The type Rotate page request.
 */
public class RotatePageRequest {
    private int pageNumber;
    private String path;
    private int rotationAmount;

    /**
     * Gets page number.
     * @return the page number
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * Sets page number.
     * @param pageNumber the page number
     */
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * Gets path.
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets path.
     * @param path the path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Gets rotation amount.
     * @return the rotation amount
     */
    public int getRotationAmount() {
        return rotationAmount;
    }

    /**
     * Sets rotation amount.
     * @param rotationAmount the rotation amount
     */
    public void setRotationAmount(int rotationAmount) {
        this.rotationAmount = rotationAmount;
    }

}
