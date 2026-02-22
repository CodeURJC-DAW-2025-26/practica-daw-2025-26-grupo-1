package es.codeurjc.daw.museum.model;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity(name = "UserTable")
public class MuseumUser {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;
	private String encodedPassword;

	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> roles;

	@Lob
	private byte[] userImage;

	@OneToMany
	private List<Note> notes;

	@ManyToMany
	private List<Object> favourites;

	@ManyToMany
	private List<Object> seen;

	public MuseumUser() {
	}

	public MuseumUser(String name, String encodedPassword, String[] roles, byte[] userImage, List<Note> notes, List<Object> favourites, List<Object> seen) {
		this.name = name;
		this.encodedPassword = encodedPassword;
		this.roles = List.of(roles);
		this.userImage = userImage;
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

	public byte[] getUserImage() {
		return userImage;
	}

	public void setUserImage(byte[] userImage) {
		this.userImage = userImage;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	public List<Object> getFavourites() {
		return favourites;
	}

	public void setFavourites(List<Object> favourites) {
		this.favourites = favourites;
	}

	public List<Object> getSeen() {
		return seen;
	}

	public void setSeen(List<Object> seen) {
		this.seen = seen;
	}

}