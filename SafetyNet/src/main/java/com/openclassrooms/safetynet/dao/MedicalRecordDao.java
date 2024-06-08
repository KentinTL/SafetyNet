package com.openclassrooms.safetynet.dao;

import java.util.List;

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

}
