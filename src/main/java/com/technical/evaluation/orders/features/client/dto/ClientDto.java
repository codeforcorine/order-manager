package com.technical.evaluation.orders.features.client.dto;

import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.technical.evaluation.orders.features.client.entity.Client}
 */
@Data
public class ClientDto implements Serializable {
    String nom;
    String email;
    String telephone;
    String adresse;
    String typeClient;
    LocalDateTime dateEnregistrement;
}