package ru.asia.shareyourphotoapp.model;

/**
 * Model of draft.
 * 
 * @author Asia
 *
 */
public class Draft {
	
	long id;
	byte[] photo;
	String photoPath;
	String email;
	String subject;
	String body;
	
	public Draft() {
	}
	
	/*
	 * Set Methods
	 */
	
	public void setId(long id) {
		this.id = id;
	}
	
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	
	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	
	/*
	 * Get Methods
	 */
	
	public long getId() {
		return id;
	}
	
	public byte[] getPhoto() {
		return photo;
	}
	
	public String getPhotoPath() {
		return photoPath;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public String getBody() {
		return body;
	}

}
