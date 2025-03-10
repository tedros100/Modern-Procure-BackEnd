package org.tedrosberihu.modernprocure.services.Impl;

import org.tedrosberihu.modernprocure.entities.Client;
import org.tedrosberihu.modernprocure.exceptions.ResourceNotFoundException;
import org.tedrosberihu.modernprocure.models.ClientModel;
import org.tedrosberihu.modernprocure.repository.ClientRepository;
import org.tedrosberihu.modernprocure.services.ClientService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.tedrosberihu.modernprocure.common.Constants.RECORD_DOES_NOT_EXIST_FOR_ID;
import static org.tedrosberihu.modernprocure.specifications.ClientSpecification.withActive;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    @NonNull
    private ClientRepository clientRepository;

    @Override
    public ClientModel addClient(ClientModel clientmodel) {
        var client = clientmodel.disassemble();
        return new ClientModel(clientRepository.save(client));
    }

    @Override
    public Page<ClientModel> getAllClients(Pageable pageable) {
        Specification<Client> clientSpecification = Specification.where(withActive(true));
        return clientRepository.findAll(clientSpecification, pageable).map(ClientModel::new);
    }

    @Override
    public ClientModel getClientWithId(Long clientId) {
        return clientRepository.findById((clientId)).map(ClientModel::new).orElseThrow(() -> new ResourceNotFoundException(RECORD_DOES_NOT_EXIST_FOR_ID + clientId));
    }

    @Override
    public ClientModel updateClient(ClientModel clientmodel, Long clientId) {
        return clientRepository.findById(clientId).map(client -> {
            client.setClientName(clientmodel.getClientName());
            client.setClientNumber(clientmodel.getClientNumber() == null ? new UUID(9, 0).toString() : client.getClientNumber());
            client.setDescription(clientmodel.getDescription() == null ? client.getClientName() : clientmodel.getDescription());
            client.setEmail(client.getEmail());
            client.setAddress(client.getAddress());
            client.setActive(true);
            return new ClientModel(clientRepository.save(client));
        }).orElseThrow(() -> new ResourceNotFoundException("Resource not exist"));

    }

    @Override
    public ClientModel deleteClient(Long clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(null);
        clientRepository.delete(client);
        return null;
    }

}
