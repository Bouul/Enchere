package fr.enchere.controller;

import fr.enchere.model.DTO.ItemDTO;
import fr.enchere.model.Item;
import fr.enchere.model.User;
import fr.enchere.service.CategoryService;
import fr.enchere.service.ItemService;
import fr.enchere.service.PickupLocationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ItemController {

     private final ItemService itemService;

     public ItemController(ItemService itemService) {
         this.itemService = itemService;
     }

     @PostMapping("/saveItem")
     public Item createItem(@ModelAttribute ItemDTO item) {
        return itemService.saveItem(item);
     }
}
