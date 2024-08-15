package com.openclassrooms.safetynet.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.controller.dto.response.InfosPersonsByFireStation;
import com.openclassrooms.safetynet.controller.dto.response.PersonPhoneNumber;
import com.openclassrooms.safetynet.controller.dto.response.PersonsInfosAndMedical;
import com.openclassrooms.safetynet.controller.dto.response.ResidentAndFireStationsByListOfFireSations;
import com.openclassrooms.safetynet.dao.IFireStationDao;
import com.openclassrooms.safetynet.dao.IMedicalRecordDao;
import com.openclassrooms.safetynet.dao.IPersonDao;
import com.openclassrooms.safetynet.exceptions.EntityAlreadyExistException;
import com.openclassrooms.safetynet.exceptions.EntityNotFoundException;
import com.openclassrooms.safetynet.model.FireStationModel;
import com.openclassrooms.safetynet.model.PersonModel;
import com.openclassrooms.safetynet.utilities.Tools;

@Service
public class FireStationService implements IFireStationService{
	
	@Autowired
	private IPersonDao iPersonDao;
	
	@Autowired
	private IFireStationDao iFireStationDao;
	
	@Autowired
	private IMedicalRecordDao iMedicalRecordDao;

	
    @Override
    public void add(FireStationModel fireStationModel) {
        Optional<FireStationModel> stationFound = iFireStationDao.fetchFireStationByAddress(fireStationModel.getAddress());
        if (stationFound.isPresent()) {
    		throw new EntityAlreadyExistException("This fire station already exist into database");
        }
        iFireStationDao.create(fireStationModel);
    }

    @Override
    public void update(String address, FireStationModel fireStationModel) {
        Optional<FireStationModel> stationFound = iFireStationDao.fetchFireStationByAddress(fireStationModel.getAddress());
        if(stationFound.isEmpty()) {
    		throw new EntityNotFoundException("No fire station founded");
        }
    	iFireStationDao.update(fireStationModel);
    }
    
    @Override
    public void deleteFireStation(String address) {
    	var stationFound = iFireStationDao.fetchFireStationByAddress(address);
    	if(stationFound.isEmpty()) {
    		throw new EntityNotFoundException("No fire station founded");
    	}
		FireStationModel fireStationModel = new FireStationModel();
		fireStationModel.setAddress(address);
		iFireStationDao.delete(fireStationModel);
    }
	
	@Override
	public List<FireStationModel> consultAllfirestations() {
		return iFireStationDao.fetchAllFireStation();
	}
	
	@Override
	public InfosPersonsByFireStation getPersonByFirestation(int stationNumber) {
		InfosPersonsByFireStation result = new InfosPersonsByFireStation();

		List<FireStationModel> fireStationList = iFireStationDao.fetchFireStationsByStationNumber(stationNumber);
		
		List<String> fireStationAddresses =  fireStationList.stream()
							.map(firestation -> firestation.getAddress())
							.collect(Collectors.toList());
		
		List<PersonModel> personsList = iPersonDao.fecthAllPerson().stream()
				.filter(personModel -> fireStationAddresses.contains(personModel.getAddress()))
				.collect(Collectors.toList());
		
		List<String> agePersons = personsList.stream()
				.map(personModel -> iMedicalRecordDao.fetchMedicalRecordByFirstNameAndLastName(personModel.getFirstName(), personModel.getLastName()))
				.filter(t -> t.isPresent())
				.map(t -> t.get().getBirthdate())
				.toList();
		
		long adultCount = agePersons.stream().map(t -> Tools.getAge(t)).filter(age -> Tools.isAdult(age)).count();
		long childCount = agePersons.stream().map(t -> Tools.getAge(t)).filter(age -> Tools.isChild(age)).count();
		
		int intChildCount = (int) childCount;
		int intAdultCount = (int) adultCount;
		
		result.setAdultCount(intAdultCount);
		result.setChildCount(intChildCount);
		result.setPersonInfos(personsList.stream().map(pesonModel -> Tools.mapToPersonInfos(pesonModel)).toList());
		return result;
	}
	
