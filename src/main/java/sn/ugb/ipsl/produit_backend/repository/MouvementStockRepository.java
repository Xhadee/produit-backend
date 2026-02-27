package sn.ugb.ipsl.produit_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.ugb.ipsl.produit_backend.model.MouvementStock;
import java.util.List;

@Repository
public interface MouvementStockRepository extends JpaRepository<MouvementStock, Long> {

    // Changement de Asc à Desc pour voir les mouvements les plus récents en premier
    List<MouvementStock> findByProduitIdOrderByDateMouvementDesc(Long produitId);
}