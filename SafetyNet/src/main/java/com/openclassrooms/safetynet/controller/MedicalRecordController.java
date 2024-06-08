package com.openclassrooms.safetynet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.model.MedicalRecordModel;
import com.openclassrooms.safetynet.services.IMedicalRecordService;

@RestController
public class MedicalRecordController {
	
	@Autowired
	IMedicalRecordService imedicalservice;
	
	@GetMapping("/medicalRecord")
	public List<MedicalRecordModel> getAllMedicelRecord() {
		return imedicalservice.consultAllMedicalRecord();
	}
}
