package com.openclassrooms.safetynet.services;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.openclassrooms.safetynet.dao.IMedicalRecordDao;
import com.openclassrooms.safetynet.dao.IPersonDao;
import com.openclassrooms.safetynet.exceptions.EntityAlreadyExistException;
import com.openclassrooms.safetynet.exceptions.EntityNotFoundException;
import com.openclassrooms.safetynet.model.MedicalRecordModel;
import com.openclassrooms.safetynet.model.PersonModel;
import com.openclassrooms.safetynet.controller.dto.response.*;

public class PersonServiceTest {

    @Mock
    private IPersonDao iPersonDao;

    @Mock
    private IMedicalRecordDao iMedicalRecordDao;

    @InjectMocks
    private PersonService personService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

	// ==========================
	// Tests pour la méthode add()
	// ==========================
    @Test
    public void testAddPerson_WhenPersonDoesNotExist_ShouldAddPerson() {
        PersonModel person = new PersonModel("John", "Doe", "123 Street", "City", "75001", "1234567890", "john@example.com");
        when(iPersonDao.findByFirstNameAndLastName("John", "Doe")).thenReturn(Optional.empty());

        personService.add(person);

        verify(iPersonDao, times(1)).create(person);
        assertEquals("John", person.getFirstName());
        assertEquals("Doe", person.getLastName());
        assertEquals("1234567890", person.getPhone());
        assertEquals("75001", person.getZip());
        assertEquals("john@example.com", person.getEmail());
    }

    @Test
    public void testAddPerson_WhenPersonExists_ShouldThrowException() {
        PersonModel person = new PersonModel("John", "Doe", "123 Street", "City", "john@example.com", "1234567890", "75001");
        when(iPersonDao.findByFirstNameAndLastName("John", "Doe")).thenReturn(Optional.of(person));

        assertThrows(EntityAlreadyExistException.class, () -> personService.add(person));
    }

	// ==========================
	// Tests pour la méthode update()
	// ==========================
    @Test
    public void testUpdatePerson_WhenPersonExists_ShouldUpdatePerson() {
        PersonModel person = new PersonModel("John", "Doe", "123 Street", "City", "75001", "1234567890", "john@example.com");
        when(iPersonDao.findByFirstNameAndLastName("John", "Doe")).thenReturn(Optional.of(person));

        personService.update("John", "Doe", person);

        verify(iPersonDao, times(1)).update(person);
        assertEquals("1234567890", person.getPhone());
        assertEquals("75001", person.getZip());
    }

    @Test
    public void testUpdatePerson_WhenPersonDoesNotExist_ShouldThrowException() {
        PersonModel person = new PersonModel("John", "Doe", "123 Street", "City", "john@example.com", "1234567890", "75001");
        when(iPersonDao.findByFirstNameAndLastName("John", "Doe")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> personService.update("John", "Doe", person));
    }

	// ==========================
	// Tests pour la méthode delete()
	// ==========================
    @Test
    public void testDeletePerson_WhenPersonExists_ShouldDeletePerson() {
        PersonModel person = new PersonModel("John", "Doe", "123 Street", "City", "john@example.com", "1234567890", "75001");
        when(iPersonDao.findByFirstNameAndLastName("John", "Doe")).thenReturn(Optional.of(person));

        personService.delete("John", "Doe");

        verify(iPersonDao, times(1)).delete(any(PersonModel.class));
    }

    @Test
    public void testDeletePerson_WhenPersonDoesNotExist_ShouldThrowException() {
        when(iPersonDao.findByFirstNameAndLastName("John", "Doe")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> personService.delete("John", "Doe"));
    }

	// ==========================
	// Tests pour la méthode consultAllPersons()
	// ==========================
    @Test
    public void testConsultAllPersons_ShouldReturnAllPersons() {
        List<PersonModel> persons = List.of(new PersonModel("John", "Doe", "123 Street", "City", "75001",  "1234567890", "john@example.com"));
        when(iPersonDao.fecthAllPerson()).thenReturn(persons);

        List<PersonModel> result = personService.consultAllPersons();

        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("1234567890", result.get(0).getPhone());
        assertEquals("75001", result.get(0).getZip());
    }

	// ==========================
	// Tests pour la méthode getChildUnderEighteen()
	// ==========================
    @Test
    public void testGetChildUnderEighteen_ShouldReturnChildrenAndAdults() {
        PersonModel child = new PersonModel("Jane", "Doe", "123 Street", "City", "jane@example.com", "0987654321", "75001");
        PersonModel adult = new PersonModel("John", "Doe", "123 Street", "City", "john@example.com", "1234567890", "75001");
        List<PersonModel> persons = List.of(child, adult);
        when(iPersonDao.findByAddress("123 Street")).thenReturn(persons);
        when(iMedicalRecordDao.fetchMedicalRecordByFirstNameAndLastName(anyString(), anyString()))
            .thenReturn(Optional.of(new MedicalRecordModel("Jane", "Doe", "01/01/2010", List.of(), List.of())),
                        Optional.of(new MedicalRecordModel("John", "Doe", "01/01/1980", List.of(), List.of())));
        
        InfosChildByAdress result = personService.getChildUnderEighteen("123 Street");

        assertNotNull(result);
        assertEquals(1, result.getListOfChild().size());
        assertEquals(14, result.getListOfChild().getFirst().getAge());
        assertEquals(1, result.getListOfAdult().size());
        assertEquals("John", result.getListOfAdult().get(0).getFirstName());
    }

