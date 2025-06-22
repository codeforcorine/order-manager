package com.technical.evaluation.orders.features.produit.repository;

import com.technical.evaluation.orders.features.produit.entity.Produit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProduitRepository extends JpaRepository<Produit, UUID> {

    boolean existsByNomAndFournisseur_Id(String string, UUID fournisseurId);
    Page<Produit> findByQuantiteStockLessThan(int quantite, Pageable pageable);
}
