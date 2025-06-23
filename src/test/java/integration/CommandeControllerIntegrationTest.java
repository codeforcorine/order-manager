package integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.technical.evaluation.orders.OrderManagementApiApplication;
import com.technical.evaluation.orders.features.client.entity.Client;
import com.technical.evaluation.orders.features.client.enums.TypeClient;
import com.technical.evaluation.orders.features.commande.dto.ArticleCommandeDto;
import com.technical.evaluation.orders.features.commande.dto.CreateCommandeRequest;
import com.technical.evaluation.orders.features.commande.entity.ArticleCommande;
import com.technical.evaluation.orders.features.commande.entity.Commande;
import com.technical.evaluation.orders.features.commande.repository.ArticleCommandeRepository;
import com.technical.evaluation.orders.features.commande.repository.CommandeRepository;
import com.technical.evaluation.orders.features.produit.entity.Categorie;
import com.technical.evaluation.orders.features.produit.entity.Fournisseur;
import com.technical.evaluation.orders.features.produit.entity.Produit;
import com.technical.evaluation.orders.features.client.repository.ClientRepository;
import com.technical.evaluation.orders.features.produit.repository.CategorieRepository;
import com.technical.evaluation.orders.features.produit.repository.FournisseurRepository;
import com.technical.evaluation.orders.features.produit.repository.ProduitRepository;
import com.technical.evaluation.orders.shared.utils.Statut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = OrderManagementApiApplication.class)
@AutoConfigureMockMvc
public class CommandeControllerIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @Autowired private ClientRepository clientRepository;
    @Autowired private ProduitRepository produitRepository;
    @Autowired private CommandeRepository commandeRepository;
    @Autowired private ArticleCommandeRepository articleCommandeRepository;
    @Autowired private CategorieRepository categorieRepository;
    @Autowired private FournisseurRepository fournisseurRepository;

    private UUID clientId;
    private UUID produitId;
    private UUID commandeId;

    @BeforeEach
    void setup() {
        articleCommandeRepository.deleteAll();
        commandeRepository.deleteAll();
        produitRepository.deleteAll();
        clientRepository.deleteAll();

        Client client = new Client();
        client.setNom("Jean Dupont");
        client.setEmail("jean@example.com");
        client.setTelephone("0600000000");
        client.setDateEnregistrement(LocalDateTime.now());
        client.setTypeClient(TypeClient.VIP.name());
        client = clientRepository.save(client);
        clientId = client.getId();

        Categorie categorie = new Categorie();
        categorie.setNom("Test-" + UUID.randomUUID());
        categorie.setDateCreation(LocalDateTime.now());
        categorie = categorieRepository.save(categorie);

        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setNom("test-" + UUID.randomUUID());
        fournisseur.setTelephone("08908984984");
        fournisseur.setDateEnregistrement(LocalDateTime.now());
        fournisseur = fournisseurRepository.save(fournisseur);

        Produit produit = new Produit();
        produit.setNom("Produit test");
        produit.setPrix(BigDecimal.valueOf(50));
        produit.setQuantiteStock(100);
        produit.setCategorie(categorie);
        produit.setDateEnregistrement(LocalDateTime.now());
        produit.setFournisseur(fournisseur);
        produit = produitRepository.save(produit);
        produitId = produit.getId();

        Commande commande = new Commande();
        commande.setClient(client);
        commande.setAdresseLivraison("Paris");
        commande.setMontantTotal(BigDecimal.valueOf(100));
        commande.setStatut(Statut.EN_ATTENTE.name());
        commande = commandeRepository.save(commande);
        commandeId = commande.getId();

        ArticleCommande article = new ArticleCommande();
        article.setCommande(commande);
        article.setProduit(produit);
        article.setQuantite(2);
        article.setPrixUnitaire(produit.getPrix());
        article.setTotalLigne(BigDecimal.valueOf(100));
        articleCommandeRepository.save(article);
    }

    @Test
    void shouldCreateCommande() throws Exception {
        CreateCommandeRequest request = new CreateCommandeRequest();
        request.setClientId(clientId);
        request.setAdresseLivraison("5 rue Exemple, Lyon");

        ArticleCommandeDto article = new ArticleCommandeDto();
        article.setProduitId(produitId);
        article.setQuantite(2);
        request.setArticles(List.of(article));

        mockMvc.perform(post("/api/commandes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Commande créée avec succès"));
    }

    @Test
    void shouldReturnCommandeDetail() throws Exception {
        mockMvc.perform(get("/api/commandes/" + commandeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(commandeId.toString()));
    }

    @Test
    void shouldAnnulerCommande() throws Exception {
        mockMvc.perform(post("/api/commandes/" + commandeId + "/annuler"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Statut mis à jour"));
    }

    @Test
    void shouldListCommandes() throws Exception {
        mockMvc.perform(get("/api/commandes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }
}
