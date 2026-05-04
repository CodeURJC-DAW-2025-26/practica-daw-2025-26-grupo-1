package es.codeurjc.daw.museum.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import es.codeurjc.daw.museum.model.Image;
import es.codeurjc.daw.museum.model.MuseumObject;
import es.codeurjc.daw.museum.repository.MuseumObjectRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class MuseumObjectService {

	@Autowired
	private MuseumObjectRepository objectRepository;

	@Autowired
	private ImageService imageService;

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

	
	public List<MuseumObject> findByTypeAll(String type) {
		return objectRepository.findByType(type);
	}
	 

	public List<MuseumObject> findByCategory(String category) {
		return objectRepository.findByCategory(category);
	}

	public Page<MuseumObject> findByCategory(String category, Pageable pageable) {
		return objectRepository.findByCategoryOrderByIdAsc(category, pageable);
	}

	public Page<MuseumObject> findByType(String type, Pageable pageable) {
		return objectRepository.findByTypeOrderByIdAsc(type, pageable);
	}

	public Page<MuseumObject> findByTypeAndCategory(String type, String category, Pageable pageable) {
		return objectRepository.findByTypeAndCategoryOrderByIdAsc(type, category, pageable);
	}

	public List<MuseumObject> findByTypeAndName(String type, String name) {
		return objectRepository.findByTypeAndObjectNameContainingIgnoreCase(type, name);
	}

	public Page<MuseumObject> findAllPageable(Pageable pageable) {
		return objectRepository.findAll(pageable);
	}

	public Page<MuseumObject> findByNamePageable(String name, Pageable pageable) {
		return objectRepository.findByObjectNameContainingIgnoreCase(name, pageable);
	}

	public long countByType(String type) {
		return objectRepository.countByTypeIgnoreCase(type);
	}

	public MuseumObject saveObject(MuseumObject object) {

		if (object.getObjectName() == null || object.getObjectName().isEmpty()) {
			throw new RuntimeException("An object name is required.");
		}

		if (object.getGroupName() == null || object.getGroupName().isEmpty()) {
			throw new RuntimeException("A group name is required.");
		}

		if (object.getTechnicalData() == null || object.getTechnicalData().isEmpty()) {
			throw new RuntimeException("Information of technical data is required.");
		}

		if (object.getDescription() == null || object.getDescription().isEmpty()) {
			throw new RuntimeException("A description is required.");
		}

		if (object.getCategory() == null || object.getCategory().isEmpty()) {
			throw new RuntimeException("A category is required.");
		}

		return objectRepository.save(object);
	}

	public MuseumObject getObject(long id) {
		return objectRepository.findById(id).orElseThrow(() -> new RuntimeException("Object not found with id: " + id));
	}

	public MuseumObject createObject(MuseumObject museumObject) {

		if (museumObject.getId() != null) {
			throw new IllegalArgumentException();
		}

		return saveObject(museumObject);
	}

	public MuseumObject replaceObject(long id, MuseumObject updatedObject) {

		MuseumObject oldObject = objectRepository.findById(id)
				.orElseThrow();
		updatedObject.setId(id);
		updatedObject.setType(oldObject.getType());

		if (updatedObject.getImage() == null && oldObject.getImage() != null) {
			updatedObject.setImage(oldObject.getImage());
		}

		return saveObject(updatedObject);
	}

	public MuseumObject deleteObject(long id) {
		MuseumObject obj = objectRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Object not found with id: " + id));
		objectRepository.delete(obj);

		return obj;
	}

	public MuseumObject addImageToObject(long id, Image image) {
		MuseumObject obj = getObject(id);
		obj.setImage(image);
		return objectRepository.save(obj);
	}

	public MuseumObject updateObjectImage(long id, MultipartFile imageField) throws IOException {
		MuseumObject obj = getObject(id);

		Image oldImage = obj.getImage();

		Image newImage = imageService.createImage(imageField.getInputStream());

		obj.setImage(newImage);
		MuseumObject savedObj = objectRepository.save(obj);

		if (oldImage != null) {
			imageService.deleteImage(oldImage.getId());
		}

		return savedObj;
	}

	public MuseumObject removeImageFromObject(long id) {
		MuseumObject obj = getObject(id);
		Image image = obj.getImage();

		if (image != null) {
			obj.setImage(null);
			objectRepository.save(obj);
			imageService.deleteImage(image.getId());
		}

		return obj;
	}

}