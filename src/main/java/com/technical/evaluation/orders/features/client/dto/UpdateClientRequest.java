package com.technical.evaluation.orders.features.client.dto;

import com.technical.evaluation.orders.features.client.enums.TypeClient;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.technical.evaluation.orders.features.client.entity.Client}
 */
@Data
public class UpdateClientRequest implements Serializable {
    String nom;
    String email;
    String telephone;
    String adresse;
    TypeClient typeClient;
}