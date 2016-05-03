package com.groupdocs.viewer.samples.spring.model.response;

import java.util.ArrayList;
import java.util.List;

/**
 * The type View document response.
 */
public class ViewDocumentResponse {
    private String doc_type;
    private String documentDescription;
    private String fileType;
    private String path;
    private String id;
    private List<String> imageUrls = new ArrayList<String>(0);
    private Boolean lic;
    private String name;
    private Integer page_count;
    private String pdfDownloadUrl;
    private String pdfPrintUrl;
    private String token;
    private String url;
    private String[] pageCss;
    private String[] pageHtml;
    private String urlForResourcesInHtml;
    private String sharedCss = "";

    /**
     * Gets doc type.
     * @return the doc type
     */
    public String getDoc_type() {
        return doc_type;
    }

    /**
     * Sets doc type.
     * @param doc_type the doc type
     */
    public void setDoc_type(String doc_type) {
        this.doc_type = doc_type;
    }

    /**
     * Gets document description.
     * @return the document description
     */
    public String getDocumentDescription() {
        return documentDescription;
    }

    /**
     * Sets document description.
     * @param documentDescription the document description
     */
    public void setDocumentDescription(String documentDescription) {
        this.documentDescription = documentDescription;
    }

    /**
     * Gets id.
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets id.
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
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
     * Gets page count.
     * @return the page count
     */
    public Integer getPage_count() {
        return page_count;
    }

    /**
     * Sets page count.
     * @param page_count the page count
     */
    public void setPage_count(Integer page_count) {
        this.page_count = page_count;
    }

    /**
     * Gets url.
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets url.
     * @param url the url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets name.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
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

    /**
     * Gets token.
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets token.
     * @param token the token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Gets lic.
     * @return the lic
     */
    public Boolean getLic() {
        return lic;
    }

    /**
     * Sets lic.
     * @param lic the lic
     */
    public void setLic(Boolean lic) {
        this.lic = lic;
    }

    /**
     * Get page css string [ ].
     * @return the string [ ]
     */
    public String[] getPageCss() {
        return pageCss;
    }

    /**
     * Sets page css.
     * @param pageCss the page css
     */
    public void setPageCss(String[] pageCss) {
        this.pageCss = pageCss;
    }

    /**
     * Get page html string [ ].
     * @return the string [ ]
     */
    public String[] getPageHtml() {
        return pageHtml;
    }

    /**
     * Sets page html.
     * @param pageHtml the page html
     */
    public void setPageHtml(String[] pageHtml) {
        this.pageHtml = pageHtml;
    }

    /**
     * Gets pdf download url.
     * @return the pdf download url
     */
    public String getPdfDownloadUrl() {
        return pdfDownloadUrl;
    }

    /**
     * Sets pdf download url.
     * @param pdfDownloadUrl the pdf download url
     */
    public void setPdfDownloadUrl(String pdfDownloadUrl) {
        this.pdfDownloadUrl = pdfDownloadUrl;
    }

    /**
     * Gets pdf print url.
     * @return the pdf print url
     */
    public String getPdfPrintUrl() {
        return pdfPrintUrl;
    }

    /**
     * Sets pdf print url.
     * @param pdfPrintUrl the pdf print url
     */
    public void setPdfPrintUrl(String pdfPrintUrl) {
        this.pdfPrintUrl = pdfPrintUrl;
    }

    /**
     * Gets file type.
     * @return the file type
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * Sets file type.
     * @param fileType the file type
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /**
     * Gets url for resources in html.
     * @return the url for resources in html
     */
    public String getUrlForResourcesInHtml() {
        return urlForResourcesInHtml;
    }

    /**
     * Sets url for resources in html.
     * @param urlForResourcesInHtml the url for resources in html
     */
    public void setUrlForResourcesInHtml(String urlForResourcesInHtml) {
        this.urlForResourcesInHtml = urlForResourcesInHtml;
    }

    /**
     * Gets shared css.
     * @return the shared css
     */
    public String getSharedCss() {
        return sharedCss;
    }

    /**
     * Sets shared css.
     * @param sharedCss the shared css
     */
    public void setSharedCss(String sharedCss) {
        this.sharedCss = sharedCss;
    }

    /**
     * Equals boolean.
     * @param o the o
     * @return the boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ViewDocumentResponse document = (ViewDocumentResponse) o;

        if (path != null ? !path.equals(document.path) : document.path != null) {
            return false;
        }
        if (id != null ? !id.equals(document.id) : document.id != null) {
            return false;
        }
        if (imageUrls != null ? !imageUrls.equals(document.imageUrls) : document.imageUrls != null) {
            return false;
        }
        if (lic != null ? !lic.equals(document.lic) : document.lic != null) {
            return false;
        }
        if (name != null ? !name.equals(document.name) : document.name != null) {
            return false;
        }
        if (page_count != null ? !page_count.equals(document.page_count) : document.page_count != null) {
            return false;
        }
        if (token != null ? !token.equals(document.token) : document.token != null) {
            return false;
        }
        if (url != null ? !url.equals(document.url) : document.url != null) {
            return false;
        }

        return true;
    }

    /**
     * Hash code int.
     * @return the int
     */
    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (path != null ? path.hashCode() : 0);
        result = 31 * result + (page_count != null ? page_count.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (imageUrls != null ? imageUrls.hashCode() : 0);
        result = 31 * result + (token != null ? token.hashCode() : 0);
        result = 31 * result + (lic != null ? lic.hashCode() : 0);
        return result;
    }
}
