package es.codeurjc.daw.museum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class NavController {

   @ModelAttribute
    public void addMenuUrls(Model model) {
        model.addAttribute("homeUrl", "/");
        model.addAttribute("pecesUrl", "/section/peces");
        model.addAttribute("insectosUrl", "/section/insectos");
        model.addAttribute("fosilesUrl", "/section/fosiles");
        model.addAttribute("obrasArteUrl", "/section/arte");
    }
}
