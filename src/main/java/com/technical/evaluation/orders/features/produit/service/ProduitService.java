package com.technical.evaluation.orders.features.produit.service;

import com.technical.evaluation.orders.features.produit.dto.CreateProduitRequest;
import com.technical.evaluation.orders.features.produit.dto.ProduitDto;
import com.technical.evaluation.orders.features.produit.dto.UpdateProduitRequest;
import com.technical.evaluation.orders.features.produit.entity.Categorie;
import com.technical.evaluation.orders.features.produit.entity.Fournisseur;
import com.technical.evaluation.orders.features.produit.entity.Produit;
import com.technical.evaluation.orders.features.produit.mapper.ProduitMapper;
import com.technical.evaluation.orders.features.produit.repository.ProduitRepository;
import com.technical.evaluation.orders.shared.config.CustomPage;
import com.technical.evaluation.orders.shared.dto.ApiResponseCode;
import com.technical.evaluation.orders.shared.dto.SimpleApiResponse;
import com.technical.evaluation.orders.shared.exception.ApplicationException;
import com.technical.evaluation.orders.shared.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProduitService {
    private final ProduitRepository repository;
    private final FournisseurService fournisseurService;
    private final CategorieService categorieService;
    private final ProduitMapper mapper;

    public CustomPage<ProduitDto> findAll(Pageable pageable) {
        Page produits = repository.findAll(pageable);
        return new CustomPage<>(produits, mapper.convertToDtos(produits.toList()));
    }

    public SimpleApiResponse create(CreateProduitRequest request) {
        existByNomAndFournisseurId(request.getNom(), request.getFournisseurId());
        Fournisseur fournisseur = fournisseurService.findById(request.getFournisseurId());
        Categorie categorie = categorieService.findById(request.getCategorieId());
        Produit produit = mapper.convertToEntity(request);
        produit.setCategorie(categorie);
        produit.setFournisseur(fournisseur);
        produit.setDateEnregistrement(LocalDateTime.now());
        repository.save(produit);
        return new SimpleApiResponse(ApiResponseCode.SUCCESS.name(), "Produit enregistré avec succès!");
    }

    public SimpleApiResponse update(UUID id, UpdateProduitRequest request) {
        Produit produit = findById(id);
        mapper.update(produit, request);
        if(request.getFournisseurId() != null){
            produit.setFournisseur(fournisseurService.findById(request.getFournisseurId()));
        }
        if(request.getCategorieId()!= 0){
            produit.setCategorie(categorieService.findById(request.getCategorieId()));
        }
        produit.setDateMiseAJour(LocalDateTime.now());
        repository.save(produit);
        return new SimpleApiResponse(ApiResponseCode.SUCCESS.name(), "Produit mis à jour avec succès!");

    }

    void existById(UUID id){
        if(!repository.existsById(id)) throw  new DataNotFoundException("Nous n'avons pas trouvé le produit dans le système.");
    }
    Produit findById(UUID id){
        existById(id);
        return repository.findById(id).get();
    }

    void existByNomAndFournisseurId(String nom, UUID fournisseurId){
        if(repository.existsByNomAndFournisseur_Id(nom, fournisseurId)) throw new ApplicationException(ApiResponseCode.DATA_ALREADY_EXISTS, "Produit déjà existant.");
    }

    public SimpleApiResponse updateStock(UUID id, int request) {
        Produit produit = findById(id);
        produit.setQuantiteStock(request);
        produit.setDateMiseAJour(LocalDateTime.now());
        repository.save(produit);
        return new SimpleApiResponse(ApiResponseCode.SUCCESS.name(), "Stock du produit mis à jour avec succès!");
    }

    public CustomPage<ProduitDto> findAllWithLowStock(Pageable pageable) {
        Page produits = repository.findByQuantiteStockLessThan(5, pageable);
        return new CustomPage<>(produits, mapper.convertToDtos(produits.toList()));
    }
}
