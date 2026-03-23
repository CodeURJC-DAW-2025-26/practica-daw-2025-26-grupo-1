package es.codeurjc.daw.museum.controller.web;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.codeurjc.daw.museum.service.MuseumObjectService;

import java.util.List;

@Controller
public class StatisticsWebController {
    

    @Autowired
    private MuseumObjectService objectService;

    @GetMapping("/statistics")
    public String countObjects(Model model) {
        
        long fishObjects = objectService.countByType("peces");
        long insectObjects = objectService.countByType("insectos");
        long fossilObjects = objectService.countByType("fosiles");
        long artObjects = objectService.countByType("arte");

        model.addAttribute("fishObjects", fishObjects);
        model.addAttribute("insectObjects", insectObjects);
        model.addAttribute("fossilObjects", fossilObjects);
        model.addAttribute("artObjects", artObjects);
        return "data-graphics";
    }

}
