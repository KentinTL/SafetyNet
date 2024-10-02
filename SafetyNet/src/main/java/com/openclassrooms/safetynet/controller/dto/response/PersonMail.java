package com.openclassrooms.safetynet.controller.dto.response;

import java.util.Objects;

public class PersonMail {
	
	@Override
	public int hashCode() {
		return Objects.hash(mail);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PersonMail other = (PersonMail) obj;
		return Objects.equals(mail, other.mail);
	}
	
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
