package com.technical.evaluation.orders.features.client.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class StatistiquesClientDto implements Serializable {
    private Long totalCommandes;
    private BigDecimal totalMontantDepense;
    private BigDecimal montantMoyenParCommande;
    private LocalDateTime derniereCommande;
    private Long produitsCommandes;

}
