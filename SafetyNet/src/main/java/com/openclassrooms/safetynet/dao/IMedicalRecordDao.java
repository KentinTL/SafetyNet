package com.openclassrooms.safetynet.dao;

import java.util.List;

import com.openclassrooms.safetynet.model.MedicalRecordModel;

public interface IMedicalRecordDao {
	List<MedicalRecordModel> fetchAllMedicalRecord();
}
