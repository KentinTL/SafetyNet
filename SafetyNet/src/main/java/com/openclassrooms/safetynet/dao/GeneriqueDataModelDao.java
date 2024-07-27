package com.openclassrooms.safetynet.dao;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.model.DataModel;


@Repository
public class GeneriqueDataModelDao {	
	private final ObjectMapper objectMapper = new ObjectMapper();
	private final String filePath = "src/main/resources/data.json";
	
	public DataModel fetchData() {
		try {
	        DataModel dataModel = objectMapper.readValue(new File(filePath), DataModel.class);
	        return dataModel;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new DataModel();
	}
	
	public void updateData(DataModel dataModel) {
		try {
            objectMapper.writeValue(new File(filePath), dataModel);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
}
