package com.openclassrooms.safetynet.controller.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ResidentAndFireStationByAddress {
	
	public ResidentAndFireStationByAddress(List<ResidentByAddress> residentByAddress, int fireStationNumber) {
		super();
		this.residentByAddress = residentByAddress;
		this.fireStationNumber = fireStationNumber;
	}
	public ResidentAndFireStationByAddress() {
		// TODO Auto-generated constructor stub
	}
	private List<ResidentByAddress> residentByAddress;
	private int fireStationNumber;
	
	public List<ResidentByAddress> getResidentByAddress() {
		return residentByAddress;
	}
	public void setResidentByAddress(List<ResidentByAddress> residentByAddress) {
		this.residentByAddress = residentByAddress;
	}
	public int getFireStationNumber() {
		return fireStationNumber;
	}
	public void setFireStationNumber(int fireStationNumber) {
		this.fireStationNumber = fireStationNumber;
	}
}
