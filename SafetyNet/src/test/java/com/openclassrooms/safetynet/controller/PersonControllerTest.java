package com.openclassrooms.safetynet.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.openclassrooms.safetynet.model.PersonModel;
import com.openclassrooms.safetynet.services.IPersonService;
import com.openclassrooms.safetynet.services.IGetResidentAndFirestationByAddress;
import com.openclassrooms.safetynet.controller.dto.response.InfosChildByAdress;
import com.openclassrooms.safetynet.controller.dto.response.InfosMailsByCity;
import com.openclassrooms.safetynet.controller.dto.response.InfosPersonByLastName;
import com.openclassrooms.safetynet.controller.dto.response.ResidentAndFireStationByAddress;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IPersonService iPersonService;  // Mock du service de gestion des personnes

    @MockBean
    private IGetResidentAndFirestationByAddress iGetResidentAndFirestationByAddress;

    private PersonModel person;

    @BeforeEach
    public void setUp() {
        person = new PersonModel("John", "Doe", "123 Street", "City", "email", "1234567890", "75000");
    }

    // ============================
    // Test pour le GET /person
    // ============================
    @Test
    public void testGetAllPerson_ShouldReturnListOfPersons() throws Exception {
        when(iPersonService.consultAllPersons()).thenReturn(List.of(person));

        mockMvc.perform(get("/person")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"));
    }

    // ============================
    // Test pour le GET /childAlert
    // ============================
    @Test
    public void testGetChildByAddress_ShouldReturnChildrenInfo() throws Exception {
        InfosChildByAdress infosChildByAdress = new InfosChildByAdress();
        when(iPersonService.getChildUnderEighteen("123 Street")).thenReturn(infosChildByAdress);

        mockMvc.perform(get("/childAlert")
                .param("address", "123 Street")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // ============================
    // Test pour le GET /fire
    // ============================
    @Test
    public void testGetResidentsAndFireStationsByAddress_ShouldReturnResidentInfo() throws Exception {
        ResidentAndFireStationByAddress response = new ResidentAndFireStationByAddress();
        when(iGetResidentAndFirestationByAddress.getResidentAndFireStationByAddress("123 Street")).thenReturn(response);

        mockMvc.perform(get("/fire")
                .param("address", "123 Street")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // ============================
    // Test pour le GET /communityEmail
    // ============================
    @Test
    public void testGetMailsByCity_ShouldReturnEmails() throws Exception {
        InfosMailsByCity response = new InfosMailsByCity();
        when(iPersonService.getMailsByCity("City")).thenReturn(response);

        mockMvc.perform(get("/communityEmail")
                .param("city", "City")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // ============================
    // Test pour le GET /personInfolastName
    // ============================
    @Test
    public void testGetInfosPersonsByLastName_ShouldReturnPersonInfo() throws Exception {
        InfosPersonByLastName response = new InfosPersonByLastName();
        when(iPersonService.getInfosPersonsByLastName("Doe")).thenReturn(response);

        mockMvc.perform(get("/personInfolastName")
                .param("lastname", "Doe")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // ============================
    // Test pour le POST /person
    // ============================
    @Test
    public void testAddPerson_ShouldCreatePerson() throws Exception {
        mockMvc.perform(post("/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"John\", \"lastName\":\"Doe\"}"))
                .andExpect(status().isCreated());
    }

    // ============================
    // Test pour le PUT /person/{firstName}/{lastName}
    // ============================
    @Test
    public void testUpdatePerson_ShouldUpdatePerson() throws Exception {
        mockMvc.perform(put("/person/John/Doe")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"John\", \"lastName\":\"Doe\", \"address\":\"123 Street\"}"))
                .andExpect(status().isOk());
    }

    // ============================
    // Test pour le DELETE /person/{firstName}/{lastName}
    // ============================
    @Test
    public void testDeletePerson_ShouldDeletePerson() throws Exception {
        mockMvc.perform(delete("/person/John/Doe"))
                .andExpect(status().isOk());
    }
}
