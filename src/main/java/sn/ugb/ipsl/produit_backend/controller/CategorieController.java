package sn.ugb.ipsl.produit_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.ugb.ipsl.produit_backend.model.Categorie;
import sn.ugb.ipsl.produit_backend.service.CategorieService;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Gestion des Catégories", description = "Endpoints pour l'organisation des produits par type")
public class CategorieController {

    @Autowired
    private CategorieService categorieService;

    @PostMapping
    @Operation(summary = "Créer une catégorie")
    public Categorie ajouter(@RequestBody Categorie categorie) {
        return categorieService.enregistrer(categorie);
    }

    @GetMapping
    @Operation(summary = "Lister toutes les catégories")
    public List<Categorie> lister() {
        return categorieService.listerToutes();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir une catégorie par ID")
    public Categorie getUne(@PathVariable Long id) {
        return categorieService.trouverParId(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour une catégorie", description = "Modifie le nom ou la description d'une catégorie existante.")
    public Categorie modifier(@PathVariable Long id, @RequestBody Categorie categorie) {
        return categorieService.mettreAJour(id, categorie);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une catégorie", description = "Supprime la catégorie. Attention, cela peut échouer si des produits y sont encore liés.")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        categorieService.supprimer(id);
        return ResponseEntity.noContent().build();
    }
}