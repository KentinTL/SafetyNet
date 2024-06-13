package com.openclassrooms.safetynet.controller.dto.response;

import java.util.List;

public class InfosPersonsByFireStation {
	private int adultCount;
	private int childCount;
	private List<PersonInfos> personInfos;
	
	public int getAdultCount() {
		return adultCount;
	}
	public void setAdultCount(int adultCount) {
		this.adultCount = adultCount;
	}
	public int getChildCount() {
		return childCount;
	}
	public void setChildCount(int childCount) {
		this.childCount = childCount;
	}
	public List<PersonInfos> getPersonInfos() {
		return personInfos;
	}
	public void setPersonInfos(List<PersonInfos> personInfos) {
		this.personInfos = personInfos;
	}
	
	
}
