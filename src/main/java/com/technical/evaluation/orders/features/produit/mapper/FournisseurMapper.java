package com.technical.evaluation.orders.features.produit.mapper;

import com.technical.evaluation.orders.features.produit.dto.FournisseurDto;
import com.technical.evaluation.orders.features.produit.entity.Fournisseur;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface FournisseurMapper {
    FournisseurDto convertToDto(Fournisseur fournisseur);
    List<FournisseurDto> convertToDtos(List<Fournisseur> fournisseurs);
}
