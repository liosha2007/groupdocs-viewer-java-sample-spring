package com.groupdocs.viewer.samples.spring;

import com.groupdocs.viewer.samples.spring.config.SpringConfig;
import com.groupdocs.viewer.samples.spring.model.ViewGenerator;
import com.groupdocs.viewer.samples.spring.model.request.LoadFileBrowserTreeDataRequest;
import com.groupdocs.viewer.samples.spring.model.response.LoadFileBrowserTreeDataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

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
    public String index(Model model, HttpServletRequest request) {
        model.addAttribute("BaseUrl", makeBaseUrl(request));
        return "index";
    }

    /**
     * Load file browser tree data handler load file browser tree data response.
     * @param model   the model
     * @param request the request
     * @return the load file browser tree data response
     */
    @ResponseBody
    @RequestMapping(value = "/LoadFileBrowserTreeDataHandler", method = RequestMethod.POST)
    public LoadFileBrowserTreeDataResponse loadFileBrowserTreeDataHandler(Model model, @RequestBody LoadFileBrowserTreeDataRequest request) {
        return ViewGenerator.loadFileTree(request.getPath());
    }
}
