package com.technical.evaluation.orders.features.produit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "categorie_produit")
@AllArgsConstructor
@NoArgsConstructor
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "categorie_id", unique = true, nullable = false)
    private int id;
    @Column(name = "nom", nullable = false, unique = true)
    private String nom;
    @Column(name = "description")
    private String description;
    @Column(name = "date_creation", nullable = false)
    private LocalDateTime dateCreation;

    public Categorie(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }
}
