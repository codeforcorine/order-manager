package integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.technical.evaluation.orders.OrderManagementApiApplication;
import com.technical.evaluation.orders.features.commande.dto.CreateCommandeRequest;
import com.technical.evaluation.orders.features.commande.dto.ArticleCommandeDto;
import com.technical.evaluation.orders.shared.utils.Statut;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = OrderManagementApiApplication.class)
@AutoConfigureMockMvc
public class CommandeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateCommande() throws Exception {
        CreateCommandeRequest request = new CreateCommandeRequest();
        request.setClientId(UUID.fromString("22222222-2222-2222-2222-222222222222"));
        request.setAdresseLivraison("5 rue Exemple, Lyon");

        ArticleCommandeDto article = new ArticleCommandeDto();
        article.setProduitId(UUID.fromString("33333333-3333-3333-3333-333333333333"));
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
        UUID commandeId = UUID.fromString("44444444-4444-4444-4444-444444444444");
        mockMvc.perform(get("/api/commandes/" + commandeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(commandeId.toString()));
    }

    @Test
    void shouldUpdateStatut() throws Exception {
        UUID commandeId = UUID.fromString("44444444-4444-4444-4444-444444444444");
        mockMvc.perform(put("/api/commandes/" + commandeId)
                        .param("statut", Statut.CONFIRMEE.name()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Statut mis à jour"));
    }

    @Test
    void shouldAnnulerCommande() throws Exception {
        UUID commandeId = UUID.fromString("44444444-4444-4444-4444-444444444444"); // Remplacer par un ID existant
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
