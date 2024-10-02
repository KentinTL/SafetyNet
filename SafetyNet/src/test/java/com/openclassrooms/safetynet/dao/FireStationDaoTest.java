package com.openclassrooms.safetynet.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.openclassrooms.safetynet.model.DataModel;
import com.openclassrooms.safetynet.model.FireStationModel;

public class FireStationDaoTest {

    @Mock
    private GeneriqueDataModelDao generiqueDataModelDao;

    @InjectMocks
    private FireStationDao fireStationDao;

    private DataModel dataModel;
    private FireStationModel fireStation1;
    private FireStationModel fireStation2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create mock FireStation models
        fireStation1 = new FireStationModel("123 Street", 1);
        fireStation2 = new FireStationModel("456 Avenue", 2);

        // Initialize DataModel with mutable fire stations
        dataModel = new DataModel();
        dataModel.setFirestations(new ArrayList<>(List.of(fireStation1, fireStation2)));  // Mutable list

        // Simuler fetchData() pour retourner le dataModel
        when(generiqueDataModelDao.fetchData()).thenReturn(dataModel);
    }


    // ===========================
    // Tests pour fetchAllFireStation()
    // ===========================
    @Test
    public void testFetchAllFireStation_ShouldReturnAllFireStations() {
        // Act
        List<FireStationModel> result = fireStationDao.fetchAllFireStation();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("123 Street", result.get(0).getAddress());
    }

    // ===========================
    // Tests pour fetchFireStationsByStationNumber()
    // ===========================
    @Test
    public void testFetchFireStationsByStationNumber_ShouldReturnMatchingFireStations() {
        // Act
        List<FireStationModel> result = fireStationDao.fetchFireStationsByStationNumber(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("123 Street", result.get(0).getAddress());
    }

    @Test
    public void testFetchFireStationsByStationNumber_ShouldReturnEmptyList() {
        // Act
        List<FireStationModel> result = fireStationDao.fetchFireStationsByStationNumber(3);

        // Assert
        assertTrue(result.isEmpty());
    }

    // ===========================
    // Tests pour fetchFireStationByAddress()
    // ===========================
    @Test
    public void testFetchFireStationByAddress_ShouldReturnMatchingFireStation() {
        // Act
        Optional<FireStationModel> result = fireStationDao.fetchFireStationByAddress("123 Street");

        // Assert
        assertTrue(result.isPresent());
        assertEquals("123 Street", result.get().getAddress());
    }

    @Test
    public void testFetchFireStationByAddress_ShouldReturnEmptyOptional() {
        // Act
        Optional<FireStationModel> result = fireStationDao.fetchFireStationByAddress("789 Unknown St");

        // Assert
        assertTrue(result.isEmpty());
    }

    // ===========================
    // Tests pour create()
    // ===========================
    @Test
    public void testCreate_ShouldAddNewFireStation() {
        // Arrange
        FireStationModel newFireStation = new FireStationModel("789 Boulevard", 3);

        // Act
        fireStationDao.create(newFireStation);

        // Assert
        assertEquals(3, dataModel.getFirestations().size());
        assertEquals("789 Boulevard", dataModel.getFirestations().get(2).getAddress());
        verify(generiqueDataModelDao, times(1)).updateData(dataModel);
    }

    // ===========================
    // Tests pour update()
    // ===========================
    @Test
    public void testUpdate_ShouldModifyExistingFireStation() {
        // Arrange
        FireStationModel updatedFireStation = new FireStationModel("123 Street", 5);

        // Act
        fireStationDao.update(updatedFireStation);

        // Assert
        assertEquals(5, dataModel.getFirestations().get(0).getStation());
        verify(generiqueDataModelDao, times(1)).updateData(dataModel);
    }

    @Test
    public void testUpdate_ShouldDoNothingIfFireStationNotFound() {
        // Arrange
        FireStationModel updatedFireStation = new FireStationModel("Nonexistent Address", 5);

        // Act
        fireStationDao.update(updatedFireStation);

        // Assert
        // Verify that updateData was not called
        verify(generiqueDataModelDao, never()).updateData(dataModel);
    }

    // ===========================
    // Tests pour delete()
    // ===========================
    @Test
    public void testDelete_ShouldRemoveFireStation() {
        // Act
        fireStationDao.delete(fireStation1);

        // Assert
        assertEquals(1, dataModel.getFirestations().size());
        assertFalse(dataModel.getFirestations().contains(fireStation1));
        verify(generiqueDataModelDao, times(1)).updateData(dataModel);
    }

    @Test
    public void testDelete_ShouldNotRemoveIfFireStationNotFound() {
        // Arrange
        FireStationModel nonExistentFireStation = new FireStationModel("Nonexistent Address", 5);

        // Act
        fireStationDao.delete(nonExistentFireStation);

        // Assert
        assertEquals(2, dataModel.getFirestations().size());  // Nothing should be removed
        verify(generiqueDataModelDao, times(1)).updateData(dataModel);
    }
}
