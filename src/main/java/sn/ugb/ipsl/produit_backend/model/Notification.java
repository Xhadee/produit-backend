package sn.ugb.ipsl.produit_backend.model;

import jakarta.persistence.*;
import lombok.Data; // <--- TRÈS IMPORTANT
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data // Génère automatiquement les Getters et Setters
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public boolean isLu() {
        return lu;
    }

    public void setLu(boolean lu) {
        this.lu = lu;
    }

    private String titre; // Vérifie l'orthographe ici
    private String message;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private LocalDateTime dateCreation;
    private boolean lu = false;

    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
    }
}