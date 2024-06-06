package com.openclassrooms.safetynet.model;

public class FireStationModel {
	private String address;
	private int station;
	
	public FireStationModel() {}
	
	public FireStationModel(String address, int station) {
		this.address = address;
		this.station = station;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getStation() {
		return station;
	}

	public void setStation(int station) {
		this.station = station;
	}
}
