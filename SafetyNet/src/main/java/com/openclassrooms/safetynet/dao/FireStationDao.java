package com.openclassrooms.safetynet.dao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.openclassrooms.safetynet.model.DataModel;
import com.openclassrooms.safetynet.model.FireStationModel;

@Repository
public class FireStationDao implements IFireStationDao{
	@Autowired
	private GeneriqueDataModelDao generiqueDataModelDao;
	
	@Override
	public List<FireStationModel> fetchAllFireStation() {
		DataModel dataModel = generiqueDataModelDao.fetchData();
		return dataModel.getFirestations();
	}

	@Override
	public List<FireStationModel> fetchFireStationsByStationNumber(int stationNumber) {
		return fetchAllFireStation().stream()
				.filter(firestation -> firestation.getStation() == stationNumber)
				.collect(Collectors.toList());
	}

	@Override
	public Optional<FireStationModel> fetchFireStationByAddress(String address) {

		return fetchAllFireStation().stream()
				.filter(firestation -> firestation.getAddress().equals(address))
				.findFirst();
	}
	
    @Override
    public void create(FireStationModel fireStationModel) {
        DataModel dataModel = generiqueDataModelDao.fetchData();
        dataModel.getFirestations().add(fireStationModel);
        generiqueDataModelDao.updateData(dataModel);
    }

    @Override
    public void update(FireStationModel fireStationModel) {
        DataModel dataModel = generiqueDataModelDao.fetchData();
        List<FireStationModel> stations = dataModel.getFirestations();
        for (int i = 0; i < stations.size(); i++) {
            if (stations.get(i).getAddress().equals(fireStationModel.getAddress())) {
                stations.set(i, fireStationModel);
                generiqueDataModelDao.updateData(dataModel);
            }
        }
    }

    @Override
    public void delete(FireStationModel fireStationModel) {
        DataModel dataModel = generiqueDataModelDao.fetchData();
        dataModel.getFirestations().removeIf(fireStation -> fireStation.getAddress().equals(fireStationModel.getAddress()) ||
        		fireStation.getStation() == fireStationModel.getStation());
        generiqueDataModelDao.updateData(dataModel);
    }
}
