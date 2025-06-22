package com.technical.evaluation.orders.features.client.controller;

import com.technical.evaluation.orders.features.client.dto.ClientDto;
import com.technical.evaluation.orders.features.client.service.ClientService;
import com.technical.evaluation.orders.shared.config.CustomPage;
import com.technical.evaluation.orders.shared.utils.Route;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
@RestController
@RequestMapping(Route.ROOT+Route.CLIENT)
public class ClientController {

    private final ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    @GetMapping(value = "", produces = APPLICATION_JSON_VALUE)
    public CustomPage<ClientDto> getAllClients(Pageable pageable){
        return service.findAll(pageable);
    }
}
