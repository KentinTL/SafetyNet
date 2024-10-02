package com.openclassrooms.safetynet.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.safetynet.model.DataModel;

public class GeneriqueDataModelDaoTest {

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private GeneriqueDataModelDao generiqueDataModelDao;

    private final String filePath = "data.json";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        generiqueDataModelDao = new GeneriqueDataModelDao(objectMapper, filePath);
    }

    // ==============================
    // Tests pour la méthode fetchData
    // ==============================
    
    @Test
    public void testFetchData_ShouldReturnDataModel() throws IOException {
        DataModel expectedDataModel = new DataModel();
        when(objectMapper.readValue(new File(filePath), DataModel.class)).thenReturn(expectedDataModel);

        DataModel result = generiqueDataModelDao.fetchData();

        assertNotNull(result);
        assertEquals(expectedDataModel, result);
    }

    @Test
    public void testFetchData_ShouldHandleIOException() throws IOException {
        when(objectMapper.readValue(new File(filePath), DataModel.class)).thenThrow(new IOException("Test IOException"));

        DataModel result = generiqueDataModelDao.fetchData();

        assertNotNull(result);
        assertThat(result.getPersons()).isEmpty();
    }

    @Test
    public void testFetchData_ShouldHandleJsonParseException() throws IOException {
        JsonParseException exception = new JsonParseException(null, "Test JsonParseException");
        
        when(objectMapper.readValue(any(File.class), eq(DataModel.class))).thenThrow(exception);

        DataModel result = generiqueDataModelDao.fetchData();

        assertNotNull(result);
        assertThat(result.getPersons()).isEmpty();
    }


    @Test
    public void testFetchData_ShouldHandleJsonMappingException() throws IOException {
        JsonMappingException exception = new JsonMappingException(null, "Test JsonMappingException");
        
        when(objectMapper.readValue(any(File.class), eq(DataModel.class))).thenThrow(exception);

        DataModel result = generiqueDataModelDao.fetchData();

        assertNotNull(result);
        assertThat(result.getPersons()).isEmpty();
    }


    // ==============================
    // Tests pour la méthode updateData
    // ==============================
    @Test
    public void testUpdateData_ShouldWriteDataModel() throws IOException {
        // Arrange
        DataModel dataModel = new DataModel();  // Créez des données fictives

        // Act
        generiqueDataModelDao.updateData(dataModel);

        // Assert
        verify(objectMapper, times(1)).writeValue(new File(filePath), dataModel);  // Vérifiez que la méthode writeValue a été appelée
    }

    @Test
    public void testUpdateData_ShouldHandleIOException() throws IOException {
        // Arrange
        DataModel dataModel = new DataModel();
        
        // Simuler une exception IOException lors de l'écriture des données
        doThrow(new IOException("Test IOException")).when(objectMapper).writeValue(any(File.class), eq(dataModel));

        // Act
        generiqueDataModelDao.updateData(dataModel);

        // Assert
        // Vérifiez que la méthode writeValue a bien été appelée et qu'elle a lancé une IOException
        verify(objectMapper, times(1)).writeValue(new File(filePath), dataModel);
        // Aucune exception ne doit être propagée, car IOException est capturée et gérée
    }

}
