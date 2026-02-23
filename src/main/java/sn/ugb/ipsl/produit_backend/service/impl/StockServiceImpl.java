package sn.ugb.ipsl.produit_backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.ugb.ipsl.produit_backend.exception.ResourceNotFoundException; // Ajout import
import sn.ugb.ipsl.produit_backend.exception.StockInsuffisantException; // Ajout import
import sn.ugb.ipsl.produit_backend.model.*;
import sn.ugb.ipsl.produit_backend.repository.*;
import sn.ugb.ipsl.produit_backend.service.StockService;

@Service
public class StockServiceImpl implements StockService {

    @Autowired private ProduitRepository produitRepository;
    @Autowired private MouvementStockRepository mouvementRepository;

    @Override
    @Transactional
    public MouvementStock enregistrerSortie(Long produitId, Integer quantite) {
        // Utilisation de ResourceNotFoundException
        Produit produit = produitRepository.findById(produitId)
                .orElseThrow(() -> new ResourceNotFoundException("Le produit avec l'ID " + produitId + " n'existe pas."));

        // Utilisation de StockInsuffisantException
        if (produit.getQuantiteStock() < quantite) {
            throw new StockInsuffisantException("Stock insuffisant pour " + produit.getDesignation() +
                    ". Disponible: " + produit.getQuantiteStock() + ", Demandé: " + quantite);
        }

        // 1. Mise à jour du stock physique
        produit.setQuantiteStock(produit.getQuantiteStock() - quantite);
        produitRepository.save(produit);

        // 2. Création de la trace historique pour l'IA
        MouvementStock mouvement = new MouvementStock();
        mouvement.setProduit(produit);
        mouvement.setQuantite(-quantite);
        mouvement.setType(TypeMouvement.SORTIE);

        return mouvementRepository.save(mouvement);
    }

    @Override
    @Transactional
    public MouvementStock enregistrerEntree(Long produitId, Integer quantite) {
        // Utilisation de ResourceNotFoundException
        Produit produit = produitRepository.findById(produitId)
                .orElseThrow(() -> new ResourceNotFoundException("Le produit avec l'ID " + produitId + " n'existe pas."));

        produit.setQuantiteStock(produit.getQuantiteStock() + quantite);
        produitRepository.save(produit);

        MouvementStock mouvement = new MouvementStock();
        mouvement.setProduit(produit);
        mouvement.setQuantite(quantite);
        mouvement.setType(TypeMouvement.ENTREE);

        return mouvementRepository.save(mouvement);
    }
}