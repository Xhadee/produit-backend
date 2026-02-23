package sn.ugb.ipsl.produit_backend.service;

import sn.ugb.ipsl.produit_backend.model.Categorie;

import java.util.List;

public interface CategorieService {
    Categorie enregistrer(Categorie categorie);
    List<Categorie> listerToutes();
    Categorie trouverParId(Long id);

    Categorie mettreAJour(Long id, Categorie categorie);

    void supprimer(Long id);
}
