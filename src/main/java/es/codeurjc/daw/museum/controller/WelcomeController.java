package es.codeurjc.daw.museum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class WelcomeController {

    /*@GetMapping("/welcome-anonymous")
    public String welcomeAnonymous(Model model) {

        List<Map<String, Object>> userSections = new ArrayList<>();

        Map<String, Object> pecesSection = new HashMap<>();
        pecesSection.put("nameSection", "Peces");
        pecesSection.put("image", "/assets/images/icons/logo-pez.png");
        pecesSection.put("link", "/peces");
        pecesSection.put("categories", List.of(
                Map.of("type", "badge-primary", "text", "Mar"),
                Map.of("type", "badge-info", "text", "Agua dulce"),
                Map.of("type", "badge-dark", "text", "Abisales")));
        userSections.add(pecesSection);

        Map<String, Object> insectosSection = new HashMap<>();
        insectosSection.put("nameSection", "Insectos");
        insectosSection.put("image", "/assets/images/icons/logo-mariposa.png");
        insectosSection.put("link", "/insectos");
        insectosSection.put("categories", List.of(
                Map.of("type", "badge-success", "text", "Terrestre"),
                Map.of("type", "badge-warning", "text", "Aéreos"),
                Map.of("type", "badge-primary", "text", "Acuáticos")));
        userSections.add(insectosSection);

        Map<String, Object> fosilesSection = new HashMap<>();
        fosilesSection.put("nameSection", "Fósiles");
        fosilesSection.put("image", "/assets/images/icons/logo-fosil.png");
        fosilesSection.put("link", "/fosiles");
        fosilesSection.put("categories", List.of(
                Map.of("type", "badge-secondary", "text", "Prehistórico"),
                Map.of("type", "badge-dark", "text", "Mineral")));
        userSections.add(fosilesSection);

        Map<String, Object> obrasArteSection = new HashMap<>();
        obrasArteSection.put("nameSection", "Obras de arte");
        obrasArteSection.put("image", "/assets/images/icons/logo-pintura.png");
        obrasArteSection.put("link", "/obras-arte");
        obrasArteSection.put("categories", List.of(
                Map.of("type", "badge-danger", "text", "Pintura"),
                Map.of("type", "badge-warning", "text", "Escultura"),
                Map.of("type", "badge-info", "text", "Grabados"),
                Map.of("type", "badge-primary", "text", "Cerámica")));
        userSections.add(obrasArteSection);

        model.addAttribute("userSections", userSections);

        return "welcome-page-anonymous";
    }*/

}