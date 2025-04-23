package fr.enchere.controller;

import fr.enchere.model.Bid;
import fr.enchere.model.Category;
import fr.enchere.model.DTO.ItemDTO;
import fr.enchere.model.Item;
import fr.enchere.repository.BidRepository;
import fr.enchere.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/api")
public class ItemController {


    private final ItemService itemService;
    private final CategoryService categoryService;
    private final BidService bidService;

    public ItemController(ItemService itemService, CategoryService categoryService, BidService bidService) {
        this.itemService = itemService;
        this.categoryService = categoryService;
        this.bidService = bidService;
    }

    @PostMapping("/saveItem")
     public String createItem(@ModelAttribute ItemDTO item, Model model) {
        List<Bid> bids = bidService.getBids();
        List<Category> categories = categoryService.findAll();
        itemService.saveItem(item);
        model.addAttribute("bids", bids);
        model.addAttribute("categories", categories);
        model.addAttribute("item", item);
        model.addAttribute("showModal", true);
        return "index";
     }



}
