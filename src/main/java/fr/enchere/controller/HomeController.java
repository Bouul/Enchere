package fr.enchere.controller;

import fr.enchere.model.*;
import fr.enchere.model.DTO.BidPageDTO;
import fr.enchere.repository.BidRepository;
import fr.enchere.model.Item;
import fr.enchere.service.*;
import fr.enchere.service.ItemService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Controller
public class HomeController {

    private final ItemService itemService;
    private final UserService userService;
    private final BidService bidService;
    private final BidRepository bidRepository;
    private final CategoryService categoryService;

    public HomeController(ItemService itemService, UserService userService, BidService bidService, BidRepository bidRepository, CategoryService categoryService) {
        this.itemService = itemService;
        this.userService = userService;
        this.bidService = bidService;
        this.bidRepository = bidRepository;
        this.categoryService = categoryService;
    }


    @GetMapping("/enchere")
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
        Long userId = isAuthenticated ? userService.findByUsername(username).getUserId() : null;

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
                case "my-bidding":
                    bidPage = bidService.getBidsByUsernamePage(username, PageRequest.of(page, size));
                    break;
                case "my-bids":
                    bidPage = bidService.getByUserIdPage(userId, PageRequest.of(page, size));
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
    public String sellItemPage(Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "/sell-item-page";
    }

    @GetMapping("/deconnexion-confirmation")
    public String deconnexionPage() {
        return "deconnexion";
    }

    @GetMapping("/bidding-page")
    public String getBiddingPage(@RequestParam Long id, Model model) {
        // Récupérer les informations de l'enchère
        Item item = itemService.findByItemId(id);
        Bid highestBid = bidService.findHighestBidByItemId(id);
        LocalDateTime now = LocalDateTime.now();

        if (item != null && !Objects.equals(item.getSaleStatus(), "RETRAIT")) {
            if (item.getEndDate().isAfter(LocalDateTime.now())) {
                model.addAttribute("item", item);
                model.addAttribute("highestBid", highestBid);
                return "bidding-page";
            } else {
                model.addAttribute("item", item);
                model.addAttribute("highestBid", highestBid);
                return "sale-detail-page-end-of-auction";
            }
        } else {
            model.addAttribute("error", "L'article demandé n'existe pas.");
            return "redirect:/";
        }
    }

    @PostMapping("/bidding")
    public String placeBid(@RequestParam("itemId") Long itemId,
                           @RequestParam("bidAmount") int bidAmount,
                           Principal principal,
                           RedirectAttributes redirectAttributes) {

        String username = principal.getName();
        User user = userService.findByUsername(username);

        Item item = itemService.findByItemId(itemId);
        if (item == null) {
            redirectAttributes.addFlashAttribute("error", "L'article n'existe pas.");
            return "redirect:/";
        }

        // Vérifie si l'utilisateur est le vendeur
        if (item.getSeller().getUserId().equals(user.getUserId())) {
            redirectAttributes.addFlashAttribute("error", "Vous ne pouvez pas enchérir sur votre propre article.");
            return "redirect:/bidding-page?id=" + itemId;
        }

        LocalDateTime endDate = item.getEndDate();


        if (endDate.isBefore(LocalDateTime.now())) {
            redirectAttributes.addFlashAttribute("error", "L'enchère est terminée.");
            return "redirect:/bidding-page?id=" + itemId;
        }


        // Vérifie si j'ai plus de crédit que le montant de l'enchère
        if (user.getCredit() < bidAmount) {
            redirectAttributes.addFlashAttribute("error", "Vous n'avez pas assez de crédit pour enchérir.");
            return "redirect:/bidding-page?id=" + itemId;
        }


        Bid highestBid = bidService.findHighestBidByItemId(itemId);

        if (bidAmount <= highestBid.getBidAmount()) {
            redirectAttributes.addFlashAttribute("error", "Le montant doit être supérieur à " + highestBid.getBidAmount() + " .");
            return "redirect:/bidding-page?id=" + itemId;
        }

        Bid bid = new Bid();
        bid.setBidAmount(bidAmount);
        bid.setBidDate(LocalDateTime.now());
        bid.setItem(item);
        bid.setUser(user);
        bidRepository.save(bid);

        user.setCredit(user.getCredit()-bid.getBidAmount());
        userService.updateUser(user);

        redirectAttributes.addFlashAttribute("success", "Votre enchère a bien été enregistrée !");
        return "redirect:/bidding-page?id=" + itemId;
    }

    @PostMapping("/item/retrait")
    public String retraitItem(@RequestParam Long itemId,
                              RedirectAttributes redirectAttributes,
                              HttpServletRequest request) {

        Item item = itemService.findByItemId(itemId);
        if (item != null) {
            item.setSaleStatus("RETRAIT");
            itemService.updateItem(item);

            // Invalider la session
            request.getSession().invalidate();

            // Rediriger vers la page de déconnexion
            redirectAttributes.addFlashAttribute("success", "Retrait confirmé avec succès !");
            return "redirect:/";
        } else {
            redirectAttributes.addFlashAttribute("error", "Un problème est survenu lors du retrait.");
            return "redirect:/";
        }
    }
