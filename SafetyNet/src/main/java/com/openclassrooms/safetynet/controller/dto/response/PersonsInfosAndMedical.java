package com.openclassrooms.safetynet.controller.dto.response;

import java.util.List;

public class PersonsInfosAndMedical {
	private String name;
	private String address;
	private int age;
	private String mail;
	private List<String> medications;
	private List<String> allergies;
	
	public PersonsInfosAndMedical(String name, String address, int age, String mail, List<String> medications,
			List<String> allergies) {
		super();
		this.name = name;
		this.address = address;
		this.age = age;
		this.mail = mail;
		this.medications = medications;
		this.allergies = allergies;
	}
	
	public PersonsInfosAndMedical() {
		super();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
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
