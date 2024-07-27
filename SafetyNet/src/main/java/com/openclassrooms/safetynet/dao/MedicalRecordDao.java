package com.openclassrooms.safetynet.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.openclassrooms.safetynet.model.DataModel;
import com.openclassrooms.safetynet.model.MedicalRecordModel;

@Repository
public class MedicalRecordDao implements IMedicalRecordDao {
	@Autowired
	GeneriqueDataModelDao generiqueDataModelDao;
	
	private DataModel dataModel;

	@Override
	public List<MedicalRecordModel> fetchAllMedicalRecord() {
		dataModel = generiqueDataModelDao.fetchData();
		return dataModel.getMedicalrecords();
	}
	
	@Override
	public Optional<MedicalRecordModel> fetchMedicalRecordByFirstNameAndLastName(String firstName, String lastName) {
		return fetchAllMedicalRecord().stream()
				.filter(medicalRecord -> medicalRecord.getFirstName().equals(firstName)&& medicalRecord.getLastName().equals(lastName))
				.findFirst();
	}
	
	public void create(MedicalRecordModel medicalRecord) {
		dataModel = generiqueDataModelDao.fetchData();
		dataModel.getMedicalrecords().add(medicalRecord);
		generiqueDataModelDao.updateData(dataModel);
	}
	
	public void update(MedicalRecordModel medicalRecord) {
		List<MedicalRecordModel> medicalRecords = dataModel.getMedicalrecords();
		for(int i = 0; i < medicalRecords.size(); i++) {
            if (medicalRecords.get(i).getFirstName().equals(medicalRecord.getFirstName()) && medicalRecords.get(i).getLastName().equals(medicalRecord.getLastName())) {
            	medicalRecords.set(i, medicalRecord);
                generiqueDataModelDao.updateData(dataModel);
                return;
            }
		}
	}
	
    @Override
    public void delete(MedicalRecordModel medicalRecord) {
        DataModel dataModel = generiqueDataModelDao.fetchData();
        dataModel.getMedicalrecords().removeIf(p -> p.getFirstName().equals(medicalRecord.getFirstName()) && p.getLastName().equals(medicalRecord.getLastName()));
        generiqueDataModelDao.updateData(dataModel);
    }
}
