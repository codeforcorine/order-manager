package com.technical.evaluation.orders.features.commande.repository;

import com.technical.evaluation.orders.features.commande.entity.ArticleCommande;
import com.technical.evaluation.orders.features.commande.entity.Commande;
import com.technical.evaluation.orders.features.produit.entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ArticleCommandeRepository extends JpaRepository<ArticleCommande, UUID> {
    List<ArticleCommande> findAllByCommande(Commande commande);
    List<ArticleCommande> findAllByProduit(Produit produit);
}
