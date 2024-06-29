package com.openclassrooms.safetynet.utilities;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.openclassrooms.safetynet.controller.dto.response.PersonInfos;
import com.openclassrooms.safetynet.model.PersonModel;

public class Tools {
	
	public static PersonInfos mapToPersonInfos(PersonModel personModel) {
		PersonInfos result = new PersonInfos();
		result.setAddress(personModel.getAddress());
		result.setFirestName(personModel.getFirstName());
		result.setLastName(personModel.getLastName());
		result.setPhone(personModel.getPhone());
		return result;
	}
	
	public static int getAge(String birthDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        try {
            LocalDate birthDateLocal = LocalDate.parse(birthDate, formatter);
            LocalDate currentDate = LocalDate.now();
            return Period.between(birthDateLocal, currentDate).getYears();
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format: " + birthDate);
            return 0;
        }
	}
	
	public static boolean isAdult(int age) {
		if (age > 18) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isChild(int age) {
		if (age <= 18) {
			return true;
		} else {
			return false;
		}
	}
}
