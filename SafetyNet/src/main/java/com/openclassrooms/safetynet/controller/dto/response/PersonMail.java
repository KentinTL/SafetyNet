package com.openclassrooms.safetynet.controller.dto.response;

public class PersonMail {
	
	public PersonMail(String mail) {
		super();
		this.mail = mail;
	}

	private String mail;

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

}
