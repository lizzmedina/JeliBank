package com.example.jeliBankBackend;

import com.example.jeliBankBackend.exceptions.ResourseNotFoundException;
import com.example.jeliBankBackend.model.Client;
import com.example.jeliBankBackend.repository.ClientRepository;
import com.example.jeliBankBackend.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.dao.DataAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClientServiceTests {
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    @BeforeEach
    public void setUp() {
        clientRepository = mock(ClientRepository.class);
        clientService = new ClientService(clientRepository);
    }

    @Test
    public void createClient_ValidClient_ReturnsClient() throws ResourseNotFoundException {
        // Arrange
        Client client = new Client();
        when(clientRepository.save(client)).thenReturn(client);

        // Act
        Client createdClient = clientService.createClient(client);

        // Assert
        assertEquals(client, createdClient);
    }

    @Test
    public void createClient_ClientRepositoryThrowsDataAccessException_ThrowsResourseNotFoundException() {
        // Arrange
        Client clientToCreate = new Client();
        when(clientRepository.save(clientToCreate)).thenThrow(new DataAccessException("Simulated DataAccessException") {
        });

        // Act & Assert
        assertThrows(ResourseNotFoundException.class, () -> clientService.createClient(clientToCreate));
    }


    @Test
    public void getAllClients_ValidClients_ReturnsClientList() throws ResourseNotFoundException {
        // Arrange
        List<Client> clients = new ArrayList<>();
        clients.add(new Client());
        clients.add(new Client());
        when(clientRepository.findAll()).thenReturn(clients);

        // Act
        List<Client> retrievedClients = clientService.getAllClients();

        // Assert
        assertEquals(clients, retrievedClients);
    }

    @Test
    public void getAllClients_NoClientsInRepository_ThrowsResourseNotFoundException() {
        // Arrange
        when(clientRepository.findAll()).thenReturn(new ArrayList<>());

        // Act & Assert
        assertThrows(ResourseNotFoundException.class, () -> clientService.getAllClients());
    }

    @Test
    public void getClientById_ExistingClient_ReturnsClient() throws ResourseNotFoundException {
        // Arrange
        Long clientId = 1L;
        Client client = new Client();
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

        // Act
        Optional<Client> foundClient = clientService.getClientById(clientId);

        // Assert
        assertTrue(foundClient.isPresent());
        assertEquals(client, foundClient.get());
    }

    @Test
    public void getClientById_NonExistingClient_ThrowsResourseNotFoundException() {
        // Arrange
        Long clientId = 1L;
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourseNotFoundException.class, () -> clientService.getClientById(clientId));
    }

    @Test
    public void getClientByDocument_ExistingDocument_ReturnsClient() {
        // Arrange
        Long documentNumber = 12345L;
        Client client = new Client();
        when(clientRepository.findByNumberDocumentId(documentNumber)).thenReturn(Optional.of(client));

        // Act
        Client foundClient = clientService.getClientByDocument(documentNumber);

        // Assert
        assertEquals(client, foundClient);
    }

    @Test
    public void getClientByDocument_NonExistingDocument_ThrowsRuntimeException() {
        // Arrange
        Long documentNumber = 12345L;
        when(clientRepository.findByNumberDocumentId(documentNumber)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> clientService.getClientByDocument(documentNumber));
    }

    @Test
    public void updateClient_NonExistingClient_ThrowsResourseNotFoundException() {
        // Arrange
        Long nonExistingClientId = 999L;
        when(clientRepository.findById(nonExistingClientId)).thenReturn(Optional.empty());

        Client updatedClient = new Client();
        updatedClient.setClient_id(nonExistingClientId);
        updatedClient.setName("UpdatedName");
        updatedClient.setLastName("UpdatedLastName");
        updatedClient.setNumberDocumentId(54321L);

        // Act & Assert
        assertThrows(ResourseNotFoundException.class, () -> clientService.upDateClient(updatedClient));
    }

    @Test
    public void updateClient_ExistingClientWithAllFieldsUpdated_ReturnsUpdatedClient() throws ResourseNotFoundException {
        // Arrange
        Client existingClient = new Client();
        existingClient.setClient_id(1L);
        existingClient.setName("John");
        existingClient.setLastName("Doe");
        existingClient.setNumberDocumentId(12345L);

        when(clientRepository.findById(existingClient.getClient_id())).thenReturn(Optional.of(existingClient));

        Client updatedClient = new Client();
        updatedClient.setClient_id(1L); // Mismo ID
        updatedClient.setName("UpdatedName");
        updatedClient.setLastName("UpdatedLastName");
        updatedClient.setNumberDocumentId(54321L);

        when(clientRepository.save(existingClient)).thenReturn(updatedClient);

        // Act
        Client result = clientService.upDateClient(updatedClient);

        // Assert
        assertEquals(updatedClient.getName(), result.getName());
        assertEquals(updatedClient.getLastName(), result.getLastName());
        assertEquals(updatedClient.getNumberDocumentId(), result.getNumberDocumentId());
    }
    @Test
    public void updateClient_ExistingClientWithSomeFieldsNull_ReturnsUpdatedClient() throws ResourseNotFoundException {
        // Arrange
        Client existingClient = new Client();
        existingClient.setClient_id(1L);
        existingClient.setName("John");
        existingClient.setLastName("Doe");
        existingClient.setNumberDocumentId(12345L);

        when(clientRepository.findById(existingClient.getClient_id())).thenReturn(Optional.of(existingClient));

        Client updatedClient = new Client();
        updatedClient.setClient_id(1L);
        updatedClient.setName("UpdatedName");
        updatedClient.setLastName(null);
        updatedClient.setNumberDocumentId(54321L);

        when(clientRepository.save(existingClient)).thenAnswer(invocation -> {
            Client savedClient = invocation.getArgument(0);

            return savedClient;
        });

        // Act
        Client result = clientService.upDateClient(updatedClient);

        // Assert
        assertEquals(updatedClient.getName(), result.getName());
        assertEquals("Doe", result.getLastName());
        assertEquals(updatedClient.getNumberDocumentId(), result.getNumberDocumentId());
    }

    @Test
    public void deleteClient_ExistingClient_ReturnsSuccessMessage() throws ResourseNotFoundException {
        // Arrange
        Long clientId = 1L;
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(new Client()));

        // Act
        String result = clientService.deleteClient(clientId);

        // Assert
        assertEquals("Cliente eliminado exitosamente", result);
    }

    @Test
    public void deleteClient_NonExistingClient_ThrowsResourseNotFoundException() {
        // Arrange
        Long clientId = 1L;
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourseNotFoundException.class, () -> clientService.deleteClient(clientId));
    }

    @Test
    public void deleteClient_ClientIdIsNull_ThrowsResourseNotFoundException() {
        // Act & Assert
        assertThrows(ResourseNotFoundException.class, () -> clientService.deleteClient(null));
    }

    @Test
    public void deleteClient_ClientIdIsZero_ThrowsResourseNotFoundException() {
        // Act & Assert
        assertThrows(ResourseNotFoundException.class, () -> clientService.deleteClient(0L));
    }

}
