package com.technical.evaluation.orders.features.commande.dto;

import com.technical.evaluation.orders.features.produit.dto.ProduitDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO for {@link com.technical.evaluation.orders.features.commande.entity.ArticleCommande}
 */
@Data
@NoArgsConstructor
public class DetailArticleCommande implements Serializable {
    UUID id;
    DetailsProduitCommande produit;
    int quantite;
    BigDecimal prixUnitaire;
    BigDecimal totalLigne;
}