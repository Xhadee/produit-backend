package sn.ugb.ipsl.produit_backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import sn.ugb.ipsl.produit_backend.model.Utilisateur;
import sn.ugb.ipsl.produit_backend.repository.UtilisateurRepository;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Utilisateurs & Auth", description = "Endpoints pour la gestion des comptes et l'authentification")
public class UtilisateurController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // --- PARTIE AUTHENTIFICATION (PUBLIQUE) ---

    @Operation(summary = "Inscription d'un nouvel utilisateur", description = "Crée un compte et hache le mot de passe avant sauvegarde.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Username déjà utilisé")
    })
    @PostMapping("/api/auth/register")
    public ResponseEntity<?> register(@RequestBody Utilisateur user) {
        if (utilisateurRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Nom d'utilisateur déjà pris !"));
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() == null) user.setRole("USER");

        Utilisateur savedUser = utilisateurRepository.save(user);
        savedUser.setPassword(null); // Sécurité : ne pas renvoyer le hash dans la réponse
        return ResponseEntity.ok(savedUser);
    }

    @Operation(summary = "Connexion utilisateur", description = "Vérifie les credentials et retourne les infos de l'utilisateur.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentification réussie"),
            @ApiResponse(responseCode = "401", description = "Identifiants invalides")
    })
    @PostMapping("/api/auth/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        return utilisateurRepository.findByUsername(username)
                .map(user -> {
                    if (passwordEncoder.matches(password, user.getPassword())) {
                        user.setPassword(null); // Sécurité
                        return ResponseEntity.ok(user);
                    }
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Mot de passe incorrect"));
                })
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Utilisateur non trouvé")));
    }

    // --- PARTIE PROFIL (PRIVÉE) ---

    @Operation(summary = "Récupérer mon profil", description = "Retourne les détails de l'utilisateur actuellement connecté via la session.")
    @GetMapping("/api/utilisateurs/me")
    public ResponseEntity<Utilisateur> getMyProfile(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        return utilisateurRepository.findByUsername(userDetails.getUsername())
                .map(user -> {
                    user.setPassword(null);
                    return ResponseEntity.ok(user);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Mettre à jour mon profil", description = "Modifie le nom, l'email ou le mot de passe de l'utilisateur connecté.")
    @PutMapping("/api/utilisateurs/update")
    public ResponseEntity<?> updateProfile(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Utilisateur updatedUser) {
        if (userDetails == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        return utilisateurRepository.findByUsername(userDetails.getUsername())
                .map(user -> {
                    user.setNom(updatedUser.getNom());
                    user.setEmail(updatedUser.getEmail());

                    if (updatedUser.getPassword() != null && !updatedUser.getPassword().trim().isEmpty()) {
                        user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                    }

                    utilisateurRepository.save(user);
                    return ResponseEntity.ok(Map.of("message", "Profil mis à jour avec succès"));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}