package com.openclassrooms.safetynet.controller.dto.response;

import java.util.List;

public class ResidentAndFireStationsByListOfFireSations {
	int stationNumber;
	String address;
	private List<PersonsInfosAndMedical> personsInfosAndMedical;
	
	public ResidentAndFireStationsByListOfFireSations(int stationNumber, String address,
			List<PersonsInfosAndMedical> personsInfosAndMedical) {
		super();
		this.stationNumber = stationNumber;
		this.address = address;
		this.personsInfosAndMedical = personsInfosAndMedical;
	}
	
	public ResidentAndFireStationsByListOfFireSations() {
		super();
	}
	
	public int getStationNumber() {
		return stationNumber;
	}
	public void setStationNumber(int stationNumber) {
		this.stationNumber = stationNumber;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public List<PersonsInfosAndMedical> getPersonsInfosAndMedical() {
		return personsInfosAndMedical;
	}
	public void setPersonsInfosAndMedical(List<PersonsInfosAndMedical> personsInfosAndMedical) {
		this.personsInfosAndMedical = personsInfosAndMedical;
	}
}
