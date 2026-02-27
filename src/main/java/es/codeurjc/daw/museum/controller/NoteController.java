package es.codeurjc.daw.museum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import es.codeurjc.daw.museum.model.MuseumObject;
import es.codeurjc.daw.museum.model.Note;
import es.codeurjc.daw.museum.model.User;
import es.codeurjc.daw.museum.service.MuseumObjectService;
import es.codeurjc.daw.museum.service.NoteService;

@Controller
public class NoteController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private MuseumObjectService objectService;

    @PostMapping("/objects/{id}/notes")
    public String addNote(@PathVariable Long id, 
                          @RequestParam String text,
                          @SessionAttribute("user") User user) {
        MuseumObject object = objectService.findById(id)
                .orElseThrow(() -> new RuntimeException("Object not found"));

        Note note = new Note(text, user, object);
        noteService.save(note);

        return "redirect:/objects/" + id;
    }
}
