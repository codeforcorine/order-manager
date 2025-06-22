package com.technical.evaluation.orders.features.produit.service;

import com.technical.evaluation.orders.features.produit.dto.FournisseurDto;
import com.technical.evaluation.orders.features.produit.entity.Fournisseur;
import com.technical.evaluation.orders.features.produit.mapper.FournisseurMapper;
import com.technical.evaluation.orders.features.produit.repository.FournisseurRepository;
import com.technical.evaluation.orders.shared.dto.ApiResponseCode;
import com.technical.evaluation.orders.shared.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FournisseurService {

    private final FournisseurRepository repository;
    private final FournisseurMapper mapper;

    void existsById(UUID id){
        if(!repository.existsById(id)) throw new ApplicationException(ApiResponseCode.DATA_NOT_FOUND, "Ce fournisseur est introuvable.");
    }

    public Fournisseur findById(UUID id){
        existsById(id);
        return repository.findById(id).get();
    }

    public List<FournisseurDto> getAll(){
        return mapper.convertToDtos(repository.findAll());
    }
}
