package com.openclassrooms.safetynet.model;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;

public class MedicalRecordModel {
	@NotEmpty(message = "Le prénom de la personne est obligatoire") private String firstName;
	@NotEmpty(message = "Le nom de la personne est obligatoire") private String lastName;
	@NotEmpty(message = "La date de naissance de la personne est obligatoire") private String birthdate;
	private List<String> medications;
	private List<String> allergies;
	

	public MedicalRecordModel(String firstName, String lastName, String birthdate, List<String> medications,
			List<String> allergies) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthdate = birthdate;
		this.medications = medications;
		this.allergies = allergies;
	}
	
	public MedicalRecordModel() {
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
	public String getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	public List<String> getMedications() {
		return medications;
	}
	public void setMedications(List<String> medications) {
		this.medications = medications;
	}
	public List<String> getAllergies() {
		return allergies;
	}
	public void setAllergies(List<String> allergies) {
		this.allergies = allergies;
	}
}
