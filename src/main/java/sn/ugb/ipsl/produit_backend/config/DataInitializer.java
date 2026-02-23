package sn.ugb.ipsl.produit_backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import sn.ugb.ipsl.produit_backend.model.*;
import sn.ugb.ipsl.produit_backend.repository.*;
import java.util.Arrays;
import java.util.Random;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired private ProduitRepository produitRepository;
    @Autowired private CategorieRepository categorieRepository;
    @Autowired private FournisseurRepository fournisseurRepository;
    @Autowired private MouvementStockRepository mouvementRepository;

    @Override
    public void run(String... args) throws Exception {
        if (categorieRepository.count() > 0) return; // Évite de remplir à chaque redémarrage

        System.out.println("🚀 Initialisation de la base de données avec des données massives...");

        // 1. Création des Catégories
        Categorie informatique = new Categorie(null, "Informatique", "Ordinateurs et périphériques");
        Categorie reseau = new Categorie(null, "Réseau", "Matériel de connectivité");
        Categorie bureau = new Categorie(null, "Fournitures", "Articles de bureau");
        categorieRepository.saveAll(Arrays.asList(informatique, reseau, bureau));

        // 2. Création des Fournisseurs
        Fournisseur f1 = new Fournisseur(null, "Sénégal Digital", "338001122", "contact@sendigital.sn");
        Fournisseur f2 = new Fournisseur(null, "Global Tech Dakar", "775554433", "sales@globaltech.sn");
        Fournisseur f3 = new Fournisseur(null, "BuroTop", "339887766", "info@burotop.com");
        fournisseurRepository.saveAll(Arrays.asList(f1, f2, f3));

        // 3. Création massive de Produits (50 produits)
        Random random = new Random();
        String[] types = {"Laptop", "Clavier", "Souris", "Ecran", "Switch", "Routeur", "Imprimante", "Scanner", "Câble", "Onduleur"};
        String[] marques = {"HP", "Dell", "Cisco", "Logitech", "Apple", "Lenovo", "TP-Link"};

        for (int i = 1; i <= 50; i++) {
            String designation = types[random.nextInt(types.length)] + " " + marques[random.nextInt(marques.length)] + " v" + i;
            Double prix = 5000 + (1000000 - 5000) * random.nextDouble();
            Integer stock = random.nextInt(100);
            Integer seuil = 5 + random.nextInt(15);

            Produit p = new Produit();
            p.setDesignation(designation);
            p.setPrixUnitaire(Math.round(prix * 100.0) / 100.0);
            p.setQuantiteStock(stock);
            p.setSeuilAlerte(seuil);
            p.setCategorie(i % 2 == 0 ? informatique : (i % 3 == 0 ? reseau : bureau));
            p.setFournisseur(i % 3 == 0 ? f1 : (i % 2 == 0 ? f2 : f3));

            produitRepository.save(p);

            // 4. Génération d'historique (Mouvements de stock) pour l'IA
            // On crée 5 mouvements aléatoires par produit
            for (int j = 0; j < 5; j++) {
                TypeMouvement type = random.nextBoolean() ? TypeMouvement.ENTREE : TypeMouvement.SORTIE;
                Integer qte = 1 + random.nextInt(10);
                MouvementStock m = new MouvementStock(p, qte, type);
                mouvementRepository.save(m);
            }
        }

        System.out.println("✅ Base de données alimentée avec 50 produits et 250 mouvements de stock !");
    }
}