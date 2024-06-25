package com.openclassrooms.safetynet.dao;

import java.util.List;
import java.util.Optional;

import com.openclassrooms.safetynet.model.PersonModel;

public interface IPersonDao {
	Optional<PersonModel> findByEmail(String email);
	Optional<List<PersonModel>> findByAddress(String address);
	public void create(PersonModel personModel);
	List<PersonModel> fecthAllPerson();
}
