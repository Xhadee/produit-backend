package sn.ugb.ipsl.produit_backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.ugb.ipsl.produit_backend.exception.ResourceNotFoundException;
import sn.ugb.ipsl.produit_backend.model.Categorie;
import sn.ugb.ipsl.produit_backend.repository.CategorieRepository;
import sn.ugb.ipsl.produit_backend.service.CategorieService;

import java.util.List;

@Service
public class CategorieServiceImpl implements CategorieService {

    @Autowired
    private CategorieRepository categorieRepository;

    @Override
    public Categorie enregistrer(Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    @Override
    public List<Categorie> listerToutes() {
        return categorieRepository.findAll();
    }

    @Override
    public Categorie trouverParId(Long id) {
        return categorieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Catégorie non trouvée avec l'id : " + id));
    }


    @Override
    public void supprimer(Long id) {
        if (!categorieRepository.existsById(id)) {
            throw new ResourceNotFoundException("Impossible de supprimer : Catégorie inexistante (ID: " + id + ")");
        }
        categorieRepository.deleteById(id);
    }

  
    @Override
    public Categorie mettreAJour(Long id, Categorie detailsCategorie) {
        Categorie categorie = trouverParId(id);
        categorie.setNom(detailsCategorie.getNom());
        categorie.setDescription(detailsCategorie.getDescription());
        return categorieRepository.save(categorie);
    }
}