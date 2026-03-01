package sn.ugb.ipsl.produit_backend.service;

import sn.ugb.ipsl.produit_backend.model.Notification;
import java.util.List;

public interface NotificationService {

    // --- MÉTHODES EXISTANTES ---

    // Récupérer les alertes non lues pour la cloche de notification
    List<Notification> getNotificationsNonLues();

    // Créer une alerte (sera appelé par le ProduitService ou un Scheduler)
    void creerAlerteStock(String nomProduit, int quantiteRestante);

    // Marquer une notification comme vue
    void marquerCommeLue(Long id);

    // Tout marquer comme lu
    void marquerToutCommeLu();

    // --- NOUVELLES MÉTHODES À AJOUTER ---

    /**
     * Récupère TOUTES les notifications pour la page historique (lues et non lues)
     */
    List<Notification> getAllNotifications();

    /**
     * Supprime définitivement une notification de la base de données
     */
    void supprimerNotification(Long id);
}