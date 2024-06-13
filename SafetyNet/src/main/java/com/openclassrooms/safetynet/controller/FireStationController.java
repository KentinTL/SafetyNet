package com.openclassrooms.safetynet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.model.FireStationModel;
import com.openclassrooms.safetynet.services.IFireStationService;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
public class FireStationController {
	
	@Autowired
	private IFireStationService ifirestationservice;
	
	/*
	 * @GetMapping("/firestation") public List<FireStationModel>getAllFireStations()
	 * { return ifirestationservice.consultAllfirestations(); }
	 */
	
}
