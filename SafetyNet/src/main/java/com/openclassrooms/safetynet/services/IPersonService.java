package com.openclassrooms.safetynet.services;

import java.util.List;

import com.openclassrooms.safetynet.model.PersonModel;

public interface IPersonService {
	List<PersonModel> consultAllPersons();
	public void add(PersonModel personModel);
}
