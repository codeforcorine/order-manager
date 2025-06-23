package com.technical.evaluation.orders.features.inventaire.service;

import com.technical.evaluation.orders.features.commande.entity.ArticleCommande;
import com.technical.evaluation.orders.features.commande.entity.Commande;
import com.technical.evaluation.orders.features.commande.repository.ArticleCommandeRepository;
import com.technical.evaluation.orders.features.commande.service.CommandeService;
import com.technical.evaluation.orders.shared.utils.Statut;
import com.technical.evaluation.orders.features.inventaire.dto.InventaireResumeDto;
import com.technical.evaluation.orders.features.inventaire.dto.ProduitStockDto;
import com.technical.evaluation.orders.features.inventaire.dto.RapportVenteDto;
import com.technical.evaluation.orders.features.inventaire.dto.TendanceRevenuDto;
import com.technical.evaluation.orders.features.produit.entity.Produit;
import com.technical.evaluation.orders.features.produit.service.ProduitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.TreeMap;


import java.math.BigDecimal;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventaireService {
    private final ProduitService produitService;
    private final ArticleCommandeRepository articleCommandeRepository;
    private final CommandeService commandeService;

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



    public List<TendanceRevenuDto> getTendancesRevenus() {
        int anneeEnCours = Year.now().getValue();

        List<Commande> commandes = commandeService.findAll().stream()
                .filter(c -> c.getStatut() == Statut.LIVREE.name())
                .filter(c -> c.getDateCommande().getYear() == anneeEnCours)
                .collect(Collectors.toList());

        Map<Month, BigDecimal> revenusParMois = commandes.stream()
                .collect(Collectors.groupingBy(
                        c -> c.getDateCommande().getMonth(),
                        TreeMap::new, // pour trier les mois
                        Collectors.reducing(BigDecimal.ZERO, Commande::getMontantTotal, BigDecimal::add)
                ));

        return revenusParMois.entrySet().stream()
                .map(e -> new TendanceRevenuDto(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

}
