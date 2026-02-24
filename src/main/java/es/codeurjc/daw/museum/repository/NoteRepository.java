package es.codeurjc.daw.museum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.codeurjc.daw.museum.model.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {

    List <Note> findByUser (String user);
    List <Note> findByMuseumObject (String object);

}