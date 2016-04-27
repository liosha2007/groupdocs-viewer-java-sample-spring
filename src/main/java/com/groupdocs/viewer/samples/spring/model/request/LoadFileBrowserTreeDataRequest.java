package com.groupdocs.viewer.samples.spring.model.request;

/**
 * The type Load file browser tree data request.
 */
public class LoadFileBrowserTreeDataRequest {
    private String userId;
    private String privateKey;
    private String path;
    private Integer pageIndex;
    private Integer pageSize;
    private String orderBy;
    private Boolean orderAsc;
    private String filter;
    private String fileTypes;
    private Boolean extended;

    /**
     * Gets user id.
     * @return the user id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets user id.
     * @param userId the user id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Gets private key.
     * @return the private key
     */
    public String getPrivateKey() {
        return privateKey;
    }

    /**
     * Sets private key.
     * @param privateKey the private key
     */
    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
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
     * Gets page index.
     * @return the page index
     */
    public Integer getPageIndex() {
        return pageIndex;
    }

    /**
     * Sets page index.
     * @param pageIndex the page index
     */
    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    /**
     * Gets page size.
     * @return the page size
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * Sets page size.
     * @param pageSize the page size
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Gets order by.
     * @return the order by
     */
    public String getOrderBy() {
        return orderBy;
    }

    /**
     * Sets order by.
     * @param orderBy the order by
     */
    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    /**
     * Gets order asc.
     * @return the order asc
     */
    public Boolean getOrderAsc() {
        return orderAsc;
    }

    /**
     * Sets order asc.
     * @param orderAsc the order asc
     */
    public void setOrderAsc(Boolean orderAsc) {
        this.orderAsc = orderAsc;
    }

    /**
     * Gets filter.
     * @return the filter
     */
    public String getFilter() {
        return filter;
    }

    /**
     * Sets filter.
     * @param filter the filter
     */
    public void setFilter(String filter) {
        this.filter = filter;
    }

    /**
     * Gets file types.
     * @return the file types
     */
    public String getFileTypes() {
        return fileTypes;
    }

    /**
     * Sets file types.
     * @param fileTypes the file types
     */
    public void setFileTypes(String fileTypes) {
        this.fileTypes = fileTypes;
    }

    /**
     * Gets extended.
     * @return the extended
     */
    public Boolean getExtended() {
        return extended;
    }

    /**
     * Sets extended.
     * @param extended the extended
     */
    public void setExtended(Boolean extended) {
        this.extended = extended;
    }
}
