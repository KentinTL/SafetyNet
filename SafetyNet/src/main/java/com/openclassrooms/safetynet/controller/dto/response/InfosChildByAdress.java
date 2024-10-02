package com.openclassrooms.safetynet.controller.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class InfosChildByAdress {
	
	public InfosChildByAdress(List<PersonModelWithAge> childList, List<MinimalPersonModel> adultList) {
		super();
		setListOfChild(childList);
		setListOfAdult(adultList);
	}
	
	public InfosChildByAdress() {
		// TODO Auto-generated constructor stub
	}

	private List<PersonModelWithAge> listOfChild;
	private List<MinimalPersonModel> listOfAdult;
	
	public List<PersonModelWithAge> getListOfChild() {
		return listOfChild;
	}
	public void setListOfChild(List<PersonModelWithAge> listOfChild) {
		this.listOfChild = listOfChild;
	}
	public List<MinimalPersonModel> getListOfAdult() {
		return listOfAdult;
	}
	public void setListOfAdult(List<MinimalPersonModel> listOfAdult) {
		this.listOfAdult = listOfAdult;
	}
	

}
