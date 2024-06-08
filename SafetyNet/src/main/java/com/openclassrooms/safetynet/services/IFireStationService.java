package com.openclassrooms.safetynet.services;

import java.util.List;

import com.openclassrooms.safetynet.model.FireStationModel;

public interface IFireStationService {
    FireStationModel consultFirestation(int numStation);
    FireStationModel consultFirestationByAddress(String address);
    List<FireStationModel> consultAllfirestations();
    void deleteMappingFireStation(String address);
    void addMappingFireStation(FireStationModel fireStationModel);
    void updateMappingFireStation(String address, int newStationNumber);
}
