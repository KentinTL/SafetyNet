package com.openclassrooms.safetynet.services;

import java.util.List;

import com.openclassrooms.safetynet.controller.dto.response.InfosPersonsByFireStation;
import com.openclassrooms.safetynet.controller.dto.response.PersonPhoneNumber;
import com.openclassrooms.safetynet.controller.dto.response.ResidentAndFireStationsByListOfFireSations;
import com.openclassrooms.safetynet.model.FireStationModel;

public interface IFireStationService {
    List<FireStationModel> consultAllfirestations();
	public InfosPersonsByFireStation getPersonByFirestation(int stationNumber);
	public PersonPhoneNumber getAllPhoneNumberByFireStationNumber(int stationNumber);
	List<ResidentAndFireStationsByListOfFireSations> getResidentAndFireStationsByListOfFireSations(List<Integer>fireStationNumbers);
	void add(FireStationModel fireStationModel);
	void update(String address, FireStationModel fireStationModel);
	void deleteFireStation(String address);
}
