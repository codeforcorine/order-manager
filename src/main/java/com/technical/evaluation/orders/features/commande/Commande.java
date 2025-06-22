package com.technical.evaluation.orders.features.commande;

import com.technical.evaluation.orders.features.client.entity.Client;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
@Getter
@Setter
@Entity
@Table(name = "commande")
public class Commande {
    @Id
    @UuidGenerator
    @Column(name = "commande_id", unique = true, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", referencedColumnName = "client_id", nullable = false)
    private Client clientId;

    @Column(name = "date_commande")
    private LocalDateTime dateCommande;

    @Column(name = "statut")
    private String statut;

    @Column(name = "montant_total")
    private BigDecimal montantTotal;

    @Column(name = "adresse_livraison", nullable = false)
    private String adresseLivraison;
}
