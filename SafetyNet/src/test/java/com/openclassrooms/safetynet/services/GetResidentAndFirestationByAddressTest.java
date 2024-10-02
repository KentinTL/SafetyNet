package com.openclassrooms.safetynet.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.openclassrooms.safetynet.controller.dto.response.ResidentAndFireStationByAddress;
import com.openclassrooms.safetynet.controller.dto.response.ResidentByAddress;
import com.openclassrooms.safetynet.dao.IFireStationDao;
import com.openclassrooms.safetynet.dao.IMedicalRecordDao;
import com.openclassrooms.safetynet.dao.IPersonDao;
import com.openclassrooms.safetynet.model.FireStationModel;
import com.openclassrooms.safetynet.model.MedicalRecordModel;
import com.openclassrooms.safetynet.model.PersonModel;

public class GetResidentAndFirestationByAddressTest {
    @Mock
    private IPersonDao iPersonDao;

    @Mock
    private IMedicalRecordDao iMedicalRecordDao;

    @Mock
    private IFireStationDao iFireStationDao;

    @InjectMocks
    private GetResidentAndFireStationByAddress service;
	
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks before each test
        // Verification that mocks are properly initialized
        assertNotNull(iPersonDao);
        assertNotNull(iMedicalRecordDao);
        assertNotNull(iFireStationDao);
    }
    
	// ==========================
	// Tests pour la méthode getResidentAndFireStationByAddress()
	// ==========================
	@Test
	public void testGetResidentAndFireStationByAddress_ShouldReturnResidentsWithFireStation() {
	    // Arrange
	    PersonModel person = new PersonModel("John", "Doe", "123 Street", "City", "john@example.com", "1234567890", "75001");
	    when(iPersonDao.findByAddress("123 Street")).thenReturn(List.of(person));
	    
	    MedicalRecordModel medicalRecord = new MedicalRecordModel("John", "Doe", "01/01/1980", List.of("med1"), List.of("allergy1"));
	    when(iMedicalRecordDao.fetchMedicalRecordByFirstNameAndLastName("John", "Doe")).thenReturn(Optional.of(medicalRecord));
	    
	    FireStationModel fireStation = new FireStationModel("123 Street", 2);
	    when(iFireStationDao.fetchFireStationByAddress("123 Street")).thenReturn(Optional.of(fireStation));
	    
	    // Act
	    ResidentAndFireStationByAddress result = service.getResidentAndFireStationByAddress("123 Street");
	    
	    // Assert
	    assertNotNull(result);
	    assertEquals(1, result.getResidentByAddress().size()); // Utiliser getResidentByAddress() ici
	    assertEquals(2, result.getFireStationNumber()); // Utiliser getFireStationNumber()

	    ResidentByAddress resident = result.getResidentByAddress().get(0); // Récupérer le premier résident
	    assertEquals("Doe", resident.getName());
	    assertEquals(44, resident.getAge()); // assuming the year is 2024
	    assertEquals("1234567890", resident.getPhoneNumber());
	    assertEquals(List.of("med1"), resident.getMedications());
	    assertEquals(List.of("allergy1"), resident.getAllergies());
	}
	
	@Test
	public void testGetResidentAndFireStationByAddress_NoFireStationFound() {
	    // Arrange
	    PersonModel person = new PersonModel("John", "Doe", "123 Street", "City", "john@example.com", "1234567890", "75001");
	    when(iPersonDao.findByAddress("123 Street")).thenReturn(List.of(person));
	    
	    when(iFireStationDao.fetchFireStationByAddress("123 Street")).thenReturn(Optional.empty()); // Pas de caserne trouvée
	    
	    MedicalRecordModel medicalRecord = new MedicalRecordModel("John", "Doe", "01/01/1980", List.of("med1"), List.of("allergy1"));
	    when(iMedicalRecordDao.fetchMedicalRecordByFirstNameAndLastName("John", "Doe")).thenReturn(Optional.of(medicalRecord));
	    
	    // Act
	    ResidentAndFireStationByAddress result = service.getResidentAndFireStationByAddress("123 Street");
	    
	    // Assert
	    assertNotNull(result);
	    assertEquals(0, result.getFireStationNumber()); // Vérifie que le numéro de la caserne est 0
	    assertEquals(1, result.getResidentByAddress().size());
	}
	
	@Test
	public void testGetResidentAndFireStationByAddress_NoMedicalRecordFound() {
	    // Arrange
	    PersonModel person = new PersonModel("John", "Doe", "123 Street", "City", "john@example.com", "1234567890", "75001");
	    when(iPersonDao.findByAddress("123 Street")).thenReturn(List.of(person));
	    
	    FireStationModel fireStation = new FireStationModel("123 Street", 2);
	    when(iFireStationDao.fetchFireStationByAddress("123 Street")).thenReturn(Optional.of(fireStation));
	    
	    when(iMedicalRecordDao.fetchMedicalRecordByFirstNameAndLastName("John", "Doe")).thenReturn(Optional.empty()); // Pas de fiche médicale trouvée
	    
	    // Act
	    ResidentAndFireStationByAddress result = service.getResidentAndFireStationByAddress("123 Street");
	    
	    // Assert
	    assertNotNull(result);
	    assertEquals(2, result.getFireStationNumber()); // La caserne est bien trouvée
	    assertEquals(0, result.getResidentByAddress().size()); // Pas de résident ajouté sans fiche médicale
	}

	
	@Test
	public void testGetResidentAndFireStationByAddress_NoPersonFound() {
	    // Arrange
	    when(iPersonDao.findByAddress("123 Street")).thenReturn(List.of()); // Aucune personne trouvée
	    
	    FireStationModel fireStation = new FireStationModel("123 Street", 2);
	    when(iFireStationDao.fetchFireStationByAddress("123 Street")).thenReturn(Optional.of(fireStation));
	    
	    // Act
	    ResidentAndFireStationByAddress result = service.getResidentAndFireStationByAddress("123 Street");
	    
	    // Assert
	    assertNotNull(result);
	    assertEquals(2, result.getFireStationNumber()); // La caserne est bien trouvée
	    assertEquals(0, result.getResidentByAddress().size()); // Liste vide car pas de personnes à cette adresse
	}
	
	@Test
	public void testGetResidentAndFireStationByAddress_MultipleResidents() {
	    // Arrange
	    PersonModel person1 = new PersonModel("John", "Doe", "123 Street", "City", "john@example.com", "1234567890", "75001");
	    PersonModel person2 = new PersonModel("Jane", "Smith", "123 Street", "City", "jane@example.com", "0987654321", "75001");
	    when(iPersonDao.findByAddress("123 Street")).thenReturn(List.of(person1, person2));
	    
	    MedicalRecordModel medicalRecord1 = new MedicalRecordModel("John", "Doe", "01/01/1980", List.of("med1"), List.of("allergy1"));
	    MedicalRecordModel medicalRecord2 = new MedicalRecordModel("Jane", "Smith", "01/01/1990", List.of("med2"), List.of("allergy2"));
	    when(iMedicalRecordDao.fetchMedicalRecordByFirstNameAndLastName("John", "Doe")).thenReturn(Optional.of(medicalRecord1));
	    when(iMedicalRecordDao.fetchMedicalRecordByFirstNameAndLastName("Jane", "Smith")).thenReturn(Optional.of(medicalRecord2));
	    
	    FireStationModel fireStation = new FireStationModel("123 Street", 2);
	    when(iFireStationDao.fetchFireStationByAddress("123 Street")).thenReturn(Optional.of(fireStation));
	    
	    // Act
	    ResidentAndFireStationByAddress result = service.getResidentAndFireStationByAddress("123 Street");
	    
	    // Assert
	    assertNotNull(result);
	    assertEquals(2, result.getFireStationNumber()); // Numéro de caserne correct
	    assertEquals(2, result.getResidentByAddress().size()); // Deux résidents ajoutés
	}


}
