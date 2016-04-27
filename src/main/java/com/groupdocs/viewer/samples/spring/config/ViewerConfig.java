package com.groupdocs.viewer.samples.spring.config;

import com.groupdocs.viewer.licensing.License;

import java.io.File;

/**
 * The type Viewer config.
 * @author Aleksey Permyakov (21.04.2016).
 */
public class ViewerConfig extends com.groupdocs.viewer.config.ViewerConfig {
    /**
     * The License path.
     */
    public final String LICENSE_PATH;
    /**
     * The Storage path.
     */
    public final String STORAGE_PATH;

    /**
     * Instantiates a new Viewer config.
     * @param configuration the configuration
     */
    public ViewerConfig(SpringConfig configuration) {
        STORAGE_PATH = configuration.getStoragePath();
        LICENSE_PATH = configuration.getLicensePath();

        if (LICENSE_PATH != null) {
            new License().setLicense(LICENSE_PATH);
        }
    }

    /**
     * Gets storage path.
     * @return the storage path
     */
    @Override
    public String getStoragePath() {
        return STORAGE_PATH;
    }

    /**
     * Gets cache path.
     * @return the cache path
     */
    @Override
    public String getCachePath() {
        final File cache = new File(STORAGE_PATH + "\\cache\\");
        if (!cache.exists() && !cache.mkdirs()) {
            System.out.println("Problem with cache path!");
        }
        return cache.getAbsolutePath();
    }

    /**
     * Gets temp path.
     * @return the temp path
     */
    @Override
    public String getTempPath() {
        final File temp = new File(STORAGE_PATH + "\\temp\\");
        if (!temp.exists() && !temp.mkdirs()) {
            System.out.println("Problem with temp path!");
        }
        return temp.getAbsolutePath();
    }

    /**
     * Gets use cache.
     * @return the use cache
     */
    @Override
    public boolean getUseCache() {
        return false;
    }

    /**
     * Gets use pdf.
     * @return the use pdf
     */
    @Override
    public boolean getUsePdf() {
        return false;
    }
}