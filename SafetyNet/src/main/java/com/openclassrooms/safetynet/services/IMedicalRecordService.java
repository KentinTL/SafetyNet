package com.openclassrooms.safetynet.services;

import java.util.List;

import com.openclassrooms.safetynet.model.MedicalRecordModel;

public interface IMedicalRecordService {
	List<MedicalRecordModel> consultAllMedicalRecord();
}
