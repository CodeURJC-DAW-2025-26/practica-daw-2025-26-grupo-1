package es.codeurjc.daw.museum.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.codeurjc.daw.museum.model.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {

}