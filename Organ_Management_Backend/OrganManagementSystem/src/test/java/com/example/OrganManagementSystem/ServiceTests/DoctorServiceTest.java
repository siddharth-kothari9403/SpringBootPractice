package com.example.OrganManagementSystem.ServiceTests;

import com.example.OrganManagementSystem.entity.DoctorInformation;
import com.example.OrganManagementSystem.entity.PatientInformation;
import com.example.OrganManagementSystem.exception.DoctorNotFoundException;
import com.example.OrganManagementSystem.exception.PatientNotFoundException;
import com.example.OrganManagementSystem.service.DoctorService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.OrganManagementSystem.dao.PatientInfoDAO;
import com.example.OrganManagementSystem.dao.DoctorInfoDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DoctorServiceTest {

    @Mock
    private PatientInfoDAO patientDAO;

    @Mock
    private DoctorInfoDAO doctorInfoDAO;

    @Autowired
    @InjectMocks
    private DoctorService doctorService;

    private List<PatientInformation> patients;
    private DoctorInformation doc1;
    private PatientInformation patientInformation;

    @BeforeEach
    public void setUp(){
        doc1 = new DoctorInformation(1, "ABC", "Orthopaedic", "9876543210");
        patients = new ArrayList<>();
        patientInformation = new PatientInformation(1, "PQR", "M", "9898989898", 12, "B+");
        patients.add(patientInformation);
    }

    @AfterEach
    public void tearDown(){
        doc1 = null;
        patients = null;
        patientInformation = null;
    }

    @Test
    public void givenDoctorToAddShouldReturnAddedDoctor(){
        when(doctorInfoDAO.save(any())).thenReturn(doc1);
        DoctorInformation doctorInformation = doctorService.addDocInfo(doc1);
        assertThat(doctorInformation).isEqualTo(doc1);
        verify(doctorInfoDAO, times(1)).save(any());
    }

    @Test
    public void givenDoctorToUpdateShouldReturnUpdatedDoctor(){
        when(doctorInfoDAO.save(any())).thenReturn(doc1);
        DoctorInformation doctorInformation = doctorService.updateMyInfo(doc1);
        assertThat(doctorInformation).isEqualTo(doc1);
        verify(doctorInfoDAO, times(1)).save(any());
    }

    @Test
    public void givenGetAllPatientsShouldReturnListOfPatients(){
        when(patientDAO.findAll()).thenReturn(patients);
        List<PatientInformation> patientInformationList = doctorService.showPatients();

        assertEquals(patientInformationList, patients);
        verify(patientDAO, times(1)).findAll();
    }

    @Test
    public void givenNullShouldThrowException(){
        when(doctorInfoDAO.getDoctorByUserId(1)).thenReturn(null);
        assertThrows(DoctorNotFoundException.class, () -> doctorService.getDoctorByUserId(1));
        verify(doctorInfoDAO, times(1)).getDoctorByUserId(any());
    }

    @Test
    public void givenUserIdShouldReturnDoctor(){
        when(doctorInfoDAO.getDoctorByUserId(1)).thenReturn(doc1);
        DoctorInformation doctorInformation = doctorService.getDoctorByUserId(1);
        assertEquals(doctorInformation, doc1);
        verify(doctorInfoDAO, times(1)).getDoctorByUserId(any());
    }

    @Test
    public void givenNullPatientShouldThrowException(){
        when(patientDAO.findById(1)).thenReturn(Optional.empty());
        assertThrows(PatientNotFoundException.class, () -> doctorService.showPatientById(1));
        verify(patientDAO, times(1)).findById(any());
    }

    @Test
    public void givenPatientIdShouldReturnPatient(){
        when(patientDAO.findById(1)).thenReturn(Optional.of(patientInformation));
        Optional<PatientInformation> patientInformation1 = doctorService.showPatientById(1);
        assertEquals(patientInformation1, Optional.of(patientInformation));
        verify(patientDAO, times(1)).findById(any());
    }
}
