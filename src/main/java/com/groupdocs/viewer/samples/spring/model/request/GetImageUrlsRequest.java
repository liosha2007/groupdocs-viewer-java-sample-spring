package com.groupdocs.viewer.samples.spring.model.request;

public class GetImageUrlsRequest {
    private Integer width;
    private String docVersion;
    private Integer firstPage;
    private String path;
    private Integer pageCount;
    private String privateKey;
    private Integer quality;
    private Boolean supportPageRotation;
    private String token;
    private Boolean useHtmlBasedEngine;
    private Boolean usePdf;
    private String userId;
    private String watermarkColor;
    private Integer watermarkFontSize;
    private String watermarkPosition;
    private String watermarkText;
    private Boolean ignoreDocumentAbsence;


    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public String getDocVersion() {
        return docVersion;
    }

    public void setDocVersion(String docVersion) {
        this.docVersion = docVersion;
    }

    public Integer getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(Integer firstPage) {
        this.firstPage = firstPage;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public Integer getQuality() {
        return quality;
    }

    public void setQuality(Integer quality) {
        this.quality = quality;
    }

    public Boolean getSupportPageRotation() {
        return supportPageRotation;
    }

    public void setSupportPageRotation(Boolean supportPageRotation) {
        this.supportPageRotation = supportPageRotation;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean isUsePdf() {
        return usePdf;
    }

    public void setUsePdf(Boolean usePdf) {
        this.usePdf = usePdf;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWatermarkColor() {
        return watermarkColor;
    }

    public void setWatermarkColor(String watermarkColor) {
        this.watermarkColor = watermarkColor;
    }

    public Integer getWatermarkFontSize() {
        return watermarkFontSize;
    }

    public void setWatermarkFontSize(Integer watermarkFontSize) {
        this.watermarkFontSize = watermarkFontSize;
    }

    public String getWatermarkText() {
        return watermarkText;
    }

    public void setWatermarkText(String watermarkText) {
        this.watermarkText = watermarkText;
    }

    public Boolean isUseHtmlBasedEngine() {
        return useHtmlBasedEngine;
    }

    public void setUseHtmlBasedEngine(Boolean useHtmlBasedEngine) {
        this.useHtmlBasedEngine = useHtmlBasedEngine;
    }

    public String getWatermarkPosition() {
        return watermarkPosition;
    }

    public void setWatermarkPosition(String watermarkPosition) {
        this.watermarkPosition = watermarkPosition;
    }

    public Boolean isIgnoreDocumentAbsence() {
        return ignoreDocumentAbsence;
    }

    public void setIgnoreDocumentAbsence(Boolean ignoreDocumentAbsence) {
        this.ignoreDocumentAbsence = ignoreDocumentAbsence;
    }
}
