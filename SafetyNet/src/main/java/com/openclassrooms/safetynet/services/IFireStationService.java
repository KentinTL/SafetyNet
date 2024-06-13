package com.openclassrooms.safetynet.services;

import java.util.List;

import com.openclassrooms.safetynet.model.FireStationModel;

public interface IFireStationService {
    List<FireStationModel> consultAllfirestations();
}
