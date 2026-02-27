package sn.ugb.ipsl.produit_backend.service;

import sn.ugb.ipsl.produit_backend.model.MouvementStock;
import java.util.List; // Import manquant

public interface StockService {
    List<MouvementStock> getMouvementsParProduit(Long produitId);
    MouvementStock enregistrerEntree(Long produitId, Integer quantite);
    MouvementStock enregistrerSortie(Long produitId, Integer quantite);
}