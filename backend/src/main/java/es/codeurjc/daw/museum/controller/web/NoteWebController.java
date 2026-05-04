package es.codeurjc.daw.museum.controller.web;

import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import es.codeurjc.daw.museum.model.MuseumObject;
import es.codeurjc.daw.museum.model.Note;
import es.codeurjc.daw.museum.model.User;
import es.codeurjc.daw.museum.service.MuseumObjectService;
import es.codeurjc.daw.museum.service.NoteService;
import es.codeurjc.daw.museum.service.UserService;

@Controller
public class NoteWebController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private MuseumObjectService objectService;

    @Autowired
    private UserService userService;

    // Add common atributes to all the views
    @ModelAttribute
    public void addAttributes(Model model, HttpServletRequest request) {

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


    @GetMapping("/object/{id}/add-note")
    public String showNewNotePage(@PathVariable Long id, Model model) {

        // Load a page where you can write a note about the object 
        MuseumObject item = objectService.findById(id).orElseThrow();
        String sectionName = item.getType().toLowerCase();
        String imageName;

        switch (sectionName) {
            case "peces":
                imageName = "fondo-marino-siluetas.png";
                break;
            case "insectos":
                imageName = "fondo-insectos-siluetas.png";
                break;
            case "fosiles":
                imageName = "fondo-fosiles-siluetas.png";
                break;
            case "arte":
                imageName = "fondo-secundario-arte.png";
                break;

            default:
                imageName = "fondo-" + sectionName + "-siluetas.png";
                break;
        }

        model.addAttribute("formImage", "/assets/images/" + imageName);
        model.addAttribute("object", item);
        model.addAttribute("sectionName", item.getType().toLowerCase());

        return "new-note"; 
    }

    @PostMapping("/newNote/{id}")
    public String newNote(@PathVariable Long id, @RequestParam String text, Principal principal,
            @RequestParam String sectionName, Model model) throws IOException {

        // Creates a new note with the provided text, associated to the current user and the specified object
        Optional<MuseumObject> objectOpt = objectService.findById(id);
        if (objectOpt.isEmpty()) {
            return "redirect:/system-error?action=objectNotFound";
        }

        MuseumObject item = objectOpt.get();

        if (principal != null) {
            User user = userService.findByUsername(principal.getName()).orElse(null);
            if (user != null) {
                Note note = new Note(text, user, item);

                user.getNotes().add(note);
                noteService.save(note);
                userService.saveUser(user);
            }
        }

        return "redirect:/confirmation?action=addNote&section=" + item.getType().toLowerCase() + "&id=" + item.getId();
    }

    @PostMapping("/deleteNote/{id}")
    public String deleteNote(@PathVariable Long id, @RequestParam String sectionName, @RequestParam long objectId, Principal principal) {

        // Deletes the note with the specified id if it belongs to the current user
        User user = userService.findByUsername(principal.getName()).orElseThrow();

        try {
            noteService.deleteNote(id, user);
            return "redirect:/confirmation?action=deleteNote";

        } catch (ResponseStatusException exception) {
            return "redirect:/system-error?action=deleteNote";
        }
    }

}
