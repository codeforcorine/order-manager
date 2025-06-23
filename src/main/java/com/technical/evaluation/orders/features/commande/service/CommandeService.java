package com.technical.evaluation.orders.features.commande.service;

import com.technical.evaluation.orders.features.client.entity.Client;
import com.technical.evaluation.orders.features.client.service.ClientService;
import com.technical.evaluation.orders.features.commande.dto.ArticleCommandeDto;
import com.technical.evaluation.orders.features.commande.dto.CommandeDetailDto;
import com.technical.evaluation.orders.features.commande.dto.CreateCommandeRequest;
import com.technical.evaluation.orders.features.commande.dto.DetailArticleCommande;
import com.technical.evaluation.orders.features.commande.entity.ArticleCommande;
import com.technical.evaluation.orders.features.commande.entity.Commande;
import com.technical.evaluation.orders.features.commande.mapper.CommandeMapper;
import com.technical.evaluation.orders.features.commande.repository.ArticleCommandeRepository;
import com.technical.evaluation.orders.features.commande.repository.CommandeRepository;
import com.technical.evaluation.orders.features.enums.Statut;
import com.technical.evaluation.orders.features.produit.entity.Produit;
import com.technical.evaluation.orders.features.produit.service.ProduitService;
import com.technical.evaluation.orders.shared.config.CustomPage;
import com.technical.evaluation.orders.shared.dto.ApiResponseCode;
import com.technical.evaluation.orders.shared.dto.SimpleApiResponse;
import com.technical.evaluation.orders.shared.exception.ApplicationException;
import com.technical.evaluation.orders.shared.exception.DataNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CommandeService {

    private final CommandeRepository repository;
    private final ArticleCommandeRepository articleCommandeRepository;
    private final ProduitService produitService;
    private final ClientService clientService;
    private final CommandeMapper mapper;

    public CommandeDetailDto detailerCommande(UUID id){
        Commande commande = findById(id);
        List<ArticleCommande> articleCommandes = articleCommandeRepository.findAllByCommande(commande);
        List<DetailArticleCommande> articleCommandesDtosList = mapper.convertToListDetailArticleCommande(articleCommandes);
        CommandeDetailDto result = mapper.convertToCommandeDetailDto(commande);
        result.setArticles(articleCommandesDtosList);
        return result;
    }

    public SimpleApiResponse creerCommande(CreateCommandeRequest request) {
        Client client = clientService.findById(request.getClientId());

        Commande commande = new Commande();
        commande.setClient(client);
        commande.setDateCommande(LocalDateTime.now());
        commande.setStatut(Statut.EN_ATTENTE.name());
        commande.setAdresseLivraison(request.getAdresseLivraison());
        commande = repository.save(commande);

        BigDecimal total = BigDecimal.ZERO;

        for (ArticleCommandeDto a : request.getArticles()) {
            Produit produit = produitService.findById(a.getProduitId());
            if (produit.getQuantiteStock() < a.getQuantite()) {
                throw new ApplicationException(ApiResponseCode.FAIL, "Stock insuffisant pour le produit " + produit.getNom());
            }

            ArticleCommande article = new ArticleCommande();
            article.setProduit(produit);
            article.setCommande(commande);
            article.setQuantite(a.getQuantite());
            article.setPrixUnitaire(produit.getPrix());
            article.setTotalLigne(produit.getPrix().multiply(BigDecimal.valueOf(a.getQuantite())));
            articleCommandeRepository.save(article);
            total = total.add(article.getTotalLigne());
        }

        commande.setMontantTotal(total);
        repository.save(commande);
        return new SimpleApiResponse(ApiResponseCode.SUCCESS.name(), "Commande créée avec succès");
    }


    public SimpleApiResponse changerStatut(UUID id, Statut statut) {
        Commande commande = findById(id);

        if (commande.getStatut() == Statut.ANNULEE.name()) {
            throw new ApplicationException(ApiResponseCode.FAIL, "Commande déjà annulée");
        }

        if (statut == Statut.CONFIRMEE) {
            for (ArticleCommande a : articleCommandeRepository.findAllByCommande(commande)) {
                Produit p = a.getProduit();
                p.setQuantiteStock(p.getQuantiteStock() - a.getQuantite());
                produitService.save(p);
            }
        }

        if (statut == Statut.ANNULEE && commande.getStatut().equals(Statut.CONFIRMEE)) {
            for (ArticleCommande a : articleCommandeRepository.findAllByCommande(commande)) {
                Produit p = a.getProduit();
                p.setQuantiteStock(p.getQuantiteStock() + a.getQuantite());
                produitService.save(p);
            }
        }

        commande.setStatut(statut.name());
        repository.save(commande);
        return new  SimpleApiResponse(ApiResponseCode.SUCCESS.name(), "Statut mis à jour");
    }


    public SimpleApiResponse annulerCommande(UUID id) {
        return changerStatut(id, Statut.ANNULEE);
    }


    public CustomPage<CommandeDetailDto> listerCommandes(org.springframework.data.domain.Pageable pageable) {
        List<CommandeDetailDto> results = new ArrayList<>();
        Page<Commande> commandes  = findAll(pageable);
        for (Commande commande : commandes.toList()){
            List<ArticleCommande> articleCommandes = articleCommandeRepository.findAllByCommande(commande);
            List<DetailArticleCommande> articleCommandesDtosList = mapper.convertToListDetailArticleCommande(articleCommandes);
            CommandeDetailDto result = mapper.convertToCommandeDetailDto(commande);
            result.setArticles(articleCommandesDtosList);
            results.add(result);
        }
        return new CustomPage<>(commandes, results);
    }

    public Commande findById(UUID id){
        return repository.findById(id).orElseThrow(()-> new DataNotFoundException("Commande introuvable dans le systeme"));
    }

    public Page<Commande> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }
}
