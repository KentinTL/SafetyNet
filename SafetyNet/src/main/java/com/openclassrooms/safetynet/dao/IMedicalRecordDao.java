package com.openclassrooms.safetynet.dao;

import java.util.List;
import java.util.Optional;

import com.openclassrooms.safetynet.model.MedicalRecordModel;

public interface IMedicalRecordDao {
	List<MedicalRecordModel> fetchAllMedicalRecord();
	Optional<MedicalRecordModel> fetchMedicalRecordByFirstNameAndLastName(String firstName, String lastName);
	public void create(MedicalRecordModel medicalRecord);
	public void update(MedicalRecordModel medicalRecord);
	public void delete(MedicalRecordModel medicalRecord);
}
