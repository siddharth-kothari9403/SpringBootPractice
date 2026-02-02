package com.example.OrganManagementSystem.ServiceTests;
import com.example.OrganManagementSystem.dao.DonorDAO;
import com.example.OrganManagementSystem.dao.PatientInfoDAO;
import com.example.OrganManagementSystem.entity.Donor;
import com.example.OrganManagementSystem.entity.PatientInformation;
import com.example.OrganManagementSystem.exception.DonorNotFoundException;
import com.example.OrganManagementSystem.exception.PatientNotFoundException;
import com.example.OrganManagementSystem.service.DonorService;
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
public class DonorServiceTest {
    @Mock
    private DonorDAO donorDAO;

    @Mock
    private PatientInfoDAO patientInfoDAO;

    @Autowired
    @InjectMocks
    private DonorService donorService;

    private Donor donor;
    private PatientInformation patientInformation;
    private List<Donor> donorList;

    @BeforeEach
    public void setUp(){
        donorList = new ArrayList<>();
        this.donor = new Donor("Kidney");
        this.donor.setDonorId(1);
        this.patientInformation = new PatientInformation("ABC","F","9090909090",33,"B+");
        this.patientInformation.setPatientId(1);
    }

    @AfterEach
    public void tearDown(){
        this.donor = null;
        this.donorList = null;
    }

    @Test
    public void givenGetAllDonorsReturnsListOfAllDonors(){
        when(donorDAO.findAll()).thenReturn(this.donorList);
        List<Donor> donors = donorService.getAllDonors();
        assertEquals(donors,donorList);
        verify(donorDAO,times(1)).findAll();
    }

    @Test
    public void givenDonorToAddShouldReturnAddedDonorInfo(){
        when(donorDAO.save(any())).thenReturn(donor);
        Donor donor1 = this.donorService.addInfo(this.donor);
        assertThat(donor1).isEqualTo(donor);
        verify(donorDAO, times(1)).save(any());
    }

    @Test
    public void givenIdReturnsDonorInfo(){
        when(donorDAO.findById(1)).thenReturn(Optional.of(donor));
        Optional<Donor> donor1 = donorService.viewInfoById(1);
        assertTrue(donor1.isPresent());
        assertThat(donor1.get()).isEqualTo(donor);
        verify(donorDAO,times(1)).findById(1);
    }

    @Test
    public void givenPatientIdReturnsDonorInfo(){
        when(patientInfoDAO.findById(1)).thenReturn(Optional.of(patientInformation));
        when(donorDAO.getDonorByPatientId(1)).thenReturn(donorList);
        List<Donor> donor1 = donorService.getDonorByPatientId(1);
        assertEquals(donor1,donorList);
        verify(patientInfoDAO, times(1)).findById(1);
        verify(donorDAO, times(1)).getDonorByPatientId(1);
    }

    @Test
    public void givenPatientIdOfDonorNullThrowsException(){
        when(patientInfoDAO.findById(1)).thenReturn(Optional.empty());
        assertThrows(PatientNotFoundException.class, ()->donorService.getDonorByPatientId(1));
        verify(patientInfoDAO, times(1)).findById(any());
        verify(donorDAO, never()).getDonorByPatientId(any());
    }

    @Test
    public void GivenDonorNullWillThrowException(){
        when(donorDAO.findById(1)).thenReturn(Optional.empty());
        assertThrows(DonorNotFoundException.class, ()->donorService.viewInfoById(1));
        verify(donorDAO, times(1)).findById(1);
    }
}
