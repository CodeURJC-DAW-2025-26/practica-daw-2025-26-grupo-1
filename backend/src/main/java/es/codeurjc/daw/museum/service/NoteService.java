package es.codeurjc.daw.museum.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import es.codeurjc.daw.museum.model.User;

import es.codeurjc.daw.museum.model.MuseumObject;
import es.codeurjc.daw.museum.model.Note;
import es.codeurjc.daw.museum.repository.NoteRepository;

@Service
public class NoteService {

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private MuseumObjectService objectService;


	public boolean exist(long id) {
		return noteRepository.existsById(id);
	}

	

	public Optional<Note> findById(long id) {
		return noteRepository.findById(id);
	}

	public List<Note> findAllById(List<Long> ids) {
		return noteRepository.findAllById(ids);
	}

	public Page<Note> findAll(Pageable pageable) {
		return noteRepository.findAll(pageable);
	}

	public List<Note> findAllByUser(User user) {
		return noteRepository.findByUser(user);
	}

	public List<Note> findAllByObject(MuseumObject object) {
		return noteRepository.findByMuseumObject(object);
	}

	public Note save(Note note) {
		return noteRepository.save(note);
	}

	public List<Note> findAllByUserAndMuseumObject(User user, MuseumObject object) {
		return noteRepository.findAllByUserAndMuseumObject(user, object);
	}

	public boolean canUserModifyNote(User user, Note note) {

		if (user == null || note == null) {
			return false;
		}

		boolean isAuthor = note.getUser().getId().equals(user.getId());
		boolean isAdmin = user.getRoles().contains("ADMIN");

		if (isAdmin) {
			return false;
		}

		return isAuthor;
	}

	public Note createNote(Long objectId, String text, User user) {

		if (user == null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario no identificado");
		}

		MuseumObject item = objectService.findById(objectId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Objeto del museo no encontrado"));

		Note note = new Note(text, user, item);
		return noteRepository.save(note);
	}

	public Note updateNote(Long id, String text, User user) {

		Note note = noteRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nota no encontrada"));

		if (!canUserModifyNote(user, note)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para editar esta nota");
		}

		note.setText(text);
		return noteRepository.save(note);
	}

	public Note deleteNote(long id, User user) {
		Note note = noteRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nota no encontrada"));

		if (!canUserModifyNote(user, note)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para borrar esta nota");
		}

		noteRepository.deleteById(id);
		return note;
	}

	public List<Note> findByObjectId(long id) {
		return noteRepository.findByMuseumObjectId(id);
	}

}
