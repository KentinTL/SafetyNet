package com.openclassrooms.safetynet.services;

import com.openclassrooms.safetynet.controller.dto.response.ResidentAndFireStationByAddress;

public interface IGetResidentAndFirestationByAddress {
	public ResidentAndFireStationByAddress getResidentAndFireStationByAddress(String address);
}
