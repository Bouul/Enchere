package fr.enchere.controller;

import fr.enchere.model.User;
import fr.enchere.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Page profilView

    // Afficher le profil (view profil)
    @GetMapping("/profil")
    public String getProfil(@RequestParam String username, Model model) {
        // Récupérer l'utilisateur par son pseudo
        User user = userService.findByUsername(username);
        if (user != null) {
            model.addAttribute("user", user);
            return "profilView"; // Page de profil (vue)
        } else {
            model.addAttribute("error", "Utilisateur non trouvé");
            return "error"; // Vue d'erreur (ou une redirection vers une autre page)
        }
    }


    // Page profil-modification

    // Méthode pour afficher les informations du profil pour les modifiers
    @GetMapping("/profil/modifier")
    public String getProfilModif(@RequestParam("username") String username, Model model) {
        // Récupère l'utilisateur depuis la base de données via le UserService
        User user = userService.findByUsername(username);

        if (user != null) {
            // Passe l'utilisateur à la vue (profil-modification.html)
            model.addAttribute("user", user);
            return "profile-modification"; // Vue de modification du profil
        } else {
            // Si l'utilisateur n'existe pas, redirige vers une page d'erreur ou une autre page
            model.addAttribute("error", "Utilisateur non trouvé");
            return "error"; // Vue d'erreur ou redirection
        }
    }

    // Sauvegarder les modifications du profil (POST)
    @PostMapping("/profil/modifier")
    public String enregistrerProfil(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        // Appeler le service pour mettre à jour les informations de l'utilisateur
        try {
            userService.updateUser(user);
            redirectAttributes.addFlashAttribute("success", "Profil mis à jour avec succès!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la mise à jour du profil");
        }
        // Rediriger vers la page de profil après la mise à jour
        return "redirect:/profil?username=" + user.getUsername();
    }
}
