package com.technical.evaluation.orders.features.produit.repository;

import com.technical.evaluation.orders.features.produit.entity.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FournisseurRepository extends JpaRepository<Fournisseur, UUID> {
}
