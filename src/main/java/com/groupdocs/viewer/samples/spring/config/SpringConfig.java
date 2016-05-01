package com.groupdocs.viewer.samples.spring.config;

import com.urbanmania.spring.beans.factory.config.annotations.Property;
import org.springframework.stereotype.Component;

/**
 * The type Spring config.
 * @author Aleksey Permyakov (21.04.2016).
 */
@Component
@SuppressWarnings("unused")
public class SpringConfig {
    @Property(key = "storagePath")
    private String storagePath;
    @Property(key = "licensePath")
    private String licensePath;
    @Property(key = "applicationPath")
    private String applicationPath;
    @Property(key = "useHtmlBasedEngine")
    private Boolean useHtmlBasedEngine;

    /**
     * Gets storage path.
     * @return the storage path
     */
    public String getStoragePath() {
        return storagePath;
    }

    /**
     * Sets storage path.
     * @param storagePath the storage path
     */
    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }

    /**
     * Gets license path.
     * @return the license path
     */
    public String getLicensePath() {
        return licensePath;
    }

    /**
     * Sets license path.
     * @param licensePath the license path
     */
    public void setLicensePath(String licensePath) {
        this.licensePath = licensePath;
    }

    /**
     * Gets application path.
     * @return the application path
     */
    public String getApplicationPath() {
        return applicationPath;
    }

    /**
     * Sets application path.
     * @param applicationPath the application path
     */
    public void setApplicationPath(String applicationPath) {
        this.applicationPath = applicationPath;
    }

    /**
     * Gets use html based engine.
     * @return the use html based engine
     */
    public Boolean getUseHtmlBasedEngine() {
        return useHtmlBasedEngine;
    }

    /**
     * Sets use html based engine.
     * @param useHtmlBasedEngine the use html based engine
     */
    public void setUseHtmlBasedEngine(Boolean useHtmlBasedEngine) {
        this.useHtmlBasedEngine = useHtmlBasedEngine;
    }
}
