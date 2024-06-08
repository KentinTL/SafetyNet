package com.openclassrooms.safetynet.dao;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.model.DataModel;


@Repository
public class GeneriqueDataModelDao {	
	public DataModel fetchData() {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			InputStream inputStream = new ClassPathResource("data.json").getInputStream();
	        DataModel dataModel = objectMapper.readValue(inputStream, DataModel.class);
	        return dataModel;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new DataModel();
	}
	
	public void updateData(DataModel dataModel) {
		//TODO Ecrire dans le JSON
	}
	
}
