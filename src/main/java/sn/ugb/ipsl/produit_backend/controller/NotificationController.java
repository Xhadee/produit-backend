package sn.ugb.ipsl.produit_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.ugb.ipsl.produit_backend.model.Notification;
import sn.ugb.ipsl.produit_backend.service.NotificationService;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "http://localhost:4200")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // Récupérer uniquement les notifications non lues (pour la pastille rouge sur la cloche)
    @GetMapping("/non-lues")
    public ResponseEntity<List<Notification>> getNotificationsNonLues() {
        return ResponseEntity.ok(notificationService.getNotificationsNonLues());
    }

    // Marquer une notification spécifique comme lue (quand on clique dessus)
    @PutMapping("/{id}/lire")
    public ResponseEntity<Void> marquerCommeLue(@PathVariable Long id) {
        notificationService.marquerCommeLue(id);
        return ResponseEntity.ok().build();
    }

    // Marquer tout comme lu (bouton "Tout effacer" dans Angular)
    @PutMapping("/tout-lire")
    public ResponseEntity<Void> marquerToutCommeLu() {
        notificationService.marquerToutCommeLu();
        return ResponseEntity.ok().build();
    }
}