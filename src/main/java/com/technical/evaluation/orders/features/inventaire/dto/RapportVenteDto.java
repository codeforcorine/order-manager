package com.technical.evaluation.orders.features.inventaire.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
@Data
@AllArgsConstructor
public class RapportVenteDto implements Serializable {
    private long quantiteTotaleVendue;
    private BigDecimal revenuTotal;


}
