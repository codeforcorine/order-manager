package com.technical.evaluation.orders.features.client.controller;

import com.technical.evaluation.orders.features.client.dto.ClientDto;
import com.technical.evaluation.orders.features.client.dto.CreateClientRequest;
import com.technical.evaluation.orders.features.client.dto.UpdateClientRequest;
import com.technical.evaluation.orders.features.client.service.ClientService;
import com.technical.evaluation.orders.features.commande.dto.CommandeDetailDto;
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

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
@RestController
@RequestMapping(Route.ROOT+Route.CLIENT)
@Tag(name = "Clients", description = "Gestion des clients et de leurs commandes")
public class ClientController {

    private final ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    @Operation(
            summary = "Lister tous les clients",
            description = "Récupère la liste paginée des clients avec possibilité de recherche par nom."
    )

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Liste des clients récupérée avec succès",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Paramètres de requête invalides"
            )
    })
    @GetMapping(value = "", produces = APPLICATION_JSON_VALUE)
    public CustomPage<ClientDto> getAllClients(Pageable pageable){
        return service.findAll(pageable);
    }

    @Operation(
            summary = "Créer un client",
            description = "Ajouter un nouveau client dans le système."
    )
    @PostMapping(value = "", produces = APPLICATION_JSON_VALUE)
    public SimpleApiResponse createClient(@RequestBody @Valid CreateClientRequest request){
        return service.create(request);
    }
    @Operation(
            summary = "Mettre à jour un client",
            description = "Modifier les informations d'un client dans le système."
    )
    @PutMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public SimpleApiResponse update(@PathVariable(name = "id") UUID id, @RequestBody @Valid UpdateClientRequest request){
        return service.update(id, request);
    }

    @Operation(
            summary = "Liste des commandes du client",
            description = "Liste des commandes dans le système."
    )
    @GetMapping(value = "{id}/commandes", produces = APPLICATION_JSON_VALUE)
    public CustomPage<CommandeDetailDto> historiqueCommande(@PathVariable(value = "id") UUID id, Pageable pageable){
        return service.historiqueCommande(id,pageable);
    }
}
