package com.technical.evaluation.orders.features.inventaire.service;

import com.technical.evaluation.orders.features.commande.entity.ArticleCommande;
import com.technical.evaluation.orders.features.commande.repository.ArticleCommandeRepository;
import com.technical.evaluation.orders.features.inventaire.dto.InventaireResumeDto;
import com.technical.evaluation.orders.features.inventaire.dto.ProduitStockDto;
import com.technical.evaluation.orders.features.inventaire.dto.RapportVenteDto;
import com.technical.evaluation.orders.features.produit.entity.Produit;
import com.technical.evaluation.orders.features.produit.service.ProduitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventaireService {
    private final ProduitService produitService;
    private final ArticleCommandeRepository articleCommandeRepository;

    public InventaireResumeDto getResumeInventaire() {
        List<Produit> produits = produitService.findAll();

        long totalStock = 0;
        long seuilCritique = 5;
        List<ProduitStockDto> details = new ArrayList<>();

        for (Produit produit : produits) {
            totalStock += produit.getQuantiteStock();

            ProduitStockDto dto = new ProduitStockDto();
            dto.setId(produit.getId());
            dto.setNom(produit.getNom());
            dto.setQuantiteStock(produit.getQuantiteStock());
            dto.setCategorie(produit.getCategorie().getNom());
            dto.setStockCritique(produit.getQuantiteStock() < seuilCritique);

            details.add(dto);
        }

        long produitsStockCritique = details.stream().filter(ProduitStockDto::isStockCritique).count();

        InventaireResumeDto resume = new InventaireResumeDto();
        resume.setNombreProduits(produits.size());
        resume.setStockTotal(totalStock);
        resume.setProduitsStockCritique(produitsStockCritique);
        resume.setDetailsProduits(details);

        return resume;
    }

    public RapportVenteDto getRapportVentes() {
        List<ArticleCommande> articles = articleCommandeRepository.findAll();

        long totalArticlesVendus = articles.stream().mapToLong(ArticleCommande::getQuantite).sum();
        BigDecimal revenuTotal = articles.stream()
                .map(ArticleCommande::getTotalLigne)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new RapportVenteDto(totalArticlesVendus, revenuTotal);
    }
}
