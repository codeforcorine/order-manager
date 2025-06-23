package unit;


import com.technical.evaluation.orders.features.produit.entity.Categorie;
import com.technical.evaluation.orders.features.produit.repository.CategorieRepository;
import com.technical.evaluation.orders.features.produit.service.CategorieService;
import com.technical.evaluation.orders.shared.exception.ApplicationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategorieServiceUnitTest {

    @Mock
    private CategorieRepository repository;

    @InjectMocks
    private CategorieService categorieService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void existsById_shouldNotThrow_whenExists() {
        when(repository.existsById(1)).thenReturn(true);

        assertThatCode(() -> categorieService.existsById(1)).doesNotThrowAnyException();
    }

    @Test
    void existsById_shouldThrow_whenNotExists() {
        when(repository.existsById(999)).thenReturn(false);

        assertThatThrownBy(() -> categorieService.existsById(999))
                .isInstanceOf(ApplicationException.class)
                .hasMessageContaining("Catégorie introuvable");
    }

    @Test
    void findById_shouldReturnCategorie_whenExists() {
        Categorie categorie = new Categorie();
        categorie.setId(1);
        categorie.setNom("Informatique");

        when(repository.existsById(1)).thenReturn(true);
        when(repository.findById(1)).thenReturn(Optional.of(categorie));

        Categorie found = categorieService.findById(1);

        assertThat(found).isNotNull();
        assertThat(found.getNom()).isEqualTo("Informatique");
    }

    @Test
    void findById_shouldThrow_whenNotExists() {
        when(repository.existsById(1)).thenReturn(false);

        assertThatThrownBy(() -> categorieService.findById(1))
                .isInstanceOf(ApplicationException.class)
                .hasMessageContaining("Catégorie introuvable");
    }
}