	@Override
	public PersonPhoneNumber getAllPhoneNumberByFireStationNumber(int stationNumber) {
		
		List<FireStationModel> fireStationList = iFireStationDao.fetchFireStationsByStationNumber(stationNumber);

		List<String> fireStationAddresses =  fireStationList.stream()
				.map(firestation -> firestation.getAddress())
				.collect(Collectors.toList());
				
		List<PersonModel> personsCoveredByFireStation = iPersonDao.fecthAllPerson().stream()
				.filter(personModel -> fireStationAddresses.contains(personModel.getAddress()))
				.collect(Collectors.toList());
		
		Set<String> phoneNumbersSet = personsCoveredByFireStation.stream()
				.map(person -> person.getPhone())
				.collect(Collectors.toSet());
		
		List<String> phoneNumbers = new ArrayList<>(phoneNumbersSet);
				
		PersonPhoneNumber result = new PersonPhoneNumber();
		result.setPhoneNumberList(phoneNumbers);
		return result;
	}

	@Override
	public List<ResidentAndFireStationsByListOfFireSations> getResidentAndFireStationsByListOfFireSations(List<Integer> fireStationNumbers) {
	    List<ResidentAndFireStationsByListOfFireSations> resultList = new ArrayList<>();
	    
	    
		/*
		 * var personsByAddress = fireStationNumbers.stream() .map(number ->
		 * iFireStationDao.fetchFireStationsByStationNumber(number))
		 * .flatMap(listFireStation -> listFireStation.stream()) .map(fireStationModel
		 * -> iPersonDao.findByAddress(fireStationModel.getAddress())) .toList();
		 * 
		 * var personsInfosMedical = personsByAddress.stream() .map(optionalPersonModel
		 * -> optionalPersonModel.get()) .flatMap(listPersonModel ->
		 * listPersonModel.stream()) .map(personModel -> buildPersonsInfo(personModel))
		 * .toList();
		 */
	    
	    
	    
	    for (Integer fireStationNumber : fireStationNumbers) {
	        List<FireStationModel> fireStationList = iFireStationDao.fetchFireStationsByStationNumber(fireStationNumber);

	        for (FireStationModel fireStation : fireStationList) {
	            String address = fireStation.getAddress();

	            Optional<List<PersonModel>> optionalPersonsList = iPersonDao.findByAddress(address);

	            if (optionalPersonsList.isPresent()) {
	                List<PersonModel> allPersonsList = optionalPersonsList.get();
	                List<PersonsInfosAndMedical> personsInfosList = new ArrayList<>();

	                for (PersonModel person : allPersonsList) {
	                    var medicalRecord = iMedicalRecordDao.fetchMedicalRecordByFirstNameAndLastName(person.getFirstName(), person.getLastName());

	                    if (medicalRecord.isPresent()) {
	                        List<String> medications = medicalRecord.get().getMedications();
	                        List<String> allergies = medicalRecord.get().getAllergies();
	                        int age = Tools.getAge(medicalRecord.get().getBirthdate());

	                        PersonsInfosAndMedical personInfo = new PersonsInfosAndMedical(
	                            person.getLastName(),
	                            person.getPhone(),
	                            address,
	                            age,
	                            null,
	                            medications,
	                            allergies
	                        );

	                        personsInfosList.add(personInfo);
	                    }
	                }
	                ResidentAndFireStationsByListOfFireSations addressInfo = new ResidentAndFireStationsByListOfFireSations(
	                    fireStationNumber,
	                    address,
	                    personsInfosList
	                );
	                resultList.add(addressInfo);
	            }
	        }
	    }

	    return resultList;
	}
	
	/*
	 * private PersonsInfosAndMedical buildPersonsInfo(PersonModel person) { var
	 * medicalRecord =
	 * iMedicalRecordDao.fetchMedicalRecordByFirstNameAndLastName(person.
	 * getFirstName(), person.getLastName());
	 * 
	 * if (medicalRecord.isPresent()) { List<String> medications =
	 * medicalRecord.get().getMedications(); List<String> allergies =
	 * medicalRecord.get().getAllergies(); int age =
	 * Tools.getAge(medicalRecord.get().getBirthdate());
	 * 
	 * return new PersonsInfosAndMedical( person.getLastName(), person.getPhone(),
	 * person.getAddress(), age, null, medications, allergies ); } return null; }
	 */
}
