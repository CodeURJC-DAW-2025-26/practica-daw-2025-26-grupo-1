package es.codeurjc.daw.museum.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Note {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String text;

	@ManyToOne
 	private User user;

	/*@ManyToOne
 	private User owner;*/

	@ManyToOne
 	private MuseumObject museumObject;

	public Note() {}

	public Note(String text, User user, MuseumObject museumObject) {
		this.text = text;
		this.user = user;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public MuseumObject getMuseumObject() {
		return museumObject;
	}

	public void setMuseumObject(MuseumObject museumObject) {
		this.museumObject = museumObject;
	}
}
