package es.codeurjc.daw.museum.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.codeurjc.daw.museum.model.MuseumObject;
import es.codeurjc.daw.museum.repository.MuseumObjectRepository;

@Service
public class MuseumObjectService {

	@Autowired
	private MuseumObjectRepository repository;

	public Optional<MuseumObject> findById(long id) {
		return repository.findById(id);
	}

	/*public Optional<MuseumObject> findBy... (...) {
		return repository.findBy...(...);
	}

	*/
	
	public boolean exist(long id) {
		return repository.existsById(id);
	}

	public List<MuseumObject> findAll() {
		return repository.findAll();
	}

	public void saveObject(MuseumObject museumObject) {
		repository.save(museumObject);
	}

	public void deleteObject(long id) {
		repository.deleteById(id);
	}
}
