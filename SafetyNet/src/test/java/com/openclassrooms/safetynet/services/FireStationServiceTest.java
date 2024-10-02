package com.openclassrooms.safetynet.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.openclassrooms.safetynet.controller.dto.response.InfosPersonsByFireStation;
import com.openclassrooms.safetynet.controller.dto.response.PersonPhoneNumber;
import com.openclassrooms.safetynet.controller.dto.response.ResidentAndFireStationsByListOfFireSations;
import com.openclassrooms.safetynet.dao.IFireStationDao;
import com.openclassrooms.safetynet.dao.IMedicalRecordDao;
import com.openclassrooms.safetynet.dao.IPersonDao;
import com.openclassrooms.safetynet.exceptions.EntityAlreadyExistException;
import com.openclassrooms.safetynet.exceptions.EntityNotFoundException;
import com.openclassrooms.safetynet.model.FireStationModel;
import com.openclassrooms.safetynet.model.MedicalRecordModel;
import com.openclassrooms.safetynet.model.PersonModel;

public class FireStationServiceTest {

	@Mock
	private IPersonDao iPersonDao;

	@Mock
	private IFireStationDao iFireStationDao;

	@Mock
	private IMedicalRecordDao iMedicalRecordDao;

	@InjectMocks
	private FireStationService service;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	// ==========================
	// Tests pour la méthode add()
	// ==========================
	@Test
	public void testAdd_ShouldAddFireStation() {
		// Arrange
		FireStationModel fireStation = new FireStationModel("123 Street", 1);
		when(iFireStationDao.fetchFireStationByAddress("123 Street")).thenReturn(Optional.empty());

		// Act
		service.add(fireStation);

		// Assert
		verify(iFireStationDao, times(1)).create(fireStation);
	}

	@Test
	public void testAdd_ShouldThrowEntityAlreadyExistException() {
		// Arrange
		FireStationModel fireStation = new FireStationModel("123 Street", 1);
		when(iFireStationDao.fetchFireStationByAddress("123 Street")).thenReturn(Optional.of(fireStation));

		// Act & Assert
		assertThrows(EntityAlreadyExistException.class, () -> service.add(fireStation));
		verify(iFireStationDao, never()).create(any(FireStationModel.class));
	}

	// ==========================
	// Tests pour la méthode update()
	// ==========================
	@Test
	public void testUpdate_ShouldUpdateFireStation() {
		// Arrange
		FireStationModel fireStation = new FireStationModel("123 Street", 1);
		when(iFireStationDao.fetchFireStationByAddress("123 Street")).thenReturn(Optional.of(fireStation));

		// Act
		service.update("123 Street", fireStation);

		// Assert
		verify(iFireStationDao, times(1)).update(fireStation);
	}

	@Test
	public void testUpdate_ShouldThrowEntityNotFoundException() {
		// Arrange
		FireStationModel fireStation = new FireStationModel("123 Street", 1);
		when(iFireStationDao.fetchFireStationByAddress("123 Street")).thenReturn(Optional.empty());

		// Act & Assert
		assertThrows(EntityNotFoundException.class, () -> service.update("123 Street", fireStation));
		verify(iFireStationDao, never()).update(any(FireStationModel.class));
	}

	// ==========================
	// Tests pour la méthode deleteFireStation()
	// ==========================
	@Test
	public void testDelete_ShouldDeleteFireStation() {
		// Arrange
		FireStationModel fireStation = new FireStationModel("123 Street", 1);
		when(iFireStationDao.fetchFireStationByAddress("123 Street")).thenReturn(Optional.of(fireStation));

		// Act
		service.deleteFireStation("123 Street");

		// Assert
		verify(iFireStationDao, times(1)).delete(any(FireStationModel.class));
	}

	@Test
	public void testDelete_ShouldThrowEntityNotFoundException() {
		// Arrange
		when(iFireStationDao.fetchFireStationByAddress("123 Street")).thenReturn(Optional.empty());

		// Act & Assert
		assertThrows(EntityNotFoundException.class, () -> service.deleteFireStation("123 Street"));
		verify(iFireStationDao, never()).delete(any(FireStationModel.class));
	}

	// ==========================
	// Tests pour la méthode getPersonByFirestation()
	// ==========================
	@Test
	public void testGetPersonByFirestation_ShouldReturnPersonsInfos() {
		// Arrange
		FireStationModel fireStation = new FireStationModel("123 Street", 1);
		PersonModel person = new PersonModel("John", "Doe", "123 Street", "City", "email", "1234567890", "75000");
		MedicalRecordModel medicalRecord = new MedicalRecordModel("John", "Doe", "01/01/1980", List.of(), List.of());

		when(iFireStationDao.fetchFireStationsByStationNumber(1)).thenReturn(List.of(fireStation));
		when(iPersonDao.fecthAllPerson()).thenReturn(List.of(person));
		when(iMedicalRecordDao.fetchMedicalRecordByFirstNameAndLastName("John", "Doe"))
				.thenReturn(Optional.of(medicalRecord));

		// Act
		InfosPersonsByFireStation result = service.getPersonByFirestation(1);

		// Assert
		assertNotNull(result);
		assertEquals(1, result.getAdultCount());
		assertEquals(0, result.getChildCount());
		assertEquals(1, result.getPersonInfos().size());
	}

