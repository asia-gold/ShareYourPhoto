package ru.asia.shareyourphotoapp.model;

import android.media.Image;

public class Draft {
	
	Image photo;
	String email;
	String subject;
	
	public Draft(String email, String subject) {
		this.email = email;
		this.subject = subject;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getSubject() {
		return subject;
	}

}
