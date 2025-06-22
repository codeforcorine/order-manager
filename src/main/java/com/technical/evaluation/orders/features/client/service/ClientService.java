package com.technical.evaluation.orders.features.client.service;

import com.technical.evaluation.orders.features.client.dto.ClientDto;
import com.technical.evaluation.orders.features.client.dto.CreateClientRequest;
import com.technical.evaluation.orders.features.client.entity.Client;
import com.technical.evaluation.orders.features.client.mapper.ClientMapper;
import com.technical.evaluation.orders.features.client.repository.ClientRepository;
import com.technical.evaluation.orders.shared.config.CustomPage;
import com.technical.evaluation.orders.shared.dto.ApiResponseCode;
import com.technical.evaluation.orders.shared.dto.SimpleApiResponse;
import com.technical.evaluation.orders.shared.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ClientService{

    private final ClientRepository repository;
    private final ClientMapper mapper;

    public CustomPage<ClientDto> findAll(Pageable pageable) {
        Page<Client> clients = repository.findAll(pageable);
        return new CustomPage<>(clients, mapper.clientDtoList(clients.toList()));
    }
    void existsByNomAndTelephone(String nom, String telephone){
        if(repository.existsByNomAndTelephone(nom, telephone)) throw new ApplicationException(ApiResponseCode.DATA_ALREADY_EXISTS, "Donnéed déjà existante. ");
    }

    public SimpleApiResponse create(CreateClientRequest request) {
        existsByNomAndTelephone(request.getNom(), request.getTelephone());
        Client client = mapper.convertToEntity(request);
        client.setTypeClient(request.getTypeClient().name());
        client.setDateEnregistrement(LocalDateTime.now());
        repository.save(client);
        return new SimpleApiResponse(ApiResponseCode.SUCCESS.getLabel(), "Client enregistré avec succès.");
    }
}
