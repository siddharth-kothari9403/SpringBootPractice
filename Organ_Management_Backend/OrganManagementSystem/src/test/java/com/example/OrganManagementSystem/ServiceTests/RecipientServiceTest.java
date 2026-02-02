package com.example.OrganManagementSystem.ServiceTests;
import com.example.OrganManagementSystem.dao.PatientInfoDAO;
import com.example.OrganManagementSystem.dao.RecipientDAO;
import com.example.OrganManagementSystem.entity.Recipient;
import com.example.OrganManagementSystem.entity.PatientInformation;
import com.example.OrganManagementSystem.entity.Recipient;
import com.example.OrganManagementSystem.exception.DoctorNotFoundException;
import com.example.OrganManagementSystem.exception.DonorNotFoundException;
import com.example.OrganManagementSystem.exception.PatientNotFoundException;
import com.example.OrganManagementSystem.exception.RecipientNotFoundException;
import com.example.OrganManagementSystem.service.RecipientService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecipientServiceTest {
    @Mock
    private RecipientDAO recipientDAO;

    @Mock
    private PatientInfoDAO patientInfoDAO;

    @Autowired
    @InjectMocks
    private RecipientService recipientService;

    private List<Recipient> recipients;
    private Recipient recipient;
    private PatientInformation patientInformation;

    @BeforeEach
    public void setUp(){
        recipient = new Recipient(1, "Kidney", 10);
        recipients = new ArrayList<>();
        patientInformation = new PatientInformation(1, "ABC", "M", "9807876789", 20, "B+");
        recipients.add(recipient);
    }

    @AfterEach
    public void tearDown(){
        recipient = null;
        recipients = null;
        patientInformation = null;
    }

    @Test
    public void givenRecipientToAddShouldReturnAddedRecipient(){
        when(recipientDAO.save(any())).thenReturn(recipient);
        Recipient recipient1 = recipientService.addInfo(recipient);
        assertEquals(recipient, recipient1);
        verify(recipientDAO, times(1)).save(any());
    }

    @Test
    public void givenRecipientToUpdateShouldReturnUpdatedRecipient(){
        when(recipientDAO.save(any())).thenReturn(recipient);
        Recipient recipient1 = recipientService.updateInfo(recipient);
        assertEquals(recipient, recipient1);
        verify(recipientDAO, times(1)).save(any());
    }

    @Test
    public void givenGetAllRecipientsShouldReturnListOfRecipients(){
        when(recipientDAO.findAll()).thenReturn(recipients);
        List<Recipient> recipientList = recipientService.getAllRecipients();
        assertEquals(recipients, recipientList);
        verify(recipientDAO, times(1)).findAll();
    }

    @Test
    public void givenIdReturnsRecipientInfo(){
        when(recipientDAO.findById(1)).thenReturn(Optional.of(recipient));
        Optional<Recipient> recipient1 = recipientService.viewInfoById(1);
        assertTrue(recipient1.isPresent());
        assertThat(recipient1.get()).isEqualTo(recipient);
        verify(recipientDAO,times(1)).findById(1);
    }

    @Test
    public void givenPatientIdReturnsRecipientInfo(){
        when(patientInfoDAO.findById(1)).thenReturn(Optional.of(patientInformation));
        when(recipientDAO.getRecipientByPatientId(1)).thenReturn(recipients);
        List<Recipient> recipient1 = recipientService.getRecipientByPatientId(1);
        assertEquals(recipient1,recipients);
        verify(patientInfoDAO, times(1)).findById(1);
        verify(recipientDAO, times(1)).getRecipientByPatientId(1);
    }

    @Test
    public void givenRecipientNullWillThrowException(){
        when(recipientDAO.findById(1)).thenReturn(Optional.empty());
        assertThrows(RecipientNotFoundException.class, ()->recipientService.viewInfoById(1));
        verify(recipientDAO, times(1)).findById(1);
    }

    @Test
    public void givenPatientIdOfRecipientNullThrowsException(){
        when(patientInfoDAO.findById(1)).thenReturn(Optional.empty());
        assertThrows(PatientNotFoundException.class, ()->recipientService.getRecipientByPatientId(1));
        verify(patientInfoDAO, times(1)).findById(any());
        verify(recipientDAO, never()).getRecipientByPatientId(any());
    }
}
