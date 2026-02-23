package sn.ugb.ipsl.produit_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sn.ugb.ipsl.produit_backend.model.Categorie;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Long> {
    // Permet de retrouver une catégorie par son nom (utile pour les imports)
    Categorie findByNom(String nom);
}