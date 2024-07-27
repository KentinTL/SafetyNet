package com.openclassrooms.safetynet.dao;

import java.util.List;
import java.util.Optional;

import com.openclassrooms.safetynet.model.FireStationModel;

public interface IFireStationDao {
	List<FireStationModel> fetchAllFireStation();
	List<FireStationModel> fetchFireStationsByStationNumber(int stationNumber);
	Optional<FireStationModel> fetchFireStationByAddress(String address);
	void create(FireStationModel fireStationModel);
	void update(FireStationModel fireStationModel);
	void delete(FireStationModel fireStationModel);
}
