package fr.enchere.controller;

import fr.enchere.model.Bid;
import fr.enchere.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private BidService bidService;

    @GetMapping("/")
    public String home(Model model) {
        List<Bid> bids = bidService.getBids();
        model.addAttribute("bids", bids);
        return "index";
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

    @GetMapping("/bidding-page")
    public String biddingPage() {
        return "/bidding-page";
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

}
