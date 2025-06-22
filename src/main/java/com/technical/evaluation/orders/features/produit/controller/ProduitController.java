package com.technical.evaluation.orders.features.produit.controller;

import com.technical.evaluation.orders.features.produit.dto.CreateProduitRequest;
import com.technical.evaluation.orders.features.produit.dto.ProduitDto;
import com.technical.evaluation.orders.features.produit.dto.UpdateProduitRequest;
import com.technical.evaluation.orders.features.produit.service.ProduitService;
import com.technical.evaluation.orders.shared.config.CustomPage;
import com.technical.evaluation.orders.shared.dto.SimpleApiResponse;
import com.technical.evaluation.orders.shared.utils.Route;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@Tag(name = "Produits", description = "Gestion des produits")
@RequestMapping(Route.ROOT+Route.PRODUIT)
public class ProduitController {
    private final ProduitService service;

    @Operation(
            summary = "Lister tous les produits",
            description = "Récupère la liste paginée des produits avec possibilité de recherche par nom."
    )

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Liste des produits récupérée avec succès",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Paramètres de requête invalides"
            )
    })
    @GetMapping(value = "", produces = APPLICATION_JSON_VALUE)
    public CustomPage<ProduitDto> getAllproduits(Pageable pageable){
        return service.findAll(pageable);
    }

    @Operation(
            summary = "Ajouter un produit",
            description = "Ajouter un nouveau produit dans le système."
    )
    @PostMapping(value = "", produces = APPLICATION_JSON_VALUE)
    public SimpleApiResponse addProduct(@RequestBody @Valid CreateProduitRequest request){
        return service.create(request);
    }
    @Operation(
            summary = "Mettre à jour un produit",
            description = "Modifier les informations d'un produit dans le système."
    )
    @PutMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public SimpleApiResponse update(@PathVariable(name = "id") UUID id, @RequestBody @Valid UpdateProduitRequest request){
        return service.update(id, request);
    }

    @Operation(
            summary = "Mettre à jour la quantité en stock pour un produit",
            description = "Modifier la quantité en stock pour un produit dans le système."
    )
    @PutMapping(value = "/{id}/stock", produces = APPLICATION_JSON_VALUE)
    public SimpleApiResponse update(@PathVariable(name = "id") UUID id, @RequestBody @Valid int request){
        return service.updateStock(id, request);
    }

    @Operation(
            summary = "Lister tous les produits avec un stock bas",
            description = "Récupère la liste paginée des produits avec un stock bas."
    )
    @GetMapping(value = "/stock-bas", produces = APPLICATION_JSON_VALUE)
    public CustomPage<ProduitDto> getAllproduitsWithLowStock(Pageable pageable){
        return service.findAllWithLowStock(pageable);
    }
}
