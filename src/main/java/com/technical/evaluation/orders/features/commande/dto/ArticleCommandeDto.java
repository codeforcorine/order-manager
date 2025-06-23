package com.technical.evaluation.orders.features.commande.dto;

import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link com.technical.evaluation.orders.features.commande.entity.ArticleCommande}
 */
@Data
public class ArticleCommandeDto implements Serializable {
    private UUID produitId;
    int quantite;

}