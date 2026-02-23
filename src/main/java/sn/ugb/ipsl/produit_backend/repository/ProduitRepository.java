package sn.ugb.ipsl.produit_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sn.ugb.ipsl.produit_backend.model.Produit;
import java.util.List;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {

    // Requête personnalisée pour trouver les produits en alerte
    @Query("SELECT p FROM Produit p WHERE p.quantiteStock <= p.seuilAlerte")
    List<Produit> findProduitsEnAlerte();
}