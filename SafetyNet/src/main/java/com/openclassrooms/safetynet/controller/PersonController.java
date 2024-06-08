package com.openclassrooms.safetynet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.model.PersonModel;
import com.openclassrooms.safetynet.services.IPersonService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class PersonController {
	@Autowired
	private IPersonService ipersonservice;
	
	@GetMapping("/person")
	public List<PersonModel> getAllPerson() {
		return ipersonservice.consultAllPersons();
	}
	
}