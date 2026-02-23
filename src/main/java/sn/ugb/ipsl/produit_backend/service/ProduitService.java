package sn.ugb.ipsl.produit_backend.service;

import sn.ugb.ipsl.produit_backend.model.Produit;
import java.util.List;

public interface ProduitService {
    Produit creerProduit(Produit produit);
    List<Produit> listerTousLesProduits();
    Produit obtenirProduitParId(Long id);
    void supprimerProduit(Long id);
    List<Produit> verifierStocksCritiques();

    List<Produit> listeProduits();
}