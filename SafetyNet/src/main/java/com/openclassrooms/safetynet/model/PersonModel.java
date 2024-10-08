package com.openclassrooms.safetynet.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class PersonModel {
	
    @NotEmpty(message = "Votre Prénom doit être renseigné") private String firstName;
    @NotEmpty(message = "Votre Nom doit être renseigné") private String lastName;
    private String address;
    private String city;
    private String zip;
    private String phone;
    @NotEmpty(message = "Votre email doit être renseigné") 
    @Email(message = "L'email renseigné n'est pas valide")private String email;
    

	public PersonModel(String firstName, String lastName, String address, String city, String zip, String phone,
			String email) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.zip = zip;
		this.phone = phone;
		this.email = email;
	}
	
	public PersonModel() {
		super();
	}

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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
