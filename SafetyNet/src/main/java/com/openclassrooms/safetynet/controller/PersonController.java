package com.openclassrooms.safetynet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.controller.dto.response.InfosChildByAdress;
import com.openclassrooms.safetynet.controller.dto.response.InfosPersonsByFireStation;
import com.openclassrooms.safetynet.controller.dto.response.PersonPhoneNumber;
import com.openclassrooms.safetynet.controller.dto.response.ResidentAndFireStationByAddress;
import com.openclassrooms.safetynet.model.PersonModel;
import com.openclassrooms.safetynet.services.IPersonService;
import com.openclassrooms.safetynet.services.IPhoneNumbersByStationNumber;
import com.openclassrooms.safetynet.services.IChildUnderEighteen;
import com.openclassrooms.safetynet.services.IGetResidentAndFirestationByAddress;
import com.openclassrooms.safetynet.services.IPersonByFireStation;
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

	@Autowired
	private IPersonByFireStation ipersonByFirestation;
	@GetMapping("/firestation")
	public InfosPersonsByFireStation getPersonsByFireStation(@RequestParam int stationNumber) {
		return ipersonByFirestation.getPersonByFirestation(stationNumber);
	}
	
	
	@Autowired
	private IChildUnderEighteen iChildUnderEighteen;
	@GetMapping("/childAlert")
	public InfosChildByAdress getChildByAddress(@RequestParam String address) {
		return iChildUnderEighteen.getChildUnderEighteen(address);
	}
	
	
	@Autowired
	private IPhoneNumbersByStationNumber phoneNumberByStationNumber;
	@GetMapping("/phoneAlert")
	public PersonPhoneNumber getPhoneByFireStation(@RequestParam int firestation) {
		return phoneNumberByStationNumber.getAllPhoneNumberByFireStationNumber(firestation);
	}
	
	@Autowired
	private IGetResidentAndFirestationByAddress iGetResidentAndFirestationByAddress;
	@GetMapping("fire")
	public ResidentAndFireStationByAddress getResidentsAndFireStationsByAddress(@RequestParam String address) {
		return iGetResidentAndFirestationByAddress.getResidentAndFireStationByAddress(address);
	}
	
}
