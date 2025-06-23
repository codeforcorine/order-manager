package com.technical.evaluation.orders.features.commande.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class HistoriqueVenteProduitDto implements Serializable {

    private UUID produitId;
    private String nomProduit;
    private long quantiteTotaleVendue;
    private long nombreCommandes;
    private BigDecimal revenuTotal;
    private LocalDateTime derniereVente;


}
