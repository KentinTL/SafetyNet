package com.openclassrooms.safetynet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.controller.dto.response.InfosChildByAdress;
import com.openclassrooms.safetynet.controller.dto.response.InfosMailsByCity;
import com.openclassrooms.safetynet.controller.dto.response.InfosPersonByLastName;
import com.openclassrooms.safetynet.controller.dto.response.ResidentAndFireStationByAddress;
import com.openclassrooms.safetynet.model.PersonModel;
import com.openclassrooms.safetynet.services.IPersonService;
import com.openclassrooms.safetynet.services.IGetResidentAndFirestationByAddress;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class PersonController {
	
	@Autowired
	private IPersonService ipersonservice;
	@GetMapping("/person")
	public List<PersonModel> getAllPerson() {
		return ipersonservice.consultAllPersons();
	}
	
	
	@GetMapping("/childAlert")
	public InfosChildByAdress getChildByAddress(@RequestParam String address) {
		return ipersonservice.getChildUnderEighteen(address);
	}
	
	@Autowired
	private IGetResidentAndFirestationByAddress iGetResidentAndFirestationByAddress;
	@GetMapping("fire")
	public ResidentAndFireStationByAddress getResidentsAndFireStationsByAddress(@RequestParam String address) {
		return iGetResidentAndFirestationByAddress.getResidentAndFireStationByAddress(address);
	}
	
	@GetMapping("communityEmail")
	public InfosMailsByCity getMailsByCity(@RequestParam String city) {
		return ipersonservice.getMailsByCity(city);
	}
	
	@GetMapping("/personInfolastName")
	public InfosPersonByLastName getInfosPersonsByLastName(@RequestParam String lastname) {
		return ipersonservice.getInfosPersonsByLastName(lastname);
	}
	
	
	
}
