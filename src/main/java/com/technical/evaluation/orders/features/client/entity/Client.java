package com.technical.evaluation.orders.features.client.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "client")
@Entity
@Getter
@Setter
public class Client {
    @Id
    @UuidGenerator
    @Column(name = "client_id", nullable = false, unique = true)
    private UUID id;
    @Column(name = "nom", nullable = false)
    private String nom;
    @Column(name = "email")
    private String email;
    @Column(name = "telephone", nullable = false)
    private String telephone;
    @Column(name = "adresse")
    private String adresse;
    @Column(name = "type_client", nullable = false)
    private String typeClient;
    @Column(name = "date_enregistrement", nullable = false)
    private LocalDateTime dateEnregistrement;
    @Column(name = "date_mise_a_jour")
    private LocalDateTime dateMiseAJour;
}
