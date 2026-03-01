package sn.ugb.ipsl.produit_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.ugb.ipsl.produit_backend.model.Notification;
import sn.ugb.ipsl.produit_backend.service.NotificationService;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Notifications", description = "Gestion des alertes de stock et notifications système")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Operation(summary = "Historique complet", description = "Récupère toutes les notifications (lues et non lues) triées par date")
    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotifications() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    @Operation(summary = "Alertes actives", description = "Récupère uniquement les notifications non lues pour la cloche de la Navbar")
    @GetMapping("/non-lues")
    public ResponseEntity<List<Notification>> getNotificationsNonLues() {
        return ResponseEntity.ok(notificationService.getNotificationsNonLues());
    }

    @Operation(summary = "Supprimer une notification", description = "Supprime définitivement une notification de la base de données")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(
            @Parameter(description = "ID de la notification à supprimer") @PathVariable Long id) {
        notificationService.supprimerNotification(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Marquer comme lue", description = "Change l'état d'une notification de 'non lue' à 'lue'")
    @PutMapping("/{id}/lire")
    public ResponseEntity<Void> marquerCommeLue(@PathVariable Long id) {
        notificationService.marquerCommeLue(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Tout marquer comme lu", description = "Passe toutes les notifications non lues à l'état 'lue'")
    @PutMapping("/tout-lire")
    public ResponseEntity<Void> marquerToutCommeLu() {
        notificationService.marquerToutCommeLu();
        return ResponseEntity.ok().build();
    }
}