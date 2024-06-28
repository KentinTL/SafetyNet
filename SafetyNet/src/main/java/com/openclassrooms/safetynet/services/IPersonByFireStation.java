package com.openclassrooms.safetynet.services;

import com.openclassrooms.safetynet.controller.dto.response.InfosPersonsByFireStation;

public interface IPersonByFireStation {
	public InfosPersonsByFireStation getPersonByFirestation(int stationNumber);
}
