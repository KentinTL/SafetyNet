package com.openclassrooms.safetynet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.controller.dto.response.InfosPersonsByFireStation;
import com.openclassrooms.safetynet.controller.dto.response.PersonPhoneNumber;
import com.openclassrooms.safetynet.controller.dto.response.ResidentAndFireStationsByListOfFireSations;
import com.openclassrooms.safetynet.services.IFireStationService;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
public class FireStationController {
	
	@Autowired
	private IFireStationService ifirestationservice;
	
	@GetMapping("/firestation")
	public InfosPersonsByFireStation getPersonsByFireStation(@RequestParam int stationNumber) {
		return ifirestationservice.getPersonByFirestation(stationNumber);
	}
	
	@GetMapping("/phoneAlert")
	public PersonPhoneNumber getPhoneByFireStation(@RequestParam int firestation) {
		return ifirestationservice.getAllPhoneNumberByFireStationNumber(firestation);
	}
	
	@GetMapping("/flood/stations")
    public List<ResidentAndFireStationsByListOfFireSations> getResidentAndFireStationsByListOfFireSations(@RequestParam List<Integer> stations) {
        return ifirestationservice.getResidentAndFireStationsByListOfFireSations(stations);
    }
	
}
