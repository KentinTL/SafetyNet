package com.openclassrooms.safetynet.services;

import java.util.List;

import com.openclassrooms.safetynet.controller.dto.response.InfosChildByAdress;
import com.openclassrooms.safetynet.controller.dto.response.InfosMailsByCity;
import com.openclassrooms.safetynet.controller.dto.response.InfosPersonByLastName;
import com.openclassrooms.safetynet.model.PersonModel;

public interface IPersonService {
	List<PersonModel> consultAllPersons();
	public void add(PersonModel personModel);
	public InfosChildByAdress getChildUnderEighteen(String address);

	public InfosMailsByCity getMailsByCity(String city);
	
	public InfosPersonByLastName getInfosPersonsByLastName(String lastName);
	void update(String firstName, String lastName,PersonModel personModel);
	void delete(String firstName, String lastName);
	}
