package sn.ugb.ipsl.produit_backend.ia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sn.ugb.ipsl.produit_backend.model.MouvementStock;
import sn.ugb.ipsl.produit_backend.model.Produit;
import sn.ugb.ipsl.produit_backend.model.TypeMouvement;
import sn.ugb.ipsl.produit_backend.repository.ProduitRepository;

import java.time.LocalDate;
import java.util.List;

@Component
public class StockPredictor {

    @Autowired
    private ProduitRepository produitRepository;

    @Transactional(readOnly = true)
    public PredictionResult calculerPrediction(Long produitId) {
        Produit produit = produitRepository.findById(produitId)
                .orElseThrow(() -> new RuntimeException("Produit introuvable"));

        List<MouvementStock> sorties = produit.getMouvements().stream()
                .filter(m -> m.getType() == TypeMouvement.SORTIE)
                .toList();

        if (sorties.size() < 2) {
            return genererResultatVide(produit);
        }

        double totalVendu = sorties.stream().mapToDouble(MouvementStock::getQuantite).sum();
        double vitesseMoyenne = totalVendu / sorties.size();

        int joursRestants = (int) (produit.getQuantiteStock() / vitesseMoyenne);
        LocalDate dateRupture = LocalDate.now().plusDays(joursRestants);

        double derniereVente = sorties.get(sorties.size() - 1).getQuantite();
        String tendance = (derniereVente > vitesseMoyenne) ? "HAUSSE" :
                (derniereVente < vitesseMoyenne ? "BAISSE" : "STABLE");

        int quantiteSuggeree = (int) Math.max(0, (vitesseMoyenne * 30) - produit.getQuantiteStock());

        // Sécurité : évite le NullPointerException si le prix est nul
        double prix = (produit.getPrixUnitaire() != null) ? produit.getPrixUnitaire() : 0.0;
        double impactFinancier = quantiteSuggeree * prix;

        double confianceIA = Math.min(0.98, 0.5 + (sorties.size() * 0.05));
        boolean estSaisonnier = tendance.equals("HAUSSE") && sorties.size() > 10;

        String message = (joursRestants < 5) ?
                "⚠️ Rupture imminente. Réapprovisionnement critique." :
                "✅ Stock maîtrisé. Prochaine commande suggérée dans " + (joursRestants - 5) + " jours.";

        // On retourne le record avec le champ imageUrl du produit
        return new PredictionResult(
                produit.getId(),
                produit.getDesignation(),
                joursRestants,
                tendance,
                message,
                confianceIA,
                quantiteSuggeree,
                impactFinancier,
                estSaisonnier,
                dateRupture,
                produit.getImageUrl() // Ajout de l'image ici
        );
    }

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
            return "Aucune vente significative enregistrée.";
        }

        return String.format("🏆 PRODUIT STAR : %s | Total des sorties : %.0f unités.",
                star.getDesignation(), maxVentes);
    }

    private PredictionResult genererResultatVide(Produit p) {
        // Ajout de l'URL de l'image même en cas de données insuffisantes
        return new PredictionResult(p.getId(), p.getDesignation(), 0, "STABLE",
                "Données historiques insuffisantes pour une prédiction fiable.",
                0.0, 0, 0.0, false, LocalDate.now(), p.getImageUrl());
    }

    @Transactional(readOnly = true)
    public List<PredictionResult> analyserToutLeStock() {
        return produitRepository.findAll().stream()
                .map(p -> calculerPrediction(p.getId()))
                .toList();
    }
}