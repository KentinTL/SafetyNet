package com.openclassrooms.safetynet.services;

import com.openclassrooms.safetynet.controller.dto.response.PersonPhoneNumber;

public interface IPhoneNumbersByStationNumber {
	public PersonPhoneNumber getAllPhoneNumberByFireStationNumber(int stationNumber);

}
