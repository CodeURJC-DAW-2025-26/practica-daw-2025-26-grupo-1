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

	public List<MuseumObject> findByName(String name) {
		return objectRepository.findByObjectName(name);
	}

	public List<MuseumObject> findByType(String type) {
		return objectRepository.findByType(type);
	}

	public List<MuseumObject> findByCategory(String category) {
		return objectRepository.findByCategory(category);
	}

	public MuseumObject saveObject(MuseumObject object) {

		if(object.getObjectName() == null || object.getObjectName().isEmpty()){
			throw new RuntimeException("An object name is required.");
		}

		if(object.getGroupName() == null || object.getGroupName().isEmpty()){
			throw new RuntimeException("A group name is required.");
		}

		if(object.getTechnicalData() == null || object.getTechnicalData().isEmpty()){
			throw new RuntimeException("Information of technical data is required.");
		}

		if(object.getDescription() == null || object.getDescription().isEmpty()){
			throw new RuntimeException("A description is required.");
		}

		if(object.getCategory() == null || object.getCategory().isEmpty()){
			throw new RuntimeException("A category is required.");
		}

		return objectRepository.save(object);
	}

	public void deleteObject(long id) {
    	MuseumObject obj = objectRepository.findById(id)
        	.orElseThrow(() -> new RuntimeException("Object not found with id: " + id));
    	objectRepository.delete(obj);
	}

	public List<String> findAllTypes() {
    	return objectRepository.findDistinctTypes();
	}
}