package com.technical.evaluation.orders.features.commande;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.UUID;
@Data
@Entity
@Table(name = "article_commande")
public class ArticleCommande {
    @Id
    @UuidGenerator
    @Column(name = "article_commande_id", nullable = false, unique = true)
    private UUID id;
    @Column(name = "nom")
    private UUID commandeId;
    private UUID produitId;
    private int quantite;
    private BigDecimal prixUnitaire;
    private BigDecimal totalLigne;

}
