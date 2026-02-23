package es.codeurjc.daw.museum.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.codeurjc.daw.museum.model.Note;
import es.codeurjc.daw.museum.repository.NoteRepository;

@Service
public class NoteService {

	@Autowired
	private NoteRepository noteRepository;

	public boolean exist(long id) {
		return noteRepository.existsById(id);
	}

	public Optional<Note> findById(long id) {
		return noteRepository.findById(id);
	}

	public List<Note> findById(List<Long> ids){
		return noteRepository.findAllById(ids);
	}

	public List<Note> findAll() {
		return noteRepository.findAll();
	}

	public List<Note> findAllByUser(User user){
		return noteRepository.findByMuseumUser(user);
	}

	public List<Note> findAllByObject(MuseumObject object){
		return noteRepository.findByMuseumObject(object);
	}


	public Note save(Note note) {
		return noteRepository.save(note);
	}

	public void deleteNoteById(long id) {
		noteRepository.deleteById(id);
	}

	
}


