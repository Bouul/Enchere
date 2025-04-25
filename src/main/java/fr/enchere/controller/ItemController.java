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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
     public String createItem(@ModelAttribute ItemDTO item,
                              @RequestParam("photo") MultipartFile photo,
                              RedirectAttributes redirectAttrs) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime startDate = LocalDateTime.parse(item.getStartDate(), formatter);
        LocalDateTime endDate = LocalDateTime.parse(item.getEndDate(), formatter);
        LocalDateTime now = LocalDateTime.parse(LocalDateTime.now().format(formatter));
        if (startDate.isAfter(endDate)) {
            redirectAttrs.addFlashAttribute("error", "La date de début doit être avant la date de fin.");
            return "redirect:/sell-item-page";
        }
        if (startDate.isBefore(now)) {
            redirectAttrs.addFlashAttribute("error", "La date de début doit être après la date actuelle.");
            return "redirect:/sell-item-page";
        }
        if (endDate.isBefore(now)) {
            redirectAttrs.addFlashAttribute("error", "La date de fin doit être après la date actuelle.");
            return "redirect:/sell-item-page";
        }
        List<Bid> bids = bidService.getBids();
        List<Category> categories = categoryService.findAll();
        itemService.saveItem(item, photo);
        Long categoryId = item.getCategory();
        String category = categoryService.findById(categoryId).getLabel();
        redirectAttrs.addFlashAttribute("item", item);
        redirectAttrs.addFlashAttribute("category", category);
        redirectAttrs.addFlashAttribute("bids", bids);
        redirectAttrs.addFlashAttribute("categories", categories);
        redirectAttrs.addFlashAttribute("showModal", true);
        return "redirect:/enchere";
     }



}
