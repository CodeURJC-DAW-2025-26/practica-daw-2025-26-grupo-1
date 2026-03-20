package es.codeurjc.daw.museum.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import es.codeurjc.daw.museum.model.MuseumObject;
import es.codeurjc.daw.museum.model.Note;
import es.codeurjc.daw.museum.model.User;
import es.codeurjc.daw.museum.service.MuseumObjectService;
import es.codeurjc.daw.museum.service.NoteService;
import es.codeurjc.daw.museum.service.UserService;

@Controller
public class NoteController {

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
        } else {
            model.addAttribute("logged", false);
        }
    }


    @GetMapping("/object/{id}/add-note")
    public String showNewNotePage(@PathVariable Long id, Model model) {
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

    // Create note
    /*
     * @PostMapping("/objects/{id}/notes")
     * public String addNote(@PathVariable Long id,
     * 
     * @RequestParam String text,
     * Principal principal) {
     * 
     * Optional<MuseumObject> objectOpt = objectService.findById(id);
     * if (objectOpt.isEmpty()) {
     * return "redirect:/"; // objeto no encontrado
     * }
     * 
     * if (principal != null) {
     * User user = userService.findByUsername(principal.getName()).orElse(null);
     * if (user != null) {
     * Note note = new Note(text, user, objectOpt.get());
     * noteService.save(note);
     * }
     * }
     * 
     * return "redirect:/objects/" + id;
     * }
     * 
     * // Form to edit note
     * 
     * @GetMapping("/notes/{id}/edit")
     * public String editNoteForm(@PathVariable Long id, Model model, Principal
     * principal) {
     * 
     * Optional<Note> noteOpt = noteService.findById(id);
     * if (noteOpt.isEmpty() || principal == null) {
     * return "redirect:/";
     * }
     * 
     * Note note = noteOpt.get();
     * User user = userService.findByUsername(principal.getName()).orElse(null);
     * 
     * if (user == null || !note.getUser().getId().equals(user.getId())) {
     * return "redirect:/objects/" + note.getMuseumObject().getId(); // no
     * autorizado
     * }
     * 
     * model.addAttribute("note", note);
     * return "editNotePage"; // plantilla estilo clase
     * }
     * 
     * // Save note changes
     * 
     * @PostMapping("/notes/{id}/edit")
     * public String editNote(@PathVariable Long id,
     * 
     * @RequestParam String text,
     * Principal principal) {
     * 
     * Optional<Note> noteOpt = noteService.findById(id);
     * if (noteOpt.isEmpty() || principal == null) {
     * return "redirect:/";
     * }
     * 
     * Note note = noteOpt.get();
     * User user = userService.findByUsername(principal.getName()).orElse(null);
     * 
     * if (user == null || !note.getUser().getId().equals(user.getId())) {
     * return "redirect:/objects/" + note.getMuseumObject().getId();
     * }
     * 
     * note.setText(text);
     * noteService.save(note);
     * 
     * return "redirect:/objects/" + note.getMuseumObject().getId();
     * }
     * 
     * // Delete note
     * 
     * @PostMapping("/notes/{id}/delete")
     * public String deleteNote(@PathVariable Long id, Principal principal) {
     * 
     * Optional<Note> noteOpt = noteService.findById(id);
     * if (noteOpt.isEmpty() || principal == null) {
     * return "redirect:/";
     * }
     * 
     * Note note = noteOpt.get();
     * User user = userService.findByUsername(principal.getName()).orElse(null);
     * 
     * if (user != null && (note.getUser().getId().equals(user.getId()) ||
     * user.getRoles().contains("ADMIN"))) {
     * Long objectId = note.getMuseumObject().getId();
     * noteService.deleteNoteById(id);
     * return "redirect:/objects/" + objectId;
     * }
     * 
     * return "redirect:/objects/" + note.getMuseumObject().getId();
     * }
     */

}
