package sn.ugb.ipsl.produit_backend.ia;

import java.time.LocalDate;

public record PredictionResult(
        Long produitId,
        String nomProduit,
        int joursRestants,
        String tendance,
        String message,
        double confianceIA,
        int quantiteSuggeree,
        double impactFinancier,
        boolean estSaisonnier,
        LocalDate dateRupturePrevue,
        String imageUrl // Nouveau champ ajouté ici
) {}