package com.openclassrooms.safetynet.dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.model.DataModel;


@Repository
public class GeneriqueDataModelDao {
	
	private final ObjectMapper objectMapper;
	private final String filePath;
	
	public GeneriqueDataModelDao(ObjectMapper objectMapper, @Value("${filePath}") String filePath) {
		super();
		this.objectMapper = objectMapper;
		this.filePath = filePath;
	}

	public DataModel fetchData() {
		try {
	        DataModel dataModel = objectMapper.readValue(new File(filePath), DataModel.class);
	        return dataModel;
		} catch (IOException e) {
			e.printStackTrace();
		}
	    // En cas d'exception, renvoyer un DataModel avec des listes initialisées
	    DataModel emptyDataModel = new DataModel();
	    emptyDataModel.setPersons(new ArrayList<>()); 
	    emptyDataModel.setFirestations(new ArrayList<>());
	    emptyDataModel.setMedicalrecords(new ArrayList<>());  
	    return emptyDataModel;
	}
	
	public void updateData(DataModel dataModel) {
		try {
            objectMapper.writeValue(new File(filePath), dataModel);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
}
