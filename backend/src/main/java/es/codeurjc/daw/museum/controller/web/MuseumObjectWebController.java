package es.codeurjc.daw.museum.controller.web;

import java.util.Optional;
import java.io.IOException;
import java.security.Principal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.PageRequest;

import es.codeurjc.daw.museum.controller.web.SectionWebController.SectionElement;

import es.codeurjc.daw.museum.model.Image;
import es.codeurjc.daw.museum.model.MuseumObject;
import es.codeurjc.daw.museum.model.Note;
import es.codeurjc.daw.museum.model.User;
import es.codeurjc.daw.museum.service.ImageService;
import es.codeurjc.daw.museum.service.MuseumObjectService;
import es.codeurjc.daw.museum.service.NoteService;
import es.codeurjc.daw.museum.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MuseumObjectWebController {

    @Autowired
    private UserService userService;

    @Autowired
    private MuseumObjectService objectService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private NoteService noteService;

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

    @GetMapping("/section/{sectionName}/{id}")
    public String showObject(Model model, @PathVariable String sectionName, @PathVariable long id,
            Principal principal) {

        String section = sectionName.toLowerCase();

        // Retrieves museum object by ID
        Optional<MuseumObject> museumObject = objectService.findById(id);

        // Adds object data to model for rendering (name, category, description, image,
        // etc.)
        if (museumObject.isPresent()) {
            MuseumObject obj = museumObject.get();

            switch (section) {
                case "peces":
                    model.addAttribute("backgroundSectionImage", "/assets/images/fondo-marino-siluetas.png");
                    break;
                case "insectos":
                    model.addAttribute("backgroundSectionImage", "/assets/images/fondo-insectos-siluetas.png");
                    break;
                case "fosiles":
                    model.addAttribute("backgroundSectionImage", "/assets/images/fondo-fosiles-siluetas.png");
                    break;
                case "arte":
                    model.addAttribute("backgroundSectionImage", "/assets/images/fondo-secundario-arte.png");
                    break;
                default:
                    model.addAttribute("backgroundSectionImage", "/assets/images/interior-museo.png");
                    break;
            }

            model.addAttribute("nameElement", obj.getObjectName());
            model.addAttribute("groupNameElement", obj.getGroupName());
            model.addAttribute("categoryName", obj.getCategory());
            model.addAttribute("type", obj.getType());
            model.addAttribute("elementImage", obj.getImage().getId().toString());
            model.addAttribute("descriptionElement", obj.getDescription());
            model.addAttribute("technicalData", obj.getTechnicalData());
            model.addAttribute("elementId", obj.getId());

            model.addAttribute("backUrl", "/section/" + sectionName);

            // If user is authenticated, load user-specific data (notes, seen status)
            if (principal != null) {

                User user = userService.findByUsername(principal.getName()).orElse(null);
                if (user != null) {
                    model.addAttribute("isSeen", user.getSeen().contains(obj));
                    List<Note> notes = noteService.findAllByUserAndMuseumObject(user, obj);
                    model.addAttribute("userNotes", notes);
                }
            } else {

                model.addAttribute("userNotes", new ArrayList<>());
                model.addAttribute("isSeen", false);
            }

            return "informative-page";

        } else {

            List<MuseumObject> objects = objectService
                    .findByType(section, PageRequest.of(0, 4))
                    .getContent();

            model.addAttribute("sectionName", sectionName);

            model.addAttribute("sectionElements", convertToSectionElement(objects, sectionName));

            return "partials/section-elements";
        }
    }

    private List<SectionWebController.SectionElement> convertToSectionElement(List<MuseumObject> objects,
            String sectionName) {

        // Converts MuseumObject entities into DTO-like objects for view rendering
        return objects.stream()
                .map(obj -> new SectionWebController.SectionElement(obj.getId(),
                        obj.getObjectName(),
                        "/images/" + obj.getImage().getId(),
                        obj.getCategory(),
                        "bg-secondary",
                        "/section/" + sectionName + "/" + obj.getId()))
                .toList();
    }

    @GetMapping("/search")
    public String search(@RequestParam String searchName) {

        // Searches objects by name and redirects to first result if found
        List<MuseumObject> results = objectService.findByName(searchName);

        if (!results.isEmpty()) {
            MuseumObject obj = results.get(0);
            return "redirect:/section/" + obj.getType() + "/" + obj.getId();
        } else {
            return "redirect:/system-error?action=objectNotFound";
        }
    }

    @PostMapping("/objects/delete/{id}")
    public String deleteObjectSelected(@PathVariable long id) {
        objectService.deleteObject(id);

        return "redirect:/confirmation?action=deleteObject";
    }

    @GetMapping("/admin/objects/new")
    public String newMuseumObject(Model model, @RequestParam String sectionName) {

        String imageName;

        switch (sectionName.toLowerCase()) {
            case "peces":
                imageName = "fondo-marino-siluetas.png";
                break;
            case "arte":
                imageName = "fondo-secundario-arte.png";
                break;
            case "insectos":
                imageName = "fondo-insectos-siluetas.png";
                break;
            default:

                imageName = "fondo-" + sectionName + "-siluetas.png";
                break;
        }

        model.addAttribute("museumObject", new MuseumObject());
        model.addAttribute("sectionName", sectionName);
        model.addAttribute("backToSection", sectionName);
        model.addAttribute("formImage", "/assets/images/" + imageName);

        model.addAttribute("isPeces", sectionName.equals("peces"));
        model.addAttribute("isInsectos", sectionName.equals("insectos"));
        model.addAttribute("isFosiles", sectionName.equals("fosiles"));
        model.addAttribute("isArte", sectionName.equals("arte"));

        return "new-object-form";
    }

    @PostMapping("/newObject")
    public String newMuseumObjectProcess(MuseumObject museumObject, MultipartFile imageField) throws IOException {

        // Validates required fields before saving object
        if (museumObject.getObjectName() == null || museumObject.getObjectName().isBlank()
                || museumObject.getGroupName() == null || museumObject.getGroupName().isBlank()) {
            return "redirect:/system-error?action=invalidData";
        }

        // Creates and assigns image if provided
        if (imageField != null && !imageField.isEmpty()) {
            Image image = imageService.createImage(imageField.getInputStream());
            museumObject.setImage(image);
        } else {
            return "redirect:/system-error?action=addObject";
        }

        // Saves object in database
        objectService.saveObject(museumObject);

        return "redirect:/confirmation?action=addObject&section=" + museumObject.getType();
    }

    @PostMapping("/objects/edit/{id}")
    public String editObjectProcess(@PathVariable long id, MuseumObject updatedObject,
            @RequestParam(required = false) boolean removeImage, MultipartFile imageField) throws IOException {

        // Updates or removes image if requested
        if (removeImage) {
            objectService.removeImageFromObject(id);
        } else if (imageField != null && !imageField.isEmpty()) {
            objectService.updateObjectImage(id, imageField);
        }

        // Replaces object data with updated values
        objectService.replaceObject(id, updatedObject);

        MuseumObject obj = objectService.getObject(id);
        return "redirect:/section/" + obj.getType() + "/" + id;
    }

    @PostMapping("/objects/{id}/seen")
    public String markSeen(@PathVariable long id, Principal principal) {

        // Marks object as seen by the current user (if not already marked)
        if (principal == null) {
            return "redirect:/login";
        }

        String username = principal.getName();
        User user = userService.findByUsername(username).orElseThrow();
        MuseumObject item = objectService.findById(id).orElseThrow();

        if (!user.getSeen().contains(item)) {
            user.getSeen().add(item);
            userService.saveUser(user);
        }

        return "redirect:/section/" + item.getType() + "/" + id;
    }

}
