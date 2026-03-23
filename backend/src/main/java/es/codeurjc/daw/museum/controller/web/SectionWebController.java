package es.codeurjc.daw.museum.controller.web;

import java.security.Principal;
import java.security.cert.PKIXRevocationChecker.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.PageRequest;

import es.codeurjc.daw.museum.model.Image;
import es.codeurjc.daw.museum.model.MuseumObject;
import es.codeurjc.daw.museum.model.User;
import es.codeurjc.daw.museum.service.MuseumObjectService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class SectionWebController {

    @Autowired
    private MuseumObjectService objectService;

    public static class SectionElement {
        private Long id;
        private String nameElement;
        private String objectSectionImage;
        private String categoryName;
        private String type;
        private String goToElement;

        public SectionElement(Long id, String nameElement, String objectSectionImage, String categoryName, String type,
                String goToElement) {
            this.id = id;
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

    public class Button {
        private String type;
        private String text;

        public Button(String type, String text) {
            this.type = type;
            this.text = text;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

    }

    @ModelAttribute
    public void addAttributes(Model model, HttpServletRequest request) {

        Principal principal = request.getUserPrincipal();

        if (principal != null) {

            model.addAttribute("logged", true);
            model.addAttribute("userName", principal.getName());
            model.addAttribute("admin", request.isUserInRole("ADMIN"));

        } else {
            model.addAttribute("logged", false);
        }
    }

    private List<SectionElement> convertToSectionElement(List<MuseumObject> objects, String sectionName) {
        return objects.stream()
                .map(obj -> new SectionElement(obj.getId(),
                        obj.getObjectName(),
                        "/images/" + obj.getImage().getId(),
                        obj.getCategory(),
                        "bg-secondary",
                        "/section/" + sectionName + "/" + obj.getId()))
                .toList();
    }

    // Se podría cambiar por una variable booleana que se meta en el mustache
    @GetMapping("/section/{sectionName}")
    public String viewSection(@PathVariable String sectionName, @RequestParam(required = false) String category,
            @RequestParam(required = false) String searchName,
            Model model) {

        String section = sectionName.toLowerCase();

        List<Button> buttons = new ArrayList<>();

        switch (section) {
            case "peces":
                buttons = List.of(
                        new Button("bg-success", "Agua dulce"),
                        new Button("bg-primary", "Mar"),
                        new Button("bg-danger", "Abisales"));
                break;

            case "insectos":
                buttons = List.of(
                        new Button("bg-warning", "Terrestres"),
                        new Button("bg-info", "Aéreos"),
                        new Button("bg-primary", "Acuáticos"));
                break;

            case "fosiles":
                buttons = List.of(
                        new Button("bg-warning", "Prehistóricos"),
                        new Button("bg-info", "Minerales"));
                break;

            case "arte":
                buttons = List.of(
                        new Button("bg-danger", "Pintura"),
                        new Button("bg-warning", "Escultura"),
                        new Button("bg-success", "Cerámica"));
                break;
        }

        model.addAttribute("buttons", buttons);

        List<MuseumObject> objectsInSection;

        if (searchName != null && !searchName.isEmpty()) {
            objectsInSection = objectService.findByTypeAndName(section, searchName);
            model.addAttribute("searchName", searchName);
        } else if (category != null && !category.isEmpty()) {
            objectsInSection = objectService.findByCategory(category, PageRequest.of(0,4)).getContent();
        } else {
            objectsInSection = objectService.findByType(section, PageRequest.of(0,4)).getContent();
        }

        if (objectsInSection.isEmpty()) {
            model.addAttribute("museumHeroImage", "/assets/images/interior-museo.png");
            model.addAttribute("errorText",
                    "La sección '" + sectionName + "' no existe o no tiene objetos disponibles.");
            model.addAttribute("backLink", "/welcome-user");
            return "error-page";
        }

        model.addAttribute("sectionElements", convertToSectionElement(objectsInSection, sectionName));

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

            case "arte":
                model.addAttribute("heroSectionImage", "/assets/images/fondo-arte.png");
                model.addAttribute("backgroundSectionImage", "/assets/images/fondo-secundario-arte.png");
                model.addAttribute("heroSectionLogo", "/assets/images/icons/logo-pintura.png");
                model.addAttribute("heroSectionTitle", "Obras de arte");
                model.addAttribute("heroSectionInfo",
                        "En esta sección, podrás admirar una colección de obras de arte que abarcan diferentes estilos y épocas. Desde pinturas clásicas hasta esculturas contemporáneas, esta sección te invita a explorar la creatividad humana a través de sus expresiones artísticas.");
                break;

            default:
                model.addAttribute("museumHeroImage", "/assets/images/interior-museo.png");
                model.addAttribute("errorText", "La sección '" + sectionName + "' no existe.");
                model.addAttribute("backLink", "/welcome-user");
                return "error-page";
        }

        return "section-list-page";
    }

    /*
     * @GetMapping("/section/{sectionName}/more/{page}")
     * public String loadMore(
     * 
     * @PathVariable String sectionName,
     * 
     * @PathVariable int page,
     * 
     * @RequestParam(required = false) String category,
     * Model model) {
     * 
     * 
     * String section = sectionName.toLowerCase();
     * List<MuseumObject> objects = objectService
     * .findByType(section, page)
     * .getContent();
     * 
     * 
     * List<SectionElement> sectionElements = objects.stream()
     * .map(obj -> new SectionElement(
     * obj.getObjectName(),
     * "/images/" + obj.getImage().getId(),
     * obj.getCategory(),
     * "bg-secondary",
     * "/object/" + obj.getId()))
     * .toList();
     * 
     * 
     * model.addAttribute("sectionElements", convertToSectionElement(objects,
     * sectionName));
     * 
     * return "partials/section-elements";
     * 
     * 
     * // 1. Imprimimos en la consola para ver que funciona (búscalo en "Debug
     * // Console")
     * System.err.println("--- ENTRANDO EN LOAD MORE ---");
     * System.err.println("Sección: " + sectionName);
     * System.err.println("Página: " + page);
     * System.err.println("Categoría recibida: " + category);
     * 
     * String section = sectionName.toLowerCase();
     * 
     * // 2. Pedimos los objetos de esa página al service
     * List<MuseumObject> objects = objectService.findByType(section,
     * page).getContent();
     * 
     * // 3. Filtramos nosotros mismos para no tocar el Repository
     * List<MuseumObject> filteredObjects;
     * if (category != null && !category.isEmpty()) {
     * filteredObjects = objects.stream()
     * .filter(obj -> obj.getCategory().equalsIgnoreCase(category))
     * .toList();
     * } else {
     * filteredObjects = objects;
     * }
     * 
     * // 4. Mandamos a la plantilla los objetos filtrados
     * model.addAttribute("sectionElements",
     * convertToSectionElement(filteredObjects, sectionName));
     * 
     * return "partials/section-elements";
     * }
     */

    @GetMapping("/section/{sectionName}/more/{page}")
    public String loadMore(
            @PathVariable String sectionName,
            @PathVariable int page,
            @RequestParam(required = false) String category,
            Model model) {

        String section = sectionName.toLowerCase();
        List<MuseumObject> objects;

        // 1. Usamos el Service para traer SOLO lo que necesitamos de la BD
        if (category != null && !category.isEmpty()) {
            // Pedimos la página X de esa categoría específica
            objects = objectService.findByCategory(category, PageRequest.of(0,4)).getContent();
        } else {
            // Pedimos la página X de toda la sección
            objects = objectService.findByType(section, PageRequest.of(0,4)).getContent();
        }

        // 2. Convertimos y mandamos a la plantilla
        model.addAttribute("sectionElements", convertToSectionElement(objects, sectionName));

        return "partials/section-elements";
    }

}