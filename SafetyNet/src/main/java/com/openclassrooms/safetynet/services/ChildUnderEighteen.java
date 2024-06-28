package com.openclassrooms.safetynet.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.controller.dto.response.InfosChildByAdress;
import com.openclassrooms.safetynet.controller.dto.response.MinimalPersonModel;
import com.openclassrooms.safetynet.controller.dto.response.PersonModelWithAge;
import com.openclassrooms.safetynet.dao.IMedicalRecordDao;
import com.openclassrooms.safetynet.dao.IPersonDao;
import com.openclassrooms.safetynet.model.PersonModel;
import com.openclassrooms.safetynet.utilities.Tools;

@Service
public class ChildUnderEighteen implements IChildUnderEighteen {
	
	@Autowired
	private IPersonDao iPersonDao;
	
	@Autowired
	private IMedicalRecordDao iMedicalRecordDao;

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
}
