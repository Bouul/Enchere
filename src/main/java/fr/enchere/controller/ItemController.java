package fr.enchere.controller;

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
     private final CategoryService categoryService;
     private final PickupLocationService pickupLocationService;

     public ItemController(ItemService itemService, CategoryService categoryService, PickupLocationService pickupLocationService) {
         this.itemService = itemService;
         this.categoryService = categoryService;
         this.pickupLocationService = pickupLocationService;
     }

     @PostMapping("/saveItem")
     public Item createItem(@ModelAttribute Item item) {
        return itemService.saveItem(item);
     }


}
