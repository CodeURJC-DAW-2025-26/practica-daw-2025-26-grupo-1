package es.codeurjc.daw.museum.controller;

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

import es.codeurjc.daw.museum.model.Image;
import es.codeurjc.daw.museum.model.MuseumObject;
import es.codeurjc.daw.museum.model.Note;
import es.codeurjc.daw.museum.model.User;
import es.codeurjc.daw.museum.service.ImageService;
import es.codeurjc.daw.museum.service.MuseumObjectService;
import es.codeurjc.daw.museum.service.NoteService;
import es.codeurjc.daw.museum.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

// This controller has been deprecated because its URL mapping collided
// with the more elaborate viewObject method in UserController.  The
// logic for displaying an object is now handled there, which also adds
// user-specific attributes for logged-in users.
//
// Keeping the class around for reference, but without the
// @Controller annotation Spring will ignore it and the duplicate
// mapping error disappears.

@Controller
public class MuseumObjectController {

    @Autowired
    private UserService userService;

    @Autowired
    private MuseumObjectService objectService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private NoteService noteService;

    /*
     * @GetMapping("/object/{id}")
     * public String viewObject(@PathVariable long id, Model model) {
     * 
     * Optional<MuseumObject> museumObject = objectService.findById(id);
     * if (museumObject.isPresent()) {
     * 
     * MuseumObject obj = museumObject.get();
     * String tipo = obj.getType();
     * 
     * switch (tipo) {
     * case "peces":
     * model.addAttribute("backgroundSectionImage",
     * "/assets/images/fondo-marino-siluetas.png");
     * break;
     * case "insectos":
     * model.addAttribute("backgroundSectionImage",
     * "/assets/images/fondo-insectos-siluetas.png");
     * break;
     * case "fosiles":
     * model.addAttribute("backgroundSectionImage",
     * "/assets/images/fondo-fosiles-siluetas.png");
     * break;
     * case "arte":
     * model.addAttribute("backgroundSectionImage",
     * "/assets/images/fondo-secundario-arte.png");
     * break;
     * default:
     * model.addAttribute("backgroundSectionImage",
     * "/assets/images/interior-museo.png");
     * break;
     * }
     * 
     * model.addAttribute("nameElement", obj.getObjectName());
     * model.addAttribute("groupNameElement", obj.getGroupName());
     * model.addAttribute("categoryName", obj.getCategory());
     * model.addAttribute("type", obj.getType());
     * model.addAttribute("elementImage", obj.getImage().getId());
     * model.addAttribute("descriptionElement", obj.getDescription());
     * model.addAttribute("technicalData", obj.getTechnicalData());
     * 
     * model.addAttribute("backUrl", "/section/" + tipo);
     * 
     * return "informative-page";
     * } else {
     * return "partials/section-elements";
     * }
     * 
     * }
     */

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

    @GetMapping("/section/{sectionName}/{id}")
    public String showObject(Model model, @PathVariable String sectionName, @PathVariable long id,
            Principal principal) {

        String section = sectionName.toLowerCase();
        Optional<MuseumObject> museumObject = objectService.findById(id);

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

            if (principal != null) {

                User user = userService.findByUsername(principal.getName()).orElse(null);
                if (user != null) {
                    List<Note> notes = noteService.findAllByUserAndMuseumObject(user, obj);
                    model.addAttribute("userNotes", notes);
                }
            } else {

                model.addAttribute("userNotes", new ArrayList<>());
            }

            return "informative-page";

        } else {

            List<MuseumObject> objects = objectService
                    .findByType(section, 0)
                    .getContent();

            model.addAttribute("sectionName", sectionName);

            model.addAttribute("sectionElements", convertToSectionElement(objects, sectionName));

            return "partials/section-elements";
        }
    }

    private List<SectionController.SectionElement> convertToSectionElement(List<MuseumObject> objects,
            String sectionName) {
        return objects.stream()
                .map(obj -> new SectionController.SectionElement(obj.getId(),
                        obj.getObjectName(),
                        "/images/" + obj.getImage().getId(),
                        obj.getCategory(),
                        "bg-secondary",
                        "/section/" + sectionName + "/" + obj.getId()))
                .toList();
    }

    @GetMapping("/search")
    public String search(@RequestParam String searchName) {
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

        if (imageField != null && !imageField.isEmpty()) {
            Image image = imageService.createImage(imageField.getInputStream());
            museumObject.setImage(image);
        }

        objectService.saveObject(museumObject);

        return "redirect:/confirmation?action=addObject&section=" + museumObject.getType();
    }

    /*
     * @GetMapping("/editbook/{id}")
     * public String editBook(Model model, @PathVariable long id) {
     * 
     * Optional<Book> book = bookService.findById(id);
     * if (book.isPresent()) {
     * model.addAttribute("book", book.get());
     * return "editBookPage";
     * } else {
     * return "books";
     * }
     * }
     * 
     * @PostMapping("/editbook")
     * public String editBookProcess(Model model, Book book, boolean removeImage,
     * MultipartFile imageField)
     * throws IOException, SQLException {
     * 
     * updateImage(book, removeImage, imageField);
     * 
     * bookService.save(book);
     * 
     * model.addAttribute("bookId", book.getId());
     * 
     * return "redirect:/books/" + book.getId();
     * }
     * 
     * private void updateImage(Book book, boolean removeImage, MultipartFile
     * imageField)
     * throws IOException, SQLException {
     * 
     * if (!imageField.isEmpty()) {
     * Book dbBook = bookService.findById(book.getId()).orElseThrow();
     * 
     * if (dbBook.getImage() == null) {
     * Image image = imageService.createImage(imageField.getInputStream());
     * book.setImage(image);
     * } else {
     * Image image = imageService.replaceImageFile(dbBook.getImage().getId(),
     * imageField.getInputStream());
     * book.setImage(image);
     * }
     * } else {
     * if (removeImage) {
     * if (book.getImage() != null) {
     * imageService.deleteImage(book.getImage().getId());
     * book.setImage(null);
     * }
     * } else {
     * // Maintain the same image loading it before updating the book
     * Book dbBook = bookService.findById(book.getId()).orElseThrow();
     * book.setImage(dbBook.getImage());
     * }
     * }
     * }
     */

}
