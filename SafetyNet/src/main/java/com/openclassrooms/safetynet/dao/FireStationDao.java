package com.openclassrooms.safetynet.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.openclassrooms.safetynet.model.DataModel;
import com.openclassrooms.safetynet.model.FireStationModel;

@Repository
public class FireStationDao implements IFireStationDao{
	@Autowired
	private GeneriqueDataModelDao generiqueDataModelDao;
	
	@Override
	public List<FireStationModel> fetchAllFireStation() {
		DataModel dataModel = generiqueDataModelDao.fetchData();
		return dataModel.getFirestations();
	}
	
}
