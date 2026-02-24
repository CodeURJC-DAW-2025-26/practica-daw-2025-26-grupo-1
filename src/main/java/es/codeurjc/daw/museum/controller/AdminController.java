package es.codeurjc.daw.museum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import es.codeurjc.daw.museum.model.MuseumObject;
import es.codeurjc.daw.museum.service.MuseumObjectService;
import es.codeurjc.daw.museum.service.NoteService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private MuseumObjectService objectService;

    @Autowired
    private NoteService noteService;

    // Página para ver todos los objetos (admin)
    @GetMapping("/objects")
    public String listObjects(Model model) {
        model.addAttribute("objects", objectService.findAll());
        return "admin/objects";  // Mustache: admin/objects.html
    }

    // Crear objeto nuevo
    @GetMapping("/objects/new")
    public String newObjectForm(Model model) {
        model.addAttribute("object", new MuseumObject());
        return "admin/object_form";
    }

    @PostMapping("/objects")
    public String saveObject(@ModelAttribute MuseumObject object) {
        objectService.saveObject(object);
        return "redirect:/admin/objects";
    }

    // Editar objeto
    @GetMapping("/objects/{id}/edit")
    public String editObjectForm(@PathVariable long id, Model model) {
        MuseumObject object = objectService.findById(id).orElseThrow();
        model.addAttribute("object", object);
        return "admin/object_form";
    }

    @PostMapping("/objects/{id}/edit")
    public String updateObject(@PathVariable long id, @ModelAttribute MuseumObject object) {
        object.setId(id);
        objectService.saveObject(object);
        return "redirect:/admin/objects";
    }

    // Borrar objeto
    @PostMapping("/objects/{id}/delete")
    public String deleteObject(@PathVariable long id) {
        objectService.deleteObject(id);
        return "redirect:/admin/objects";
    }

    // Borrar nota
    @PostMapping("/notes/{id}/delete")
    public String deleteNote(@PathVariable long id) {
        noteService.deleteNoteById(id);
        return "redirect:/admin/objects";
    }
}