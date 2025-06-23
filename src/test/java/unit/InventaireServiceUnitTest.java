package unit;

import com.technical.evaluation.orders.features.commande.entity.ArticleCommande;
import com.technical.evaluation.orders.features.commande.entity.Commande;
import com.technical.evaluation.orders.features.commande.repository.ArticleCommandeRepository;
import com.technical.evaluation.orders.features.commande.service.CommandeService;
import com.technical.evaluation.orders.features.inventaire.dto.InventaireResumeDto;
import com.technical.evaluation.orders.features.inventaire.dto.TendanceRevenuDto;
import com.technical.evaluation.orders.features.inventaire.service.InventaireService;
import com.technical.evaluation.orders.features.produit.entity.Categorie;
import com.technical.evaluation.orders.features.produit.entity.Produit;
import com.technical.evaluation.orders.features.produit.service.ProduitService;
import com.technical.evaluation.orders.shared.utils.Statut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventaireServiceUnitTest {

    @InjectMocks
    private InventaireService inventaireService;

    @Mock private ProduitService produitService;
    @Mock private ArticleCommandeRepository articleCommandeRepository;
    @Mock private CommandeService commandeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getResumeInventaire_shouldReturnSummary() {
        Produit p1 = new Produit();
        p1.setId(UUID.randomUUID());
        p1.setNom("Produit A");
        p1.setQuantiteStock(3);
        p1.setCategorie(new Categorie(1, "Cat A"));

        Produit p2 = new Produit();
        p2.setId(UUID.randomUUID());
        p2.setNom("Produit B");
        p2.setQuantiteStock(10);
        p2.setCategorie(new Categorie(2, "Cat B"));

        when(produitService.findAll()).thenReturn(List.of(p1, p2));

        InventaireResumeDto result = inventaireService.getResumeInventaire();

        assertThat(result.getNombreProduits()).isEqualTo(2);
        assertThat(result.getStockTotal()).isEqualTo(13);
        assertThat(result.getProduitsStockCritique()).isEqualTo(1);
        assertThat(result.getDetailsProduits()).hasSize(2);
    }

    @Test
    void getRapportVentes_shouldAggregateData() {
        ArticleCommande a1 = new ArticleCommande();
        a1.setQuantite(2);
        a1.setPrixUnitaire(BigDecimal.valueOf(25)); // 25 * 2 = 50

        ArticleCommande a2 = new ArticleCommande();
        a2.setQuantite(3);
        a2.setPrixUnitaire(BigDecimal.valueOf(25)); // 25 * 3 = 75

        when(articleCommandeRepository.findAll()).thenReturn(List.of(a1, a2));

        var rapport = inventaireService.getRapportVentes();

        assertThat(rapport.getRevenuTotal()).isEqualTo(BigDecimal.valueOf(125));
        assertThat(rapport.getQuantiteTotaleVendue()).isEqualTo(5);
    }



    @Test
    void getTendancesRevenus_shouldReturnGroupedRevenusByMonth() {
        Commande jan = new Commande();
        jan.setDateCommande(LocalDateTime.of(Year.now().getValue(), Month.JANUARY, 10, 0, 0));
        jan.setStatut(Statut.LIVREE.name());
        jan.setMontantTotal(BigDecimal.valueOf(100));

        Commande feb = new Commande();
        feb.setDateCommande(LocalDateTime.of(Year.now().getValue(), Month.FEBRUARY, 5, 0, 0));
        feb.setStatut(Statut.LIVREE.name());
        feb.setMontantTotal(BigDecimal.valueOf(200));

        Commande other = new Commande(); // annulée, ignorée
        other.setDateCommande(LocalDateTime.of(Year.now().getValue(), Month.JANUARY, 5, 0, 0));
        other.setStatut(Statut.ANNULEE.name());

        when(commandeService.findAll()).thenReturn(List.of(jan, feb, other));

        List<TendanceRevenuDto> tendances = inventaireService.getTendancesRevenus();

        assertThat(tendances).hasSize(2);
        assertThat(tendances).anyMatch(t -> t.getMois() == Month.JANUARY && t.getRevenuTotal().equals(BigDecimal.valueOf(100)));
        assertThat(tendances).anyMatch(t -> t.getMois() == Month.FEBRUARY && t.getRevenuTotal().equals(BigDecimal.valueOf(200)));
    }
}
