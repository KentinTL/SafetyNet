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

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
    @PostMapping("/person")
    public String addPerson(@RequestBody PersonModel personModel) {
        try {
            ipersonservice.add(personModel);
            return "Person added successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @PutMapping("/person/{firstName}/{lastName}")
    public String updatePerson(@PathVariable String firstName, @PathVariable String lastName, @RequestBody PersonModel personModel) {
        try {
            ipersonservice.update(firstName, lastName, personModel);
            return "Person updated successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @DeleteMapping("/person/{firstName}/{lastName}")
    public String deletePerson(@PathVariable String firstName, @PathVariable String lastName) {
        try {
            PersonModel personModel = new PersonModel();
            personModel.setFirstName(firstName);
            personModel.setLastName(lastName);
            ipersonservice.delete(firstName, lastName);
            return "Person deleted successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
	
}
