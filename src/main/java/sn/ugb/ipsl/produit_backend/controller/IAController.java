package sn.ugb.ipsl.produit_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sn.ugb.ipsl.produit_backend.ia.StockPredictor;
import sn.ugb.ipsl.produit_backend.ia.PredictionResult;

@RestController
@RequestMapping("/api/ia")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
@Tag(name = "Analyse IA & Prédictions", description = "Calculs avancés pour anticiper les ruptures de stock et analyser les tendances")
public class IAController {

    @Autowired private StockPredictor stockPredictor;

    @GetMapping("/analyse/{id}")
    @Operation(summary = "Prédire la rupture de stock",
            description = "Analyse l'historique des sorties pour estimer combien de jours il reste avant la rupture de stock (Stock-out).")
    public PredictionResult analyser(
            @Parameter(description = "ID du produit à analyser") @PathVariable Long id) {
        return stockPredictor.calculerPrediction(id);
    }

    @GetMapping("/top-ventes")
    @Operation(summary = "Identifier le produit star",
            description = "Analyse tous les mouvements de sortie pour déterminer quel produit est le plus vendu actuellement.")
    public String getTopVentes() {
        return stockPredictor.calculerProduitStar();
    }
}