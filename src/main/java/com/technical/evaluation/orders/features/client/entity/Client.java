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
    @Column(name = "nom")
    private String nom;
    @Column(name = "email")
    private String email;
    @Column(name = "telephone")
    private String telephone;
    @Column(name = "adresse")
    private String adresse;
    @Column(name = "type_client")
    private String typeClient;
    @Column(name = "date_enregistrement")
    private LocalDateTime dateEnregistrement;
}
