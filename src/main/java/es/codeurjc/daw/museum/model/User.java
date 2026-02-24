package es.codeurjc.daw.museum.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity(name = "UserTable")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;
	private String encodedPassword;

	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> roles;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "image_id")
	private Image image;

	@OneToMany
	private List<Note> notes;

	@ManyToMany
	private List<MuseumObject> favourites;

	@ManyToMany
	private List<MuseumObject> seen;

	public User() {
	}

	public User(String name, String encodedPassword, List<String> roles) {
		this.name = name;
		this.encodedPassword = encodedPassword;
		this.roles = roles;
		this.notes = new ArrayList<>();
		this.favourites = new ArrayList<>();
		this.seen = new ArrayList<>();
	}

	public User(String name, String encodedPassword, String[] roles, Image userImage, List<Note> notes, List<MuseumObject> favourites, List<MuseumObject> seen) {
		this.name = name;
		this.encodedPassword = encodedPassword;
		this.roles = List.of(roles);
		this.image = userImage;
		this.notes = notes;
		this.favourites = favourites;
		this.seen = seen;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEncodedPassword() {
		return encodedPassword;
	}

	public void setEncodedPassword(String encodedPassword) {
		this.encodedPassword = encodedPassword;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public Image getUserImage() {
		return image;
	}

	public void setUserImage(Image userImage) {
		this.image = userImage;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	public List<MuseumObject> getFavourites() {
		return favourites;
	}

	public void setFavourites(List<MuseumObject> favourites) {
		this.favourites = favourites;
	}

	public List<MuseumObject> getSeen() {
		return seen;
	}

	public void setSeen(List<MuseumObject> seen) {
		this.seen = seen;
	}

}