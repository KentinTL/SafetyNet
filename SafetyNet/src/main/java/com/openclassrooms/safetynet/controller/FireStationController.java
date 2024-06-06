package com.openclassrooms.safetynet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.model.FireStationModel; 
import com.openclassrooms.safetynet.services.FireStationDataService;

@RestController
public class FireStationController {
	@Autowired
	private FireStationDataService firestationservice;
	
	@GetMapping("/firestation")
	public List<FireStationModel> getAllFireStations() {
		return firestationservice.getFireStationModels();
	}
}
