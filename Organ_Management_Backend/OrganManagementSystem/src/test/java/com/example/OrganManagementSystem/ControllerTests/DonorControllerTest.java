package com.example.OrganManagementSystem.ControllerTests;

import com.example.OrganManagementSystem.config.JwtTokenUtil;
import com.example.OrganManagementSystem.controller.DonorRestController;
import com.example.OrganManagementSystem.entity.*;
import com.example.OrganManagementSystem.service.DonorService;
import com.example.OrganManagementSystem.service.JwtUserDetailsService;
import com.example.OrganManagementSystem.service.MatchService;
import com.example.OrganManagementSystem.service.PatientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(DonorRestController.class)
public class DonorControllerTest {
    @Autowired
    private ObjectMapper om;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @MockBean
    private DonorService donorService;
    @MockBean
    private MatchService matchService;

    @MockBean
    private JwtUserDetailsService jwtUserDetailsService;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @InjectMocks
    private DonorRestController donorRestController;

    private PatientInformation patientInformation;
    private Donor donor;
    private List<Donor> donorList;

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        patientInformation = new PatientInformation(1, "ABC", "M", "1234567890", 20, "B+");
        donor = new Donor(1, "Kidney");
        donor.setPatientInformation(patientInformation);
        donorList = new ArrayList<>();
        donorList.add(donor);
    }

    @AfterEach
    public void tearDown(){
        mockMvc = null;
        patientInformation = null;
        donor = null;
        donorList = null;
    }

    @Test
    @WithMockUser(username = "doc1", password = "test123", roles = "DOCTOR")
    public void testViewAllDonors() throws Exception {
        when(donorService.getAllDonors()).thenReturn(donorList);

        mockMvc.perform(MockMvcRequestBuilders.get("/donor/getAll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkb2MxIiwiaWF0IjoxNzAxMzY2NTkyLCJleHAiOjE3MDI1NzYxOTJ9.hTSEWT_DylwBnCDi5xe0TaMeROK-rcH_lBbzdiGsSFk"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].organName").value("Kidney"));
    }

    @Test
    @WithMockUser(username = "patient1", password = "test123", roles = "USER")
    public void testViewDonorsByPatient() throws Exception {
        when(jwtUserDetailsService.getUserByUsername(any())).thenReturn(new User());
        when(patientService.viewPatientByUserId(any())).thenReturn(patientInformation);
        when(donorService.getDonorByPatientId(any())).thenReturn(donorList);

        mockMvc.perform(MockMvcRequestBuilders.get("/donor/viewInfo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwYXRpZW50MSIsImlhdCI6MTcwMTM2NzI0NiwiZXhwIjoxNzAyNTc2ODQ2fQ.i5dJkbNKzifod6q9HzoGUV35ngxIprgCYIxf_vIvI4I"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].organName").value("Kidney"));
    }

    @Test
    @WithMockUser(username = "patient1", password = "test123", roles = "USER")
    public void testViewDonorById() throws Exception {
        when(jwtUserDetailsService.getUserByUsername(any())).thenReturn(new User());
        when(patientService.viewPatientByUserId(any())).thenReturn(patientInformation);
        when(donorService.viewInfoById(any())).thenReturn(Optional.of(donor));

        mockMvc.perform(MockMvcRequestBuilders.get("/donor/viewInfo/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwYXRpZW50MSIsImlhdCI6MTcwMTM2NzI0NiwiZXhwIjoxNzAyNTc2ODQ2fQ.i5dJkbNKzifod6q9HzoGUV35ngxIprgCYIxf_vIvI4I"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.organName").value("Kidney"));
    }

    @Test
    @WithMockUser(username = "doc1", password = "test123", roles = "DOCTOR")
    public void testAddDonor() throws Exception {
        when(patientService.viewPatientInfo(any())).thenReturn(Optional.of(patientInformation));
        when(donorService.addInfo(any())).thenReturn(donor);
        when(matchService.matchDonorToRecipient(any())).thenReturn(new DonorRecipientMatch(1, donor, new Recipient(), 0));

        mockMvc.perform(MockMvcRequestBuilders.post("/donor/addInfo/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(donor))
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkb2MxIiwiaWF0IjoxNzAxMzY2NTkyLCJleHAiOjE3MDI1NzYxOTJ9.hTSEWT_DylwBnCDi5xe0TaMeROK-rcH_lBbzdiGsSFk"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.donor.organName").value("Kidney"));
    }
}
