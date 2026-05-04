package es.codeurjc.daw.museum.controller.rest;

import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

import es.codeurjc.daw.museum.dto.ElementDTO;
import es.codeurjc.daw.museum.dto.NoteBasicDTO;
import es.codeurjc.daw.museum.dto.NoteDTO;
import es.codeurjc.daw.museum.dto.NoteMapper;
import es.codeurjc.daw.museum.service.NoteService;
import es.codeurjc.daw.museum.service.UserService;
import es.codeurjc.daw.museum.model.MuseumObject;
import es.codeurjc.daw.museum.model.Note;
import es.codeurjc.daw.museum.model.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
    public ResponseEntity<NoteBasicDTO> createNote(@PathVariable long objectId, @RequestBody NoteBasicDTO noteBasicDTO, Principal principal) {

        User user = userService.findByUsername(principal.getName()).orElseThrow();
        Note newNote = noteService.createNote(objectId, noteBasicDTO.text(), user);
        NoteBasicDTO responseDTO = noteMapper.toBasicDTO(newNote);

        URI location = fromCurrentRequest().path("/{id}")
            .buildAndExpand(responseDTO.id()).toUri();

        return ResponseEntity.created(location).body(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable long id, Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow();

        noteService.deleteNote(id, user);
        
        return ResponseEntity.noContent().build(); 
    }

    @GetMapping("/object/{objectId}")
    public ResponseEntity<List<NoteBasicDTO>> getNotesByObject(@PathVariable long objectId) {
        
        List<Note> notes = noteService.findByObjectId(objectId);
        List <NoteBasicDTO> dtos = new ArrayList<>();

        for (Note note : notes) {
            NoteBasicDTO noteDTO = noteMapper.toBasicDTO(note);
            dtos.add(noteDTO);
        }

        return ResponseEntity.ok(dtos);
    }

    // List of all notes
    @GetMapping("/all")
    public ResponseEntity<Page<NoteDTO>> getAllNotes(Pageable pageable) {
        Page<Note> notes = noteService.findAll(pageable);

        Page <NoteDTO> notesDTOs = notes.map(noteMapper::toDTO);

        return ResponseEntity.ok(notesDTOs);
    }

}
