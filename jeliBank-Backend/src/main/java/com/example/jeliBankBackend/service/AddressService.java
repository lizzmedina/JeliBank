package com.example.jeliBankBackend.service;

import com.example.jeliBankBackend.exceptions.ResourseNotFoundException;
import com.example.jeliBankBackend.model.Address;
import com.example.jeliBankBackend.model.Client;
import com.example.jeliBankBackend.repository.AddressRepository;
import com.example.jeliBankBackend.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository, ClientRepository clientRepository){
        this.addressRepository = addressRepository;
        this.clientRepository = clientRepository;
    }

    public Address createAddress(Address address) throws ResourseNotFoundException {
        try {
            return addressRepository.save(address);
        }catch (DataAccessException e){
            throw new ResourseNotFoundException("no se pudo crear la dirección");
        }
    }

    public List<Address> getAllAddress() throws ResourseNotFoundException {
        try{
            return addressRepository.findAll();
        }catch (DataAccessException e){
            throw new ResourseNotFoundException("no hay direcciones creadas");
        }

    }

    public Optional<Address> getAddressById(Long idAddress) throws ResourseNotFoundException {
        try {
            return addressRepository.findById(idAddress);

        }catch (DataAccessException e){
            throw new ResourseNotFoundException("no existe una direccion para ese id");
        }
    }

    public Optional<Address> getAddressByClient(Long documentClient) throws ResourseNotFoundException {
        if (documentClient <= 0){
            throw new IllegalArgumentException("El número de documento no es válido");
        }
        Optional<Client> clientOptional = this.clientRepository.findById(documentClient);
        if (clientOptional.isPresent()){
            try {
                return addressRepository.findByClient_NumberDocumentId(documentClient);
            }catch (DataAccessException e) {
                throw new ResourseNotFoundException("no se encontró el documento ingresado" + e.getMessage());
            }
        }
        throw new RuntimeException("No hay ninguna direccion para el número de documento ingresado");
    }

    public Address upDateAddress(Long addressId, Address updatedAddress) throws ResourseNotFoundException {
        Optional<Address> existingAddress;
        try {
            existingAddress = addressRepository.findById(addressId);
        } catch (DataAccessException e) {
            throw new ResourseNotFoundException("Error al actualizar la dirección: " + e.getMessage());
        }
        if (existingAddress.isPresent()) {
            Address addressToUpdate = existingAddress.get();

            addressToUpdate.setAddress(updatedAddress.getAddress());
            addressToUpdate.setCity(updatedAddress.getCity());
            addressToUpdate.setNeighborhood(updatedAddress.getNeighborhood());
            addressToUpdate.setHouseType(updatedAddress.getHouseType());

            return addressRepository.save(addressToUpdate);

        } else {
            throw new ResourseNotFoundException("No existe o no fue posible actualizar la dirección ingresada");
        }
    }

    public String deleteAddress(Long addressNumberId) throws ResourseNotFoundException {
        if (addressRepository.findById(addressNumberId).isPresent()){
            try {
                addressRepository.deleteById(addressNumberId);
                return "Dirección eliminada exitosamente";
            } catch (DataAccessException e) {
                throw new ResourseNotFoundException("Error al eliminar la dirección: " + e.getMessage());
            }
        }else  {
            throw new ResourseNotFoundException("No existe o no fue posible eliminar  la dirección, por favor revise los datos ingresados e intente nuevamnete");
        }
    }

}
