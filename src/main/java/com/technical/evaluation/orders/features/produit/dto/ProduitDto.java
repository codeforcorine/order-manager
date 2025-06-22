package com.technical.evaluation.orders.features.produit.dto;

import com.technical.evaluation.orders.features.produit.dto.CategorieDto;
import com.technical.evaluation.orders.features.produit.dto.FournisseurDto;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link com.technical.evaluation.orders.features.produit.entity.Produit}
 */
@Data
public class ProduitDto implements Serializable {
    UUID id;
    String nom;
    String description;
    BigDecimal prix;
    int quantiteStock;
    CategorieDto categorie;
    FournisseurDto fournisseur;
    LocalDateTime dateEnregistrement;
    LocalDateTime dateMiseAJour;
}