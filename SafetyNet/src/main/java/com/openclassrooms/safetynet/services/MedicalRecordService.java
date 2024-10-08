package com.openclassrooms.safetynet.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.dao.IMedicalRecordDao;
import com.openclassrooms.safetynet.dao.IPersonDao;
import com.openclassrooms.safetynet.exceptions.EntityAlreadyExistException;
import com.openclassrooms.safetynet.exceptions.EntityNotFoundException;
import com.openclassrooms.safetynet.model.MedicalRecordModel;

@Service
public class MedicalRecordService implements IMedicalRecordService{
	@Autowired
	private IPersonDao iPersonDao;
	@Autowired
	private IMedicalRecordDao iMedicalRecordDao;
	
	@Override
	public List<MedicalRecordModel> consultAllMedicalRecord() {
		return iMedicalRecordDao.fetchAllMedicalRecord();	}
		
	@Override
	public void add(MedicalRecordModel medicalRecord) {
    	var personExist = iPersonDao.findByFirstNameAndLastName(medicalRecord.getFirstName(), medicalRecord.getLastName());
    	
    	if(personExist.isEmpty()) {
    		throw new EntityAlreadyExistException("You can't create a MedicalRecord for a person who doesn't exist");
    	}
    	
    	iMedicalRecordDao.create(medicalRecord);
	}
	
    @Override
    public void update(String firstName, String lastName, MedicalRecordModel medicalRecord) {
    	var medicalRecordForPerson = iMedicalRecordDao.fetchMedicalRecordByFirstNameAndLastName(firstName, lastName);
    	
    	if(medicalRecordForPerson.isEmpty()) {
    		throw new EntityNotFoundException("No Medical Record founded");
    	}
    	
    	iMedicalRecordDao.update(medicalRecord);
    }
	
    @Override
    public void delete(String firstName, String lastName) {
    	var medicalRecordForPerson = iMedicalRecordDao.fetchMedicalRecordByFirstNameAndLastName(firstName, lastName);
    	if(medicalRecordForPerson.isEmpty()) {
    		throw new EntityNotFoundException("Medical Record does not exist");
    	}
        MedicalRecordModel medicalRecord = new MedicalRecordModel();
        medicalRecord.setFirstName(firstName);
        medicalRecord.setLastName(lastName);
        iMedicalRecordDao.delete(medicalRecord);
    }
}
