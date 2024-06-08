package com.openclassrooms.safetynet.dao;

import java.util.List;

import com.openclassrooms.safetynet.model.FireStationModel;

public interface IFireStationDao {
	List<FireStationModel> fetchAllFireStation();
	
}
