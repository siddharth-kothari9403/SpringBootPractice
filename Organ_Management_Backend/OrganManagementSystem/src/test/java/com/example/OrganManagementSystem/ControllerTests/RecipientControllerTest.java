package com.example.OrganManagementSystem.ControllerTests;

import com.example.OrganManagementSystem.config.JwtTokenUtil;
import com.example.OrganManagementSystem.controller.RecipientRestController;
import com.example.OrganManagementSystem.entity.*;
import com.example.OrganManagementSystem.service.*;
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
@WebMvcTest(RecipientRestController.class)
public class RecipientControllerTest {
    @Autowired
    private ObjectMapper om;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @MockBean
    private RecipientService recipientService;

    @MockBean
    private MatchService matchService;

    @MockBean
    private JwtUserDetailsService jwtUserDetailsService;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @InjectMocks
    private RecipientRestController recipientRestController;

    private PatientInformation patientInformation;
    private Recipient recipient;
    private List<Recipient> recipientList;

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        patientInformation = new PatientInformation(1, "ABC", "M", "1234567890", 20, "B+");
        recipient = new Recipient(1,"Kidney",9);
        recipient.setPatientInformation(patientInformation);
        recipientList = new ArrayList<>();
        recipientList.add(recipient);
    }

    @AfterEach
    public void tearDown(){
        mockMvc = null;
        patientInformation = null;
        recipientList = null;
        recipient = null;
    }

    @Test
    @WithMockUser(username = "patient1", password = "test123", roles = "USER")
    public void testViewRecipientsByPatient() throws Exception{
        when(jwtUserDetailsService.getUserByUsername(any())).thenReturn(new User());
        when(patientService.viewPatientByUserId(any())).thenReturn(patientInformation);
        when(recipientService.getRecipientByPatientId(any())).thenReturn(recipientList);

        mockMvc.perform(MockMvcRequestBuilders.get("/recipient/viewInfo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwYXRpZW50MSIsImlhdCI6MTcwMTM2NzI0NiwiZXhwIjoxNzAyNTc2ODQ2fQ.i5dJkbNKzifod6q9HzoGUV35ngxIprgCYIxf_vIvI4I"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].organName").value("Kidney"));
    }

    @Test
    @WithMockUser(username = "doc1", password = "test123", roles = "DOCTOR")
    public void testViewAllRecipients() throws Exception {
        when(recipientService.getAllRecipients()).thenReturn(recipientList);

        mockMvc.perform(MockMvcRequestBuilders.get("/recipient/getAll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkb2MxIiwiaWF0IjoxNzAxMzY2NTkyLCJleHAiOjE3MDI1NzYxOTJ9.hTSEWT_DylwBnCDi5xe0TaMeROK-rcH_lBbzdiGsSFk"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].organName").value("Kidney"));
    }

    @Test
    @WithMockUser(username = "patient1", password = "test123", roles = "USER")
    public void testViewRecipientById() throws Exception {
        when(jwtUserDetailsService.getUserByUsername(any())).thenReturn(new User());
        when(patientService.viewPatientByUserId(any())).thenReturn(patientInformation);
        when(recipientService.viewInfoById(any())).thenReturn(Optional.of(recipient));

        mockMvc.perform(MockMvcRequestBuilders.get("/recipient/viewInfo/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwYXRpZW50MSIsImlhdCI6MTcwMTM2NzI0NiwiZXhwIjoxNzAyNTc2ODQ2fQ.i5dJkbNKzifod6q9HzoGUV35ngxIprgCYIxf_vIvI4I"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.organName").value("Kidney"));
    }

    @Test
    @WithMockUser(username="doc1",password = "test123", roles = "DOCTOR")
    public void testAddRecipient() throws Exception {
        when(patientService.viewPatientInfo(any())).thenReturn(Optional.of(patientInformation));
        when(recipientService.addInfo(any())).thenReturn(recipient);
        when(matchService.matchRecipientToDonor(any())).thenReturn(new DonorRecipientMatch(1, new Donor(), recipient, 0));

        mockMvc.perform(MockMvcRequestBuilders.post("/recipient/addInfo/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(recipient))
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkb2MxIiwiaWF0IjoxNzAxMzY2NTkyLCJleHAiOjE3MDI1NzYxOTJ9.hTSEWT_DylwBnCDi5xe0TaMeROK-rcH_lBbzdiGsSFk"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.donor.organName").value("Kidney"));
    }
}
