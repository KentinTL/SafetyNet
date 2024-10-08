package com.openclassrooms.safetynet.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class FireStationModel {
	@NotEmpty(message = "L'addresse de la station est nécessaire")private String address;
	@NotNull(message = "Le numéro de la station doit être renseigné")private int station;
	
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
