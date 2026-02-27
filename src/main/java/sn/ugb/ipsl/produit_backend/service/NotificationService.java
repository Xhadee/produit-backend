package sn.ugb.ipsl.produit_backend.service;

import sn.ugb.ipsl.produit_backend.model.Notification;
import java.util.List;

public interface NotificationService {
    // Récupérer les alertes non lues pour la cloche de notification
    List<Notification> getNotificationsNonLues();

    // Créer une alerte (sera appelé par le ProduitService ou un Scheduler)
    void creerAlerteStock(String nomProduit, int quantiteRestante);

    // Marquer une notification comme vue
    void marquerCommeLue(Long id);

    // Tout supprimer ou marquer tout comme lu (optionnel)
    void marquerToutCommeLu();
}