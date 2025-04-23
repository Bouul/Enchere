package fr.enchere.controller;

import fr.enchere.model.Bid;
import fr.enchere.model.DTO.ItemDTO;
import fr.enchere.model.Item;
import fr.enchere.model.User;
import fr.enchere.repository.BidRepository;
import fr.enchere.service.*;
import jakarta.servlet.http.HttpServletRequest;
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
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class ItemController {


    private final ItemService itemService;
    private final UserService userService;
    private final BidService bidService;
    private final BidRepository bidRepository;



     public ItemController(UserService userService, ItemService itemService, BidService bidService, BidRepository bidRepository) {
         this.userService = userService;
         this.itemService = itemService;
         this.bidService = bidService;
         this.bidRepository = bidRepository;
     }

     @PostMapping("/saveItem")
     public Item createItem(@ModelAttribute ItemDTO item) {
        return itemService.saveItem(item);
     }



}
