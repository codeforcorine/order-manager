package com.technical.evaluation.orders.features.produit.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "fournisseur")
public class Fournisseur {
    @Id
    @UuidGenerator
    @Column(name = "fournisseur_id", unique = true, nullable = false)
    private UUID id;
    @Column(name = "nom", nullable = false, unique = true)
    private String nom;
    @Column(name = "description")
    private String description;
    @Column(name = "email")
    private String email;
    @Column(name = "telephone", nullable = false)
    private String telephone;
    @Column(name = "adresse")
    private String adresse;
    @Column(name = "date_enregistrement", nullable = false)
    private LocalDateTime dateEnregistrement;
    @Column(name = "date_mise_a_jour")
    private LocalDateTime dateMiseAJour;
}
