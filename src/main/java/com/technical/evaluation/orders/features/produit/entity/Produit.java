package com.technical.evaluation.orders.features.produit.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
@Getter
@Setter
@Entity
@Table(name = "produit")
public class Produit {
    @Id
    @UuidGenerator
    @Column(name = "produit_id", unique = true, nullable = false)
    private UUID id;

    @Column(name = "nom", nullable = false, unique = true)
    private String nom;

    @Column(name = "description")
    private String description;

    @Column(name = "prix", nullable = false)
    private BigDecimal prix;

    @Column(name = "quantite_stock", nullable = false)
    private int quantiteStock;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categorie_id", referencedColumnName = "categorie_id", nullable = false)
    private Categorie categorie;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fournisseur_id", referencedColumnName = "fournisseur_id", nullable = false)
    private Fournisseur fournisseur;

    @Column(name = "date_enregistrement", nullable = false)
    private LocalDateTime dateEnregistrement;

    @Column(name = "date_mise_a_jour")
    private LocalDateTime dateMiseAJour;
}
