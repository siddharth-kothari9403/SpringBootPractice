package com.example.OrganManagementSystem.ServiceTests;
import com.example.OrganManagementSystem.dao.DonorDAO;
import com.example.OrganManagementSystem.dao.DonorRecipientMatchDAO;
import com.example.OrganManagementSystem.dao.PatientInfoDAO;
import com.example.OrganManagementSystem.dao.RecipientDAO;
import com.example.OrganManagementSystem.entity.Donor;
import com.example.OrganManagementSystem.entity.DonorRecipientMatch;
import com.example.OrganManagementSystem.entity.PatientInformation;
import com.example.OrganManagementSystem.entity.Recipient;
import com.example.OrganManagementSystem.exception.DonorNotFoundException;
import com.example.OrganManagementSystem.exception.MatchNotFoundException;
import com.example.OrganManagementSystem.exception.PatientNotFoundException;
import com.example.OrganManagementSystem.exception.RecipientNotFoundException;
import com.example.OrganManagementSystem.service.MatchService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MatchServiceTest {
    @Mock
    private DonorRecipientMatchDAO donorRecipientMatchDAO;

    @Mock
    private DonorDAO donorDAO;

    @Mock
    private RecipientDAO recipientDAO;

    @Mock
    private PatientInfoDAO patientInfoDAO;

    @Autowired @InjectMocks
    private MatchService matchService;

    private DonorRecipientMatch donorRecipientMatch;
    private List<DonorRecipientMatch> donorRecipientMatchList;
    private Donor donor;
    private Recipient recipient;
    private PatientInformation patientInformation;
    private PatientInformation patientInformation1;
    private List<Recipient> recipients;
    private List<Donor> donors;

    @BeforeEach
    public void setUp(){
        this.donor = new Donor("Kidney");
        this.donor.setDonorId(1);
        this.recipient = new Recipient("Kidney",10);
        this.recipient.setRecipientId(1);
        this.donorRecipientMatch = new DonorRecipientMatch(donor,recipient,0);
        this.donorRecipientMatch.setId(1);
        this.donorRecipientMatchList = new ArrayList<>();
        this.patientInformation = new PatientInformation("ABC","F","9090909090",33,"B+");
        this.patientInformation.setPatientId(1);
        this.patientInformation1 = new PatientInformation("DEF", "M", "9898988989", 42, "B+");
        this.patientInformation1.setPatientId(2);

        this.recipients = new ArrayList<>();
        Recipient temp1 = new Recipient(10, "Kidney", 10);
        temp1.setPatientInformation(patientInformation);
        Recipient temp2 = new Recipient(11, "Eyes", 9);
        temp2.setPatientInformation(patientInformation);
        donor.setPatientInformation(patientInformation1);
        recipients.add(temp1);
        recipients.add(temp2);

        this.donors = new ArrayList<>();
        Donor don1 = new Donor(1, "Kidney");
        don1.setPatientInformation(patientInformation);
        Donor don2 = new Donor(2, "Eyes");
        don2.setPatientInformation(patientInformation);
        recipient.setPatientInformation(patientInformation1);
        donors.add(don1);
        donors.add(don2);
    }

    @AfterEach
    public void tearDown(){
        this.donor = null;
        this.recipient = null;
        this.donorRecipientMatch = null;
        this.patientInformation = null;
        this.donorRecipientMatchList = null;
    }

    @Test
    public void givenDonorIdReturnsMatchInfo(){
        when(donorDAO.findById(1)).thenReturn(Optional.of(donor));
        when(donorRecipientMatchDAO.getMatchByDonorId(1)).thenReturn(donorRecipientMatch);
        DonorRecipientMatch donorRecipientMatch1 = matchService.getMatchByDonorId(1);
        assertThat(donorRecipientMatch1).isEqualTo(donorRecipientMatch);
        verify(donorDAO, times(1)).findById(1);
        verify(donorRecipientMatchDAO, times(1)).getMatchByDonorId(1);
    }

    @Test
    public void givenRecipientIdReturnsMatchInfo(){
        when(recipientDAO.findById(1)).thenReturn(Optional.of(recipient));
        when(donorRecipientMatchDAO.getMatchByRecipientId(1)).thenReturn(donorRecipientMatch);
        DonorRecipientMatch donorRecipientMatch1 = matchService.getMatchByRecipientId(1);
        assertThat(donorRecipientMatch1).isEqualTo(donorRecipientMatch);
        verify(recipientDAO, times(1)).findById(1);
        verify(donorRecipientMatchDAO, times(1)).getMatchByRecipientId(1);
    }

    @Test
    public void givenDonorIdNullThrowsException(){
        when(donorDAO.findById(1)).thenReturn(Optional.empty());
        assertThrows(DonorNotFoundException.class, ()->matchService.getMatchByDonorId(1));
        verify(donorDAO, times(1)).findById(any());
        verify(donorRecipientMatchDAO, never()).getMatchByDonorId(any());
    }

    @Test
    public void givenRecipientIdNullThrowsException(){
        when(recipientDAO.findById(1)).thenReturn(Optional.empty());
        assertThrows(RecipientNotFoundException.class, ()->matchService.getMatchByRecipientId(1));
        verify(recipientDAO, times(1)).findById(any());
        verify(donorRecipientMatchDAO, never()).getMatchByRecipientId(any());
    }

    @Test
    public void givenDonorPatientIdReturnsAllMatches(){
        when(patientInfoDAO.findById(1)).thenReturn(Optional.of(patientInformation));
        when(donorRecipientMatchDAO.getMatchesByDonorPatientId(1)).thenReturn(donorRecipientMatchList);
        List<DonorRecipientMatch> donorRecipientMatchList1 = matchService.getMatchesByDonorPatientId(1);
        assertEquals(donorRecipientMatchList1,donorRecipientMatchList);
        verify(patientInfoDAO, times(1)).findById(1);
        verify(donorRecipientMatchDAO, times(1)).getMatchesByDonorPatientId(1);
    }

    @Test
    public void givenRecipientPatientIdReturnsAllMatches(){
        when(patientInfoDAO.findById(1)).thenReturn(Optional.of(patientInformation));
        when(donorRecipientMatchDAO.getMatchesByRecipientPatientId(1)).thenReturn(donorRecipientMatchList);
        List<DonorRecipientMatch> donorRecipientMatchList1 = matchService.getMatchesByRecipientPatientId(1);
        assertEquals(donorRecipientMatchList1,donorRecipientMatchList);
        verify(patientInfoDAO, times(1)).findById(1);
        verify(donorRecipientMatchDAO, times(1)).getMatchesByRecipientPatientId(1);
    }

    @Test
    public void givenPatientIdOfDonorNullThrowsException(){
        when(patientInfoDAO.findById(1)).thenReturn(Optional.empty());
        assertThrows(PatientNotFoundException.class, ()->matchService.getMatchesByDonorPatientId(1));
        verify(patientInfoDAO, times(1)).findById(any());
        verify(donorRecipientMatchDAO, never()).getMatchesByDonorPatientId(any());
    }

    @Test
    public void givenPatientIdOfRecipientNullThrowsException(){
        when(patientInfoDAO.findById(1)).thenReturn(Optional.empty());
        assertThrows(PatientNotFoundException.class, ()->matchService.getMatchesByRecipientPatientId(1));
        verify(patientInfoDAO, times(1)).findById(any());
        verify(donorRecipientMatchDAO, never()).getMatchesByRecipientPatientId(any());
    }

    @Test
    public void givenDonorAddedFindMatches(){
        when(donorDAO.findById(1)).thenReturn(Optional.of(donor));
        when(recipientDAO.getAll(any())).thenReturn(recipients);
        when(donorRecipientMatchDAO.getMatchByRecipientId(any())).thenReturn(null);
        when(recipientDAO.findById(any())).thenReturn(Optional.of(recipients.get(0)));

        DonorRecipientMatch donorRecipientMatch1 = this.matchService.matchDonorToRecipient(donor);
        verify(donorRecipientMatchDAO, times(1)).save(any());
    }

    @Test
    public void givenNullDonorThrowException(){
        when(donorDAO.findById(1)).thenReturn(Optional.empty());

        assertThrows(DonorNotFoundException.class, ()->matchService.matchDonorToRecipient(donor));
        verify(donorDAO, times(1)).findById(any());
        verify(recipientDAO, never()).getAll(any());
    }

    @Test
    public void givenNoMatchesForDonorThrowException(){
        when(donorDAO.findById(1)).thenReturn(Optional.of(donor));
        when(recipientDAO.getAll(any())).thenReturn(new ArrayList<>());

        assertThrows(MatchNotFoundException.class, ()->matchService.matchDonorToRecipient(donor));
        verify(donorDAO, times(1)).findById(any());
        verify(recipientDAO, times(1)).getAll(any());
        verify(donorRecipientMatchDAO, never()).save(any());
    }

    @Test
    public void givenRecipientAddedFindMatches(){
        when(recipientDAO.findById(1)).thenReturn(Optional.of(recipient));
        when(donorDAO.findAll()).thenReturn(donors);
        when(donorRecipientMatchDAO.getMatchByDonorId(any())).thenReturn(null);
        when(donorDAO.findById(any())).thenReturn(Optional.of(donors.get(0)));

        DonorRecipientMatch donorRecipientMatch1 = this.matchService.matchRecipientToDonor(recipient);
        verify(donorRecipientMatchDAO, times(1)).save(any());
    }

    @Test
    public void givenNullRecipientThrowException(){
        when(recipientDAO.findById(1)).thenReturn(Optional.empty());

        assertThrows(RecipientNotFoundException.class, ()->matchService.matchRecipientToDonor(recipient));
        verify(recipientDAO, times(1)).findById(any());
        verify(donorDAO, never()).findAll();
    }

    @Test
    public void givenNoMatchesForRecipientThrowException(){
        when(recipientDAO.findById(1)).thenReturn(Optional.of(recipient));
        when(donorDAO.findAll()).thenReturn(new ArrayList<>());

        assertThrows(MatchNotFoundException.class, ()->matchService.matchRecipientToDonor(recipient));
        verify(recipientDAO, times(1)).findById(any());
        verify(donorDAO, times(1)).findAll();
        verify(donorRecipientMatchDAO, never()).save(any());
    }
}
