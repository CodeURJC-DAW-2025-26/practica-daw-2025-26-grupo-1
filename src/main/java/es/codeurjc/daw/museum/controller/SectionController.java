package es.codeurjc.daw.museum.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.codeurjc.daw.museum.model.Image;
import es.codeurjc.daw.museum.model.MuseumObject;
import es.codeurjc.daw.museum.model.User;
import es.codeurjc.daw.museum.service.MuseumObjectService;

@Controller
public class SectionController {

    @Autowired
    private MuseumObjectService objectService;

    @GetMapping("/sections")
    public String listSections(Model model, @AuthenticationPrincipal User user) {

        // 1️⃣ Obtener todos los objetos
        List<MuseumObject> allObjects = objectService.findAll();

        // 2️⃣ Agrupar objetos por type → secciones
        Map<String, List<MuseumObject>> sectionsMap = allObjects.stream()
                .collect(Collectors.groupingBy(MuseumObject::getType));

        // 3️⃣ Crear DTOs
        List<SectionDto> sections = sectionsMap.entrySet().stream()
                .map(entry -> {
                    String sectionName = entry.getKey();
                    List<MuseumObject> objects = entry.getValue();

                    Image coverImage = objects.isEmpty() ? null : objects.get(0).getImage();

                    List<CategoryDto> categories = objects.stream()
                            .map(obj -> new CategoryDto(obj.getCategory(), obj.getType()))
                            .distinct()
                            .toList();

                    return new SectionDto(sectionName, coverImage, categories);
                })
                .toList();

        // 4️⃣ Tipo de usuario para Mustache
        String userType = "anonymous";
        if (user != null) {
            if (user.getRoles().contains("ADMIN")) {
                userType = "admin";
            } else {
                userType = "registered";
            }
        }

        model.addAttribute("userType", userType);
        model.addAttribute("sections", sections);

        return "sections";
    }

    // DTOs para Mustache
    public record SectionDto(String name, Image image, List<CategoryDto> categories) {}
    public record CategoryDto(String text, String type) {}
}