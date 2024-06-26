package com.openclassrooms.safetynet.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.controller.dto.response.InfosChildByAdress;
import com.openclassrooms.safetynet.controller.dto.response.InfosMailsByCity;
import com.openclassrooms.safetynet.controller.dto.response.MinimalPersonModel;
import com.openclassrooms.safetynet.controller.dto.response.PersonMail;
import com.openclassrooms.safetynet.controller.dto.response.PersonModelWithAge;
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


}
