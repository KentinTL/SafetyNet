package com.openclassrooms.safetynet.dao;

import java.util.List;
import java.util.Optional;

import com.openclassrooms.safetynet.model.PersonModel;

public interface IPersonDao {
	Optional<PersonModel> findByEmail(String email);
	List<PersonModel> findByAddress(String address);
	List<PersonModel>  findPersonByCity(String city);
	public void create(PersonModel personModel);
	List<PersonModel> fecthAllPerson();
	List<PersonModel> findPersonsByLastName(String lastName);
	void update(PersonModel personModel);
	void delete(PersonModel personModel);
	Optional<PersonModel> findByFirstNameAndLastName(String firstName, String lastName);
}
