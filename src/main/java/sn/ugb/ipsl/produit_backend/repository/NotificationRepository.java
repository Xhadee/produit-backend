package sn.ugb.ipsl.produit_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.ugb.ipsl.produit_backend.model.Notification;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    /**
     * Pour la Navbar : Récupère uniquement les alertes non lues.
     * Trié du plus récent au plus ancien.
     */
    List<Notification> findByLuFalseOrderByDateCreationDesc();

    /**
     * Pour l'Historique Complet : Récupère TOUTES les notifications.
     * Indispensable pour ton composant NotificationHistoryComponent.
     */
    List<Notification> findAllByOrderByDateCreationDesc();
}
