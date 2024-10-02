package com.openclassrooms.safetynet.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.openclassrooms.safetynet.controller.dto.response.InfosPersonsByFireStation;
import com.openclassrooms.safetynet.controller.dto.response.PersonPhoneNumber;
import com.openclassrooms.safetynet.controller.dto.response.ResidentAndFireStationsByListOfFireSations;
import com.openclassrooms.safetynet.model.FireStationModel;
import com.openclassrooms.safetynet.services.IFireStationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.ArrayList;

@WebMvcTest(FireStationController.class)
public class FireStationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IFireStationService iFireStationService;

    private FireStationModel fireStation;

    @BeforeEach
    public void setUp() {
        fireStation = new FireStationModel("123 Street", 1);
    }

    // ============================
    // Test pour le GET /firestation
    // ============================
    @Test
    public void testGetPersonsByFireStation_ShouldReturnInfosPersonsByFireStation() throws Exception {
        InfosPersonsByFireStation infos = new InfosPersonsByFireStation();
        when(iFireStationService.getPersonByFirestation(1)).thenReturn(infos);

        mockMvc.perform(get("/firestation?stationNumber=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // ============================
    // Test pour le GET /phoneAlert
    // ============================
    @Test
    public void testGetPhoneByFireStation_ShouldReturnPhoneNumbers() throws Exception {
        PersonPhoneNumber phoneNumbers = new PersonPhoneNumber(List.of("333 333", "332 332"));
        when(iFireStationService.getAllPhoneNumberByFireStationNumber(1)).thenReturn(phoneNumbers);

        mockMvc.perform(get("/phoneAlert?firestation=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phoneNumberList[0]", is("333 333")))
                .andExpect(jsonPath("$.phoneNumberList[1]", is("332 332")));
    }

    // ============================
    // Test pour le GET /flood/stations
    // ============================
    @Test
    public void testGetResidentAndFireStationsByListOfFireSations_ShouldReturnResidents() throws Exception {
        List<ResidentAndFireStationsByListOfFireSations> residents = new ArrayList<>();
        when(iFireStationService.getResidentAndFireStationsByListOfFireSations(List.of(1, 2)))
                .thenReturn(residents);

        mockMvc.perform(get("/flood/stations?stations=1,2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // ============================
    // Test pour le POST /firestation
    // ============================
    @Test
    public void testAddFireStation_ShouldCreateFireStation() throws Exception {
        mockMvc.perform(post("/firestation")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"address\":\"123 Street\", \"stationNumber\":1}"))
                .andExpect(status().isCreated());
    }

    // ============================
    // Test pour le PUT /firestation/{address}
    // ============================
    @Test
    public void testUpdateFireStation_ShouldUpdateFireStation() throws Exception {
        mockMvc.perform(put("/firestation/123 Street")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"address\":\"123 Street\", \"stationNumber\":1}"))
                .andExpect(status().isOk());
    }

    // ============================
    // Test pour le DELETE /firestation/{address}
    // ============================
    @Test
    public void testDeleteFireStation_ShouldDeleteFireStation() throws Exception {
        mockMvc.perform(delete("/firestation/123 Street"))
                .andExpect(status().isOk());
    }
}
