package com.openclassrooms.safetynet.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.model.DataModel;
import com.openclassrooms.safetynet.model.FireStationModel;

import jakarta.annotation.PostConstruct;

@Service
public class FireStationDataService {
	private List<FireStationModel>firestations;
	
	@PostConstruct
	public void init() {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			InputStream inputStream = new ClassPathResource("data.json").getInputStream();
	           DataModel dataModel = objectMapper.readValue(inputStream, DataModel.class);
	           firestations = dataModel.getFirestations();		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public List<FireStationModel> getFireStationModels(){
		return firestations;
	}
}
