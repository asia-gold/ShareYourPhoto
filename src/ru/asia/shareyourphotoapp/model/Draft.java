package ru.asia.shareyourphotoapp.model;


public class Draft {
	
	long id;
	byte[] photo;
	String email;
	String subject;
	String body;
	
	public Draft() {
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public void setPhoto(byte[] photo) {
		this.photo = photo;
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
	
	public long getId() {
		return id;
	}
	
	public byte[] getPhoto() {
		return photo;
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
