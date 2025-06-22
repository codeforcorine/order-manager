package com.technical.evaluation.orders.features.commande.entity;

import com.technical.evaluation.orders.features.produit.entity.Produit;
import jakarta.persistence.*;
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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "commande_id", referencedColumnName = "commande_id", nullable = false)
    private Commande commande;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "produit_id", referencedColumnName = "produit_id", nullable = false)
    private Produit produitId;
    private int quantite;
    private BigDecimal prixUnitaire;
    private BigDecimal totalLigne;

}
