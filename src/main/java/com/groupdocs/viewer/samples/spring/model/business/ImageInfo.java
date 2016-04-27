package com.groupdocs.viewer.samples.spring.model.business;

/**
 * The type Image info.
 * @author Aleksey Permyakov (12.04.2016).
 */
public class ImageInfo {
    private String imageUrl;
    private int pageNmber;
    private String htmlContent;

    /**
     * Gets image url.
     * @return the image url
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Sets image url.
     * @param imageUrl the image url
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * Gets page nmber.
     * @return the page nmber
     */
    public int getPageNmber() {
        return pageNmber;
    }

    /**
     * Sets page nmber.
     * @param pageNmber the page nmber
     */
    public void setPageNmber(int pageNmber) {
        this.pageNmber = pageNmber;
    }

    /**
     * Gets html content.
     * @return the html content
     */
    public String getHtmlContent() {
        return htmlContent;
    }

    /**
     * Sets html content.
     * @param htmlContent the html content
     */
    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }
}
