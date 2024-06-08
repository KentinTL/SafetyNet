package com.openclassrooms.safetynet.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.dao.IMedicalRecordDao;
import com.openclassrooms.safetynet.model.MedicalRecordModel;

@Service
public class MedicalRecordService implements IMedicalRecordService{
	
	@Autowired
	private IMedicalRecordDao imedicalrecorddao;
	
	@Override
	public List<MedicalRecordModel> consultAllMedicalRecord() {
		return imedicalrecorddao.fetchAllMedicalRecord();	}
	
}
