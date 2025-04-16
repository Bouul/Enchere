package fr.enchere.controller;

import fr.enchere.model.User;
import fr.enchere.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    public String getProfil(@RequestParam String username, Model model) {
        // Récupérer l'utilisateur par son pseudo
        User user = userService.findByUsername(username);
        if (user != null) {
            model.addAttribute("user", user);
            return "profile-view"; // Page de profil (vue)
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
