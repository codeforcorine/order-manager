package com.technical.evaluation.orders.features.commande.dto;

import com.technical.evaluation.orders.features.client.dto.ClientDto;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * DTO for {@link com.technical.evaluation.orders.features.commande.entity.Commande}
 */
@Data
public class CommandeDto implements Serializable {
    UUID id;
    ClientDto clientId;
    LocalDateTime dateCommande;
    String statut;
    BigDecimal montantTotal;
    String adresseLivraison;
    List<ArticleCommandeDto> articles;
}