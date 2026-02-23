package sn.ugb.ipsl.produit_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.ugb.ipsl.produit_backend.model.MouvementStock;
import java.util.List;

@Repository
public interface MouvementStockRepository extends JpaRepository<MouvementStock, Long> {
    // Récupérer tous les mouvements d'un produit spécifique pour l'IA
    List<MouvementStock> findByProduitIdOrderByDateMouvementAsc(Long produitId);
}