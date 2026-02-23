package sn.ugb.ipsl.produit_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sn.ugb.ipsl.produit_backend.model.MouvementStock;
import sn.ugb.ipsl.produit_backend.service.StockService;

@RestController
@RequestMapping("/api/stock")
@CrossOrigin("*")
@Tag(name = "Mouvements de Stock", description = "Endpoints pour enregistrer les entrées (achats) et sorties (ventes)")
public class StockController {

    @Autowired
    private StockService stockService;

    @PostMapping("/entree/{id}")
    @Operation(summary = "Enregistrer une entrée en stock",
            description = "Augmente la quantité physique d'un produit. À utiliser lors de la réception d'une commande fournisseur.")
    public MouvementStock entree(
            @Parameter(description = "ID du produit à approvisionner") @PathVariable Long id,
            @Parameter(description = "Quantité à ajouter") @RequestParam Integer quantite) {
        return stockService.enregistrerEntree(id, quantite);
    }

    @PostMapping("/sortie/{id}")
    @Operation(summary = "Enregistrer une sortie de stock",
            description = "Diminue la quantité physique d'un produit. L'opération échoue si le stock est insuffisant.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sortie enregistrée avec succès"),
            @ApiResponse(responseCode = "400", description = "Stock insuffisant pour réaliser l'opération"),
            @ApiResponse(responseCode = "404", description = "Produit introuvable")
    })
    public MouvementStock sortie(
            @Parameter(description = "ID du produit vendu ou retiré") @PathVariable Long id,
            @Parameter(description = "Quantité à retirer") @RequestParam Integer quantite) {
        return stockService.enregistrerSortie(id, quantite);
    }
}