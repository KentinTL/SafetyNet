package com.openclassrooms.safetynet.services;

import com.openclassrooms.safetynet.controller.dto.response.InfosChildByAdress;

public interface IChildUnderEighteen {
	public InfosChildByAdress getChildUnderEighteen(String address);

}
