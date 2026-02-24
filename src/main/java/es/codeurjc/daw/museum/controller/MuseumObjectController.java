package es.codeurjc.daw.museum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import es.codeurjc.daw.museum.model.MuseumObject;
import es.codeurjc.daw.museum.service.MuseumObjectService;

@Controller
public class MuseumObjectController {

    @Autowired
    private MuseumObjectService objectService;

    @GetMapping("/objects/{id}")
    public String viewObject(@PathVariable Long id, Model model) {
        MuseumObject object = objectService.findById(id)
                .orElseThrow(() -> new RuntimeException("Object not found"));
        model.addAttribute("object", object);
        return "object_detail";
    }
}