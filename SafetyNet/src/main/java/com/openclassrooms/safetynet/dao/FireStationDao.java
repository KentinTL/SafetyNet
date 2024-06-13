package com.openclassrooms.safetynet.dao;

import java.util.List;
import java.util.stream.Collectors;

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

	@Override
	public List<FireStationModel> fetchFireStationsByStationNumber(int stationNumber) {
		return fetchAllFireStation().stream()
				.filter(firestation -> firestation.getStation() == stationNumber)
				.collect(Collectors.toList());
	}
	
}
