package com.openclassrooms.safetynet.controller.dto.response;

public class PersonModelWithAge extends MinimalPersonModel{
	int age;
	
	public PersonModelWithAge(String firstName, String lastName, int age) {
		super(firstName, lastName);
		this.age = age;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
