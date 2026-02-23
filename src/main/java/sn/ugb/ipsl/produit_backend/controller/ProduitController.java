package sn.ugb.ipsl.produit_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.ugb.ipsl.produit_backend.model.Produit;
import sn.ugb.ipsl.produit_backend.service.ProduitService;

import java.util.List;

@RestController
@RequestMapping("/api/produits")
@CrossOrigin("*")
@Tag(name = "Gestion des Produits", description = "Endpoints pour gérer le catalogue de produits et les alertes de stock")
public class ProduitController {

    @Autowired
    private ProduitService produitService;

    @PostMapping
    @Operation(summary = "Créer un nouveau produit", description = "Ajoute un produit au catalogue avec un seuil d'alerte par défaut si non précisé.")
    public Produit creer(@RequestBody Produit produit) {
        return produitService.creerProduit(produit);
    }

    @GetMapping
    @Operation(summary = "Lister tous les produits", description = "Récupère la liste complète des produits en stock.")
    public List<Produit> toutLister() {
        return produitService.listeProduits();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un produit par ID", description = "Retourne les détails d'un produit spécifique ou une erreur 404.")
    public Produit getUn(@PathVariable Long id) {
        return produitService.obtenirProduitParId(id);
    }

    @GetMapping("/alertes")
    @Operation(summary = "Produits en alerte de stock", description = "Liste les produits dont la quantité est inférieure ou égale au seuil d'alerte.")
    public List<Produit> getAlertes() {
        return produitService.verifierStocksCritiques();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un produit", description = "Supprime définitivement un produit de la base de données via son ID.")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        produitService.supprimerProduit(id);
        return ResponseEntity.noContent().build(); // Renvoie un code 204 (Succès, pas de contenu)
    }
}