package com.example.OrganManagementSystem.ControllerTests;


import com.example.OrganManagementSystem.config.JwtTokenUtil;
import com.example.OrganManagementSystem.controller.MatchRestController;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(MatchRestController.class)
public class MatchControllerTest {
    @Autowired
    private ObjectMapper om;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MatchService matchService;

    @MockBean
    private JwtUserDetailsService jwtUserDetailsService;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @InjectMocks
    private MatchRestController matchRestController;

    private DonorRecipientMatch donorRecipientMatch;
    private Donor donor;
    private Recipient recipient;
    private List<DonorRecipientMatch> donorRecipientMatchList;

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        donor = new Donor(1, "Kidney");
        recipient = new Recipient(1, "Kidney", 10);
        donorRecipientMatch = new DonorRecipientMatch(1, donor, recipient, 0);
        donorRecipientMatchList = new ArrayList<>();
        donorRecipientMatchList.add(donorRecipientMatch);
    }

    @AfterEach
    public void tearDown(){
        mockMvc = null;
        donor = null;
        recipient = null;
        donorRecipientMatch = null;
        donorRecipientMatchList = null;
    }

    @Test
    @WithMockUser(username = "patient1", password = "test123", roles = "USER")
    public void testGetByDonorId() throws Exception {
        when(matchService.getMatchByDonorId(any())).thenReturn(donorRecipientMatch);

        mockMvc.perform(MockMvcRequestBuilders.get("/match/donor/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwYXRpZW50MSIsImlhdCI6MTcwMTM2NzI0NiwiZXhwIjoxNzAyNTc2ODQ2fQ.i5dJkbNKzifod6q9HzoGUV35ngxIprgCYIxf_vIvI4I"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.donor.organName").value("Kidney"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.recipient.organName").value("Kidney"));
    }

    @Test
    @WithMockUser(username = "patient1", password = "test123", roles = "USER")
    public void testGetByRecipientId() throws Exception {
        when(matchService.getMatchByRecipientId(any())).thenReturn(donorRecipientMatch);

        mockMvc.perform(MockMvcRequestBuilders.get("/match/recipient/1")
                .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwYXRpZW50MSIsImlhdCI6MTcwMTM2NzI0NiwiZXhwIjoxNzAyNTc2ODQ2fQ.i5dJkbNKzifod6q9HzoGUV35ngxIprgCYIxf_vIvI4I"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.donor.organName").value("Kidney"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.recipient.organName").value("Kidney"));
    }

    @Test
    @WithMockUser(username = "patient1", password = "test123", roles = "USER")
    public void testGetByDonorPatientId() throws Exception {
        when(matchService.getMatchesByDonorPatientId(any())).thenReturn(donorRecipientMatchList);

        mockMvc.perform(MockMvcRequestBuilders.get("/match/patient/donor/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwYXRpZW50MSIsImlhdCI6MTcwMTM2NzI0NiwiZXhwIjoxNzAyNTc2ODQ2fQ.i5dJkbNKzifod6q9HzoGUV35ngxIprgCYIxf_vIvI4I"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].donor.organName").value("Kidney"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].recipient.organName").value("Kidney"));
    }

    @Test
    @WithMockUser(username = "patient1", password = "test123", roles = "USER")
    public void testGetByPatientRecipientId() throws Exception {
        when(matchService.getMatchesByRecipientPatientId(any())).thenReturn(donorRecipientMatchList);

        mockMvc.perform(MockMvcRequestBuilders.get("/match/patient/recipient/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwYXRpZW50MSIsImlhdCI6MTcwMTM2NzI0NiwiZXhwIjoxNzAyNTc2ODQ2fQ.i5dJkbNKzifod6q9HzoGUV35ngxIprgCYIxf_vIvI4I"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].donor.organName").value("Kidney"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].recipient.organName").value("Kidney"));
    }
}
