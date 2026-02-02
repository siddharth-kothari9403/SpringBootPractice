package com.example.OrganManagementSystem.ControllerTests;

import com.example.OrganManagementSystem.config.JwtTokenUtil;
import com.example.OrganManagementSystem.controller.DoctorRestController;
import com.example.OrganManagementSystem.entity.DoctorInformation;
import com.example.OrganManagementSystem.entity.PatientInformation;
import com.example.OrganManagementSystem.entity.User;
import com.example.OrganManagementSystem.service.DoctorService;
import com.example.OrganManagementSystem.service.JwtUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@WebMvcTest(DoctorRestController.class)
public class DoctorControllerTest {

    @Autowired
    private ObjectMapper om;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private DoctorService doctorService;

    @MockBean
    private JwtUserDetailsService jwtUserDetailsService;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @InjectMocks
    private DoctorRestController doctorRestController;

    private List<PatientInformation> patients;
    private PatientInformation patientInformation;
    private DoctorInformation doctorInformation;

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        patientInformation = new PatientInformation(1, "ABC", "M", "1234567890", 20, "B+");
        patients = new ArrayList<>();
        patients.add(patientInformation);
        doctorInformation = new DoctorInformation(1, "ABC", "PQR", "8776678787");
    }

    @AfterEach
    public void tearDown(){
        mockMvc = null;
        patients = null;
        patientInformation = null;
        doctorInformation = null;
    }

    @Test
    @WithMockUser(username = "doc1", password = "test123", roles = "DOCTOR")
    public void testViewPatients() throws Exception {
        when(doctorService.showPatients()).thenReturn(patients);

        mockMvc.perform(MockMvcRequestBuilders.get("/doctor/viewPatients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkb2MxIiwiaWF0IjoxNzAxMzY2NTkyLCJleHAiOjE3MDI1NzYxOTJ9.hTSEWT_DylwBnCDi5xe0TaMeROK-rcH_lBbzdiGsSFk"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].pname").value("ABC"));
    }

    @Test
    @WithMockUser(username = "doc1", password = "test123", roles = "DOCTOR")
    public void testViewPatientsById() throws Exception {
        when(doctorService.showPatientById(any())).thenReturn(Optional.of(patientInformation));

        mockMvc.perform(MockMvcRequestBuilders.get("/doctor/viewPatients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkb2MxIiwiaWF0IjoxNzAxMzY2NTkyLCJleHAiOjE3MDI1NzYxOTJ9.hTSEWT_DylwBnCDi5xe0TaMeROK-rcH_lBbzdiGsSFk"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.pname").value("ABC"));
    }

    @Test
    @WithMockUser(username = "doc1", password = "test123", roles = "DOCTOR")
    public void testViewMyInfo() throws Exception {
        when(jwtUserDetailsService.getUserByUsername(any())).thenReturn(new User());
        when(doctorService.getDoctorByUserId(any())).thenReturn(doctorInformation);

        mockMvc.perform(MockMvcRequestBuilders.get("/doctor/viewMyInfo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkb2MxIiwiaWF0IjoxNzAxMzY2NTkyLCJleHAiOjE3MDI1NzYxOTJ9.hTSEWT_DylwBnCDi5xe0TaMeROK-rcH_lBbzdiGsSFk"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.doctorName").value("ABC"));
    }

    @Test
    @WithMockUser(username = "doc1", password = "test123", roles = "DOCTOR")
    public void testUpdateMyInfo() throws Exception {
        when(jwtUserDetailsService.getUserByUsername(any())).thenReturn(new User());
        when(doctorService.getDoctorByUserId(any())).thenReturn(doctorInformation);
        when(doctorService.updateMyInfo(any())).thenReturn(doctorInformation);

        mockMvc.perform(MockMvcRequestBuilders.put("/doctor/updateMyInfo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(doctorInformation))
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkb2MxIiwiaWF0IjoxNzAxMzY2NTkyLCJleHAiOjE3MDI1NzYxOTJ9.hTSEWT_DylwBnCDi5xe0TaMeROK-rcH_lBbzdiGsSFk"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.doctorName").value("ABC"));
    }

    @Test
    @WithMockUser(username = "doc1", password = "test123", roles = "DOCTOR")
    public void testAddMyInfo() throws Exception {
        when(jwtUserDetailsService.getUserByUsername(any())).thenReturn(new User());
        when(doctorService.addDocInfo(any())).thenReturn(doctorInformation);

        mockMvc.perform(MockMvcRequestBuilders.post("/doctor/addMyInfo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(doctorInformation))
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkb2MxIiwiaWF0IjoxNzAxMzY2NTkyLCJleHAiOjE3MDI1NzYxOTJ9.hTSEWT_DylwBnCDi5xe0TaMeROK-rcH_lBbzdiGsSFk"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.doctorName").value("ABC"));
    }
}
