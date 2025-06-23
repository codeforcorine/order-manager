package unit;

import com.technical.evaluation.orders.features.produit.dto.CreateProduitRequest;
import com.technical.evaluation.orders.features.produit.dto.ProduitDto;
import com.technical.evaluation.orders.features.produit.dto.UpdateProduitRequest;
import com.technical.evaluation.orders.features.produit.entity.Categorie;
import com.technical.evaluation.orders.features.produit.entity.Fournisseur;
import com.technical.evaluation.orders.features.produit.entity.Produit;
import com.technical.evaluation.orders.features.produit.mapper.ProduitMapper;
import com.technical.evaluation.orders.features.produit.repository.ProduitRepository;
import com.technical.evaluation.orders.features.produit.service.CategorieService;
import com.technical.evaluation.orders.features.produit.service.FournisseurService;
import com.technical.evaluation.orders.features.produit.service.ProduitService;
import com.technical.evaluation.orders.shared.config.CustomPage;
import com.technical.evaluation.orders.shared.dto.SimpleApiResponse;
import com.technical.evaluation.orders.shared.exception.ApplicationException;
import com.technical.evaluation.orders.shared.exception.DataNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProduitServiceUnitTest {

    @InjectMocks
    private ProduitService produitService;

    @Mock private ProduitRepository produitRepository;
    @Mock private ProduitMapper produitMapper;
    @Mock private FournisseurService fournisseurService;
    @Mock private CategorieService categorieService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_shouldReturnPagedDtos() {
        Pageable pageable = PageRequest.of(0, 5);
        List<Produit> produits = List.of(new Produit());
        List<ProduitDto> dtos = List.of(new ProduitDto());

        Page<Produit> page = new PageImpl<>(produits);
        when(produitRepository.findAll(pageable)).thenReturn(page);
        when(produitMapper.convertToDtos(produits)).thenReturn(dtos);

        CustomPage<ProduitDto> result = produitService.findAll(pageable);

        assertThat(result.getContent()).hasSize(1);
        verify(produitMapper).convertToDtos(produits);
    }

    @Test
    void findAllWithLowStock_shouldReturnPagedDtos() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Produit> produits = List.of(new Produit());
        List<ProduitDto> dtos = List.of(new ProduitDto());

        Page<Produit> page = new PageImpl<>(produits);
        when(produitRepository.findByQuantiteStockLessThan(5, pageable)).thenReturn(page);
        when(produitMapper.convertToDtos(produits)).thenReturn(dtos);

        CustomPage<ProduitDto> result = produitService.findAllWithLowStock(pageable);

        assertThat(result.getContent()).hasSize(1);
        verify(produitMapper).convertToDtos(produits);
    }

    @Test
    void create_shouldSaveProduit_whenValid() {
        CreateProduitRequest req = new CreateProduitRequest();
        req.setNom("Produit test");
        UUID fournisseurId = UUID.randomUUID();
        req.setFournisseurId(fournisseurId);
        req.setCategorieId(1);

        Produit produit = new Produit();
        Fournisseur fournisseur = new Fournisseur();
        Categorie categorie = new Categorie();

        when(produitRepository.existsByNomAndFournisseur_Id(req.getNom(), fournisseurId)).thenReturn(false);
        when(fournisseurService.findById(fournisseurId)).thenReturn(fournisseur);
        when(categorieService.findById(1)).thenReturn(categorie);
        when(produitMapper.convertToEntity(req)).thenReturn(produit);

        SimpleApiResponse response = produitService.create(req);

        assertThat(response.getMessage()).contains("Produit enregistré");
        verify(produitRepository).save(produit);
    }

    @Test
    void create_shouldThrow_whenAlreadyExists() {
        CreateProduitRequest req = new CreateProduitRequest();
        req.setNom("Produit existant");
        UUID fournisseurId = UUID.randomUUID();
        req.setFournisseurId(fournisseurId);

        when(produitRepository.existsByNomAndFournisseur_Id(req.getNom(), fournisseurId)).thenReturn(true);

        assertThatThrownBy(() -> produitService.create(req))
                .isInstanceOf(ApplicationException.class);
    }

    @Test
    void update_shouldUpdateProduit_whenValid() {
        UUID id = UUID.randomUUID();
        Produit produit = new Produit();
        UpdateProduitRequest req = new UpdateProduitRequest();
        req.setFournisseurId(UUID.randomUUID());
        req.setCategorieId(1);

        when(produitRepository.existsById(id)).thenReturn(true);
        when(produitRepository.findById(id)).thenReturn(Optional.of(produit));
        when(fournisseurService.findById(req.getFournisseurId())).thenReturn(new Fournisseur());
        when(categorieService.findById(1)).thenReturn(new Categorie());

        SimpleApiResponse response = produitService.update(id, req);

        assertThat(response.getMessage()).contains("mis à jour");
        verify(produitRepository).save(produit);
    }

    @Test
    void update_shouldThrow_whenProduitNotFound() {
        UUID id = UUID.randomUUID();
        when(produitRepository.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> produitService.update(id, new UpdateProduitRequest()))
                .isInstanceOf(DataNotFoundException.class);
    }

    @Test
    void updateStock_shouldUpdateAndSave() {
        UUID id = UUID.randomUUID();
        Produit produit = new Produit();

        when(produitRepository.existsById(id)).thenReturn(true);
        when(produitRepository.findById(id)).thenReturn(Optional.of(produit));

        SimpleApiResponse response = produitService.updateStock(id, 10);

        assertThat(response.getMessage()).contains("Stock");
        assertThat(produit.getQuantiteStock()).isEqualTo(10);
        verify(produitRepository).save(produit);
    }

    @Test
    void findById_shouldReturnProduit_whenExists() {
        UUID id = UUID.randomUUID();
        Produit produit = new Produit();
        when(produitRepository.existsById(id)).thenReturn(true);
        when(produitRepository.findById(id)).thenReturn(Optional.of(produit));

        Produit found = produitService.findById(id);

        assertThat(found).isNotNull();
    }

    @Test
    void findById_shouldThrow_whenNotExists() {
        UUID id = UUID.randomUUID();
        when(produitRepository.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> produitService.findById(id))
                .isInstanceOf(DataNotFoundException.class);
    }
}
