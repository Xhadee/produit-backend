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
    private StockPredictor stockPredictor;

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    /**
     * S'exécute toutes les 30 secondes pour analyser la santé du stock.
     */
    @Scheduled(fixedRate = 30000)
    public void verifierLesStocks() {
        System.out.println("\n--- 🤖 ANALYSE IA DU " + dtf.format(LocalDateTime.now()) + " ---");

        List<Produit> tousLesProduits = produitService.listeProduits();
        boolean alerteTrouvee = false;

        for (Produit p : tousLesProduits) {
            // Appel de la nouvelle logique prédictive
            PredictionResult prediction = stockPredictor.calculerPrediction(p.getId());

            // On déclenche l'alerte si le seuil est atteint OU si l'IA prévoit moins de 5 jours restants
            if (p.getQuantiteStock() <= p.getSeuilAlerte() || prediction.joursRestants() < 5) {
                alerteTrouvee = true;

                String typeAlerte = (p.getQuantiteStock() <= p.getSeuilAlerte()) ? "🚨 SEUIL" : "🔮 IA-URGENCE";

                System.out.printf("[%s] %-20s | Stock: %d | Jours restants: %d | Tendance: %s | Recommandation: %s%n",
                        typeAlerte,
                        p.getDesignation(),
                        p.getQuantiteStock(),
                        prediction.joursRestants(),
                        prediction.tendance(),
                        prediction.message());
            }
        }

        if (!alerteTrouvee) {
            System.out.println("✅ État du stock : Optimal. L'IA n'anticipe aucune rupture à court terme.");
        }

        // Affichage du produit star via la méthode que nous avons ajoutée au StockPredictor
        System.out.println(stockPredictor.calculerProduitStar());
        System.out.println("-----------------------------------------------------------\n");
    }
}