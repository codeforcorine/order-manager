package com.technical.evaluation.orders.domain.model;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;
@Data
public class Produit {
    private UUID id;
    private String nom;
    private String description;
    private BigDecimal prix;
    private BigInteger quantitéStock;
    private Categorie categorie;
    private Fournisseur fournisseur;
}
