package com.openclassrooms.safetynet.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.controller.dto.response.InfosPersonsByFireStation;
import com.openclassrooms.safetynet.controller.dto.response.PersonPhoneNumber;
import com.openclassrooms.safetynet.dao.IFireStationDao;
import com.openclassrooms.safetynet.dao.IMedicalRecordDao;
import com.openclassrooms.safetynet.dao.IPersonDao;
import com.openclassrooms.safetynet.model.FireStationModel;
import com.openclassrooms.safetynet.model.PersonModel;
import com.openclassrooms.safetynet.utilities.Tools;

@Service
public class FireStationService implements IFireStationService{
	
	@Autowired
	private IPersonDao iPersonDao;
	
	@Autowired
	private IFireStationDao iFireStationDao;
	
	@Autowired
	private IMedicalRecordDao iMedicalRecordDao;

	@Override
	public List<FireStationModel> consultAllfirestations() {
		return iFireStationDao.fetchAllFireStation();
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
				.filter(t -> t.isPresent())
				.map(t -> t.get().getBirthdate())
				.toList();
		
		long adultCount = agePersons.stream().map(t -> Tools.getAge(t)).filter(age -> Tools.isAdult(age)).count();
		long childCount = agePersons.stream().map(t -> Tools.getAge(t)).filter(age -> Tools.isChild(age)).count();
		
		int intChildCount = (int) childCount;
		int intAdultCount = (int) adultCount;
		
		result.setAdultCount(intAdultCount);
		result.setChildCount(intChildCount);
		result.setPersonInfos(personsList.stream().map(pesonModel -> Tools.mapToPersonInfos(pesonModel)).toList());
		return result;
	}
	
	@Override
	public PersonPhoneNumber getAllPhoneNumberByFireStationNumber(int stationNumber) {
		
		List<FireStationModel> fireStationList = iFireStationDao.fetchFireStationsByStationNumber(stationNumber);

		List<String> fireStationAddresses =  fireStationList.stream()
				.map(firestation -> firestation.getAddress())
				.collect(Collectors.toList());
				
		List<PersonModel> personsCoveredByFireStation = iPersonDao.fecthAllPerson().stream()
				.filter(personModel -> fireStationAddresses.contains(personModel.getAddress()))
				.collect(Collectors.toList());
		
		Set<String> phoneNumbersSet = personsCoveredByFireStation.stream()
				.map(person -> person.getPhone())
				.collect(Collectors.toSet());
		
		List<String> phoneNumbers = new ArrayList<>(phoneNumbersSet);
				
		PersonPhoneNumber result = new PersonPhoneNumber();
		result.setPhoneNumberList(phoneNumbers);
		return result;
	}
	

}
