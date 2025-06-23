package unit;
import com.technical.evaluation.orders.features.client.entity.Client;
import com.technical.evaluation.orders.features.client.service.ClientService;
import com.technical.evaluation.orders.features.commande.dto.*;
import com.technical.evaluation.orders.features.commande.entity.ArticleCommande;
import com.technical.evaluation.orders.features.commande.entity.Commande;
import com.technical.evaluation.orders.features.commande.mapper.CommandeMapper;
import com.technical.evaluation.orders.features.commande.repository.ArticleCommandeRepository;
import com.technical.evaluation.orders.features.commande.repository.CommandeRepository;
import com.technical.evaluation.orders.features.commande.service.CommandeService;
import com.technical.evaluation.orders.features.produit.entity.Produit;
import com.technical.evaluation.orders.features.produit.service.ProduitService;
import com.technical.evaluation.orders.shared.dto.ApiResponseCode;
import com.technical.evaluation.orders.shared.dto.SimpleApiResponse;
import com.technical.evaluation.orders.shared.exception.ApplicationException;
import com.technical.evaluation.orders.shared.exception.DataNotFoundException;
import com.technical.evaluation.orders.shared.utils.Statut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommandeServiceUnitTest {

    @InjectMocks
    private CommandeService commandeService;

    @Mock private CommandeRepository commandeRepository;
    @Mock private ArticleCommandeRepository articleCommandeRepository;
    @Mock private ProduitService produitService;
    @Mock private ClientService clientService;
    @Mock private CommandeMapper commandeMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void creerCommande_shouldCreateCommande_whenValid() {
        UUID clientId = UUID.randomUUID();
        UUID produitId = UUID.randomUUID();

        CreateCommandeRequest request = new CreateCommandeRequest();
        request.setClientId(clientId);
        request.setAdresseLivraison("Chez moi");
        ArticleCommandeDto article = new ArticleCommandeDto(produitId, 2);
        request.setArticles(List.of(article));

        Client client = new Client();
        Produit produit = new Produit();
        produit.setPrix(BigDecimal.valueOf(10));
        produit.setQuantiteStock(5);

        when(clientService.findById(clientId)).thenReturn(client);
        when(produitService.findById(produitId)).thenReturn(produit);

        Commande savedCommande = new Commande();
        when(commandeRepository.save(any())).thenReturn(savedCommande);

        SimpleApiResponse response = commandeService.creerCommande(request);

        assertThat(response.getMessage()).contains("créée avec succès");
        verify(commandeRepository, times(2)).save(any());
        verify(articleCommandeRepository).save(any());
    }

    @Test
    void creerCommande_shouldThrow_whenStockInsuffisant() {
        UUID clientId = UUID.randomUUID();
        UUID produitId = UUID.randomUUID();

        CreateCommandeRequest request = new CreateCommandeRequest();
        request.setClientId(clientId);
        request.setAdresseLivraison("Quelque part");
        request.setArticles(List.of(new ArticleCommandeDto(produitId, 10)));

        Produit produit = new Produit();
        produit.setQuantiteStock(5);

        when(clientService.findById(clientId)).thenReturn(new Client());
        when(produitService.findById(produitId)).thenReturn(produit);

        assertThatThrownBy(() -> commandeService.creerCommande(request))
                .isInstanceOf(ApplicationException.class)
                .hasMessageContaining("Stock insuffisant");
    }

    @Test
    void changerStatut_shouldUpdateStatut_andReduceStock_whenConfirmed() {
        UUID commandeId = UUID.randomUUID();
        Commande commande = new Commande();
        commande.setStatut(Statut.EN_ATTENTE.name());

        Produit produit = new Produit();
        produit.setQuantiteStock(10);

        ArticleCommande article = new ArticleCommande();
        article.setQuantite(2);
        article.setProduit(produit);
        article.setCommande(commande);

        when(commandeRepository.findById(commandeId)).thenReturn(Optional.of(commande));
        when(articleCommandeRepository.findAllByCommande(commande)).thenReturn(List.of(article));

        SimpleApiResponse response = commandeService.changerStatut(commandeId, Statut.CONFIRMEE);

        assertThat(response.getMessage()).contains("Statut mis à jour");
        assertThat(produit.getQuantiteStock()).isEqualTo(8);
    }

    @Test
    void changerStatut_shouldRestock_ifCancelledAfterConfirmation() {
        UUID commandeId = UUID.randomUUID();
        Commande commande = new Commande();
        commande.setStatut(Statut.CONFIRMEE.name());

        Produit produit = new Produit();
        produit.setQuantiteStock(5);

        ArticleCommande article = new ArticleCommande();
        article.setQuantite(2);
        article.setProduit(produit);
        article.setCommande(commande);

        when(commandeRepository.findById(commandeId)).thenReturn(Optional.of(commande));
        when(articleCommandeRepository.findAllByCommande(commande)).thenReturn(List.of(article));

        SimpleApiResponse response = commandeService.changerStatut(commandeId, Statut.ANNULEE);

        assertThat(produit.getQuantiteStock()).isEqualTo(7);
        assertThat(response.getMessage()).contains("Statut mis à jour");
    }

    @Test
    void changerStatut_shouldThrow_ifAlreadyCancelled() {
        UUID id = UUID.randomUUID();
        Commande commande = new Commande();
        commande.setStatut(Statut.ANNULEE.name());

        when(commandeRepository.findById(id)).thenReturn(Optional.of(commande));

        assertThatThrownBy(() -> commandeService.changerStatut(id, Statut.CONFIRMEE))
                .isInstanceOf(ApplicationException.class)
                .hasMessageContaining("déjà annulée");
    }

    @Test
    void detailerCommande_shouldReturnDetails() {
        UUID id = UUID.randomUUID();
        Commande commande = new Commande();
        List<ArticleCommande> articles = List.of(new ArticleCommande());

        when(commandeRepository.findById(id)).thenReturn(Optional.of(commande));
        when(articleCommandeRepository.findAllByCommande(commande)).thenReturn(articles);
        when(commandeMapper.convertToCommandeDetailDto(commande)).thenReturn(new CommandeDetailDto());
        when(commandeMapper.convertToListDetailArticleCommande(articles)).thenReturn(List.of());

        CommandeDetailDto result = commandeService.detailerCommande(id);

        assertThat(result).isNotNull();
    }

    @Test
    void findById_shouldReturnCommande_whenExists() {
        UUID id = UUID.randomUUID();
        Commande commande = new Commande();

        when(commandeRepository.findById(id)).thenReturn(Optional.of(commande));

        Commande found = commandeService.findById(id);
        assertThat(found).isEqualTo(commande);
    }

    @Test
    void findById_shouldThrow_whenNotExists() {
        UUID id = UUID.randomUUID();

        when(commandeRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> commandeService.findById(id))
                .isInstanceOf(DataNotFoundException.class);
    }
}

