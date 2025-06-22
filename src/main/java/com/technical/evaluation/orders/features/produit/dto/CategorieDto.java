package com.technical.evaluation.orders.features.produit.dto;

import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.technical.evaluation.orders.features.produit.entity.Categorie}
 */
@Data
public class CategorieDto implements Serializable {
    int id;
    String nom;
    String description;
    LocalDateTime dateCreation;
}