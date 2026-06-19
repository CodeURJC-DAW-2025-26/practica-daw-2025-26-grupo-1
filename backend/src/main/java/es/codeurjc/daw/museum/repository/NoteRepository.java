package es.codeurjc.daw.museum.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import es.codeurjc.daw.museum.model.Note;
import es.codeurjc.daw.museum.model.User;
import es.codeurjc.daw.museum.model.MuseumObject;

public interface NoteRepository extends JpaRepository<Note, Long> {

    Page <Note> findByUser (User user, Pageable pageable);
    List <Note> findByMuseumObject (MuseumObject museumObject);

    List <Note> findByMuseumObjectId (Long id);

    List <Note> findAllByUserAndMuseumObject (User user, MuseumObject museumObject);

}