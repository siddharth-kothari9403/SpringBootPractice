package com.example.OrganManagementSystem.ControllerTests;

import com.example.OrganManagementSystem.config.JwtTokenUtil;
import com.example.OrganManagementSystem.controller.PatientRestController;
import com.example.OrganManagementSystem.entity.PatientInformation;
import com.example.OrganManagementSystem.entity.User;
import com.example.OrganManagementSystem.service.JwtUserDetailsService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(PatientRestController.class)
public class PatientControllerTest {
    @Autowired
    private ObjectMapper om;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @MockBean
    private JwtUserDetailsService jwtUserDetailsService;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @InjectMocks
    private PatientRestController patientRestController;

    private PatientInformation patientInformation;

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        patientInformation = new PatientInformation(1, "ABC", "M", "1234567890", 20, "B+");
    }

    @AfterEach
    public void tearDown(){
        mockMvc = null;
        patientInformation = null;
    }

    @Test
    @WithMockUser(username = "patient1", password = "test123", roles = "USER")
    public void testViewMyInfo() throws Exception {
        when(jwtUserDetailsService.getUserByUsername(any())).thenReturn(new User());
        when(patientService.viewPatientByUserId(any())).thenReturn(patientInformation);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/viewMyInfo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwYXRpZW50MSIsImlhdCI6MTcwMTM2NzI0NiwiZXhwIjoxNzAyNTc2ODQ2fQ.i5dJkbNKzifod6q9HzoGUV35ngxIprgCYIxf_vIvI4I"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.pname").value("ABC"));
    }

    @Test
    @WithMockUser(username = "patient1", password = "test123", roles = "USER")
    public void testUpdateMyInfo() throws Exception {
        when(jwtUserDetailsService.getUserByUsername(any())).thenReturn(new User());
        when(patientService.viewPatientByUserId(any())).thenReturn(patientInformation);
        when(patientService.updatePatientInfo(any())).thenReturn(patientInformation);

        mockMvc.perform(MockMvcRequestBuilders.put("/user/updateMyInfo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(patientInformation))
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwYXRpZW50MSIsImlhdCI6MTcwMTM2NzI0NiwiZXhwIjoxNzAyNTc2ODQ2fQ.i5dJkbNKzifod6q9HzoGUV35ngxIprgCYIxf_vIvI4I"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.pname").value("ABC"));
    }

    @Test
    @WithMockUser(username = "patient1", password = "test123", roles = "USER")
    public void testAddMyInfo() throws Exception {
        when(jwtUserDetailsService.getUserByUsername(any())).thenReturn(new User());
        when(patientService.addPatientInfo(any())).thenReturn(patientInformation);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/addPatientInfo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(patientInformation))
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwYXRpZW50MSIsImlhdCI6MTcwMTM2NzI0NiwiZXhwIjoxNzAyNTc2ODQ2fQ.i5dJkbNKzifod6q9HzoGUV35ngxIprgCYIxf_vIvI4I"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.pname").value("ABC"));
    }
}
