package sn.ugb.ipsl.produit_backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.ugb.ipsl.produit_backend.model.Notification;
import sn.ugb.ipsl.produit_backend.model.NotificationType;
import sn.ugb.ipsl.produit_backend.repository.NotificationRepository;
import sn.ugb.ipsl.produit_backend.service.NotificationService;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public List<Notification> getNotificationsNonLues() {
        return notificationRepository.findByLuFalseOrderByDateCreationDesc();
    }

    @Override
    public void creerAlerteStock(String nomProduit, int quantiteRestante) {
        Notification notif = new Notification();
        notif.setTitre("⚠️ Alerte Stock");
        notif.setMessage("Le produit [" + nomProduit + "] est presque épuisé (" + quantiteRestante + " restants).");
        notif.setType(NotificationType.ALERTE);
        notif.setLu(false);
        notificationRepository.save(notif);
    }

    @Override
    public void marquerCommeLue(Long id) {
        notificationRepository.findById(id).ifPresent(n -> {
            n.setLu(true);
            notificationRepository.save(n);
        });
    }

    @Override
    public void marquerToutCommeLu() {
        List<Notification> nonLues = notificationRepository.findByLuFalseOrderByDateCreationDesc();
        nonLues.forEach(n -> n.setLu(true));
        notificationRepository.saveAll(nonLues);
    }
}