package fr.enchere.controller;

import fr.enchere.model.Bid;
import fr.enchere.model.Category;
import fr.enchere.model.Item;
import fr.enchere.repository.ItemRepository;
import fr.enchere.service.BidService;
import fr.enchere.service.CategoryService;
import fr.enchere.service.ItemService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private BidService bidService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public String home(Model model) {
        List<Bid> bids = bidService.getBids();
        List<Category> categories = categoryService.findAll();
        model.addAttribute("bids", bids);
        model.addAttribute("categories", categories);
        return "index";
    }


    @GetMapping("/api/bids/filter")
    @ResponseBody
    public List<Bid> filterBids(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String search) {

        List<Bid> result;

        // Si aucun filtre n'est appliqué
        if ((categoryId == null || categoryId == 0) && (search == null || search.isEmpty())) {
            result = bidService.getBids();
        }
        // Si seule la catégorie est spécifiée
        else if (search == null || search.isEmpty()) {
            result = bidService.getBidsByCategory(categoryId);
        }
        // Si seul le nom est spécifié
        else if (categoryId == null || categoryId == 0) {
            result = bidService.getBidsByItemName(search);
        }
        // Si les deux filtres sont spécifiés
        else {
            result = bidService.getBidsByCategoryAndItemName(categoryId, search);
        }

        // Vérification pour éviter les NPE
        if (result == null) {
            return new ArrayList<>();
        }
        return result;
    }

    @GetMapping("/login")
    public String showLoginPage(
            @RequestParam(required = false) String error,
            @RequestParam(required = false) String expired,
            @RequestParam(required = false) String invalid,
            Model model) {

        if (expired != null) {
            model.addAttribute("sessionMessage", "Votre session a expiré après 5 minutes d'inactivité. Veuillez vous reconnecter.");
        } else if (invalid != null) {
            model.addAttribute("sessionMessage", "Votre session n'est plus valide. Veuillez vous reconnecter.");
        } else if (error != null) {
            model.addAttribute("errorMessage", "Nom d'utilisateur ou mot de passe incorrect.");
        }

        return "login";
    }


    @GetMapping("/acquisition-page")
    public String acquisitionPage() {
        return "acquisition-page";
    }

    @GetMapping("/auction-list-pages")
    public String acquisitionListPage() {
        return "auction-list-pages";
    }

    @GetMapping("/profile-modification")
    public String profileModification() {
        return "/profile-modification";
    }

    @GetMapping("/profilView")
    public String profilView() {
        return "profile-view";
    }

    @GetMapping("/sell-item-page")
    public String sellItemPage() {
        return "/sell-item-page";
    }

    @GetMapping("/deconnexion-confirmation")
    public String deconnexionPage() {
        return "deconnexion";
    }

}
