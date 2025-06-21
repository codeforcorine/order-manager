package com.technical.evaluation.orders.domain.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;
@Data
public class ArticleCommande {
    private UUID id;
    private UUID commandeId;
    private UUID produitId;
    private int quantite;
    private BigDecimal prixUnitaire;
    private BigDecimal totalLigne;

}
