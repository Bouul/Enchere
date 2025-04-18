package fr.enchere.controller;

import fr.enchere.model.User;
import fr.enchere.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    // Page inscription
    @GetMapping("/signup")
    public String showSignup(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute User user, @RequestParam String confirmation,
                                     Model model, RedirectAttributes redirectAttributes) {
        // Vérifier que les mots de passe correspondent
        if (!user.getPassword().equals(confirmation)) {
            model.addAttribute("error", "Les mots de passe ne correspondent pas");
            return "signup";
        }

        try {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            user.setCredit(100);
            userService.createUser(user);
            redirectAttributes.addFlashAttribute("success", "Compte créé avec succès");
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "signup";
        }
    }



    // Page profilView

    // Afficher le profil (view profil)
    @GetMapping("/profil")
    public String getProfil(@RequestParam Long userId, Model model) {
        User user = userService.findByUserId(userId);
        if (user != null) {
            model.addAttribute("user", user);
            
            // Calcul et ajout du pourcentage de complétion du profil
            int profileCompletion = userService.getProfileCompletion(userId);
            model.addAttribute("profileCompletion", profileCompletion);
            
            // Ajout des statistiques d'activité
            model.addAttribute("createdAuctions", user.getCreatedAuctionsCount());
            model.addAttribute("wonAuctions", user.getWonAuctionsCount());
            model.addAttribute("activeAuctions", user.getActiveAuctionsCount());
            
            return "profile-view"; // Page de profil (vue)
        } else {
            model.addAttribute("error", "Utilisateur non trouvé");
            return "error";
        }
    }


    // Page profile-modification

    // Méthode pour afficher les informations du profil pour les modifiers
    @GetMapping("/profil/modifier")
    public String getProfilModif(@RequestParam("userId") Long userId, Model model) {
        User user = userService.findByUserId(userId);
        if (user != null) {
            // Passe l'utilisateur à la vue (profil-modification.html)
            model.addAttribute("user", user);
            return "profile-modification";
        } else {
            // Si l'utilisateur n'existe pas, redirige vers une page d'erreur ou une autre page
            model.addAttribute("error", "Utilisateur non trouvé");
            return "error"; // Vue d'erreur ou redirection
        }
    }

    // Sauvegarder les modifications du profil (POST)
    @PostMapping("/profil/modifier")
    public String enregistrerProfil(
            @ModelAttribute User user,
            @RequestParam String confirmation,
            RedirectAttributes redirectAttributes,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Données invalides dans le formulaire.");
            return "redirect:/profil/modifier?userId=" + user.getUserId();
        }

        // Récupérer l'utilisateur existant
        User existingUser = userService.findByUserId(user.getUserId());

        // Vérifier si l'utilisateur souhaite modifier son mot de passe
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            // Vérifier que les mots de passe correspondent
            if (!user.getPassword().equals(confirmation)) {
                redirectAttributes.addFlashAttribute("error", "Les mots de passe ne correspondent pas.");
                return "redirect:/profil/modifier?userId=" + user.getUserId();
            }

            // Vérifier que le mot de passe respecte les critères
            if (!isValidPassword(user.getPassword())) {
                redirectAttributes.addFlashAttribute("error",
                        "Le mot de passe doit contenir au moins une majuscule, un caractère spécial et un chiffre.");
                return "redirect:/profil/modifier?userId=" + user.getUserId();
            }

            // Encoder le nouveau mot de passe
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            // Si le mot de passe est vide, conserver l'ancien
            user.setPassword(existingUser.getPassword());
        }

        try {
            userService.updateUser(user);
            redirectAttributes.addFlashAttribute("success", "Profil mis à jour avec succès!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la mise à jour du profil");
        }

        return "redirect:/profil?userId=" + user.getUserId();
    }

    @PostMapping("/profil/supprimer")
    public String supprimerCompte(@RequestParam Long userId,
                                  RedirectAttributes redirectAttributes,
                                  HttpServletRequest request) {
        User user = userService.findByUserId(userId);
        if (user != null) {
            user.setActive(false); // Désactiver le compte
            userService.updateUser(user);

            // Invalider la session
            request.getSession().invalidate();

            // Rediriger vers la page de déconnexion
            redirectAttributes.addFlashAttribute("success", "Votre compte a été désactivé avec succès.");
            return "redirect:/deconnexion-confirmation";
        } else {
            redirectAttributes.addFlashAttribute("error", "Utilisateur non trouvé.");
            return "redirect:/profil?userId=" + userId;
        }
    }

    /**
     * Vérifie si le mot de passe respecte les critères de sécurité
     * @param password Le mot de passe à vérifier
     * @return true si le mot de passe est valide, false sinon
     */
    private boolean isValidPassword(String password) {
        // Vérifier la présence d'au moins une majuscule
        boolean hasUppercase = !password.equals(password.toLowerCase());

        // Vérifier la présence d'au moins un chiffre
        boolean hasDigit = password.matches(".*\\d.*");

        // Vérifier la présence d'au moins un caractère spécial
        boolean hasSpecialChar = password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*");

        return hasUppercase && hasDigit && hasSpecialChar;
    }
}
