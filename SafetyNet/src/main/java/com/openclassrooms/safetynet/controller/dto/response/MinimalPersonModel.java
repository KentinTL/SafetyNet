package com.openclassrooms.safetynet.controller.dto.response;

public class MinimalPersonModel {
    public MinimalPersonModel(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}
    public MinimalPersonModel() {
		super();
	}
    
	private String firstName;
    private String lastName;
    
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
