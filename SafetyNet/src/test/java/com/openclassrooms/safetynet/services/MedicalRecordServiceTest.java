package com.openclassrooms.safetynet.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.openclassrooms.safetynet.dao.IMedicalRecordDao;
import com.openclassrooms.safetynet.dao.IPersonDao;
import com.openclassrooms.safetynet.exceptions.EntityAlreadyExistException;
import com.openclassrooms.safetynet.exceptions.EntityNotFoundException;
import com.openclassrooms.safetynet.model.MedicalRecordModel;
import com.openclassrooms.safetynet.model.PersonModel;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordServiceTest {
	@Mock
	private IMedicalRecordDao iMedicalRecordDao;
	@Mock
	private IPersonDao iPersonDao;
	@InjectMocks
	private MedicalRecordService medicalRecordService;
	
	// ==========================
	// Tests pour la méthode updtate()
	// ==========================
	@Test
	@DisplayName("Should throw 'EntityNotFoundExcepion' when a Medical record with firstName and Lastname is not found")
	void update_should_throw_EntityNotFoundExcepion_when_Record_with_firstName_and_lastName_is_not_found() {
		
		when(iMedicalRecordDao.fetchMedicalRecordByFirstNameAndLastName(anyString(), anyString())).thenReturn(Optional.empty());
		try {
			medicalRecordService.update("youpi", "piou", new MedicalRecordModel());
			verify(iMedicalRecordDao, times(1)).fetchMedicalRecordByFirstNameAndLastName("youpi", "piou");
		} catch (Exception e) {
			assertThat(e instanceof EntityNotFoundException);
			assertEquals(e.getLocalizedMessage(), "No Medical Record founded");
			verifyNoMoreInteractions(iMedicalRecordDao);
		}
	}
	
	@Test
	@DisplayName("Should update a MedicalRecord when a Medical record with firstName and Lastname is found")
	void update_should_update_MedicalRecord_when_MedicalRecord_with_firstName_and_lastName_is_found() {
		
		MedicalRecordModel medicalRecordModel = new MedicalRecordModel();
		medicalRecordModel.setAllergies(null);
		medicalRecordModel.setBirthdate(null);
		
		when(iMedicalRecordDao.fetchMedicalRecordByFirstNameAndLastName(anyString(), anyString())).thenReturn(Optional.of(medicalRecordModel));

		medicalRecordService.update("youpi", "piou", new MedicalRecordModel());
		verify(iMedicalRecordDao, times(1)).fetchMedicalRecordByFirstNameAndLastName("youpi", "piou");

		verify(iMedicalRecordDao).update(any(MedicalRecordModel.class));

	}
	
	// ==========================
	// Tests pour la méthode add()
	// ==========================
    @Test
    @DisplayName("Should add MedicalRecord when person exists")
    void add_should_add_MedicalRecord_when_person_exists() {
        when(iPersonDao.findByFirstNameAndLastName(anyString(), anyString())).thenReturn(Optional.of(new PersonModel()));

        MedicalRecordModel medicalRecord = new MedicalRecordModel();
        medicalRecord.setFirstName("John");
        medicalRecord.setLastName("Doe");
        
        medicalRecordService.add(medicalRecord);
        
        verify(iPersonDao).findByFirstNameAndLastName("John", "Doe");
        verify(iMedicalRecordDao).create(medicalRecord);
    }

    @Test
    @DisplayName("Should throw 'EntityAlreadyExistException' when person does not exist")
    void add_should_throw_EntityAlreadyExistException_when_person_does_not_exist() {
        when(iPersonDao.findByFirstNameAndLastName(anyString(), anyString())).thenReturn(Optional.empty());

        MedicalRecordModel medicalRecord = new MedicalRecordModel();
        medicalRecord.setFirstName("John");
        medicalRecord.setLastName("Doe");

        try {
            medicalRecordService.add(medicalRecord);
        } catch (Exception e) {
            assertThat(e instanceof EntityAlreadyExistException);
            assertEquals(e.getLocalizedMessage(), "You can't create a MedicalRecord for a person who doesn't exist");
        }

        verify(iPersonDao).findByFirstNameAndLastName("John", "Doe");
        verify(iMedicalRecordDao, times(0)).create(any(MedicalRecordModel.class));
    }

	// ==========================
	// Tests pour la méthode delete()
	// ==========================
    @Test
    @DisplayName("Should throw 'EntityNotFoundException' when MedicalRecord does not exist")
    void delete_should_throw_EntityNotFoundException_when_MedicalRecord_does_not_exist() {
        when(iMedicalRecordDao.fetchMedicalRecordByFirstNameAndLastName(anyString(), anyString())).thenReturn(Optional.empty());

        try {
            medicalRecordService.delete("John", "Doe");
        } catch (Exception e) {
            assertThat(e instanceof EntityNotFoundException);
            assertEquals(e.getLocalizedMessage(), "Medical Record does not exist");
        }

        verify(iMedicalRecordDao, times(1)).fetchMedicalRecordByFirstNameAndLastName("John", "Doe");
        verifyNoMoreInteractions(iMedicalRecordDao);
    }

    @Test
    @DisplayName("Should delete MedicalRecord when it exists")
    void delete_should_delete_MedicalRecord_when_it_exists() {
        MedicalRecordModel medicalRecord = new MedicalRecordModel();
        when(iMedicalRecordDao.fetchMedicalRecordByFirstNameAndLastName(anyString(), anyString())).thenReturn(Optional.of(medicalRecord));

        medicalRecordService.delete("John", "Doe");

        verify(iMedicalRecordDao, times(1)).fetchMedicalRecordByFirstNameAndLastName("John", "Doe");
        verify(iMedicalRecordDao, times(1)).delete(any(MedicalRecordModel.class));
    }
}

