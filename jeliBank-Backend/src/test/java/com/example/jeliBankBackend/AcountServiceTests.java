package com.example.jeliBankBackend;

import com.example.jeliBankBackend.exceptions.ResourseNotFoundException;
import com.example.jeliBankBackend.model.Acount;
import com.example.jeliBankBackend.repository.AcountRepository;
import com.example.jeliBankBackend.repository.AddressRepository;
import com.example.jeliBankBackend.repository.ClientRepository;
import com.example.jeliBankBackend.service.AcountService;
import com.example.jeliBankBackend.service.AddressService;
import com.example.jeliBankBackend.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

public class AcountServiceTests {
//    private AcountRepository acountRepository;
//    private AcountService acountService;
//    private ClientRepository clientRepository;
//    private ClientService clientService;
//    private AddressRepository addressRepository;
//    private AddressService addressService;
//
//    @BeforeEach
//    public  void setUp() {
//        this.acountRepository = mock(acountRepository.getClass());
//        this.clientRepository = mock(clientRepository.getClass());
//        this.addressRepository = mock(addressRepository.getClass());
//
//        this.clientService = new ClientService(this.clientRepository);
//        this.addressService = new AddressService(this.addressRepository, this.clientRepository);
//
//        this.acountService = new AcountService(this.acountRepository, this.clientRepository);
//    }
    private AcountRepository acountRepository;
    private AcountService acountService;
    private ClientRepository clientRepository;

    @BeforeEach
    public  void setUp() {
        this.acountRepository = mock(AcountRepository.class);
        this.clientRepository = mock(ClientRepository.class);

        this.acountService = new AcountService(this.acountRepository, this.clientRepository);
    }

    @Test
    public void createAcount_ValidAcount_ReturnsAcount() throws ResourseNotFoundException {
        // Arrange
        Acount acount = new Acount();
        when(acountRepository.save(acount)).thenReturn(acount);

        // Act
        Acount createdAcount = acountService.createAcount(acount);

        // Assert
        assertEquals (acount, createdAcount);
    }

    @Test
    public void createAcount_AcountRepositoryThrowsDataAccessException_ThrowsResourseNotFoundException() {
        // Arrange
        Acount acountToCreate = new Acount();
        when(acountRepository.save(acountToCreate)).thenThrow(new DataAccessException("Simulated DataAccessException") {
        });

        // Act & Assert
        assertThrows(ResourseNotFoundException.class, () -> acountService.createAcount(acountToCreate));
    }

    @Test
    public void getAcountByNumber_ExistingAcountNumber_ReturnsAcount() {
        // Arrange
        Long acountNumber = 1L;
        Acount acount = new Acount();
        when(acountRepository.findById(acountNumber)).thenReturn(Optional.of(acount));

        // Act
        Acount foundAcount = acountService.getAcountByNumber(acountNumber);

        // Assert
        assertEquals(acount, foundAcount);
    }

    @Test
    public void getAcountByNumber_NonExistingAcountNumber_ThrowsRuntimeException() {
        // Arrange
        Long acountNumber = 1L;
        when(acountRepository.findById(acountNumber)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> acountService.getAcountByNumber(acountNumber));
    }
    @Test
    public void getAcounts_ValidAcounts_ReturnsAcountsList() {
        // Arrange
        List<Acount> acounts = new ArrayList<>();
        acounts.add(new Acount());
        acounts.add(new Acount());
        when(acountRepository.findAll()).thenReturn(acounts);

        // Act
        List<Acount> retrievedAcounts = acountService.getAcounts();

        // Assert
        assertEquals(acounts, retrievedAcounts);
    }

    @Test
    public void upDateAcount_ValidAcount_ReturnsUpdatedAcount() throws ResourseNotFoundException {
        // Arrange
        Acount acountToUpdate = new Acount();
        acountToUpdate.setAcountNumber(1L);
        when(acountRepository.findById(acountToUpdate.getAcountNumber())).thenReturn(Optional.of(acountToUpdate));
        when(acountRepository.save(acountToUpdate)).thenReturn(acountToUpdate);

        // Act
        Acount updatedAcount = acountService.upDateAcount(acountToUpdate);

        // Assert
        assertEquals(acountToUpdate, updatedAcount);
    }

    @Test
    public void upDateAcount_NonExistingAcount_ThrowsResourseNotFoundException() {
        // Arrange
        Acount acountToUpdate = new Acount();
        acountToUpdate.setAcountNumber(1L);
        when(acountRepository.findById(acountToUpdate.getAcountNumber())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourseNotFoundException.class, () -> acountService.upDateAcount(acountToUpdate));
    }

    @Test
    public void deleteAcount_ExistingAcountNumber_ReturnsSuccessMessage() throws ResourseNotFoundException {
        // Arrange
        Long acountNumber = 1L;
        when(acountRepository.findById(acountNumber)).thenReturn(Optional.of(new Acount()));

        // Act
        String result = acountService.deleteAcount(acountNumber);

        // Assert
        assertEquals("Cuenta eliminada exitosamente", result);
    }

    @Test
    public void deleteAcount_NonExistingAcountNumber_ThrowsResourseNotFoundException() {
        // Arrange
        Long acountNumber = 1L;
        when(acountRepository.findById(acountNumber)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourseNotFoundException.class, () -> acountService.deleteAcount(acountNumber));
    }
}
