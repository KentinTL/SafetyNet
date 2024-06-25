package com.openclassrooms.safetynet.services;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.tools.Tool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.controller.dto.response.InfosChildByAdress;
import com.openclassrooms.safetynet.controller.dto.response.InfosPersonsByFireStation;
import com.openclassrooms.safetynet.controller.dto.response.MinimalPersonModel;
import com.openclassrooms.safetynet.controller.dto.response.PersonInfos;
import com.openclassrooms.safetynet.controller.dto.response.PersonModelWithAge;
import com.openclassrooms.safetynet.dao.IFireStationDao;
import com.openclassrooms.safetynet.dao.IMedicalRecordDao;
import com.openclassrooms.safetynet.dao.IPersonDao;
import com.openclassrooms.safetynet.model.FireStationModel;
import com.openclassrooms.safetynet.model.PersonModel;
import com.openclassrooms.safetynet.utilities.Tools;

@Service
public class PersonService implements IPersonService{

	@Autowired
	private IPersonDao iPersonDao;
	
	@Autowired
	private IFireStationDao iFireStationDao;
	
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
	
	private PersonInfos mapToPersonInfos(PersonModel personModel) {
		PersonInfos result = new PersonInfos();
		result.setAddress(personModel.getAddress());
		result.setFirestName(personModel.getFirstName());
		result.setLastName(personModel.getLastName());
		result.setPhone(personModel.getPhone());
		return result;
	}

	@Override
	public List<PersonModel> consultAllPersons() {
		return iPersonDao.fecthAllPerson();
	}

	@Override
	public InfosPersonsByFireStation getPersonByFirestation(int stationNumber) {
		InfosPersonsByFireStation result = new InfosPersonsByFireStation();

		List<FireStationModel> fireStationList = iFireStationDao.fetchFireStationsByStationNumber(stationNumber);
		
		List<String> fireStationAddresses =  fireStationList.stream()
							.map(firestation -> firestation.getAddress())
							.collect(Collectors.toList());
		
		List<PersonModel> personsList = iPersonDao.fecthAllPerson().stream()
				.filter(personModel -> fireStationAddresses.contains(personModel.getAddress()))
				.collect(Collectors.toList());
		
		List<String> agePersons = personsList.stream()
				.map(personModel -> iMedicalRecordDao.fetchMedicalRecordByFirstNameAndLastName(personModel.getFirstName(), personModel.getLastName()))
				.filter(t -> !t.isBlank())
				.toList();
		
		long adultCount = agePersons.stream().map(t -> Tools.getAge(t)).filter(age -> Tools.isAdult(age)).count();
		long childCount = agePersons.stream().map(t -> Tools.getAge(t)).filter(age -> Tools.isChild(age)).count();
		
		int intChildCount = (int) childCount;
		int intAdultCount = (int) adultCount;
		
		result.setAdultCount(intAdultCount);
		result.setChildCount(intChildCount);
		result.setPersonInfos(personsList.stream().map(pesonModel -> mapToPersonInfos(pesonModel)).toList());
		return result;
	}

	@Override
	public InfosChildByAdress getChildUnderEighteen(String address) {
		
		Optional<List<PersonModel>> allPersonsList = iPersonDao.findByAddress(address);
		
		List<PersonModelWithAge> childList = new ArrayList<PersonModelWithAge>();
		List<MinimalPersonModel> adultList = new ArrayList<MinimalPersonModel>();
		
		for (PersonModel person: allPersonsList.get()) {
			int ageChild = Tools.getAge(iMedicalRecordDao.fetchMedicalRecordByFirstNameAndLastName(person.getFirstName(), person.getLastName()));
			
			if(Tools.isChild(ageChild)) {
				childList.add(new PersonModelWithAge(person.getFirstName(), person.getLastName(), ageChild));
			}else {
				adultList.add(new MinimalPersonModel(person.getFirstName(), person.getLastName()));
			}
		}
		
		return (childList.size()>0) ? new InfosChildByAdress(childList, adultList) : null;
	}
	
}
