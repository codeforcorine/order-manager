package com.technical.evaluation.orders.features.produit.dto;

import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link com.technical.evaluation.orders.features.produit.entity.Fournisseur}
 */
@Data
public class FournisseurDto implements Serializable {
    UUID id;
    String nom;
    String description;
    String email;
    String telephone;
    String adresse;
    String typeClient;
    LocalDateTime dateEnregistrement;
    LocalDateTime dateMiseAJour;
}