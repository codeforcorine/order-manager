package com.technical.evaluation.orders.features.inventaire.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class InventaireResumeDto implements Serializable {
    private long nombreProduits;
    private long stockTotal;
    private long produitsStockCritique;
    private List<ProduitStockDto> detailsProduits;
}