    @Test
    public void testGetChildUnderEighteen_ShouldReturnNull_WhenNoPersonsFound() {
        when(iPersonDao.findByAddress("some address")).thenReturn(List.of());

        InfosChildByAdress result = personService.getChildUnderEighteen("some address");

        assertNull(result);
    }

    @Test
    public void testGetChildUnderEighteen_ShouldIgnorePersonsWithoutMedicalRecord() {
        PersonModel personWithoutMedicalRecord = new PersonModel("John", "Doe", "123 Street", "City", "john@example.com", "1234567890", "75001");
        when(iPersonDao.findByAddress("123 Street")).thenReturn(List.of(personWithoutMedicalRecord));
        when(iMedicalRecordDao.fetchMedicalRecordByFirstNameAndLastName("John", "Doe")).thenReturn(Optional.empty());

        InfosChildByAdress result = personService.getChildUnderEighteen("123 Street");

        assertNull(result);  // No medical record means the person is ignored
    }

    @Test
    public void testGetChildUnderEighteen_ShouldReturnOnlyChildren_WhenNoAdults() {
        PersonModel child = new PersonModel("Jane", "Doe", "123 Street", "City", "jane@example.com", "0987654321", "75001");
        when(iPersonDao.findByAddress("123 Street")).thenReturn(List.of(child));
        when(iMedicalRecordDao.fetchMedicalRecordByFirstNameAndLastName("Jane", "Doe"))
            .thenReturn(Optional.of(new MedicalRecordModel("Jane", "Doe", "01/01/2010", List.of(), List.of())));

        InfosChildByAdress result = personService.getChildUnderEighteen("123 Street");

        assertNotNull(result);
        assertEquals(1, result.getListOfChild().size());  // Only children found
        assertEquals(0, result.getListOfAdult().size());
    }

    
	// ==========================
	// Tests pour la méthode getMailsByCity()
	// ==========================y
    @Test
    public void testGetMailsByCity_ShouldReturnMailsByCity() {
        PersonModel person = new PersonModel("John", "Doe", "123 Street", "City", "75001",  "1234567890", "john@example.com");
        List<PersonModel> persons = List.of(person);
        when(iPersonDao.findPersonByCity("City")).thenReturn(persons);

        InfosMailsByCity result = personService.getMailsByCity("City");

        assertEquals(1, result.getMails().size());
        assertEquals("john@example.com", result.getMails().get(0).getMail());
        assertEquals("1234567890", person.getPhone());
    }

	// ==========================
	// Tests pour la méthode getInfosPersonsByLastname()
	// ==========================
    @Test
    public void testGetInfosPersonsByLastName_ShouldReturnInfosPersons() {
        PersonModel person = new PersonModel("John", "Doe", "123 Street", "City", "75001",  "1234567890", "john@example.com");
        List<PersonModel> persons = List.of(person);
        when(iPersonDao.findPersonsByLastName("Doe")).thenReturn(persons);
        when(iMedicalRecordDao.fetchMedicalRecordByFirstNameAndLastName("John", "Doe"))
            .thenReturn(Optional.of(new MedicalRecordModel("John", "Doe", "01/01/1980", List.of(), List.of())));

        InfosPersonByLastName result = personService.getInfosPersonsByLastName("Doe");

        assertEquals(1, result.getPersonsInfosAndMedical().size());
        assertEquals("Doe", result.getPersonsInfosAndMedical().get(0).getName());
        assertEquals("john@example.com", result.getPersonsInfosAndMedical().getFirst().getMail());
        assertThat(result.getPersonsInfosAndMedical().getFirst().getMedications()).isEmpty();
        assertThat(result.getPersonsInfosAndMedical().getFirst().getAllergies()).isEmpty();
    }
    
    @Test
    public void testGetInfosPersonsByLastName_ShouldIgnorePersonWithoutMedicalRecord() {
        PersonModel personWithoutMedicalRecord = new PersonModel("Jane", "Doe", "123 Street", "City", "75001", "1234567890", "jane@example.com");
        List<PersonModel> persons = List.of(personWithoutMedicalRecord);

        when(iPersonDao.findPersonsByLastName("Doe")).thenReturn(persons);
        when(iMedicalRecordDao.fetchMedicalRecordByFirstNameAndLastName("Jane", "Doe")).thenReturn(Optional.empty());

        InfosPersonByLastName result = personService.getInfosPersonsByLastName("Doe");

        assertEquals(0, result.getPersonsInfosAndMedical().size()); 
    }

}