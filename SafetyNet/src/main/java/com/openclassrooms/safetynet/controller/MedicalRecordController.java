package com.openclassrooms.safetynet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<HttpStatus> createNewMedicalRecord(@RequestBody MedicalRecordModel medicalRecord) {
        imedicalservice.add(medicalRecord);
        return ResponseEntity.created(null).build();
	}
	
    @PutMapping("/medicalRecord/{firstName}/{lastName}")
    public ResponseEntity<HttpStatus> updateMedicalRecord(@PathVariable String firstName, @PathVariable String lastName, @RequestBody MedicalRecordModel medicalRecord) {
        imedicalservice.update(firstName, lastName, medicalRecord);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/medicalRecord/{firstName}/{lastName}")
    public ResponseEntity<HttpStatus> deleteMedicalRecord(@PathVariable String firstName, @PathVariable String lastName) {
        	imedicalservice.delete(firstName, lastName);
            return ResponseEntity.ok().build();
    }
}
