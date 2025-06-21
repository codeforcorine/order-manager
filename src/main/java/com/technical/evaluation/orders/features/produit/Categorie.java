package com.technical.evaluation.orders.features.produit;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class Categorie {
    private int id;
    private String nom;
    private String description;
    private LocalDateTime dateCreation;
}
