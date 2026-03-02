package es.codeurjc.daw.museum.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import es.codeurjc.daw.museum.model.Image;
import es.codeurjc.daw.museum.model.MuseumObject;
import es.codeurjc.daw.museum.model.User;
import es.codeurjc.daw.museum.service.MuseumObjectService;

@Controller
public class SectionController {

    @Autowired
    private MuseumObjectService objectService;

    /*
     * @GetMapping("/sections")
     * public String listSections(Model model, @AuthenticationPrincipal User user) {
     * 
     * // Get all objects
     * List<MuseumObject> allObjects = objectService.findAll();
     * 
     * // Group objects by type → sections
     * Map<String, List<MuseumObject>> sectionsMap = allObjects.stream()
     * .collect(Collectors.groupingBy(MuseumObject::getType));
     * 
     * // Create DTOs
     * List<SectionDto> sections = sectionsMap.entrySet().stream()
     * .map(entry -> {
     * String sectionName = entry.getKey();
     * List<MuseumObject> objects = entry.getValue();
     * 
     * Image coverImage = objects.isEmpty() ? null : objects.get(0).getImage();
     * 
     * List<CategoryDto> categories = objects.stream()
     * .map(obj -> new CategoryDto(obj.getCategory(), obj.getType()))
     * .distinct()
     * .toList();
     * 
     * return new SectionDto(sectionName, coverImage, categories);
     * })
     * .toList();
     * 
     * // Type of user for Mustache
     * String userType = "anonymous";
     * if (user != null) {
     * if (user.getRoles().contains("ADMIN")) {
     * userType = "admin";
     * } else {
     * userType = "registered";
     * }
     * }
     * 
     * // Add backUrl depending on user type
     * String backUrl;
     * if ("admin".equals(userType)) {
     * backUrl = "/sections/admin";
     * } else if ("registered".equals(userType)) {
     * backUrl = "/sections/registered";
     * } else {
     * backUrl = "/sections/anonymous";
     * }
     * 
     * model.addAttribute("userType", userType);
     * model.addAttribute("sections", sections);
     * model.addAttribute("backUrl", backUrl);
     * 
     * // Generic template
     * return "sections";
     * }
     * 
     * // DTOs para Mustache
     * public record SectionDto(String name, Image image, List<CategoryDto>
     * categories) {}
     * public record CategoryDto(String text, String type) {}
     * 
     */

    public static class SectionElement {
        private String nameElement;
        private String objectSectionImage;
        private String categoryName;
        private String type;
        private String goToElement;

        public SectionElement(String nameElement, String objectSectionImage, String categoryName, String type,
                String goToElement) {
            this.nameElement = nameElement;
            this.objectSectionImage = objectSectionImage;
            this.categoryName = categoryName;
            this.type = type;
            this.goToElement = goToElement;
        }

        public String getNameElement() {
            return nameElement;
        }

        public String getObjectSectionImage() {
            return objectSectionImage;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public String getType() {
            return type;
        }

        public String getGoToElement() {
            return goToElement;
        }
    }

    @GetMapping("/section/{sectionName}")
    public String viewSection(@PathVariable String sectionName, Model model) {

        String section = sectionName.toLowerCase();

        List<MuseumObject> objectsInSection = objectService.findByType(section);

        if (objectsInSection.isEmpty()) {
            model.addAttribute("museumHeroImage", "/assets/images/interior-museo.png");
            model.addAttribute("errorText",
                    "La sección '" + sectionName + "' no existe o no tiene objetos disponibles.");
            model.addAttribute("backLink", "/welcome-anonymous");
            return "error-page";
        }

        model.addAttribute("sectionName", sectionName);
        model.addAttribute("objects", objectsInSection);

        switch (section) {

            case "peces":
                model.addAttribute("heroSectionImage", "/assets/images/fondo-marino.png");
                model.addAttribute("backgroundSectionImage", "/assets/images/fondo-marino-siluetas.png");
                model.addAttribute("heroSectionLogo", "/assets/images/icons/logo-pez.png");
                model.addAttribute("heroSectionTitle", "Peces y criaturas marinas");
                model.addAttribute("heroSectionInfo",
                        "En esta sección, podrás sumergirte en el fascinante mundo de los peces marinos. Explora su diversidad, colores y formas únicas en esta sección dedicada a las maravillas acuáticas.");
                break;

            case "insectos":
                model.addAttribute("heroSectionImage", "/assets/images/fondo-insectos.png");
                model.addAttribute("backgroundSectionImage", "/assets/images/fondo-insectos-siluetas.png");
                model.addAttribute("heroSectionLogo", "/assets/images/icons/logo-mariposa.png");
                model.addAttribute("heroSectionTitle", "Insectos");
                model.addAttribute("heroSectionInfo",
                        "Adéntrate en el mundo de los insectos, criaturas asombrosas que habitan nuestro planeta. Desde coloridas mariposas hasta fascinantes escarabajos, esta sección te invita a descubrir la diversidad y belleza de estos pequeños seres.");
                break;

            case "fosiles":
                model.addAttribute("heroSectionImage", "/assets/images/fondo-fosiles.png");
                model.addAttribute("backgroundSectionImage", "/assets/images/fondo-fosiles-siluetas.png");
                model.addAttribute("heroSectionLogo", "/assets/images/icons/logo-fosil.png");
                model.addAttribute("heroSectionTitle", "Fósiles y minerales");
                model.addAttribute("heroSectionInfo",
                        "Los fósiles son restos o impresiones de organismos que vivieron en el pasado, mientras que los minerales son sustancias sólidas inorgánicas con una composición química definida. En esta sección, podrás explorar la historia de la Tierra a través de sus fósiles y descubrir la belleza de los minerales que la componen.");
                break;

            /*
             * case "arte":
             * model.addAttribute("heroSectionImage",
             * "/assets/images/fondo-marino.png");
             * model.addAttribute("backgroundSectionImage",
             * "/assets/images/fondo-marino-siluetas.png");
             * model.addAttribute("heroSectionLogo", "/assets/images/icons/logo-pez.png");
             * model.addAttribute("heroSectionTitle", "Peces");
             * model.addAttribute("heroSectionInfo",
             * "Descubre el fascinante mundo de los peces marinos. Explora su diversidad, colores y formas únicas en esta sección dedicada a las maravillas acuáticas."
             * );
             * break;
             */

            default:
                model.addAttribute("museumHeroImage", "/assets/images/interior-museo.png");
                model.addAttribute("errorText", "La sección '" + sectionName + "' no existe.");
                model.addAttribute("backLink", "/welcome-anonymous");
                return "error-page";
        }

        return "section-list-page-anonymous";
    }

}