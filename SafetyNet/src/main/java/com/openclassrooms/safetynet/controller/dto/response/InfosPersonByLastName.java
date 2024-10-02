package com.openclassrooms.safetynet.controller.dto.response;

import java.util.List;

public class InfosPersonByLastName {
	private List<PersonsInfosAndMedical> personsInfosAndMedical;

	public InfosPersonByLastName(List<PersonsInfosAndMedical> piam) {
		super();
		this.personsInfosAndMedical = piam;
	}

	public InfosPersonByLastName() {
		// TODO Auto-generated constructor stub
	}

	public List<PersonsInfosAndMedical> getPersonsInfosAndMedical() {
		return personsInfosAndMedical;
	}

	public void setPiam(List<PersonsInfosAndMedical> piam) {
		this.personsInfosAndMedical = piam;
	}
	
}
