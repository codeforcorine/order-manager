package integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.technical.evaluation.orders.features.client.dto.CreateClientRequest;
import com.technical.evaluation.orders.features.client.entity.Client;
import com.technical.evaluation.orders.features.client.enums.TypeClient;
import com.technical.evaluation.orders.features.client.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ClientControllerIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private ClientRepository clientRepository;

    private UUID existingClientId;

    @BeforeEach
    void setup() {
        clientRepository.deleteAll();
        Client client = new Client();
        client.setNom("Jean Dupont");
        client.setEmail("jean@exemple.com");
        client.setTelephone("0123456789");
        client.setAdresse("123 rue du Test");
        client.setTypeClient("REGULIER");
        client = clientRepository.save(client);
        existingClientId = client.getId();
    }

    @Test
    void shouldReturnAllClients() throws Exception {
        mockMvc.perform(get("/api/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    void shouldCreateClient() throws Exception {
        CreateClientRequest request = new CreateClientRequest();
        request.setNom("Nouvel Utilisateur");
        request.setEmail("nouveau@mail.com");
        request.setTelephone("0987654321");
        request.setAdresse("Rue test 99");
        request.setTypeClient(TypeClient.PREMIUM);

        mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Client enregistré avec succès."));
    }

    @Test
    void shouldUpdateClient() throws Exception {
        String url = "/api/clients/" + existingClientId;

        String payload = """
        {
            "nom": "Client Modifié",
            "email": "update@mail.com",
            "telephone": "0000111122",
            "adresse": "Adresse modifiée",
            "typeClient": "VIP"
        }
        """;

        mockMvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Client mis à jour avec succès."));
    }

    @Test
    void shouldReturnHistoriqueCommande() throws Exception {
        mockMvc.perform(get("/api/clients/" + existingClientId + "/commandes"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnStatistiquesClient() throws Exception {
        mockMvc.perform(get("/api/clients/" + existingClientId + "/statistiques"))
                .andExpect(status().isOk());
    }
}
