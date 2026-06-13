package es.codeurjc.daw.museum.controller.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import es.codeurjc.daw.museum.model.MuseumObject;
import es.codeurjc.daw.museum.model.User;
import es.codeurjc.daw.museum.service.MuseumObjectService;
import es.codeurjc.daw.museum.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class SectionWebController {

    @Autowired
    private MuseumObjectService objectService;

    @Autowired
    private UserService userService;

    // DTO used to transfer object data to the view layer
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

    // Represents UI button with style and label for filtering
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

        // Retrieves authenticated user and adds common attributes for all views
        Principal principal = request.getUserPrincipal();

        if (principal != null) {
            model.addAttribute("logged", true);
            model.addAttribute("userName", principal.getName());
            model.addAttribute("admin", request.isUserInRole("ADMIN"));

            // Loads full user entity from database
            Optional<User> userOpt = userService.findByUsername(principal.getName());

            if (userOpt.isPresent()) {
                User user = userOpt.get();
                // Adds user object to model (used for navbar/profile rendering)
                model.addAttribute("user", user);
            }

        } else {
            // Marks user as not authenticated
            model.addAttribute("logged", false);
        }
    }

    private List<SectionElement> convertToSectionElement(List<MuseumObject> objects, String sectionName) {

        // Converts MuseumObject entities into DTO-like objects for view rendering
        List<SectionElement> sectionElements = new ArrayList<>();

        for (MuseumObject obj : objects) {

            String imageRoute;

            if (obj.getImage() == null) {
                imageRoute = "/images/no_image.png";
            } else {
                imageRoute = "/images/" + obj.getImage().getId();
            }

            sectionElements.add(new SectionElement(
                    obj.getId(), obj.getObjectName(), imageRoute, obj.getCategory(), "bg-secondary",
                    "/section/" + sectionName + "/" + obj.getId()));

        }

        return sectionElements;

    }

    @GetMapping("/section/{sectionName}")
    public String viewSection(@PathVariable String sectionName, @RequestParam(required = false) String category,
            @RequestParam(required = false) String searchName,
            Model model, Principal principal, Pageable pageable) {

        // Loads objects based on section, category, or search filter and configures UI
        // elements (buttons, images, descriptions)

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
            objectsInSection = objectService.findByTypeAndName(section, searchName, pageable).getContent();
            model.addAttribute("searchName", searchName);
        } else if (category != null && !category.isEmpty()) {
            objectsInSection = objectService.findByCategory(category, PageRequest.of(0, 4)).getContent();
        } else {
            objectsInSection = objectService.findByType(section, PageRequest.of(0, 4)).getContent();
        }

        // Handles empty results with error page
        if (objectsInSection.isEmpty()) {
            model.addAttribute("museumHeroImage", "/assets/images/interior-museo.png");
            model.addAttribute("errorText",
                    "La sección no existe o no tiene objetos disponibles.");
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
                model.addAttribute("errorText", "La sección no existe o no tiene objetos disponibles.");
                model.addAttribute("backLink", "/welcome-user");
                return "error-page";
        }

        if (principal != null) {
            User user = userService.findByUsername(principal.getName()).orElse(null);
            if (user != null) {

                List<MuseumObject> allInThisSection = objectService.findByTypeAll(section);
                int totalCount = allInThisSection.size();

                if (totalCount > 0) {

                    long seenCount = user.getSeen().stream()
                            .filter(obj -> obj.getType().equalsIgnoreCase(section))
                            .count();

                    int percentage = (int) ((seenCount * 100) / totalCount);

                    model.addAttribute("showProgress", true);
                    model.addAttribute("progressPercentage", percentage);
                }
            }
        }

        return "section-list-page";
    }

    @GetMapping("/section/{sectionName}/more/{page}")
    public String loadMore(
            @PathVariable String sectionName,
            @PathVariable int page,
            @RequestParam(required = false) String category,
            Model model) {

        // Loads additional objects for pagination (AJAX / partial rendering)
        String section = sectionName.toLowerCase();
        List<MuseumObject> objects;

        if (category != null && !category.isEmpty()) {
            objects = objectService.findByCategory(category, PageRequest.of(page, 4)).getContent();
        } else {
            objects = objectService.findByType(section, PageRequest.of(page, 4)).getContent();
        }

        model.addAttribute("sectionElements", convertToSectionElement(objects, sectionName));

        return "partials/section-elements";
    }

}