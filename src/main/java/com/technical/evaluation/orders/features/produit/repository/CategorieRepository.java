package com.technical.evaluation.orders.features.produit.repository;

import com.technical.evaluation.orders.features.produit.entity.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieRepository extends JpaRepository<Categorie, Integer> {
}
