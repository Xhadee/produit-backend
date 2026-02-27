package sn.ugb.ipsl.produit_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.ugb.ipsl.produit_backend.model.Notification;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByLuFalseOrderByDateCreationDesc();
}
