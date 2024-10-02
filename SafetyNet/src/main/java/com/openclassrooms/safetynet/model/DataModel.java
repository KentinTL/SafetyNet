package com.openclassrooms.safetynet.model;

import java.util.ArrayList;
import java.util.List;

public class DataModel {
	private List<PersonModel> persons;
	private List<FireStationModel> firestations;
	private List<MedicalRecordModel> medicalrecords;

	public List<PersonModel> getPersons() {
		return persons;
	}
	public void setPersons(List<PersonModel> persons) {
		this.persons = new ArrayList<>(persons);
	}
	
	public List<FireStationModel> getFirestations() {
		return firestations;
	}
	public void setFirestations(List<FireStationModel> firestations) {
		this.firestations = new ArrayList<>(firestations);
	}
	
	public List<MedicalRecordModel> getMedicalrecords() {
		return medicalrecords;
	}
	public void setMedicalrecords(List<MedicalRecordModel> medicalrecords) {
		this.medicalrecords = new ArrayList<>(medicalrecords);
	}
}
