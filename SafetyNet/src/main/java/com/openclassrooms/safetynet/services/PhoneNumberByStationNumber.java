package com.openclassrooms.safetynet.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.controller.dto.response.PersonPhoneNumber;
import com.openclassrooms.safetynet.dao.IFireStationDao;
import com.openclassrooms.safetynet.dao.IPersonDao;
import com.openclassrooms.safetynet.model.FireStationModel;
import com.openclassrooms.safetynet.model.PersonModel;

@Service
public class PhoneNumberByStationNumber implements IPhoneNumbersByStationNumber {
	
	@Autowired
	private IPersonDao iPersonDao;
	
	@Autowired
	private IFireStationDao iFireStationDao;
	
	@Override
	public PersonPhoneNumber getAllPhoneNumberByFireStationNumber(int stationNumber) {
		
		List<FireStationModel> fireStationList = iFireStationDao.fetchFireStationsByStationNumber(stationNumber);

		List<String> fireStationAddresses =  fireStationList.stream()
				.map(firestation -> firestation.getAddress())
				.collect(Collectors.toList());
		
		System.out.println(fireStationAddresses);
		
		List<PersonModel> personsCoveredByFireStation = iPersonDao.fecthAllPerson().stream()
				.filter(personModel -> fireStationAddresses.contains(personModel.getAddress()))
				.collect(Collectors.toList());
		
		Set<String> phoneNumbersSet = personsCoveredByFireStation.stream()
				.map(person -> person.getPhone())
				.collect(Collectors.toSet());
		
		List<String> phoneNumbers = new ArrayList<>(phoneNumbersSet);
		
		System.out.println(phoneNumbers);
		
		PersonPhoneNumber result = new PersonPhoneNumber();
		result.setPhoneNumberList(phoneNumbers);
		return result;
	}

}
