package sn.ugb.ipsl.produit_backend.service;

import sn.ugb.ipsl.produit_backend.model.MouvementStock;

public interface StockService {
    MouvementStock enregistrerEntree(Long produitId, Integer quantite);
    MouvementStock enregistrerSortie(Long produitId, Integer quantite);
}