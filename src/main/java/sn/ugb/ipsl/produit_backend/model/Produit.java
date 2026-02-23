package sn.ugb.ipsl.produit_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "produits")
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String designation;

    private Double prixUnitaire;
    private Integer quantiteStock;
    private Integer seuilAlerte;

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    @ManyToOne
    @JoinColumn(name = "fournisseur_id")
    private Fournisseur fournisseur;

    @JsonIgnore // Empêche l'erreur LazyInitializationException et la boucle infinie
    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL)
    private List<MouvementStock> mouvements;

    // --- CONSTRUCTEURS ---
    public Produit() {}

    public Produit(Long id, String designation, Double prixUnitaire, Integer quantiteStock, Integer seuilAlerte, Categorie categorie, Fournisseur fournisseur) {
        this.id = id;
        this.designation = designation;
        this.prixUnitaire = prixUnitaire;
        this.quantiteStock = quantiteStock;
        this.seuilAlerte = seuilAlerte;
        this.categorie = categorie;
        this.fournisseur = fournisseur;
    }

    // --- GETTERS ET SETTERS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }

    public Double getPrixUnitaire() { return prixUnitaire; }
    public void setPrixUnitaire(Double prixUnitaire) { this.prixUnitaire = prixUnitaire; }

    public Integer getQuantiteStock() { return quantiteStock; }
    public void setQuantiteStock(Integer quantiteStock) { this.quantiteStock = quantiteStock; }

    public Integer getSeuilAlerte() { return seuilAlerte; }
    public void setSeuilAlerte(Integer seuilAlerte) { this.seuilAlerte = seuilAlerte; }

    public Categorie getCategorie() { return categorie; }
    public void setCategorie(Categorie categorie) { this.categorie = categorie; }

    public Fournisseur getFournisseur() { return fournisseur; }
    public void setFournisseur(Fournisseur fournisseur) { this.fournisseur = fournisseur; }

    public List<MouvementStock> getMouvements() { return mouvements; }
    public void setMouvements(List<MouvementStock> mouvements) { this.mouvements = mouvements; }
}