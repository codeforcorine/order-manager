package com.technical.evaluation.orders.features.produit.service;

import com.technical.evaluation.orders.features.produit.entity.Categorie;
import com.technical.evaluation.orders.features.produit.repository.CategorieRepository;
import com.technical.evaluation.orders.shared.dto.ApiResponseCode;
import com.technical.evaluation.orders.shared.exception.ApplicationException;
import com.technical.evaluation.orders.shared.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategorieService {

    private final CategorieRepository repository;

    public void existsById(int id){
        if(!repository.existsById(id)) throw new ApplicationException(ApiResponseCode.DATA_NOT_FOUND, "Catégorie introuvable");
    }

    public Categorie findById(int id){
        existsById(id);
        return repository.findById(id).get();
    }
}
