package com.openclassrooms.safetynet.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.openclassrooms.safetynet.model.DataModel;
import com.openclassrooms.safetynet.model.PersonModel;

@Repository
public class PersonDao implements IPersonDao{

	@Autowired
	private GeneriqueDataModelDao generiqueDataModelDao;

	
	@Override
	public Optional<PersonModel> findByEmail(String email) {
		DataModel dataModel = generiqueDataModelDao.fetchData();
		return dataModel.getPersons().stream()
				.filter(personModel -> personModel.getEmail().equals(email))
				.findFirst();
	}

	@Override
	public Optional<List<PersonModel>> findByAddress(String address) {
		DataModel dataModel = generiqueDataModelDao.fetchData();

		return Optional.of(dataModel.getPersons().stream()
				.filter(t -> t.getAddress().equals(address))
				.toList());
	}
	
	@Override
	public Optional<List<PersonModel>>  findPersonByCity(String city) {
		DataModel dataModel = generiqueDataModelDao.fetchData();

		return Optional.of(dataModel.getPersons().stream()
				.filter(personModel -> personModel.getCity().equals(city))
				.toList());
	}
	
	@Override
	public Optional<List<PersonModel>> findPersonsByLastName(String lastName){
		DataModel dataModel = generiqueDataModelDao.fetchData();

		return Optional.of(dataModel.getPersons().stream()
				.filter(personModel -> personModel.getLastName().equals(lastName))
				.toList());
	}
	
	@Override
	public void create(PersonModel personModel) {
		DataModel dataModel = generiqueDataModelDao.fetchData();

		dataModel = generiqueDataModelDao.fetchData();
		dataModel.getPersons().add(personModel);
		generiqueDataModelDao.updateData(dataModel);
	}

	@Override
	public List<PersonModel> fecthAllPerson() {
		DataModel dataModel = generiqueDataModelDao.fetchData();

		dataModel = generiqueDataModelDao.fetchData();
		return dataModel.getPersons();
	}


}
