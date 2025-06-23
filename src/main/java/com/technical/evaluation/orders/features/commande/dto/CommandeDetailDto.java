package com.technical.evaluation.orders.features.commande.dto;

import com.technical.evaluation.orders.features.client.dto.ClientDto;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Data
public class CommandeDetailDto implements Serializable {

    UUID id;
    ClientDto client;
    LocalDateTime dateCommande;
    String statut;
    BigDecimal montantTotal;
    String adresseLivraison;
    List<DetailArticleCommande> articles;
}
