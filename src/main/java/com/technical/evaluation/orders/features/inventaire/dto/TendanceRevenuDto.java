package com.technical.evaluation.orders.features.inventaire.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Month;

@Data
@AllArgsConstructor
public class TendanceRevenuDto implements Serializable {
    private Month mois;
    private BigDecimal revenuTotal;
}
