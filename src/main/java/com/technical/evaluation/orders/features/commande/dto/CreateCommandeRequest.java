package com.technical.evaluation.orders.features.commande.dto;

import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * DTO for {@link com.technical.evaluation.orders.features.commande.entity.Commande}
 */
@Data
public class CreateCommandeRequest implements Serializable {
    private UUID clientId;
    String adresseLivraison;
    private List<ArticleCommandeDto> articles;
}