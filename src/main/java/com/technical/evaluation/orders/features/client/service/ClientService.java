package com.technical.evaluation.orders.features.client.service;

import com.technical.evaluation.orders.features.client.dto.ClientDto;
import com.technical.evaluation.orders.features.client.dto.CreateClientRequest;
import com.technical.evaluation.orders.features.client.dto.StatistiquesClientDto;
import com.technical.evaluation.orders.features.client.dto.UpdateClientRequest;
import com.technical.evaluation.orders.features.client.entity.Client;
import com.technical.evaluation.orders.features.client.mapper.ClientMapper;
import com.technical.evaluation.orders.features.client.repository.ClientRepository;
import com.technical.evaluation.orders.features.commande.dto.CommandeDetailDto;
import com.technical.evaluation.orders.features.commande.dto.DetailArticleCommande;
import com.technical.evaluation.orders.features.commande.entity.ArticleCommande;
import com.technical.evaluation.orders.features.commande.entity.Commande;
import com.technical.evaluation.orders.features.commande.mapper.CommandeMapper;
import com.technical.evaluation.orders.features.commande.repository.ArticleCommandeRepository;
import com.technical.evaluation.orders.features.commande.repository.CommandeRepository;
import com.technical.evaluation.orders.features.commande.service.CommandeService;
import com.technical.evaluation.orders.shared.config.CustomPage;
import com.technical.evaluation.orders.shared.dto.ApiResponseCode;
import com.technical.evaluation.orders.shared.dto.SimpleApiResponse;
import com.technical.evaluation.orders.shared.exception.ApplicationException;
import com.technical.evaluation.orders.shared.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService{

    private final ClientRepository repository;
    private final ClientMapper mapper;
    private final ArticleCommandeRepository articleCommandeRepository;
    private final CommandeMapper commandeMapper;
    private final CommandeRepository commandeRepository;

    public CustomPage<ClientDto> findAll(Pageable pageable) {
        Page<Client> clients = repository.findAll(pageable);
        return new CustomPage<>(clients, mapper.clientDtoList(clients.toList()));
    }
    void existsByNomAndTelephone(String nom, String telephone){
        if(repository.existsByNomAndTelephone(nom, telephone)) throw new ApplicationException(ApiResponseCode.DATA_ALREADY_EXISTS, "Donnéed déjà existante. ");
    }

    public SimpleApiResponse create(CreateClientRequest request) {
        existsByNomAndTelephone(request.getNom(), request.getTelephone());
        Client client = mapper.convertToEntity(request);
        client.setTypeClient(request.getTypeClient().name());
        client.setDateEnregistrement(LocalDateTime.now());
        repository.save(client);
        return new SimpleApiResponse(ApiResponseCode.SUCCESS.getLabel(), "Client enregistré avec succès.");
    }

    public SimpleApiResponse update(UUID id, UpdateClientRequest request) {
        Client client = repository.findById(id).orElseThrow(() ->
                new DataNotFoundException("Client non trouvé"));

        mapper.update(client, request);
        if(request.getTypeClient().name()!= null){
            client.setTypeClient(request.getTypeClient().name());
        }

        repository.save(client);
        return new SimpleApiResponse(ApiResponseCode.SUCCESS.getLabel(), "Client mis à jour avec succès.");
    }
    public void existsById(UUID id){
        if(!repository.existsById(id)) throw new ApplicationException(ApiResponseCode.DATA_NOT_FOUND, "Client introuvable dans le système.");
    }

    public Client findById(UUID id){
        existsById(id);
        return repository.findById(id).get();
    }

    public CustomPage<CommandeDetailDto> historiqueCommande(UUID id, Pageable pageable) {
        List<CommandeDetailDto> results = new ArrayList<>();
        Client client = findById(id);
        Page<Commande> commandes  = commandeRepository.findAllByClient(client, pageable);
        for (Commande commande : commandes.toList()){
            List<ArticleCommande> articleCommandes = articleCommandeRepository.findAllByCommande(commande);
            List<DetailArticleCommande> articleCommandesDtosList = commandeMapper.convertToListDetailArticleCommande(articleCommandes);
            CommandeDetailDto result = commandeMapper.convertToCommandeDetailDto(commande);
            result.setArticles(articleCommandesDtosList);
            results.add(result);
        }
        return new CustomPage<>(commandes, results);
    }


    public StatistiquesClientDto calculerStatistiquesClient(UUID clientId) {
        Client client = findById(clientId);
        List<Commande> commandes = commandeRepository.findAllByClient(client);

        if (commandes.isEmpty()) {
            return new StatistiquesClientDto(0L, BigDecimal.ZERO, BigDecimal.ZERO, null, 0L);
        }

        long totalCommandes = commandes.size();
        BigDecimal totalDepense = commandes.stream()
                .map(Commande::getMontantTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal montantMoyen = totalDepense.divide(BigDecimal.valueOf(totalCommandes), RoundingMode.HALF_UP);

        LocalDateTime derniere = commandes.stream()
                .map(Commande::getDateCommande)
                .max(LocalDateTime::compareTo)
                .orElse(null);

        long totalProduits = calculerNombreTotalProduits(commandes);

        return new StatistiquesClientDto(
                totalCommandes,
                totalDepense,
                montantMoyen,
                derniere,
                totalProduits
        );
    }
    private long calculerNombreTotalProduits(List<Commande> commandes) {
        long total = 0;
        for (Commande commande : commandes) {
            for (ArticleCommande article : articleCommandeRepository.findAllByCommande(commande)) {
                total += article.getQuantite();
            }
        }
        return total;
    }



}
