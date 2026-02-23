package sn.ugb.ipsl.produit_backend.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sn.ugb.ipsl.produit_backend.ia.PredictionResult;
import sn.ugb.ipsl.produit_backend.ia.StockPredictor;
import sn.ugb.ipsl.produit_backend.model.Produit;
import sn.ugb.ipsl.produit_backend.service.ProduitService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class StockScheduler {

    @Autowired
    private ProduitService produitService;

    @Autowired
    private StockPredictor stockPredictor; // Injection du module IA

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Scheduled(fixedRate = 30000)
    public void verifierLesStocks() {
        System.out.println("\n--- 📊 RAPPORT INTELLIGENT DU " + dtf.format(LocalDateTime.now()) + " ---");

        List<Produit> tousLesProduits = produitService.listeProduits();
        boolean alerteTrouvee = false;

        for (Produit p : tousLesProduits) {
            // On récupère la prédiction de l'IA pour chaque produit
            PredictionResult prediction = stockPredictor.calculerPrediction(p.getId());

            // Condition d'affichage : soit le stock est bas, soit l'IA prédit une rupture proche
            if (p.getQuantiteStock() <= p.getSeuilAlerte() || prediction.ventesAvantRupture() < 10) {
                alerteTrouvee = true;

                String icone = (p.getQuantiteStock() <= p.getSeuilAlerte()) ? "⚠️ SEUIL ATTEINT" : "🔮 PRÉDICTION IA";

                System.out.printf("[%s] %-20s | Stock: %d | Seuil: %d | IA: %s (%.1f ventes rest.)%n",
                        icone,
                        p.getDesignation(),
                        p.getQuantiteStock(),
                        p.getSeuilAlerte(),
                        prediction.recommandation(),
                        prediction.ventesAvantRupture());
            }
        }

        if (!alerteTrouvee) {
            System.out.println("✅ État global du stock : Stable. Aucune action requise.");
        }

        // Petit bonus : On affiche le produit star à chaque rapport
        System.out.println(stockPredictor.calculerProduitStar());
        System.out.println("-----------------------------------------------------------\n");
    }
}