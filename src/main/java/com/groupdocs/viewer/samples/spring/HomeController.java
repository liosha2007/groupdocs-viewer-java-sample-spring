package com.groupdocs.viewer.samples.spring;

import com.groupdocs.viewer.samples.spring.config.SpringConfig;
import com.groupdocs.viewer.samples.spring.model.request.GetImageUrlsRequest;
import com.groupdocs.viewer.samples.spring.model.request.LoadFileBrowserTreeDataRequest;
import com.groupdocs.viewer.samples.spring.model.request.RotatePageRequest;
import com.groupdocs.viewer.samples.spring.model.request.ViewDocumentRequest;
import com.groupdocs.viewer.samples.spring.model.response.GetImageUrlsResponse;
import com.groupdocs.viewer.samples.spring.model.response.LoadFileBrowserTreeDataResponse;
import com.groupdocs.viewer.samples.spring.model.response.RotatePageResponse;
import com.groupdocs.viewer.samples.spring.model.response.ViewDocumentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The type Home controller.
 * @author Aleksey Permyakov (21.04.2016).
 */
@Controller
@SuppressWarnings("unused")
public class HomeController extends GenericController {

    /**
     * Instantiates a new Home controller.
     * @param springConfig the spring config
     */
    @Autowired
    public HomeController(SpringConfig springConfig) {
        super(springConfig);
    }

    /**
     * Index string.
     * @param model   the model
     * @param request the request
     * @param path    the path
     * @return the string
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model, HttpServletRequest request, @RequestParam(value = "path", required = false, defaultValue = "") String path) {
        final Boolean useHtmlBasedEngine = springConfig.getUseHtmlBasedEngine();
        model.addAttribute("BaseUrl", makeBaseUrl(request));
        model.addAttribute("FilePath", path);
        model.addAttribute("useHtmlBasedEngine", useHtmlBasedEngine == null ? "true" : useHtmlBasedEngine);
        return "index";
    }

    /**
     * Load file browser tree data handler load file browser tree data response.
     * @param request the request
     * @return the load file browser tree data response
     */
    @ResponseBody
    @RequestMapping(value = "/LoadFileBrowserTreeDataHandler", method = RequestMethod.POST)
    public LoadFileBrowserTreeDataResponse loadFileBrowserTreeDataHandler(@RequestBody LoadFileBrowserTreeDataRequest request) {
        return viewerBean.loadFileTree(request.getPath());
    }

    /**
     * View document handler view document response.
     * @param request the servletRequest
     * @return the view document response
     */
    @ResponseBody
    @RequestMapping(value = "/ViewDocumentHandler", method = RequestMethod.POST)
    public ViewDocumentResponse viewDocumentHandler(HttpServletRequest servletRequest, @RequestBody ViewDocumentRequest request) {
        return viewerBean.renderDocument(request, makeBaseUrl(servletRequest));
    }

    /**
     * Gets image urls handler.
     * @param request the request
     * @return the image urls handler
     */
    @ResponseBody
    @RequestMapping(value = "/GetImageUrlsHandler", method = RequestMethod.POST)
    public GetImageUrlsResponse getImageUrlsHandler(@RequestBody GetImageUrlsRequest request) {
        return viewerBean.createImageUrls(request);
    }

    /**
     * Get document page image handler byte [ ].
     * @param path      the path
     * @param width     the width
     * @param pageIndex the page index
     * @return the byte [ ]
     */
    @ResponseBody
    @RequestMapping(value = "/GetDocumentPageImageHandler", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getDocumentPageImageHandler(@RequestParam("path") String path,
                                              @RequestParam("width") Integer width,
                                              @RequestParam("pageIndex") Integer pageIndex) {
        return viewerBean.renderDocumentImage(path, width, pageIndex);
    }

    /**
     * Get file handler byte [ ].
     * @param path     the path
     * @param response the response
     * @return the byte [ ]
     */
    @ResponseBody
    @RequestMapping(value = "/GetFileHandler", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public byte[] getFileHandler(@RequestParam("path") String path, HttpServletResponse response) {
        response.setHeader("Content-Disposition", "attachment; filename=\"" + path + "\"");
        return viewerBean.readDocumentData(path);
    }

    /**
     * Rotate page handler rotate page response.
     * @param request the request
     * @return the rotate page response
     */
    @ResponseBody
    @RequestMapping(value = "/RotatePageHandler", method = RequestMethod.POST)
    public RotatePageResponse rotatePageHandler(@RequestBody RotatePageRequest request) {
        return viewerBean.rotateDocument(request.getPath(), request.getPageNumber() + 1, request.getRotationAmount());
    }

    /**
     * Upload file string.
     * @param file     the file
     * @param response the response
     * @return the string
     */
    @RequestMapping(value = "/UploadFile", method = RequestMethod.POST)
    public String uploadFile(@RequestParam("fileName") MultipartFile file, HttpServletResponse response) {
        return "redirect:" + springConfig.getApplicationPath() + "?path=" + viewerBean.uploadFile(file);
    }
}
