package fr.enchere.controller;

import fr.enchere.model.Bid;
import fr.enchere.model.DTO.ItemDTO;
import fr.enchere.model.Item;
import fr.enchere.model.User;
import fr.enchere.service.BidService;
import fr.enchere.service.CategoryService;
import fr.enchere.service.ItemService;
import fr.enchere.service.PickupLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ItemController {


     private final ItemService itemService;

    @Autowired
    private BidService bidService;

     public ItemController(ItemService itemService) {
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
}