	// ==========================
	// Tests pour la méthode getAllPhoneNumberByFireStationNumber()
	// ==========================
	@Test
	public void testGetAllPhoneNumberByFireStationNumber_ShouldReturnPhoneNumbers() {
		// Arrange
		FireStationModel fireStation = new FireStationModel("123 Street", 1);
		PersonModel person1 = new PersonModel("John", "Doe", "123 Street", "City", "email1", "1234567890", "75000");
		PersonModel person2 = new PersonModel("Jane", "Smith", "123 Street", "City", "email2", "0987654321", "75000");

		when(iFireStationDao.fetchFireStationsByStationNumber(1)).thenReturn(List.of(fireStation));
		when(iPersonDao.fecthAllPerson()).thenReturn(List.of(person1, person2));

		// Act
		PersonPhoneNumber result = service.getAllPhoneNumberByFireStationNumber(1);

		// Assert
		assertNotNull(result);
		assertEquals(Set.of("1234567890", "0987654321"),
				result.getPhoneNumberList().stream().collect(Collectors.toSet()));
	}

	// ==========================
	// Tests pour la méthode getResidentAndFireStationsByListOfFireSations()
	// ==========================
	@Test
	public void testGetResidentAndFireStationsByListOfFireSations_ShouldReturnResidentsAndFireStations() {
		// Arrange
		FireStationModel fireStation1 = new FireStationModel("123 Street", 1);
		FireStationModel fireStation2 = new FireStationModel("456 Avenue", 2);
		PersonModel person = new PersonModel("John", "Doe", "123 Street", "City", "email", "1234567890", "75000");
		MedicalRecordModel medicalRecord = new MedicalRecordModel("John", "Doe", "01/01/1980", List.of(), List.of());

		when(iFireStationDao.fetchFireStationsByStationNumber(1)).thenReturn(List.of(fireStation1));
		when(iFireStationDao.fetchFireStationsByStationNumber(2)).thenReturn(List.of(fireStation2));
		when(iPersonDao.findByAddress("123 Street")).thenReturn(List.of(person));
		when(iMedicalRecordDao.fetchMedicalRecordByFirstNameAndLastName("John", "Doe"))
				.thenReturn(Optional.of(medicalRecord));

		// Act
		List<ResidentAndFireStationsByListOfFireSations> result = service
				.getResidentAndFireStationsByListOfFireSations(List.of(1, 2));

		// Assert
		assertNotNull(result);
		assertEquals(1, result.get(0).getPersonsInfosAndMedical().size());
		assertEquals("Doe", result.get(0).getPersonsInfosAndMedical().get(0).getName());
	}

	@Test
	public void testGetResidentAndFireStationsByListOfFireSations_ShouldHandleNoFireStations() {
		// Arrange - Cas où aucune station de pompiers n'est trouvée pour le numéro
		// donné
		when(iFireStationDao.fetchFireStationsByStationNumber(1)).thenReturn(List.of());
		when(iFireStationDao.fetchFireStationsByStationNumber(2)).thenReturn(List.of());

		// Act
		List<ResidentAndFireStationsByListOfFireSations> result = service
				.getResidentAndFireStationsByListOfFireSations(List.of(1, 2));

		// Assert - La liste résultante doit être vide car il n'y a pas de stations de
		// pompiers
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

	@Test
	public void testGetResidentAndFireStationsByListOfFireSations_ShouldHandleNoPersonsAtAddress() {
		// Arrange - Cas où il n'y a pas de personnes trouvées à l'adresse donnée
		FireStationModel fireStation = new FireStationModel("123 Street", 1);
		when(iFireStationDao.fetchFireStationsByStationNumber(1)).thenReturn(List.of(fireStation));

		// Pas de personnes trouvées à l'adresse
		when(iPersonDao.findByAddress("123 Street")).thenReturn(List.of());

		// Act
		List<ResidentAndFireStationsByListOfFireSations> result = service
				.getResidentAndFireStationsByListOfFireSations(List.of(1));

		// Assert - La liste des personnes pour cette adresse doit être vide
		assertNotNull(result);
		assertEquals(1, result.size());
		assertTrue(result.get(0).getPersonsInfosAndMedical().isEmpty());
	}

	@Test
	public void testGetResidentAndFireStationsByListOfFireSations_ShouldHandleNoMedicalRecord() {
		// Arrange - Cas où une personne est trouvée mais aucun dossier médical n'est
		// disponible
		FireStationModel fireStation = new FireStationModel("123 Street", 1);
		PersonModel person = new PersonModel("John", "Doe", "123 Street", "City", "email", "1234567890", "75000");

		when(iFireStationDao.fetchFireStationsByStationNumber(1)).thenReturn(List.of(fireStation));
		when(iPersonDao.findByAddress("123 Street")).thenReturn(List.of(person));

		// Aucun dossier médical trouvé pour cette personne
		when(iMedicalRecordDao.fetchMedicalRecordByFirstNameAndLastName("John", "Doe")).thenReturn(Optional.empty());

		// Act
		List<ResidentAndFireStationsByListOfFireSations> result = service
				.getResidentAndFireStationsByListOfFireSations(List.of(1));

		// Assert - La personne ne doit pas apparaître dans les résultats car il n'y a
		// pas de dossier médical
		assertNotNull(result);
		assertEquals(1, result.size());
		assertTrue(result.get(0).getPersonsInfosAndMedical().isEmpty());
	}
}
