package sn.ugb.ipsl.produit_backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.ugb.ipsl.produit_backend.model.MouvementStock;
import sn.ugb.ipsl.produit_backend.model.Produit;
import sn.ugb.ipsl.produit_backend.model.TypeMouvement;
import sn.ugb.ipsl.produit_backend.repository.MouvementStockRepository;
import sn.ugb.ipsl.produit_backend.repository.ProduitRepository;
import sn.ugb.ipsl.produit_backend.service.NotificationService; // IMPORT
import sn.ugb.ipsl.produit_backend.service.StockService;

import java.util.List;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private MouvementStockRepository mouvementRepository;

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private NotificationService notificationService; // INJECTION DU SERVICE D'ALERTE

    @Override
    public List<MouvementStock> getMouvementsParProduit(Long produitId) {
        return mouvementRepository.findByProduitIdOrderByDateMouvementDesc(produitId);
    }

    @Override
    @Transactional
    public MouvementStock enregistrerEntree(Long produitId, Integer quantite) {
        Produit produit = produitRepository.findById(produitId)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));

        produit.setQuantiteStock(produit.getQuantiteStock() + quantite);
        produitRepository.save(produit);

        MouvementStock mouvement = new MouvementStock(produit, quantite, TypeMouvement.ENTREE);
        return mouvementRepository.save(mouvement);
    }

    @Override
    @Transactional
    public MouvementStock enregistrerSortie(Long produitId, Integer quantite) {
        Produit produit = produitRepository.findById(produitId)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));

        if (produit.getQuantiteStock() < quantite) {
            throw new RuntimeException("Stock insuffisant ! Disponible: " + produit.getQuantiteStock());
        }

        // 1. Mise à jour du stock physique
        produit.setQuantiteStock(produit.getQuantiteStock() - quantite);
        Produit produitMaj = produitRepository.save(produit);

        // 2. VÉRIFICATION DU SEUIL APRÈS SORTIE
        if (produitMaj.getQuantiteStock() <= produitMaj.getSeuilAlerte()) {
            notificationService.creerAlerteStock(produitMaj.getDesignation(), produitMaj.getQuantiteStock());
        }

        // 3. Création du mouvement
        MouvementStock mouvement = new MouvementStock(produitMaj, quantite, TypeMouvement.SORTIE);
        return mouvementRepository.save(mouvement);
    }
}