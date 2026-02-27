package sn.ugb.ipsl.produit_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.ugb.ipsl.produit_backend.model.MouvementStock;
import sn.ugb.ipsl.produit_backend.model.Produit;
import sn.ugb.ipsl.produit_backend.model.TypeMouvement;
import sn.ugb.ipsl.produit_backend.repository.MouvementStockRepository;
import sn.ugb.ipsl.produit_backend.repository.ProduitRepository;

import java.util.List;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private MouvementStockRepository mouvementRepository;

    @Autowired
    private ProduitRepository produitRepository;

    @Override
    public List<MouvementStock> getMouvementsParProduit(Long produitId) {
        // On utilise la méthode de tri décroissant pour Angular
        return mouvementRepository.findByProduitIdOrderByDateMouvementDesc(produitId);
    }

    @Override
    @Transactional
    public MouvementStock enregistrerEntree(Long produitId, Integer quantite) {
        Produit produit = produitRepository.findById(produitId)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));

        // 1. Mise à jour du stock physique
        produit.setQuantiteStock(produit.getQuantiteStock() + quantite);
        produitRepository.save(produit);

        // 2. Création du mouvement
        MouvementStock mouvement = new MouvementStock(produit, quantite, TypeMouvement.ENTREE);
        return mouvementRepository.save(mouvement);
    }

    @Override
    @Transactional
    public MouvementStock enregistrerSortie(Long produitId, Integer quantite) {
        Produit produit = produitRepository.findById(produitId)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));

        // 1. Vérification du stock disponible
        if (produit.getQuantiteStock() < quantite) {
            throw new RuntimeException("Stock insuffisant ! Disponible: " + produit.getQuantiteStock());
        }

        // 2. Mise à jour du stock physique
        produit.setQuantiteStock(produit.getQuantiteStock() - quantite);
        produitRepository.save(produit);

        // 3. Création du mouvement
        MouvementStock mouvement = new MouvementStock(produit, quantite, TypeMouvement.SORTIE);
        return mouvementRepository.save(mouvement);
    }
}