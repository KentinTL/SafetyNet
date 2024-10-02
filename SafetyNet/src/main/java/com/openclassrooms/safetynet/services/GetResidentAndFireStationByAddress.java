package com.openclassrooms.safetynet.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.controller.dto.response.ResidentAndFireStationByAddress;
import com.openclassrooms.safetynet.controller.dto.response.ResidentByAddress;
import com.openclassrooms.safetynet.dao.IFireStationDao;
import com.openclassrooms.safetynet.dao.IMedicalRecordDao;
import com.openclassrooms.safetynet.dao.IPersonDao;
import com.openclassrooms.safetynet.model.PersonModel;
import com.openclassrooms.safetynet.utilities.Tools;

@Service
public class GetResidentAndFireStationByAddress implements IGetResidentAndFirestationByAddress {

	@Autowired
	private IPersonDao iPersonDao;
	
	@Autowired
	private IMedicalRecordDao iMedicalRecordDao;
		
	@Autowired
	private IFireStationDao iFireStationDao;
	
	@Override
	public ResidentAndFireStationByAddress getResidentAndFireStationByAddress(String address) {
		
		List<ResidentByAddress> residentByAddress = new ArrayList<ResidentByAddress>();

		List<PersonModel> allPersonsList = iPersonDao.findByAddress(address);
		
		var fireStation = iFireStationDao.fetchFireStationByAddress(address);		
		int fireStationNumber = fireStation.isPresent() ? fireStation.get().getStation() : 0;

		for (PersonModel person: allPersonsList) {
			var medicalRecord = iMedicalRecordDao.fetchMedicalRecordByFirstNameAndLastName(person.getFirstName(), person.getLastName());
			
			
			if (medicalRecord.isPresent()) {
				List<String> medications = medicalRecord.get().getMedications();
				List<String> allergies =  medicalRecord.get().getAllergies();
				int age = Tools.getAge(medicalRecord.get().getBirthdate());

				residentByAddress.add(new ResidentByAddress(person.getLastName(), person.getPhone(), age, medications, allergies));
			}

		}
		
		return new ResidentAndFireStationByAddress(residentByAddress, fireStationNumber);
	}

}
