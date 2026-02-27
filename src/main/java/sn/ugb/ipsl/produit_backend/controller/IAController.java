package sn.ugb.ipsl.produit_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sn.ugb.ipsl.produit_backend.ia.StockPredictor;
import sn.ugb.ipsl.produit_backend.ia.PredictionResult;

import java.util.List;

@RestController
@RequestMapping("/api/ia")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
@Tag(name = "Analyse IA & Prédictions", description = "Endpoints de calcul prédictif et analyse intelligente des stocks")
public class IAController {

    @Autowired
    private StockPredictor stockPredictor;

    @GetMapping("/analyse-complete")
    @Operation(
            summary = "Obtenir l'analyse de tout le catalogue",
            description = "Génère un rapport IA complet incluant les jours restants, l'impact financier et les quantités suggérées pour chaque produit."
    )
    public List<PredictionResult> getAnalyseComplete() {
        return stockPredictor.analyserToutLeStock();
    }

    @GetMapping("/analyse/{id}")
    @Operation(
            summary = "Prédire la rupture pour un produit spécifique",
            description = "Analyse approfondie basée sur l'historique des sorties pour déterminer la date de rupture prévue et la confiance de l'IA."
    )
    public PredictionResult analyser(
            @Parameter(description = "ID du produit à analyser") @PathVariable Long id) {
        return stockPredictor.calculerPrediction(id);
    }

    @GetMapping("/top-ventes")
    @Operation(
            summary = "Identifier le produit star (Meilleure vente)",
            description = "Détecte le produit ayant généré le plus gros volume de sorties cumulées."
    )
    public String getTopVentes() {
        // Cette méthode utilise la logique interne de ton StockPredictor
        // pour calculer le produit avec le volume de sorties maximum.
        return stockPredictor.calculerProduitStar();
    }
}