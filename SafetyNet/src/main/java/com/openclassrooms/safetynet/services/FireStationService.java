package com.openclassrooms.safetynet.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.dao.IFireStationDao;
import com.openclassrooms.safetynet.model.FireStationModel;

@Service
public class FireStationService implements IFireStationService{
	
	@Autowired
	private IFireStationDao iFireStationDao;

	@Override
	public FireStationModel consultFirestation(int numStation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FireStationModel consultFirestationByAddress(String address) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FireStationModel> consultAllfirestations() {
		return iFireStationDao.fetchAllFireStation();
	}

	@Override
	public void deleteMappingFireStation(String address) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addMappingFireStation(FireStationModel fireStationModel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateMappingFireStation(String address, int newStationNumber) {
		// TODO Auto-generated method stub
		
	}

}
