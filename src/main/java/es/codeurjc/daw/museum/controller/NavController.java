package es.codeurjc.daw.museum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class NavController {

   @ModelAttribute
    public void addMenuUrls(Model model) {
        model.addAttribute("homeUrl", "/");
        model.addAttribute("pecesUrl", "/peces");
        model.addAttribute("insectosUrl", "/insectos");
        model.addAttribute("fosilesUrl", "/fosiles");
        model.addAttribute("obrasArteUrl", "/obras-arte");
    }
}
