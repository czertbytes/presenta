package cz.danielhodan.presenta.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/presentation")
public class PresentationController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView presentation() {
        final ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("presentation");
        modelAndView.addObject("presentation_name", "Test Presentation");

        return modelAndView;
    }

}
