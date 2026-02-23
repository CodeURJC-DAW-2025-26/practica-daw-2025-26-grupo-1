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
	private MuseumObjectRepository objectRepository;

	public boolean exist(long id) {
		return objectRepository.existsById(id);
	}

	public Optional<MuseumObject> findById(long id) {
		return objectRepository.findById(id);
	}

	public List<MuseumObject> findAll() {
		return objectRepository.findAll();
	}

	public List<MuseumObject> findByType(String type) {
		return objectRepository.findByType(type);
	}

	public List<MuseumObject> findByCategory(String category) {
		return objectRepository.findByCategory(category);
	}



	/*public Optional<MuseumObject> findBy... (...) {
		return repository.findBy...(...);
	}

	*/

	public void saveObject(MuseumObject museumObject) {
		objectRepository.save(museumObject);
	}

	public void deleteObject(long id) {
		objectRepository.deleteById(id);
	}
}
