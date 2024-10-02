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
	public List<PersonModel> findByAddress(String address) {
		DataModel dataModel = generiqueDataModelDao.fetchData();

		return dataModel.getPersons().stream()
				.filter(t -> t.getAddress().equals(address))
				.toList();
	}
	
	@Override
	public List<PersonModel> findPersonByCity(String city) {
		DataModel dataModel = generiqueDataModelDao.fetchData();

		return dataModel.getPersons().stream()
				.filter(personModel -> personModel.getCity().equals(city))
				.toList();
	}
	
	@Override
	public List<PersonModel> findPersonsByLastName(String lastName){
		DataModel dataModel = generiqueDataModelDao.fetchData();

		return dataModel.getPersons().stream()
				.filter(personModel -> personModel.getLastName().equals(lastName))
				.toList();
	}
	

	@Override
	public List<PersonModel> fecthAllPerson() {
		DataModel dataModel = generiqueDataModelDao.fetchData();
		dataModel = generiqueDataModelDao.fetchData();
		return dataModel.getPersons();
	}

	@Override
	public void create(PersonModel personModel) {
		DataModel dataModel = generiqueDataModelDao.fetchData();
		dataModel.getPersons().add(personModel);
		generiqueDataModelDao.updateData(dataModel);
	}

	
    @Override
    public void update(PersonModel personModel) {
        DataModel dataModel = generiqueDataModelDao.fetchData();
        List<PersonModel> persons = dataModel.getPersons();
        for (int i = 0; i < persons.size(); i++) {
            if (persons.get(i).getFirstName().equals(personModel.getFirstName()) && persons.get(i).getLastName().equals(personModel.getLastName())) {
                persons.set(i, personModel);
                generiqueDataModelDao.updateData(dataModel);
                return;
            }
        }
    }

    @Override
    public void delete(PersonModel personModel) {
        DataModel dataModel = generiqueDataModelDao.fetchData();
        dataModel.getPersons().removeIf(p -> p.getFirstName().equals(personModel.getFirstName()) && p.getLastName().equals(personModel.getLastName()));
        generiqueDataModelDao.updateData(dataModel);
    }

    @Override
    public Optional<PersonModel> findByFirstNameAndLastName(String firstName, String lastName) {
        DataModel dataModel = generiqueDataModelDao.fetchData();
        return dataModel.getPersons().stream()
                .filter(personModel -> personModel.getFirstName().equals(firstName) && personModel.getLastName().equals(lastName))
                .findFirst();
    }

}
