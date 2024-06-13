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
	public List<FireStationModel> consultAllfirestations() {
		return iFireStationDao.fetchAllFireStation();
	}

}
