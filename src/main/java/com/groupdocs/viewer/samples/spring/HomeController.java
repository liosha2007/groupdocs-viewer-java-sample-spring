package com.groupdocs.viewer.samples.spring;

import com.groupdocs.viewer.samples.spring.config.SpringConfig;
import com.groupdocs.viewer.samples.spring.model.ViewGenerator;
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
     * @return the string
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model, HttpServletRequest request, @RequestParam(value = "path", required = false) String path) {
        model.addAttribute("BaseUrl", makeBaseUrl(request));
        if (path != null && !path.isEmpty()) {
            model.addAttribute("FilePath", path);
        }
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
        return ViewGenerator.loadFileTree(request.getPath());
    }

    @ResponseBody
    @RequestMapping(value = "/ViewDocumentHandler", method = RequestMethod.POST)
    public ViewDocumentResponse viewDocumentHandler(@RequestBody ViewDocumentRequest request) {
        return ViewGenerator.renderDocumentAsHtml(request, null);
    }

    @ResponseBody
    @RequestMapping(value = "/GetImageUrlsHandler", method = RequestMethod.POST)
    public GetImageUrlsResponse getImageUrlsHandler(@RequestBody GetImageUrlsRequest request) {
        return ViewGenerator.createImageUrls(request);
    }

    @ResponseBody
    @RequestMapping(value = "/GetDocumentPageImageHandler", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getDocumentPageImageHandler(@RequestParam("path") String path,
                                              @RequestParam("width") Integer width,
                                              @RequestParam("pageIndex") Integer pageIndex) {
        return ViewGenerator.renderThumbnailForDocumentPage(path, width, pageIndex);
    }

    @ResponseBody
    @RequestMapping(value = "/GetFileHandler", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public byte[] getFileHandler(@RequestParam("path") String path, @RequestParam("getPdf") boolean getPdf, HttpServletResponse response) {
        response.setHeader("Content-Disposition", "attachment; filename=\"" + path + "\"");
        return ViewGenerator.readFileData(path, getPdf);
    }

    @ResponseBody
    @RequestMapping(value = "/RotatePageHandler", method = RequestMethod.POST)
    public RotatePageResponse rotatePageHandler(@RequestBody RotatePageRequest request) {
        return ViewGenerator.rotateDocumentAsHtml(request.getPath(), request.getPageNumber() + 1, request.getRotationAmount(), null);
    }

    @RequestMapping(value = "/UploadFile", method = RequestMethod.POST)
    public String uploadFile(@RequestParam("fileName") MultipartFile file, HttpServletResponse response) {
        // Upload file
        return "redirect:" + springConfig.getApplicationPath() + "?path=" + ViewGenerator.uploadFile(file);
    }

}
