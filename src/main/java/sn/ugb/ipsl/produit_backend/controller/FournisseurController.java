package sn.ugb.ipsl.produit_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.ugb.ipsl.produit_backend.model.Fournisseur;
import sn.ugb.ipsl.produit_backend.service.FournisseurService;

import java.util.List;

@RestController
@RequestMapping("/api/fournisseurs")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Gestion des Fournisseurs", description = "Endpoints pour gérer le carnet d'adresses des fournisseurs")
public class FournisseurController {

    @Autowired
    private FournisseurService fournisseurService;

    @PostMapping
    @Operation(summary = "Ajouter un fournisseur", description = "Enregistre un nouveau partenaire logistique.")
    public Fournisseur ajouter(@RequestBody Fournisseur fournisseur) {
        return fournisseurService.ajouter(fournisseur);
    }

    @GetMapping
    @Operation(summary = "Lister les fournisseurs")
    public List<Fournisseur> lister() {
        return fournisseurService.listerTous();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Trouver un fournisseur par ID")
    public Fournisseur obtenirParId(@PathVariable Long id) {
        return fournisseurService.trouverParId(id);
    }

    // --- NOUVELLE MÉTHODE : MISE À JOUR ---
    @PutMapping("/{id}")
    @Operation(summary = "Modifier un fournisseur", description = "Met à jour les informations d'un fournisseur existant (nom, adresse, contact).")
    public Fournisseur modifier(@PathVariable Long id, @RequestBody Fournisseur fournisseur) {
        return fournisseurService.mettreAJour(id, fournisseur);
    }

    // --- NOUVELLE MÉTHODE : SUPPRESSION ---
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un fournisseur", description = "Supprime définitivement un fournisseur. Attention aux contraintes d'intégrité si des produits y sont liés.")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        fournisseurService.supprimer(id);
        return ResponseEntity.noContent().build(); // Réponse 204
    }
}