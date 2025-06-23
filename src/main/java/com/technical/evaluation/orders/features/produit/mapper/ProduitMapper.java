package com.technical.evaluation.orders.features.produit.mapper;

import com.technical.evaluation.orders.features.commande.dto.DetailsProduitCommande;
import com.technical.evaluation.orders.features.produit.dto.CreateProduitRequest;
import com.technical.evaluation.orders.features.produit.dto.ProduitDto;
import com.technical.evaluation.orders.features.produit.dto.UpdateProduitRequest;
import com.technical.evaluation.orders.features.produit.entity.Produit;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProduitMapper {

    ProduitDto convertToDto(Produit produit);
    List<Produit> convertToDtos(List<Produit> produitList);
    Produit convertToEntity(CreateProduitRequest request);
    @Mapping(source = "categorie.nom", target = "categorie")
    @Mapping(source = "fournisseur.nom", target = "fournisseur")
    DetailsProduitCommande convertToDetailProduitCommande(Produit produit);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget Produit produit, UpdateProduitRequest request);
}
