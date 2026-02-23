package sn.ugb.ipsl.produit_backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.ugb.ipsl.produit_backend.exception.ResourceNotFoundException; // On importe ton exception
import sn.ugb.ipsl.produit_backend.model.Produit;
import sn.ugb.ipsl.produit_backend.repository.ProduitRepository;
import sn.ugb.ipsl.produit_backend.service.ProduitService;

import java.util.List;

@Service
public class ProduitServiceImpl implements ProduitService {

    @Autowired
    private ProduitRepository produitRepository;

    @Override
    public Produit creerProduit(Produit produit) {
        if (produit.getSeuilAlerte() == null) {
            produit.setSeuilAlerte(5);
        }
        return produitRepository.save(produit);
    }

    @Override
    public List<Produit> listerTousLesProduits() {
        return List.of();
    }

    // On garde uniquement la méthode attendue par l'interface
    @Override
    public List<Produit> listeProduits() {
        return produitRepository.findAll();
    }

    @Override
    public Produit obtenirProduitParId(Long id) {
        // Utilisation de ton exception personnalisée au lieu de RuntimeException
        return produitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Le produit avec l'ID " + id + " est introuvable."));
    }

    @Override
    public void supprimerProduit(Long id) {
        // Optionnel : vérifier si le produit existe avant de supprimer pour lever une 404 propre
        if (!produitRepository.existsById(id)) {
            throw new ResourceNotFoundException("Impossible de supprimer : le produit " + id + " n'existe pas.");
        }
        produitRepository.deleteById(id);
    }

    @Override
    public List<Produit> verifierStocksCritiques() {
        return produitRepository.findProduitsEnAlerte();
    }
}