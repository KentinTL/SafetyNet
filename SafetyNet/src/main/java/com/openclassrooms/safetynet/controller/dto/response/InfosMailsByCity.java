package com.openclassrooms.safetynet.controller.dto.response;

import java.util.List;

public class InfosMailsByCity {
	private List<PersonMail> mails;

	public InfosMailsByCity(List<PersonMail> mails) {
		super();
		this.mails = mails;
	}

	public List<PersonMail> getMails() {
		return mails;
	}

	public void setMails(List<PersonMail> mails) {
		this.mails = mails;
	}
}
