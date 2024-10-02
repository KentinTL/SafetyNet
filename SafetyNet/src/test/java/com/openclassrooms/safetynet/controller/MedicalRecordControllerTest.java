package com.openclassrooms.safetynet.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.openclassrooms.safetynet.model.MedicalRecordModel;
import com.openclassrooms.safetynet.services.IMedicalRecordService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@WebMvcTest(MedicalRecordController.class)
public class MedicalRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IMedicalRecordService iMedicalRecordService;

    private MedicalRecordModel medicalRecord;

    @BeforeEach
    public void setUp() {
        medicalRecord = new MedicalRecordModel("John", "Doe", "01/01/1980", List.of("medication1"), List.of("allergy1"));
    }

    // ============================
    // Test pour le GET /medicalRecord
    // ============================
    @Test
    public void testGetAllMedicalRecords_ShouldReturnListOfMedicalRecords() throws Exception {
        when(iMedicalRecordService.consultAllMedicalRecord()).thenReturn(List.of(medicalRecord));

        mockMvc.perform(get("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"));
    }

    // ============================
    // Test pour le POST /medicalRecord
    // ============================
    @Test
    public void testCreateNewMedicalRecord_ShouldCreateMedicalRecord() throws Exception {
        mockMvc.perform(post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"John\", \"lastName\":\"Doe\", \"birthdate\":\"01/01/1980\", \"medications\":[\"medication1\"], \"allergies\":[\"allergy1\"]}"))
                .andExpect(status().isCreated());
    }

    // ============================
    // Test pour le PUT /medicalRecord/{firstName}/{lastName}
    // ============================
    @Test
    public void testUpdateMedicalRecord_ShouldUpdateMedicalRecord() throws Exception {
        mockMvc.perform(put("/medicalRecord/John/Doe")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"John\", \"lastName\":\"Doe\", \"birthdate\":\"01/01/1980\", \"medications\":[\"medication1\"], \"allergies\":[\"allergy1\"]}"))
                .andExpect(status().isOk());
    }

    // ============================
    // Test pour le DELETE /medicalRecord/{firstName}/{lastName}
    // ============================
    @Test
    public void testDeleteMedicalRecord_ShouldDeleteMedicalRecord() throws Exception {
        mockMvc.perform(delete("/medicalRecord/John/Doe"))
                .andExpect(status().isOk());
    }
}

