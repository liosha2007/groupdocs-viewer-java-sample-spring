package com.groupdocs.viewer.samples.spring.model.response;

import java.util.List;

public class ViewDocumentResponse {
    private String doc_type;
    private String documentDescription;
    private String fileType;
    private String path;
    private String id;
    private List<String> imageUrls;
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

    public String getDoc_type() {
        return doc_type;
    }

    public void setDoc_type(String doc_type) {
        this.doc_type = doc_type;
    }

    public String getDocumentDescription() {
        return documentDescription;
    }

    public void setDocumentDescription(String documentDescription) {
        this.documentDescription = documentDescription;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getPage_count() {
        return page_count;
    }

    public void setPage_count(Integer page_count) {
        this.page_count = page_count;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getLic() {
        return lic;
    }

    public void setLic(Boolean lic) {
        this.lic = lic;
    }

    public String[] getPageCss() {
        return pageCss;
    }

    public void setPageCss(String[] pageCss) {
        this.pageCss = pageCss;
    }

    public String[] getPageHtml() {
        return pageHtml;
    }

    public void setPageHtml(String[] pageHtml) {
        this.pageHtml = pageHtml;
    }

    public String getPdfDownloadUrl() {
        return pdfDownloadUrl;
    }

    public void setPdfDownloadUrl(String pdfDownloadUrl) {
        this.pdfDownloadUrl = pdfDownloadUrl;
    }

    public String getPdfPrintUrl() {
        return pdfPrintUrl;
    }

    public void setPdfPrintUrl(String pdfPrintUrl) {
        this.pdfPrintUrl = pdfPrintUrl;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getUrlForResourcesInHtml() {
        return urlForResourcesInHtml;
    }

    public void setUrlForResourcesInHtml(String urlForResourcesInHtml) {
        this.urlForResourcesInHtml = urlForResourcesInHtml;
    }

    public String getSharedCss() {
        return sharedCss;
    }

    public void setSharedCss(String sharedCss) {
        this.sharedCss = sharedCss;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ViewDocumentResponse document = (ViewDocumentResponse) o;

        if (path != null ? !path.equals(document.path) : document.path != null) return false;
        if (id != null ? !id.equals(document.id) : document.id != null) return false;
        if (imageUrls != null ? !imageUrls.equals(document.imageUrls) : document.imageUrls != null) return false;
        if (lic != null ? !lic.equals(document.lic) : document.lic != null) return false;
        if (name != null ? !name.equals(document.name) : document.name != null) return false;
        if (page_count != null ? !page_count.equals(document.page_count) : document.page_count != null) return false;
        if (token != null ? !token.equals(document.token) : document.token != null) return false;
        if (url != null ? !url.equals(document.url) : document.url != null) return false;

        return true;
    }

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
