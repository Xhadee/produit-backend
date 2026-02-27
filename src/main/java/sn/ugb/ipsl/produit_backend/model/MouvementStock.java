package sn.ugb.ipsl.produit_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mouvements_stock")
public class MouvementStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produit_id")
    @JsonIgnore
    private Produit produit;

    private Integer quantite;

    @Enumerated(EnumType.STRING)
    private TypeMouvement type;

    // Force le nom de la colonne pour correspondre à MySQL et le nom JSON pour Angular
    @Column(name = "date_mouvement")
    @JsonProperty("dateMouvement")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateMouvement;

    @PrePersist
    protected void onCreate() {
        if (this.dateMouvement == null) {
            this.dateMouvement = LocalDateTime.now();
        }
    }

    // --- CONSTRUCTEURS ---
    public MouvementStock() {}

    public MouvementStock(Produit produit, Integer quantite, TypeMouvement type) {
        this.produit = produit;
        this.quantite = quantite;
        this.type = type;
        this.dateMouvement = LocalDateTime.now(); // Initialisation explicite
    }

    // --- GETTERS ET SETTERS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Produit getProduit() { return produit; }
    public void setProduit(Produit produit) { this.produit = produit; }

    public Integer getQuantite() { return quantite; }
    public void setQuantite(Integer quantite) { this.quantite = quantite; }

    public TypeMouvement getType() { return type; }
    public void setType(TypeMouvement type) { this.type = type; }

    public LocalDateTime getDateMouvement() { return dateMouvement; }
    public void setDateMouvement(LocalDateTime dateMouvement) { this.dateMouvement = dateMouvement; }
}