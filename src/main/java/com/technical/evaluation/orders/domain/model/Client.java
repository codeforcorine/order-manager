package com.technical.evaluation.orders.domain.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Client {

    private UUID id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String adresse;
    private TypeClient typeClient;
    private LocalDateTime dateEnregistrement;


}
