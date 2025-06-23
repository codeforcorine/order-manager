package com.technical.evaluation.orders.features.inventaire.controller;

import com.technical.evaluation.orders.features.inventaire.dto.InventaireResumeDto;
import com.technical.evaluation.orders.features.inventaire.dto.RapportVenteDto;
import com.technical.evaluation.orders.features.inventaire.service.InventaireService;
import com.technical.evaluation.orders.shared.utils.Route;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(Route.ROOT+Route.INVENTAIRE)
@Tag(name = "Inventaire & Analyses", description = "Gestion de l'inventaire & Analyses")
public class InventaireController {
    private final InventaireService service;

    @Operation(
            summary = "Aperçu de l’inventaire",
            description = "Aperçu de l’inventaire."
    )
    @GetMapping(value = "/resume", produces = APPLICATION_JSON_VALUE)
    public InventaireResumeDto resume(){
        return service.getResumeInventaire();
    }

    @Operation(
            summary = "Analyses des ventes",
            description = "Analyses des ventes."
    )
    @GetMapping(value = "/rapport-ventes", produces = APPLICATION_JSON_VALUE)
    public RapportVenteDto analyseVente(){
        return service.getRapportVentes();
    }

}
