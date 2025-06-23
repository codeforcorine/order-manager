package com.technical.evaluation.orders.features.commande.mapper;

import com.technical.evaluation.orders.features.client.mapper.ClientMapper;
import com.technical.evaluation.orders.features.commande.dto.CommandeDetailDto;
import com.technical.evaluation.orders.features.commande.dto.DetailArticleCommande;
import com.technical.evaluation.orders.features.commande.entity.ArticleCommande;
import com.technical.evaluation.orders.features.commande.entity.Commande;
import com.technical.evaluation.orders.features.produit.mapper.ProduitMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {ProduitMapper.class, ClientMapper.class})
public interface CommandeMapper {
    CommandeDetailDto convertToCommandeDetailDto(Commande commande);

    DetailArticleCommande convertToDetailArticleCommande(ArticleCommande articleCommande);
    List<DetailArticleCommande> convertToListDetailArticleCommande(List<ArticleCommande> articleCommandeList);
}
