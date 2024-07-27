package com.openclassrooms.safetynet.services;

import java.util.List;

import com.openclassrooms.safetynet.model.MedicalRecordModel;

public interface IMedicalRecordService {
	List<MedicalRecordModel> consultAllMedicalRecord();
	void add(MedicalRecordModel medicalRecord);
	void update(String firstName, String lastName, MedicalRecordModel medicalRecord);
	void delete(String firstName, String lastName);
}
