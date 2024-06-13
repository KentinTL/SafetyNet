package com.openclassrooms.safetynet.services;

import java.util.List;

import com.openclassrooms.safetynet.controller.dto.response.InfosPersonsByFireStation;
import com.openclassrooms.safetynet.model.PersonModel;

public interface IPersonService {
	List<PersonModel> consultAllPersons();
	public void add(PersonModel personModel);
	public InfosPersonsByFireStation getPersonByFirestation(int stationNumber);
}
