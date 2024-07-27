package com.openclassrooms.safetynet.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.dao.IMedicalRecordDao;
import com.openclassrooms.safetynet.dao.IPersonDao;
import com.openclassrooms.safetynet.model.MedicalRecordModel;

@Service
public class MedicalRecordService implements IMedicalRecordService{
	@Autowired
	private IPersonDao iPersonDao;
	@Autowired
	private IMedicalRecordDao imedicalrecorddao;
	
	@Override
	public List<MedicalRecordModel> consultAllMedicalRecord() {
		return imedicalrecorddao.fetchAllMedicalRecord();	}
		
	@Override
	public void add(MedicalRecordModel medicalRecord) {
    	var personExist = iPersonDao.findByFirstNameAndLastName(medicalRecord.getFirstName(), medicalRecord.getLastName());
    	
    	if(personExist.isEmpty()) {
    		throw new RuntimeException("You can't create a MedicalRecord for a person who doesn't exist");
    	}
    	
    	imedicalrecorddao.create(medicalRecord);
	}
	
    @Override
    public void update(String firstName, String lastName, MedicalRecordModel medicalRecord) {
    	var medicalRecordForPerson = imedicalrecorddao.fetchMedicalRecordByFirstNameAndLastName(firstName, lastName);
    	
    	if(medicalRecordForPerson.isEmpty()) {
    		throw new RuntimeException("No Medical Medical founded");
    	}
    	
    	imedicalrecorddao.update(medicalRecord);
    }
	
    @Override
    public void delete(String firstName, String lastName) {
    	var medicalRecordForPerson = imedicalrecorddao.fetchMedicalRecordByFirstNameAndLastName(firstName, lastName);
    	if(medicalRecordForPerson.isEmpty()) {
    		throw new RuntimeException("Medical Record does not exist");
    	}
        MedicalRecordModel medicalRecord = new MedicalRecordModel();
        medicalRecord.setFirstName(firstName);
        medicalRecord.setLastName(lastName);
        imedicalrecorddao.delete(medicalRecord);
    }
}
