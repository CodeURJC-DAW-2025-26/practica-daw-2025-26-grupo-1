package es.codeurjc.daw.museum.controller.rest;

import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.codeurjc.daw.museum.dto.NoteDTO;
import es.codeurjc.daw.museum.dto.NoteMapper;
import es.codeurjc.daw.museum.service.NoteService;
import es.codeurjc.daw.museum.service.UserService;
import es.codeurjc.daw.museum.model.Note;
import es.codeurjc.daw.museum.model.User;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;


@RestController
@RequestMapping("/api/v1/notes")
public class NoteRestController {
    
    @Autowired
    private NoteService noteService;

    @Autowired
    private UserService userService;

    @Autowired
    private NoteMapper noteMapper;


    @PostMapping("/object/{objectId}")
    public ResponseEntity<NoteDTO> createNote(@PathVariable long objectId, @RequestBody NoteDTO noteDTO, Principal principal) {

        User user = userService.findByUsername(principal.getName()).orElseThrow();
        Note newNote = noteService.createNote(objectId, noteDTO.text(), user);
        NoteDTO responseDTO = noteMapper.toDTO(newNote);

        URI location = fromCurrentRequest().path("/{id}")
            .buildAndExpand(responseDTO.id()).toUri();

        return ResponseEntity.created(location).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteDTO> updateNote(@PathVariable long id, @RequestBody NoteDTO noteDTO, Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow();
        Note updatedNote = noteService.updateNote(id, noteDTO.text(), user);
        return ResponseEntity.ok(noteMapper.toDTO(updatedNote));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable long id, Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow();

        noteService.deleteNote(id, user);
        
        return ResponseEntity.noContent().build(); 
    }

    @GetMapping("/object/{objectId}")
    public ResponseEntity<List<NoteDTO>> getNotesByObject(@PathVariable long objectId) {
        
        List<Note> notes = noteService.findByObjectId(objectId);
        List <NoteDTO> dtos = new ArrayList<>();

        for (Note note : notes) {
            NoteDTO noteDTO = noteMapper.toDTO(note);
            dtos.add(noteDTO);
        }

        return ResponseEntity.ok(dtos);
    }

}
