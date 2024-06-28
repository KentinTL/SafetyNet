package com.openclassrooms.safetynet.controller.dto.response;

import java.util.List;

public class ResidentByAddress {
	
	public ResidentByAddress() {
		super();
	}
	public ResidentByAddress(String lastName, String phone, int ageResident, List<String>residentMedications, List<String> residentAllergies) {
		// TODO Auto-generated constructor stub
		super();
		setName(lastName);
		setPhoneNumber(phone);
		setAge(ageResident);
		setMedications(residentMedications);
		setAllergies(residentAllergies);
	}
	private String name;
	private String phoneNumber;
	private int age;
	private List<String> medications;
	private List<String> allergies;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
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
