package com.groupdocs.viewer.samples.spring;

import com.groupdocs.viewer.samples.spring.config.SpringConfig;
import com.groupdocs.viewer.samples.spring.config.ViewerConfig;
import com.groupdocs.viewer.samples.spring.model.ViewGenerator;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Generic controller.
 * @author Aleksey Permyakov (21.04.2016).
 */
public class GenericController {

    /**
     * The Spring config.
     */
    protected SpringConfig springConfig;
    /**
     * The Viewer config.
     */
    protected ViewerConfig viewerConfig;

    /**
     * Instantiates a new Generic controller.
     * @param springConfig the spring config
     */
    public GenericController(SpringConfig springConfig) {
        this.viewerConfig = new ViewerConfig(springConfig);
        this.springConfig = springConfig;
        ViewGenerator.initGenerator(viewerConfig);
    }

    /**
     * Make base url object.
     * @param request the request
     * @return the object
     */
    protected Object makeBaseUrl(HttpServletRequest request) {
        String appPath = springConfig.getApplicationPath();
        if (appPath == null || appPath.equalsIgnoreCase("null") || appPath.isEmpty()) {
            final String contextPath = request.getContextPath();
            final StringBuilder basePath = new StringBuilder()
                    .append(request.getScheme())
                    .append("://")
                    .append(request.getLocalAddr())
                    .append(":")
                    .append(request.getLocalPort())
                    .append(contextPath == null ? "/" : !contextPath.endsWith("/") || contextPath.isEmpty() ? contextPath + "/" : contextPath);
            return basePath.toString();
        } else {
            return appPath.endsWith("/") ? appPath : appPath + "/";
        }
    }

}
