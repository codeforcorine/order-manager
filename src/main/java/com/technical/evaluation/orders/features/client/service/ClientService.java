package com.technical.evaluation.orders.features.client.service;

import com.technical.evaluation.orders.features.client.dto.ClientDto;
import com.technical.evaluation.orders.features.client.entity.Client;
import com.technical.evaluation.orders.features.client.mapper.ClientMapper;
import com.technical.evaluation.orders.features.client.repository.ClientRepository;
import com.technical.evaluation.orders.shared.config.CustomPage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService{

    private final ClientRepository repository;
    private final ClientMapper mapper;

    public CustomPage<ClientDto> findAll(Pageable pageable) {
        Page<Client> clients = repository.findAll(pageable);
        return new CustomPage<>(clients, mapper.clientDtoList(clients.toList()));
    }
}
