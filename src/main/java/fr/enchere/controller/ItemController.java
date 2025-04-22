package fr.enchere.controller;

import fr.enchere.model.Bid;
import fr.enchere.model.DTO.ItemDTO;
import fr.enchere.model.Item;
import fr.enchere.model.User;
import fr.enchere.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Controller
public class ItemController {


     private final ItemService itemService;

    private final UserService userService;
    @Autowired
    private BidService bidService;



     public ItemController(UserService userService, ItemService itemService) {
         this.userService = userService;
         this.itemService = itemService;
     }

     @PostMapping("/saveItem")
     public Item createItem(@ModelAttribute ItemDTO item) {
        return itemService.saveItem(item);
     }

    @GetMapping("/bidding-page")
    public String getBiddingPage(@RequestParam Long id, Model model) {
        // Récupérer les informations de l'enchère
        Item item = itemService.findByItemId(id);
        Bid highestBid = bidService.findHighestBidByItemId(id);

        if (item != null) {
            model.addAttribute("item", item);
            model.addAttribute("highestBid", highestBid);
            return "bidding-page"; // Retourne la vue Thymeleaf
        } else {
            model.addAttribute("error", "L'article demandé n'existe pas.");
            return "error"; // Vue d'erreur
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



        Bid highestBid = bidService.findHighestBidByItemId(itemId);

        if (bidAmount <= highestBid.getBidAmount()) {
            redirectAttributes.addFlashAttribute("error", "Le montant doit être supérieur à " + highestBid.getBidAmount() + " €.");
            return "redirect:/bidding-page?id=" + itemId;
        }

        Bid bid = new Bid();
        bid.setBidAmount(bidAmount);
        bid.setBidDate(LocalDateTime.now());
        bid.setItem(item);
        bid.setUser(user);
        bidService.createBid(bid);

        redirectAttributes.addFlashAttribute("success", "Votre enchère a bien été enregistrée !");
        return "redirect:/bidding-page?id=" + itemId;
    }



}
