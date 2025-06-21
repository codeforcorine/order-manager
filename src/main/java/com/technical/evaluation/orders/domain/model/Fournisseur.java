package com.technical.evaluation.orders.domain.model;

import lombok.Data;

@Data
public class Fournisseur {
    private Long id;
    private String nom;
    private String email;
    private String telephone;
    private String adresse;
}
