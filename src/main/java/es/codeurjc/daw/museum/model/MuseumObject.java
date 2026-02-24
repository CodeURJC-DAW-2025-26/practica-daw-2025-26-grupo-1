package es.codeurjc.daw.museum.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class MuseumObject {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String objectName;
	private String groupName;
	private String technicalData;
	private String description;
	private String type;
	private String category;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn (name = "image_id")
	private Image image;

	@OneToMany(mappedBy="museumObject", cascade = CascadeType.ALL)
	private List <Note> objectNotes;

	public MuseumObject() {
	}

	public MuseumObject(String objectName, String groupName, Image image, String technicalData, String description, String type, String category, List<Note> objectNotes) {
		this.objectName = objectName;
		this.groupName = groupName;
		this.image = image;
		this.technicalData = technicalData;
		this.description = description;
		this.type = type;
		this.category = category;
		this.objectNotes = objectNotes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getTechnicalData() {
		return technicalData;
	}

	public void setTechnicalData(String technicalData) {
		this.technicalData = technicalData;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public List<Note> getObjectNotes() {
		return objectNotes;
	}

	public void setObjectNotes(List<Note> objectNotes) {
		this.objectNotes = objectNotes;
	}

}
