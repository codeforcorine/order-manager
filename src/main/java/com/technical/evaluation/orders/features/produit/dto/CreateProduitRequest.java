package com.technical.evaluation.orders.features.produit.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CreateProduitRequest implements Serializable {
    @NotNull
    String nom;
    @NotNull
    String description;
    @NotNull
    BigDecimal prix;
    @NotNull
    int quantiteStock;
    @NotNull
    int categorieId;
    @NotNull
    UUID fournisseurId;
}
