package com.openclassrooms.safetynet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@PostMapping("/medicalRecord")
    public String createNewMedicalRecord(@RequestBody MedicalRecordModel medicalRecord) {
        try {
            imedicalservice.add(medicalRecord);
            return "Medical Record successfully created";
        } catch (Exception e) {
            return e.getMessage();
        }
	}
	
    @PutMapping("/medicalRecord/{firstName}/{lastName}")
    public String updateMedicalRecord(@PathVariable String firstName, @PathVariable String lastName, @RequestBody MedicalRecordModel medicalRecord) {
        try {
        	imedicalservice.update(firstName, lastName, medicalRecord);
            return "Medical Record successfully updated";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    
    @DeleteMapping("/medicalRecord/{firstName}/{lastName}")
    public String deleteMedicalRecord(@PathVariable String firstName, @PathVariable String lastName) {
        try {
        	imedicalservice.delete(firstName, lastName);
            return "Medical Record successfully deleted";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
