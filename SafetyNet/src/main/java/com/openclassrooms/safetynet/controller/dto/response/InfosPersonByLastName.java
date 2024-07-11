package com.openclassrooms.safetynet.controller.dto.response;

import java.util.List;

public class InfosPersonByLastName {
	private List<PersonsInfosAndMedical> piam;

	public InfosPersonByLastName(List<PersonsInfosAndMedical> piam) {
		super();
		this.piam = piam;
	}

	public List<PersonsInfosAndMedical> getPiam() {
		return piam;
	}

	public void setPiam(List<PersonsInfosAndMedical> piam) {
		this.piam = piam;
	}
	
}
