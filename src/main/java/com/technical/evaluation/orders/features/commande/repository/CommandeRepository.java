package com.technical.evaluation.orders.features.commande.repository;

import com.technical.evaluation.orders.features.commande.entity.Commande;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommandeRepository extends JpaRepository<Commande, UUID> {
    Page<Commande> findAll(Pageable pageable);
}
