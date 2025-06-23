package unit;

import com.technical.evaluation.orders.features.produit.dto.FournisseurDto;
import com.technical.evaluation.orders.features.produit.entity.Fournisseur;
import com.technical.evaluation.orders.features.produit.mapper.FournisseurMapper;
import com.technical.evaluation.orders.features.produit.repository.FournisseurRepository;
import com.technical.evaluation.orders.features.produit.service.FournisseurService;
import com.technical.evaluation.orders.shared.exception.ApplicationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FournisseurServiceUnitTest {

    @Mock
    private FournisseurRepository repository;

    @Mock
    private FournisseurMapper mapper;

    @InjectMocks
    private FournisseurService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void existsById_shouldNotThrow_whenExists() {
        UUID id = UUID.randomUUID();
        when(repository.existsById(id)).thenReturn(true);

        assertThatCode(() -> service.existsById(id)).doesNotThrowAnyException();
    }

    @Test
    void existsById_shouldThrow_whenNotExists() {
        UUID id = UUID.randomUUID();
        when(repository.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> service.existsById(id))
                .isInstanceOf(ApplicationException.class)
                .hasMessageContaining("fournisseur est introuvable");
    }

    @Test
    void findById_shouldReturnFournisseur_whenExists() {
        UUID id = UUID.randomUUID();
        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setId(id);

        when(repository.existsById(id)).thenReturn(true);
        when(repository.findById(id)).thenReturn(Optional.of(fournisseur));

        Fournisseur found = service.findById(id);

        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(id);
    }

    @Test
    void findById_shouldThrow_whenNotExists() {
        UUID id = UUID.randomUUID();
        when(repository.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> service.findById(id))
                .isInstanceOf(ApplicationException.class);
    }

    @Test
    void getAll_shouldReturnListOfDtos() {
        List<Fournisseur> fournisseurs = List.of(new Fournisseur(), new Fournisseur());
        List<FournisseurDto> dtos = List.of(new FournisseurDto(), new FournisseurDto());

        when(repository.findAll()).thenReturn(fournisseurs);
        when(mapper.convertToDtos(fournisseurs)).thenReturn(dtos);

        List<FournisseurDto> result = service.getAll();

        assertThat(result).hasSize(2);
        verify(repository).findAll();
        verify(mapper).convertToDtos(fournisseurs);
    }
}
