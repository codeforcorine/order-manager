package com.technical.evaluation.orders.features.commande;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
@Data
public class Commande {
    private UUID id;
    private UUID clientId;
    private LocalDateTime dateCommande;
    private Statut statut;
    private BigDecimal montantTotal;
    private String adresseLivraison;
}
