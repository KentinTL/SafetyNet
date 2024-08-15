package com.openclassrooms.safetynet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.controller.dto.response.InfosPersonsByFireStation;
import com.openclassrooms.safetynet.controller.dto.response.PersonPhoneNumber;
import com.openclassrooms.safetynet.controller.dto.response.ResidentAndFireStationsByListOfFireSations;
import com.openclassrooms.safetynet.model.FireStationModel;
import com.openclassrooms.safetynet.services.IFireStationService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;





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
	
	@PostMapping("/firestation")
	public ResponseEntity<HttpStatus> addFirstation(@RequestBody FireStationModel fireStationModel) {
		ifirestationservice.add(fireStationModel);
        return ResponseEntity.created(null).build();
	}
	
	@PutMapping("/firestation/{address}")
	public ResponseEntity<HttpStatus> addFirstation(@PathVariable String address, @RequestBody FireStationModel fireStationModel) {
		ifirestationservice.update(address, fireStationModel);
        return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/firestation/{address}")
	public ResponseEntity<HttpStatus> deleteFireStationByAddress(@PathVariable String address) {
		FireStationModel fireStationModel = new FireStationModel();
		fireStationModel.setAddress(address);
		ifirestationservice.deleteFireStation(address);
		return ResponseEntity.ok().build();
	}
	
	
}
