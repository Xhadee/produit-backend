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
        if (categorieRepository.count() > 0) return;

        System.out.println("🚀 Initialisation du catalogue avec adresses fournisseurs...");

        // 1. Création des Catégories
        Categorie highTech = new Categorie(null, "High-Tech & Gadgets", "Innovations et accessoires connectés");
        Categorie gastro = new Categorie(null, "Gastronomie & Terroir", "Saveurs d'exception et produits du terroir");
        Categorie mode = new Categorie(null, "Mode & Accessoires", "Élégance et style au quotidien");
        Categorie maison = new Categorie(null, "Maison & Déco", "Art de vivre et décoration intérieure");
        Categorie sport = new Categorie(null, "Sport & Bien-être", "Équipements pour une vie active");
        Categorie culture = new Categorie(null, "Culture & Loisirs", "Pour l'esprit et les passions");
        categorieRepository.saveAll(Arrays.asList(highTech, gastro, mode, maison, sport, culture));

        // 2. Création des Fournisseurs avec ADRESSES (Corrigé)
        Fournisseur fTech = new Fournisseur(null, "Dakar Digital Import", "+221 33 800 10 10", "contact@dakardigital.sn", "Avenue Cheikh Anta Diop, Dakar");
        Fournisseur fGastro = new Fournisseur(null, "Le Comptoir des Saveurs", "+221 77 500 20 20", "commandes@saveurs.sn", "Quartier Ndiolofène, Saint-Louis");
        Fournisseur fArtisan = new Fournisseur(null, "Artisans du Sénégal", "+221 33 900 30 30", "artisanat@senegal.sn", "Village Artisanal de Soumbédioune, Dakar");
        Fournisseur fGlobal = new Fournisseur(null, "Global Logistics & Trade", "+221 70 400 50 50", "info@globaltrade.sn", "Zone Industrielle de Mbao, Rufisque");

        fournisseurRepository.saveAll(Arrays.asList(fTech, fGastro, fArtisan, fGlobal));

        // 3. Ajout des produits
        // --- HIGH-TECH ---
        creerProduit("Ordinateur Portable Gaming - RTX 4070", 1250000.0, 12, 3, "https://www.laptopspirit.fr/comparateur/images/fiches/3/G-37929-1-msi-katana-17-b13-1.jpg", highTech, fTech);
        creerProduit("Casque Audio Bluetooth ANC", 185000.0, 25, 5, "https://s2.dmcdn.net/v/ZsC7y1fRVgLGGNz7t/x720", highTech, fTech);
        creerProduit("Montre Connectée Sport GPS", 95000.0, 40, 8, "https://www.cdiscount.com/pdt2/1/5/7/1/700x700/xtr1685694013157/rw/montre-connectee-sport-appel-bluetooth-ronde-1-54.jpg", highTech, fTech);
        creerProduit("Enceinte Connectée 360°", 110000.0, 15, 4, "https://blog.cobrason.com/wp-content/uploads/2017/11/sony-lf-s50g-noir-corps10-792x509.jpg", highTech, fTech);
        creerProduit("Drone Caméra 4K Stabilisé", 450000.0, 8, 2, "https://media.cdn.kaufland.de/product-images/1024x1024/aeeacbbcfab86220a4459743cd0b93e9.jpg", highTech, fTech);

        // --- GASTRONOMIE ---
        creerProduit("Coffret Dégustation de Thés", 35000.0, 50, 10, "https://static.wixstatic.com/media/05f3ce_a9ab6cf2c062453fb43cca225099e2fb~mv2.jpeg/v1/fill/w_700,h_700,al_c,q_85,enc_avif,quality_auto/05f3ce_a9ab6cf2c062453fb43cca225099e2fb~mv2.jpeg", gastro, fGastro);
        creerProduit("Huile d'Olive Vierge Extra", 12500.0, 100, 20, "https://www.huiledoliveitalienne.com/img/cms/blog/14/huile-d-olive.jpg", gastro, fGastro);
        creerProduit("Chocolat Noir 70% Grand Cru", 4500.0, 200, 30, "https://chocolaterie-lalere.fr/97-large_default/tablette-grand-cru-madagascar-64-100g.jpg", gastro, fGastro);
        creerProduit("Plateau de Fromages AOP", 28000.0, 15, 5, "https://www.produits-laitiers.com/app/uploads/2020/04/aop-45.jpg", gastro, fGastro);
        creerProduit("Café en Grains Arabica", 8500.0, 80, 15, "https://www.boutique-ethiquable.com/3291/cafe-blend-grains-arabica-equitable-conversion.jpg", gastro, fGastro);

        // --- MODE ---
        creerProduit("Sac à Main en Cuir Artisanal", 75000.0, 20, 5, "https://tegaura.com/wp-content/uploads/2025/07/Image_fx-42.png", mode, fArtisan);
        creerProduit("Montre Classique Automatique", 250000.0, 10, 2, "https://m.media-amazon.com/images/I/81aowMaayDL._AC_UY900_.jpg", mode, fGlobal);
        creerProduit("Lunettes de Soleil Polarisées", 55000.0, 35, 10, "https://m.media-amazon.com/images/I/61Tpwc02IzL._AC_SL1500_.jpg", mode, fGlobal);
        creerProduit("Ceinture Tressée Cuir", 15000.0, 60, 15, "https://www.emporiosanfelice.com/cdn/shop/files/cinta-pelle-intrecciata-marrone-fibia-tiffany-1.webp?v=1737999471", mode, fArtisan);
        creerProduit("Écharpe en Cachemire", 45000.0, 25, 5, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ_2Eo8jifAOSQYDwRbNrJN3dl0buARlTjSPQ&s", mode, fGlobal);

        // --- MAISON ---
        creerProduit("Lampe Design Connectée", 65000.0, 30, 10, "https://m.media-amazon.com/images/S/aplus-media-library-service-media/13a96b4a-434e-459f-9aab-0d9def54b09b.__CR0,0,970,600_PT0_SX970_V1___.jpg", maison, fGlobal);
        creerProduit("Couette en Plumes d'Oie", 120000.0, 15, 3, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRDiZ-UmtU3ldQwvFFIpJsKfuT0hB_XH1g_2A&s", maison, fGlobal);
        creerProduit("Set de Table en Lin (6pcs)", 22000.0, 45, 10, "https://m.media-amazon.com/images/I/81mNqCqdnLL._AC_UF1000,1000_QL80_.jpg", maison, fArtisan);
        creerProduit("Bougies Parfumées Vanille", 9500.0, 80, 20, "https://www.10doigts.fr/assets/generics/parfum-bougie-27-ml-vanille.jpg", maison, fGlobal);
        creerProduit("Cadre Photo Numérique WiFi", 85000.0, 20, 5, "https://m.media-amazon.com/images/I/71tZPRqi6+L._AC_UF350,350_QL80_.jpg", maison, fTech);

        // --- SPORT ---
        creerProduit("Tapis de Yoga Écologique", 25000.0, 40, 10, "https://inphysio.fr/cdn/shop/products/Tapisdeyogaantiderapant8_2048x.jpg?v=1677834065", sport, fGlobal);
        creerProduit("Vélo d'Appartement Connecté", 450000.0, 5, 2, "https://i.postimg.cc/s2y77Mm0/3.jpg", sport, fGlobal);
        creerProduit("Montre de Plongée 200m", 320000.0, 12, 3, "https://images.hbjo-online.com/webp/images/all/SRPK01K1-3.jpg", sport, fTech);
        creerProduit("Pack Élastiques Musculation", 18000.0, 55, 15, "https://m.media-amazon.com/images/I/71DKCAdRImL.jpg", sport, fGlobal);
        creerProduit("Bouteille Isotherme Inox 1L", 15000.0, 90, 20, "https://www.verygourde.fr/wp-content/uploads/2020/05/Les-brillantes1800x1800.jpg", sport, fGlobal);

        // --- CULTURE ---
        creerProduit("Collection Livres Best-sellers", 25000.0, 30, 5, "https://www.melty.fr/wp-content/uploads/meltyfr/2022/12/a-la-une.jpg", culture, fGlobal);
        creerProduit("Jeu de Société Stratégie", 35000.0, 20, 5, "https://www.cettefamille.com/wp-content/uploads/2018/09/jeux-de-societe-personnes-agees.jpg", culture, fGlobal);
        creerProduit("Guitare Acoustique Folk", 145000.0, 10, 2, "https://peachguitars.2dimg.com/114/4o0a6789_2b5eacd1d6.jpg", culture, fArtisan);
        creerProduit("Puzzle 3D Tour Eiffel", 19500.0, 40, 10, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTP6nWzvmciuInoGhmT1GdKdJNpZ1ZJ5Y68nA&s", culture, fGlobal);
        creerProduit("Abonnement Magazine 1 an", 48000.0, 100, 1, "https://kiosquemag.twic.pics/media/image/63/a3/f1b75507e10c8a4900044516a03d.jpg", culture, fGlobal);

        System.out.println("✅ Base de données initialisée avec succès !");
    }

    private void creerProduit(String designation, Double prix, Integer stock, Integer seuil, String img, Categorie cat, Fournisseur fou) {
        Produit p = new Produit();
        p.setDesignation(designation);
        p.setPrixUnitaire(prix);
        p.setQuantiteStock(stock);
        p.setSeuilAlerte(seuil);
        p.setImageUrl(img);
        p.setCategorie(cat);
        p.setFournisseur(fou);
        produitRepository.save(p);

        Random r = new Random();
        for (int i = 0; i < 5; i++) {
            TypeMouvement type = (r.nextDouble() > 0.4) ? TypeMouvement.ENTREE : TypeMouvement.SORTIE;
            mouvementRepository.save(new MouvementStock(p, 1 + r.nextInt(5), type));
        }
    }
}