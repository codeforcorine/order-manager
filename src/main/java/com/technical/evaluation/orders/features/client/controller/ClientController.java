package com.technical.evaluation.orders.features.client.controller;

import com.technical.evaluation.orders.features.client.dto.ClientDto;
import com.technical.evaluation.orders.features.client.dto.CreateClientRequest;
import com.technical.evaluation.orders.features.client.service.ClientService;
import com.technical.evaluation.orders.shared.config.CustomPage;
import com.technical.evaluation.orders.shared.dto.SimpleApiResponse;
import com.technical.evaluation.orders.shared.utils.Route;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
@RestController
@RequestMapping(Route.ROOT+Route.CLIENT)
@Tag(name = "Clients", description = "Gestion des clients et de leurs informations")
public class ClientController {

    private final ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    @Operation(
            summary = "Lister tous les clients",
            description = "Récupère la liste paginée des clients avec possibilité de recherche par nom"
    )
    @GetMapping(value = "", produces = APPLICATION_JSON_VALUE)
    public CustomPage<ClientDto> getAllClients(Pageable pageable){
        return service.findAll(pageable);
    }

    @PostMapping(value = "", produces = APPLICATION_JSON_VALUE)
    public SimpleApiResponse createClient(@RequestBody @Valid CreateClientRequest request){
        return service.create(request);
    }
}
