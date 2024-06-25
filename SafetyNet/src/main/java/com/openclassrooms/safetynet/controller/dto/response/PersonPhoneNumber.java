package com.openclassrooms.safetynet.controller.dto.response;

import java.util.List;

public class PersonPhoneNumber {
	
	public PersonPhoneNumber(List<String> phoneNumberList) {
		super();
		this.phoneNumberList = phoneNumberList;
	}
	
	public PersonPhoneNumber() {}


	private List<String> phoneNumberList;

	public List<String> getPhoneNumberList() {
		return phoneNumberList;
	}

	public void setPhoneNumberList(List<String> phoneNumberList) {
		this.phoneNumberList = phoneNumberList;
	}
	
	
}
