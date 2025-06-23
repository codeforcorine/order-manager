package com.technical.evaluation.orders.features.commande.controller;

import com.technical.evaluation.orders.features.commande.dto.CommandeDetailDto;
import com.technical.evaluation.orders.features.commande.dto.CreateCommandeRequest;
import com.technical.evaluation.orders.features.commande.service.CommandeService;
import com.technical.evaluation.orders.shared.utils.Statut;
import com.technical.evaluation.orders.shared.config.CustomPage;
import com.technical.evaluation.orders.shared.dto.SimpleApiResponse;
import com.technical.evaluation.orders.shared.utils.Route;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(Route.ROOT+Route.COMMANDE)
@Tag(name = "Commandes", description = "Traitement des Commandes")
public class CommandeController {
    private final CommandeService service;

    @Operation(
            summary = "Créer une commande",
            description = "Ajouter une nouvelle commande dans le système."
    )
    @PostMapping(value = "", produces = APPLICATION_JSON_VALUE)
    public SimpleApiResponse createCommande(@RequestBody @Valid CreateCommandeRequest request){
        return service.creerCommande(request);
    }
    @Operation(
            summary = " Obtenir les détails de la commande",
            description = " Obtenir les détails de la commande dans le système."
    )
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public CommandeDetailDto commandeDetail(@PathVariable(value = "id") UUID id){
        return service.detailerCommande(id);
    }

    @Operation(
            summary = "Mettre à jour le statut de la commande",
            description = "Mettre à jour le statut de la commande dans le système."
    )
    @PutMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public SimpleApiResponse updateStatus(@PathVariable(value = "id") UUID id, Statut statut){
        return service.changerStatut(id, statut);
    }
    @Operation(
            summary = "Annuler la commande",
            description = "Annuler la commande dans le système."
    )
    @PostMapping(value = "/{id}/annuler", produces = APPLICATION_JSON_VALUE)
    public SimpleApiResponse failOrder(@PathVariable(value = "id") UUID id){
        return service.annulerCommande(id);
    }

    @Operation(
            summary = "Liste des commandes",
            description = "Liste des commandes dans le système."
    )
    @GetMapping(value = "", produces = APPLICATION_JSON_VALUE)
    public CustomPage<CommandeDetailDto> updateStatus(Pageable pageable){
        return service.listerCommandes(pageable);
    }


}
