package sn.ugb.ipsl.produit_backend.ia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sn.ugb.ipsl.produit_backend.model.MouvementStock;
import sn.ugb.ipsl.produit_backend.model.Produit;
import sn.ugb.ipsl.produit_backend.model.TypeMouvement;
import sn.ugb.ipsl.produit_backend.repository.ProduitRepository;

import java.util.List;

@Component
public class StockPredictor {

    @Autowired
    private ProduitRepository produitRepository;

    /**
     * Calcule la prédiction de rupture pour un produit spécifique.
     * Utilisé par l'IA Controller et le Stock Scheduler.
     */
    @Transactional(readOnly = true)
    public PredictionResult calculerPrediction(Long produitId) {
        Produit produit = produitRepository.findById(produitId)
                .orElseThrow(() -> new RuntimeException("Produit introuvable avec l'ID: " + produitId));

        List<MouvementStock> mouvements = produit.getMouvements();

        // Calcul du volume total des sorties (ventes)
        double totalVendu = mouvements.stream()
                .filter(m -> m.getType() == TypeMouvement.SORTIE)
                .mapToDouble(MouvementStock::getQuantite)
                .sum();

        // Nombre de fois où le produit a été vendu
        long nbVentes = mouvements.stream()
                .filter(m -> m.getType() == TypeMouvement.SORTIE)
                .count();

        // Sécurité : évite les divisions par zéro ou les prédictions sur trop peu de données
        if (nbVentes < 2) {
            return new PredictionResult(produit.getDesignation(), 0.0, "Données historiques insuffisantes");
        }

        // Algorithme : Vitesse de croisière = Quantité totale vendue / Nombre de transactions
        double vitesseConsommation = totalVendu / nbVentes;
        double ventesRestantes = produit.getQuantiteStock() / vitesseConsommation;

        // Logique de recommandation
        String recommandation = (ventesRestantes < 5) ? "🚨 RÉAPPROVISIONNEMENT URGENT" : "✅ Stock sain";

        return new PredictionResult(
                produit.getDesignation(),
                Math.round(ventesRestantes * 10.0) / 10.0, // Arrondi à une décimale
                recommandation
        );
    }

    /**
     * Analyse l'ensemble des produits pour trouver celui qui a le plus de succès.
     * Logique de l'IA "Top Ventes".
     */
    @Transactional(readOnly = true)
    public String calculerProduitStar() {
        List<Produit> produits = produitRepository.findAll();
        Produit star = null;
        double maxVentes = 0;

        for (Produit p : produits) {
            double totalSorties = p.getMouvements().stream()
                    .filter(m -> m.getType() == TypeMouvement.SORTIE)
                    .mapToDouble(MouvementStock::getQuantite)
                    .sum();

            if (totalSorties > maxVentes) {
                maxVentes = totalSorties;
                star = p;
            }
        }

        if (star == null || maxVentes == 0) {
            return "Aucune vente significative n'a été enregistrée pour le moment.";
        }

        return String.format("🏆 PRODUIT STAR : %s | Total des ventes : %.0f unités.",
                star.getDesignation(), maxVentes);
    }
}