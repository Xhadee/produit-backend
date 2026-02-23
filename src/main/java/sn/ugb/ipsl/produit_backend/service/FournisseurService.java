package sn.ugb.ipsl.produit_backend.service;

import sn.ugb.ipsl.produit_backend.model.Fournisseur;
import java.util.List;

public interface FournisseurService {

    // Création
    Fournisseur ajouter(Fournisseur fournisseur);

    // Lecture
    List<Fournisseur> listerTous();
    Fournisseur trouverParId(Long id);

    // Mise à jour (Ajouté)
    Fournisseur mettreAJour(Long id, Fournisseur fournisseur);

    // Suppression (Ajouté)
    void supprimer(Long id);
}