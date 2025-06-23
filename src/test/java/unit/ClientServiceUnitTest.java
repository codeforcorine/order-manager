package unit;

import com.technical.evaluation.orders.features.client.dto.ClientDto;
import com.technical.evaluation.orders.features.client.dto.CreateClientRequest;
import com.technical.evaluation.orders.features.client.dto.StatistiquesClientDto;
import com.technical.evaluation.orders.features.client.dto.UpdateClientRequest;
import com.technical.evaluation.orders.features.client.entity.Client;
import com.technical.evaluation.orders.features.client.enums.TypeClient;
import com.technical.evaluation.orders.features.client.mapper.ClientMapper;
import com.technical.evaluation.orders.features.client.repository.ClientRepository;
import com.technical.evaluation.orders.features.client.service.ClientService;
import com.technical.evaluation.orders.features.commande.dto.CommandeDetailDto;
import com.technical.evaluation.orders.features.commande.dto.DetailArticleCommande;
import com.technical.evaluation.orders.features.commande.entity.ArticleCommande;
import com.technical.evaluation.orders.features.commande.entity.Commande;
import com.technical.evaluation.orders.features.commande.mapper.CommandeMapper;
import com.technical.evaluation.orders.features.commande.repository.ArticleCommandeRepository;
import com.technical.evaluation.orders.features.commande.repository.CommandeRepository;
import com.technical.evaluation.orders.shared.config.CustomPage;
import com.technical.evaluation.orders.shared.dto.SimpleApiResponse;
import com.technical.evaluation.orders.shared.exception.ApplicationException;
import com.technical.evaluation.orders.shared.exception.DataNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class ClientServiceUnitTest {

    @InjectMocks
    private ClientService clientService;

    @Mock private ClientRepository clientRepository;
    @Mock private ClientMapper clientMapper;
    @Mock private CommandeRepository commandeRepository;
    @Mock private ArticleCommandeRepository articleCommandeRepository;
    @Mock private CommandeMapper commandeMapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_shouldReturnPagedClients() {
        Pageable pageable = PageRequest.of(0, 5);
        List<Client> clients = List.of(new Client());
        when(clientRepository.findAll(pageable)).thenReturn(new PageImpl<>(clients));
        when(clientMapper.clientDtoList(any())).thenReturn(List.of(new ClientDto()));

        CustomPage<ClientDto> result = clientService.findAll(pageable);

        assertThat(result.getContent()).hasSize(1);
    }

    @Test
    void create_shouldSucceedWhenValid() {
        CreateClientRequest req = new CreateClientRequest();
        req.setNom("Test");
        req.setTelephone("0000");
        req.setTypeClient(TypeClient.REGULIER);

        when(clientRepository.existsByNomAndTelephone(any(), any())).thenReturn(false);
        when(clientMapper.convertToEntity(any())).thenReturn(new Client());

        SimpleApiResponse res = clientService.create(req);

        assertThat(res.getMessage()).contains("Client enregistré");
        verify(clientRepository).save(any(Client.class));
    }

    @Test
    void create_shouldThrowIfClientExists() {
        when(clientRepository.existsByNomAndTelephone(any(), any())).thenReturn(true);
        CreateClientRequest req = new CreateClientRequest();
        req.setNom("Dup");
        req.setTelephone("123");

        assertThatThrownBy(() -> clientService.create(req))
                .isInstanceOf(ApplicationException.class);
    }

    @Test
    void update_shouldUpdateClient() {
        UUID id = UUID.randomUUID();
        Client client = new Client();
        UpdateClientRequest req = new UpdateClientRequest();
        req.setNom("Nouveau nom");
        req.setTypeClient(TypeClient.VIP);

        when(clientRepository.findById(id)).thenReturn(Optional.of(client));

        SimpleApiResponse response = clientService.update(id, req);

        assertThat(response.getMessage()).contains("mis à jour");
        verify(clientRepository).save(client);
    }

    @Test
    void update_shouldThrowIfNotFound() {
        UUID id = UUID.randomUUID();
        when(clientRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> clientService.update(id, new UpdateClientRequest()))
                .isInstanceOf(DataNotFoundException.class);
    }

    @Test
    void findById_shouldReturnClient() {
        UUID id = UUID.randomUUID();
        Client client = new Client();
        when(clientRepository.existsById(id)).thenReturn(true);
        when(clientRepository.findById(id)).thenReturn(Optional.of(client));

        Client found = clientService.findById(id);

        assertThat(found).isNotNull();
    }

    @Test
    void findById_shouldThrowIfNotExist() {
        UUID id = UUID.randomUUID();
        when(clientRepository.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> clientService.findById(id))
                .isInstanceOf(ApplicationException.class);
    }

    @Test
    void historiqueCommande_shouldReturnPage() {
        UUID clientId = UUID.randomUUID();
        Client client = new Client();
        Commande commande = new Commande();
        ArticleCommande article = new ArticleCommande();

        when(clientRepository.existsById(clientId)).thenReturn(true);
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));
        when(commandeRepository.findAllByClient(eq(client), any())).thenReturn(new PageImpl<>(List.of(commande)));
        when(articleCommandeRepository.findAllByCommande(commande)).thenReturn(List.of(article));
        when(commandeMapper.convertToListDetailArticleCommande(any())).thenReturn(List.of(new DetailArticleCommande()));
        when(commandeMapper.convertToCommandeDetailDto(any())).thenReturn(new CommandeDetailDto());

        CustomPage<CommandeDetailDto> result = clientService.historiqueCommande(clientId, PageRequest.of(0, 5));

        assertThat(result.getContent()).hasSize(1);
    }

    @Test
    void calculerStatistiquesClient_shouldReturnValidStats() {
        UUID id = UUID.randomUUID();
        Client client = new Client();
        Commande commande = new Commande();
        commande.setDateCommande(LocalDateTime.now());
        commande.setMontantTotal(BigDecimal.valueOf(100));

        ArticleCommande article = new ArticleCommande();
        article.setQuantite(1);

        when(clientRepository.existsById(id)).thenReturn(true);
        when(clientRepository.findById(id)).thenReturn(Optional.of(client));
        when(commandeRepository.findAllByClient(client)).thenReturn(List.of(commande));
        when(articleCommandeRepository.findAllByCommande(commande)).thenReturn(List.of(article));

        StatistiquesClientDto stats = clientService.calculerStatistiquesClient(id);

        assertThat(stats.getTotalCommandes()).isEqualTo(1);
        assertThat(stats.getTotalMontantDepense()).isEqualTo(BigDecimal.valueOf(100));
        assertThat(stats.getProduitsCommandes()).isEqualTo(1);
    }

}
