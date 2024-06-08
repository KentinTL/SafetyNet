package com.openclassrooms.safetynet.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.dao.IPersonDao;
import com.openclassrooms.safetynet.model.PersonModel;

@Service
public class PersonService implements IPersonService{

	@Autowired
	private IPersonDao iPersonDao;
	
	@Override
	public void add(PersonModel personModel) {
		Optional<PersonModel> personFound =  iPersonDao.findByEmail(personModel.getEmail());
		if(personFound.isPresent()) {
			throw new RuntimeException("Person already exist");
		}
		iPersonDao.create(personModel);
	}

	@Override
	public List<PersonModel> consultAllPersons() {
		return iPersonDao.fecthAllPerson();
	}
	
}
