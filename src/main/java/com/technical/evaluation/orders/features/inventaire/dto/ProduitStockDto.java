package com.technical.evaluation.orders.features.inventaire.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;
@Data
public class ProduitStockDto implements Serializable {
    private UUID id;
    private String nom;
    private int quantiteStock;
    private String categorie;
    private boolean stockCritique;
}
