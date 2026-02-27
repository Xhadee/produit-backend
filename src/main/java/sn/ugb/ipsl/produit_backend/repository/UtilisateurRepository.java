package sn.ugb.ipsl.produit_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.ugb.ipsl.produit_backend.model.Utilisateur;
import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByUsername(String username);
}
