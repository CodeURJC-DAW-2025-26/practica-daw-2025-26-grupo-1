package es.codeurjc.daw.museum.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Note {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String text;

	@ManyToOne
 	private MuseumUser museumUser;

	@ManyToOne
 	private MuseumObject museumObject;

	public Note() {}

	public Note(String text, MuseumUser museumUser, MuseumObject museumObject) {
		this.text = text;
		this.museumUser = museumUser;
		this.museumObject = museumObject;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public MuseumUser getMuseumUser() {
		return museumUser;
	}

	public void setMuseumUser(MuseumUser museumUser) {
		this.museumUser = museumUser;
	}

	public MuseumObject getMuseumObject() {
		return museumObject;
	}

	public void setMuseumObject(MuseumObject museumObject) {
		this.museumObject = museumObject;
	}
}
