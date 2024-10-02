package com.openclassrooms.safetynet.dao;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.openclassrooms.safetynet.model.DataModel;
import com.openclassrooms.safetynet.model.PersonModel;


public class PersonDaoTest {

    @Mock
    private GeneriqueDataModelDao generiqueDataModelDao;

    @InjectMocks
    private PersonDao personDao;

    private DataModel dataModel;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        dataModel = new DataModel();
        dataModel.setPersons(new ArrayList<>());
    }

    @Test
    public void testFindByEmail_ShouldReturnPerson() {
        // Arrange
        PersonModel person = new PersonModel("John", "Doe", "123 Street", "City", "12345", "555-1234", "john.doe@example.com");
        dataModel.getPersons().add(person);
        when(generiqueDataModelDao.fetchData()).thenReturn(dataModel);

        // Act
        Optional<PersonModel> result = personDao.findByEmail("john.doe@example.com");

        // Assert
        assertTrue(result.isPresent());
        assertEquals(person, result.get());
    }

    @Test
    public void testFindByAddress_ShouldReturnPersons() {
        // Arrange
        PersonModel person1 = new PersonModel("John", "Doe", "123 Street", "City", "12345", "555-1234", "john.doe@example.com");
        PersonModel person2 = new PersonModel("Jane", "Doe", "123 Street", "City", "12345", "555-1233", "jane.doe@example.com");
        dataModel.getPersons().add(person1);
        dataModel.getPersons().add(person2);
        when(generiqueDataModelDao.fetchData()).thenReturn(dataModel);

        // Act
        List<PersonModel> result = personDao.findByAddress("123 Street");

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(person1));
        assertTrue(result.contains(person2));
    }

    @Test
    public void testFindPersonByCity_ShouldReturnPersons() {
        // Arrange
        PersonModel person1 = new PersonModel("John", "Doe", "123 Street", "City", "12345", "555-1234", "john.doe@example.com");
        PersonModel person2 = new PersonModel("Jane", "Doe", "123 Street", "City", "12345", "555-1233", "jane.doe@example.com");
        dataModel.getPersons().add(person1);
        dataModel.getPersons().add(person2);
        when(generiqueDataModelDao.fetchData()).thenReturn(dataModel);

        // Act
        List<PersonModel> result = personDao.findPersonByCity("City");

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(person1));
        assertTrue(result.contains(person2));
    }

    @Test
    public void testFindPersonsByLastName_ShouldReturnPersons() {
        // Arrange
        PersonModel person1 = new PersonModel("John", "Doe", "123 Street", "City", "12345", "555-1234", "john.doe@example.com");
        PersonModel person2 = new PersonModel("Jane", "Doe", "123 Street", "City", "12345", "555-1233", "jane.doe@example.com");
        dataModel.getPersons().add(person1);
        dataModel.getPersons().add(person2);
        when(generiqueDataModelDao.fetchData()).thenReturn(dataModel);

        // Act
        List<PersonModel> result = personDao.findPersonsByLastName("Doe");

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(person1));
        assertTrue(result.contains(person2));
    }

    @Test
    public void testFetchAllPerson_ShouldReturnAllPersons() {
        // Arrange
        PersonModel person1 = new PersonModel("John", "Doe", "123 Street", "City", "12345", "555-1234", "john.doe@example.com");
        dataModel.getPersons().add(person1);
        when(generiqueDataModelDao.fetchData()).thenReturn(dataModel);

        // Act
        List<PersonModel> result = personDao.fecthAllPerson();

        // Assert
        assertEquals(1, result.size());
        assertTrue(result.contains(person1));
    }

    @Test
    public void testCreate_ShouldAddNewPerson() {
        // Arrange
        PersonModel newPerson = new PersonModel("John", "Doe", "123 Street", "City", "12345", "555-1234", "john.doe@example.com");
        when(generiqueDataModelDao.fetchData()).thenReturn(dataModel);

        // Act
        personDao.create(newPerson);

        // Assert
        assertTrue(dataModel.getPersons().contains(newPerson));
        verify(generiqueDataModelDao, times(1)).updateData(dataModel);
    }

    @Test
    public void testUpdate_ShouldModifyExistingPerson() {
        // Arrange
        PersonModel existingPerson = new PersonModel("John", "Doe", "123 Street", "City", "12345", "555-1234", "john.doe@example.com");
        PersonModel updatedPerson = new PersonModel("John", "Doe", "123 Street", "City", "12345", "555-1234", "john.doe.updated@example.com");
        dataModel.getPersons().add(existingPerson);
        when(generiqueDataModelDao.fetchData()).thenReturn(dataModel);

        // Act
        personDao.update(updatedPerson);

        // Assert
        assertEquals("john.doe.updated@example.com", dataModel.getPersons().get(0).getEmail());
        verify(generiqueDataModelDao, times(1)).updateData(dataModel);
    }

    @Test
    public void testDelete_ShouldRemoveExistingPerson() {
        // Arrange
        PersonModel existingPerson = new PersonModel("John", "Doe", "123 Street", "City", "12345", "555-1234", "john.doe@example.com");
        dataModel.getPersons().add(existingPerson);
        when(generiqueDataModelDao.fetchData()).thenReturn(dataModel);

        // Act
        personDao.delete(existingPerson);

        // Assert
        assertFalse(dataModel.getPersons().contains(existingPerson));
        verify(generiqueDataModelDao, times(1)).updateData(dataModel);
    }

    @Test
    public void testFindByFirstNameAndLastName_ShouldReturnPerson() {
        // Arrange
        PersonModel person = new PersonModel("John", "Doe", "123 Street", "City", "12345", "555-1234", "john.doe@example.com");
        dataModel.getPersons().add(person);
        when(generiqueDataModelDao.fetchData()).thenReturn(dataModel);

        // Act
        Optional<PersonModel> result = personDao.findByFirstNameAndLastName("John", "Doe");

        // Assert
        assertTrue(result.isPresent());
        assertEquals(person, result.get());
    }
}
