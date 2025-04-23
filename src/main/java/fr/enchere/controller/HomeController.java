package fr.enchere.controller;

import fr.enchere.model.Bid;
import fr.enchere.model.Category;
import fr.enchere.model.DTO.BidPageDTO;
import fr.enchere.model.Item;
import fr.enchere.repository.ItemRepository;
import fr.enchere.model.Item;
import fr.enchere.repository.ItemRepository;
import fr.enchere.service.BidService;
import fr.enchere.service.CategoryService;
import fr.enchere.service.ItemService;
import fr.enchere.service.ItemService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
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
    public BidPageDTO filterBids(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String filterType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size,
            Authentication authentication) {

        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
        String username = isAuthenticated ? authentication.getName() : null;

        Page<Bid> bidPage;

        if (!isAuthenticated || filterType == null) {
            if ((categoryId == null || categoryId == 0) && (search == null || search.isEmpty())) {
                bidPage = bidService.getBidsPage(PageRequest.of(page, size));
            } else if (search == null || search.isEmpty()) {
                bidPage = bidService.getBidsByCategoryPage(categoryId, PageRequest.of(page, size));
            } else if (categoryId == null || categoryId == 0) {
                bidPage = bidService.getBidsByItemNamePage(search, PageRequest.of(page, size));
            } else {
                bidPage = bidService.getBidsByCategoryAndItemNamePage(categoryId, search, PageRequest.of(page, size));
            }
        } else {
            switch (filterType) {
                case "my-bids":
                    bidPage = bidService.getBidsByUsernamePage(username, PageRequest.of(page, size));
                    break;
                case "my-wins":
                    bidPage = bidService.getWonBidsByUsernamePage(username, PageRequest.of(page, size));
                    break;
                default:
                    if ((categoryId == null || categoryId == 0) && (search == null || search.isEmpty())) {
                        bidPage = bidService.getBidsPage(PageRequest.of(page, size));
                    } else if (search == null || search.isEmpty()) {
                        bidPage = bidService.getBidsByCategoryPage(categoryId, PageRequest.of(page, size));
                    } else if (categoryId == null || categoryId == 0) {
                        bidPage = bidService.getBidsByItemNamePage(search, PageRequest.of(page, size));
                    } else {
                        bidPage = bidService.getBidsByCategoryAndItemNamePage(categoryId, search, PageRequest.of(page, size));
                    }
            }
        }

        return new BidPageDTO(
                bidPage.getContent(),
                bidPage.getTotalPages(),
                bidPage.getNumber(),
                bidPage.getTotalElements()
        );
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
