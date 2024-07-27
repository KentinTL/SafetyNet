package com.openclassrooms.safetynet.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.controller.dto.response.InfosChildByAdress;
import com.openclassrooms.safetynet.controller.dto.response.InfosMailsByCity;
import com.openclassrooms.safetynet.controller.dto.response.InfosPersonByLastName;
import com.openclassrooms.safetynet.controller.dto.response.MinimalPersonModel;
import com.openclassrooms.safetynet.controller.dto.response.PersonMail;
import com.openclassrooms.safetynet.controller.dto.response.PersonModelWithAge;
import com.openclassrooms.safetynet.controller.dto.response.PersonsInfosAndMedical;
import com.openclassrooms.safetynet.dao.IMedicalRecordDao;
import com.openclassrooms.safetynet.dao.IPersonDao;
import com.openclassrooms.safetynet.model.PersonModel;
import com.openclassrooms.safetynet.utilities.Tools;

@Service
public class PersonService implements IPersonService{

	@Autowired
	private IPersonDao iPersonDao;
	
	@Autowired
	private IMedicalRecordDao iMedicalRecordDao;

    @Override
    public void add(PersonModel personModel) {
    	var personExist = iPersonDao.findByFirstNameAndLastName(personModel.getFirstName(), personModel.getLastName());
    	if(!personExist.isEmpty()) {
    		throw new RuntimeException("This person already exist into database");
    	}
        iPersonDao.create(personModel);
    }

    @Override
    public void update(String firstName, String lastName, PersonModel personModel) {
    	var personExist = iPersonDao.findByFirstNameAndLastName(firstName, lastName);
    	
    	if(personExist.isEmpty()) {
    		throw new RuntimeException("No persons founded");
    	}
    	
        iPersonDao.update(personModel);
    }

    @Override
    public void delete(String firstName, String lastName) {
    	var personExist = iPersonDao.findByFirstNameAndLastName(firstName, lastName);
    	if(personExist.isEmpty()) {
    		throw new RuntimeException("Person does not exist");
    	}
        PersonModel personModel = new PersonModel();
        personModel.setFirstName(firstName);
        personModel.setLastName(lastName);
        iPersonDao.delete(personModel);
    }

	
	@Override
	public List<PersonModel> consultAllPersons() {
		return iPersonDao.fecthAllPerson();
	}

	@Override
	public InfosChildByAdress getChildUnderEighteen(String address) {
		
		Optional<List<PersonModel>> allPersonsList = iPersonDao.findByAddress(address);
		
		List<PersonModelWithAge> childList = new ArrayList<PersonModelWithAge>();
		List<MinimalPersonModel> adultList = new ArrayList<MinimalPersonModel>();
		
		for (PersonModel person: allPersonsList.get()) {
			var medicalRecord = iMedicalRecordDao.fetchMedicalRecordByFirstNameAndLastName(person.getFirstName(), person.getLastName());
			if(medicalRecord.isPresent()) {
			
				int ageChild = Tools.getAge(medicalRecord.get().getBirthdate());
			
				if(Tools.isChild(ageChild)) {
					childList.add(new PersonModelWithAge(person.getFirstName(), person.getLastName(), ageChild));
				}else {
					adultList.add(new MinimalPersonModel(person.getFirstName(), person.getLastName()));
				}
			}
		}
		
		return (childList.size()>0) ? new InfosChildByAdress(childList, adultList) : null;
	}	
	

	@Override
	public InfosMailsByCity getMailsByCity(String city) {
		
		Optional<List<PersonModel>> allPersonByCity = iPersonDao.findPersonByCity(city);
		
		List<PersonMail> personMails = new ArrayList<>();
		
		
		if(allPersonByCity.isPresent()) {
			for(PersonModel person: allPersonByCity.get()) {
				personMails.add(new PersonMail(person.getEmail()));
			}
		}
		
		return new InfosMailsByCity(personMails);
	}

	@Override
	public InfosPersonByLastName getInfosPersonsByLastName(String lastName) {
		List<PersonsInfosAndMedical> personsByLastName = new ArrayList<PersonsInfosAndMedical>();
		
		Optional<List<PersonModel>> allPersonsByLastName = iPersonDao.findPersonsByLastName(lastName);
		
		for(PersonModel person : allPersonsByLastName.get()) {
			var medicalRecord = iMedicalRecordDao.fetchMedicalRecordByFirstNameAndLastName(person.getFirstName(), person.getLastName());
			if (medicalRecord.isPresent()) {
				List<String> medications = medicalRecord.get().getMedications();
				List<String> allergies =  medicalRecord.get().getAllergies();
				int age = Tools.getAge(medicalRecord.get().getBirthdate());

				personsByLastName.add(new PersonsInfosAndMedical(person.getLastName(), null, person.getAddress(), age, person.getEmail(), medications, allergies));
			}
		}
		
		return new InfosPersonByLastName(personsByLastName);
	}
}
