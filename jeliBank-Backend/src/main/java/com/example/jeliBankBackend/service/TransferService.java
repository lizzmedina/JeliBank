package com.example.jeliBankBackend.service;

import com.example.jeliBankBackend.exceptions.ResourseNotFoundException;
import com.example.jeliBankBackend.model.Client;
import com.example.jeliBankBackend.model.Transfer;
import com.example.jeliBankBackend.repository.ClientRepository;
import com.example.jeliBankBackend.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TransferService {

    private final TransferRepository transferRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public TransferService(TransferRepository transferRepository, ClientRepository clientRepository){
        this.transferRepository = transferRepository;
        this.clientRepository = clientRepository;
    }

    public Transfer createTransfer(Transfer transfer){
        return transferRepository.save(transfer);
    }

    public List<Transfer> getAllTransfers(){
        return transferRepository.findAll();
    }

        public Optional<List<Transfer>> getTransfersByDocumentClient(Long documentClient) throws ResourseNotFoundException {
        Optional<Client> client = clientRepository.findById(documentClient);
        if (client.isPresent()) {
            try {
                return Optional.of(transferRepository.findByOriginAcount_Client(client.get()));
            } catch (DataAccessException e) {
                throw new ResourseNotFoundException("Error al buscar transferencias: " + e.getMessage());
            }
        }
        throw new ResourseNotFoundException("No se encontró el cliente con el número de documento ingresado");
    }

    public Transfer getTransferById(Long transferId){
        if (transferId <= 0){
            throw new IllegalArgumentException("El número de documento no es válido");
        }
        Optional<Transfer> transferOptional = this.transferRepository.findById(transferId);
        if (transferOptional.isPresent()){
            return transferOptional.get();
        }
        throw new RuntimeException("No hay ninguna transacción con el número ingresado");
    }

    public Transfer upDateTransfer(Transfer transferToUpdate) throws ResourseNotFoundException {
        Optional<Transfer> transfer = transferRepository.findById(transferToUpdate.getTransferId());

        if (transfer.isPresent()) {
            try {
                transfer.get().setTransferDate(Objects.isNull(transferToUpdate.getTransferDate()) ?
                        transfer.get().getTransferDate() : transferToUpdate.getTransferDate());

                transfer.get().setAmount(Objects.isNull(transferToUpdate.getAmount()) ?
                        transfer.get().getAmount() : transferToUpdate.getAmount());

                transfer.get().setOriginAcount(Objects.isNull(transferToUpdate.getOriginAcount()) ?
                        transfer.get().getOriginAcount() : transferToUpdate.getOriginAcount());

                return transferRepository.save(transfer.get());
            } catch (DataAccessException e) {
                throw new ResourseNotFoundException("Error al actualizar la transferencia: " + e.getMessage());
            }
        } else {
            throw new ResourseNotFoundException("No existe o no fue posible actualizar la transferencia ingresada");
        }
    }

    public String deleteTranfer(Long transferNumberId) throws ResourseNotFoundException {
        if (transferRepository.findById(transferNumberId).isPresent()){
            try {
                transferRepository.deleteById(transferNumberId);
                return "Transferencia eliminada exitosamente";
            } catch (DataAccessException e) {
                throw new ResourseNotFoundException("Error al eliminar la transferencia: " + e.getMessage());
            }
        }else  {
            throw new ResourseNotFoundException("No existe o no fue posible eliminar la transacción, por favor revise los datos ingresados e intente nuevamnete");
        }
    }
}
