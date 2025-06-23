package com.technical.evaluation.orders.features.commande.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;
@Data
public class DetailsProduitCommande implements Serializable {
    UUID id;
    String nom;
    String description;
    String categorie;
    String fournisseur;
    LocalDateTime dateEnregistrement;
}
