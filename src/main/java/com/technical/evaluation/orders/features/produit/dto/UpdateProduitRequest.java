package com.technical.evaluation.orders.features.produit.dto;

import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO for {@link com.technical.evaluation.orders.features.produit.entity.Produit}
 */
@Data
public class UpdateProduitRequest implements Serializable {
    String nom;
    String description;
    BigDecimal prix;
    int categorieId;
    UUID fournisseurId;
}