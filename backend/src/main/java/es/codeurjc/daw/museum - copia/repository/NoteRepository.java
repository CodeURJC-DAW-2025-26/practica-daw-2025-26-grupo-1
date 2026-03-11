package es.codeurjc.daw.museum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.codeurjc.daw.museum.model.Note;
import es.codeurjc.daw.museum.model.User;
import es.codeurjc.daw.museum.model.MuseumObject;

public interface NoteRepository extends JpaRepository<Note, Long> {

    List <Note> findByUser (User user);
    List <Note> findByMuseumObject (MuseumObject museumObject);

}