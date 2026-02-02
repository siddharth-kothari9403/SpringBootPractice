package com.example.OrganManagementSystem.ServiceTests;
import com.example.OrganManagementSystem.dao.PatientInfoDAO;
import com.example.OrganManagementSystem.dao.UserDAO;
import com.example.OrganManagementSystem.entity.PatientInformation;
import com.example.OrganManagementSystem.exception.PatientNotFoundException;
import com.example.OrganManagementSystem.service.PatientService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {
    @Mock
    private PatientInfoDAO patientInfoDAO;

    @Mock
    private UserDAO userDAO;

    @Autowired
    @InjectMocks
    private PatientService patientService;

    private PatientInformation patientInformation;
    @BeforeEach
    public void setUp(){
        this.patientInformation = new PatientInformation("ABC","F","9090909090",33,"B+");
        this.patientInformation.setPatientId(1);
    }
    
    @AfterEach
    public void tearDown(){
        this.patientInformation = null;
    }

    @Test
    public void givenPatientToAddShouldReturnAddedPatientInfo(){
        when(patientInfoDAO.save(any())).thenReturn(patientInformation);
        PatientInformation patientInformation1 = this.patientService.addPatientInfo(this.patientInformation);
        assertThat(patientInformation1).isEqualTo(patientInformation);
        verify(patientInfoDAO, times(1)).save(any());
    }

    @Test
    public void givenIdReturnsPatientInfo(){
        when(patientInfoDAO.findById(1)).thenReturn(Optional.of(patientInformation));
        Optional<PatientInformation> patientInformation1 = patientService.viewPatientInfo(1);
        assertTrue(patientInformation1.isPresent());
        assertThat(patientInformation1.get()).isEqualTo(patientInformation);
        verify(patientInfoDAO,times(1)).findById(1);
    }

    @Test
    public void givenUserIdReturnsPatientInfo(){
        when(patientInfoDAO.getPatientByUserId(1)).thenReturn(patientInformation);
        PatientInformation patientInformation1 = patientService.viewPatientByUserId(1);
        assertThat(patientInformation1).isEqualTo(patientInformation);
        verify(patientInfoDAO, times(1)).getPatientByUserId(1);
    }


    @Test
    public void givenPatientInfoWillBeUpdated(){
        when(patientInfoDAO.save(any())).thenReturn(patientInformation);

        PatientInformation patientInformation1 = patientService.updatePatientInfo(patientInformation);
        assertThat(patientInformation1).isEqualTo(patientInformation);
        verify(patientInfoDAO, times(1)).save(patientInformation);
    }

    @Test
    public void GivenPatientNullWillThrowException(){
        when(patientInfoDAO.findById(1)).thenReturn(Optional.empty());
        assertThrows(PatientNotFoundException.class, () -> patientService.viewPatientInfo(1));
        verify(patientInfoDAO, times(1)).findById(1);
    }

    @Test
    public void GivenUserIdOfPatientNullWillThrowException(){
        when(patientInfoDAO.getPatientByUserId(1)).thenReturn(null);
        assertThrows(PatientNotFoundException.class, () -> patientService.viewPatientByUserId(1));
        verify(patientInfoDAO, times(1)).getPatientByUserId(1);
    }

}
