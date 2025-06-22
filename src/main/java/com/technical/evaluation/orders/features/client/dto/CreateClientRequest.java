package com.technical.evaluation.orders.features.client.dto;

import com.technical.evaluation.orders.features.client.enums.TypeClient;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * DTO for {@link com.technical.evaluation.orders.features.client.entity.Client}
 */
@Data
public class CreateClientRequest implements Serializable {
    @NotNull
    String nom;
    String email;
    @NotNull
    String telephone;
    @NotNull
    String adresse;
    @NotNull
    TypeClient typeClient;
}