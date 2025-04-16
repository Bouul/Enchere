package fr.enchere.controller;

import fr.enchere.model.User;
import fr.enchere.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Page profilView

    // Afficher le profil (view profil)
    @GetMapping("/profil")
    public String getProfil(@RequestParam Long userId, Model model) {
        User user = userService.getUserById(userId);
        if (user != null) {
            model.addAttribute("user", user);
            return "profilView";
        } else {
            model.addAttribute("error", "Utilisateur non trouvé");
            return "error";
        }
    }


    // Page profile-modification

    // Méthode pour afficher les informations du profil pour les modifiers
    @GetMapping("/profil/modifier")
    public String getProfilModif(@RequestParam("userId") Long userId, Model model) {
        User user = userService.getUserById(userId);
        if (user != null) {
            model.addAttribute("user", user);
            return "profile-modification";
        } else {
            model.addAttribute("error", "Utilisateur non trouvé");
            return "error";
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
            userService.updateUser(user);
            redirectAttributes.addFlashAttribute("success", "Profil mis à jour avec succès!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la mise à jour du profil");
        }

        return "redirect:/profil?userId=" + user.getUserId();
    }
}
