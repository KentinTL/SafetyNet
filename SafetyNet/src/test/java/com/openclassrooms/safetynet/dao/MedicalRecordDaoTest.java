package com.openclassrooms.safetynet.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.openclassrooms.safetynet.model.DataModel;
import com.openclassrooms.safetynet.model.MedicalRecordModel;

public class MedicalRecordDaoTest {

    @Mock
    private GeneriqueDataModelDao generiqueDataModelDao;

    @InjectMocks
    private MedicalRecordDao medicalRecordDao;

    private DataModel dataModel;
    private MedicalRecordModel medicalRecord1;
    private MedicalRecordModel medicalRecord2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup test data
        medicalRecord1 = new MedicalRecordModel("John", "Doe", "01/01/1980", List.of("med1"), List.of("allergy1"));
        medicalRecord2 = new MedicalRecordModel("Jane", "Doe", "02/02/1990", List.of("med2"), List.of("allergy2"));

        dataModel = new DataModel();
        dataModel.setMedicalrecords(List.of(medicalRecord1, medicalRecord2));

        // Mock fetchData() to return dataModel
        when(generiqueDataModelDao.fetchData()).thenReturn(dataModel);
    }

    // ==============================
    // Tests for fetchAllMedicalRecord
    // ==============================
    @Test
    public void testFetchAllMedicalRecord_ShouldReturnAllMedicalRecords() {
        // Act
        List<MedicalRecordModel> result = medicalRecordDao.fetchAllMedicalRecord();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(medicalRecord1));
        assertTrue(result.contains(medicalRecord2));
    }

    // ==========================================
    // Tests for fetchMedicalRecordByFirstNameAndLastName
    // ==========================================
    @Test
    public void testFetchMedicalRecordByFirstNameAndLastName_ShouldReturnMedicalRecord() {
        // Act
        Optional<MedicalRecordModel> result = medicalRecordDao.fetchMedicalRecordByFirstNameAndLastName("John", "Doe");

        // Assert
        assertTrue(result.isPresent());
        assertEquals(medicalRecord1, result.get());
    }

    @Test
    public void testFetchMedicalRecordByFirstNameAndLastName_ShouldReturnEmptyOptional() {
        // Act
        Optional<MedicalRecordModel> result = medicalRecordDao.fetchMedicalRecordByFirstNameAndLastName("Unknown", "Person");

        // Assert
        assertFalse(result.isPresent());
    }

    // ==============================
    // Tests for create
    // ==============================
    @Test
    public void testCreate_ShouldAddNewMedicalRecord() {
        // Arrange
        MedicalRecordModel newMedicalRecord = new MedicalRecordModel("Alice", "Smith", "03/03/2000", List.of("med3"), List.of("allergy3"));

        // Act
        medicalRecordDao.create(newMedicalRecord);

        // Assert
        assertTrue(dataModel.getMedicalrecords().contains(newMedicalRecord));
        verify(generiqueDataModelDao, times(1)).updateData(dataModel); // Verify that updateData was called
    }

    // ==============================
    // Tests for update
    // ==============================
    @Test
    public void testUpdate_ShouldModifyExistingMedicalRecord() {
        // Arrange
        MedicalRecordModel updatedMedicalRecord = new MedicalRecordModel("John", "Doe", "01/01/1980", List.of("med1_updated"), List.of("allergy1_updated"));

        // Act
        medicalRecordDao.update(updatedMedicalRecord);

        // Assert
        assertEquals("med1_updated", dataModel.getMedicalrecords().get(0).getMedications().get(0)); // Ensure the medical record was updated
        verify(generiqueDataModelDao, times(1)).updateData(dataModel);
    }

    @Test
    public void testUpdate_ShouldNotModifyIfRecordDoesNotExist() {
        // Arrange
        MedicalRecordModel nonExistentRecord = new MedicalRecordModel("Non", "Existent", "04/04/2000", List.of("med4"), List.of("allergy4"));

        // Act
        medicalRecordDao.update(nonExistentRecord);

        // Assert
        assertFalse(dataModel.getMedicalrecords().contains(nonExistentRecord));
        verify(generiqueDataModelDao, times(0)).updateData(dataModel); // No update should be called if record doesn't exist
    }

    // ==============================
    // Tests for delete
    // ==============================
    @Test
    public void testDelete_ShouldRemoveMedicalRecord() {
        // Act
        medicalRecordDao.delete(medicalRecord1);

        // Assert
        assertFalse(dataModel.getMedicalrecords().contains(medicalRecord1)); // Ensure it was deleted
        verify(generiqueDataModelDao, times(1)).updateData(dataModel); // Verify that updateData was called
    }

    @Test
    public void testDelete_ShouldNotRemoveIfRecordDoesNotExist() {
        // Arrange
        MedicalRecordModel nonExistentRecord = new MedicalRecordModel("Non", "Existent", "04/04/2000", List.of("med4"), List.of("allergy4"));

        // Act
        medicalRecordDao.delete(nonExistentRecord);

        // Assert
        assertEquals(2, dataModel.getMedicalrecords().size()); // No record should be removed
        verify(generiqueDataModelDao, times(1)).updateData(dataModel); // updateData is still called, as per the current implementation
    }
}
