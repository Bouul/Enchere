package fr.enchere.controller;

import fr.enchere.model.User;
import fr.enchere.service.UserService;
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

        if (user.getPassword() == null || !user.getPassword().equals(confirmation)) {
            redirectAttributes.addFlashAttribute("error", "Les mots de passe ne correspondent pas.");
            return "redirect:/profil/modifier?userId=" + user.getUserId();
        }

        try {
            // Si un nouveau mot de passe est fourni, on l’encode
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                String encodedPassword = passwordEncoder.encode(user.getPassword());
                user.setPassword(encodedPassword);
            }

            userService.updateUser(user);
            redirectAttributes.addFlashAttribute("success", "Profil mis à jour avec succès!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la mise à jour du profil");
        }

        return "redirect:/profil?userId=" + user.getUserId();
    }
}
