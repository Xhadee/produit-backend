package sn.ugb.ipsl.produit_backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.ugb.ipsl.produit_backend.exception.ResourceNotFoundException;
import sn.ugb.ipsl.produit_backend.model.Produit;
import sn.ugb.ipsl.produit_backend.repository.ProduitRepository;
import sn.ugb.ipsl.produit_backend.service.ProduitService;
import sn.ugb.ipsl.produit_backend.service.NotificationService; // IMPORT CRUCIAL

import java.util.List;

@Service
public class ProduitServiceImpl implements ProduitService {

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private NotificationService notificationService; // INJECTION CRUCIALE

    @Override
    public Produit creerProduit(Produit produit) {
        if (produit.getSeuilAlerte() == null) {
            produit.setSeuilAlerte(5);
        }
        Produit p = produitRepository.save(produit);

        // APPEL ICI
        verifierEtAlerter(p);

        return p;
    }

    @Override
    public List<Produit> listerTousLesProduits() {
        return produitRepository.findAll();
    }

    @Override
    public List<Produit> listeProduits() {
        return produitRepository.findAll();
    }

    @Override
    public Produit obtenirProduitParId(Long id) {
        return produitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Le produit " + id + " est introuvable."));
    }

    @Override
    public void supprimerProduit(Long id) {
        if (!produitRepository.existsById(id)) {
            throw new ResourceNotFoundException("Impossible de supprimer : le produit " + id + " n'existe pas.");
        }
        produitRepository.deleteById(id);
    }

    @Override
    public List<Produit> verifierStocksCritiques() {
        return produitRepository.findProduitsEnAlerte();
    }

    @Override
    public Produit modifierProduit(Long id, Produit details) {
        Produit produitExistant = produitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé avec l'id : " + id));

        produitExistant.setDesignation(details.getDesignation());
        produitExistant.setPrixUnitaire(details.getPrixUnitaire());
        produitExistant.setQuantiteStock(details.getQuantiteStock());
        produitExistant.setSeuilAlerte(details.getSeuilAlerte());
        produitExistant.setCategorie(details.getCategorie());
        produitExistant.setFournisseur(details.getFournisseur());

        Produit maj = produitRepository.save(produitExistant);

        // APPEL ICI AUSSI (quand on diminue le stock manuellement)
        verifierEtAlerter(maj);

        return maj;
    }

    /**
     * LA MÉTHODE QUI REMPLIT TA TABLE MYSQL
     */
    private void verifierEtAlerter(Produit p) {
        if (p.getQuantiteStock() <= p.getSeuilAlerte()) {
            notificationService.creerAlerteStock(p.getDesignation(), p.getQuantiteStock());
        }
    }
}