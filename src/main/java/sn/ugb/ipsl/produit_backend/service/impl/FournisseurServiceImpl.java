package sn.ugb.ipsl.produit_backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.ugb.ipsl.produit_backend.exception.ResourceNotFoundException;
import sn.ugb.ipsl.produit_backend.model.Fournisseur;
import sn.ugb.ipsl.produit_backend.repository.FournisseurRepository;
import sn.ugb.ipsl.produit_backend.service.FournisseurService;

import java.util.List;

@Service
public class FournisseurServiceImpl implements FournisseurService {

    @Autowired
    private FournisseurRepository fournisseurRepository; // On garde un seul nom cohérent

    @Override
    public Fournisseur ajouter(Fournisseur f) {
        return fournisseurRepository.save(f);
    }

    @Override
    public List<Fournisseur> listerTous() {
        return fournisseurRepository.findAll();
    }

    @Override
    public Fournisseur trouverParId(Long id) {
        // Correction : Il faut implémenter la recherche réelle
        return fournisseurRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fournisseur non trouvé avec l'id : " + id));
    }


    public Fournisseur mettreAJour(Long id, Fournisseur detailsFournisseur) {
        Fournisseur fournisseur = trouverParId(id); // On réutilise la méthode du dessus

        fournisseur.setNom(detailsFournisseur.getNom());
        fournisseur.setTelephone(detailsFournisseur.getTelephone());
        fournisseur.setEmail(detailsFournisseur.getEmail());
        fournisseur.setNom(detailsFournisseur.getNom());

        return fournisseurRepository.save(fournisseur);
    }


    public void supprimer(Long id) {
        if (!fournisseurRepository.existsById(id)) {
            throw new ResourceNotFoundException("Fournisseur impossible à supprimer : id " + id + " inexistant");
        }
        fournisseurRepository.deleteById(id);
    }
}