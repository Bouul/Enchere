package fr.enchere.service;

import fr.enchere.model.*;
import fr.enchere.model.DTO.ItemDTO;
import fr.enchere.repository.ItemRepository;
import fr.enchere.repository.PickupLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final UserService userService;
    private final PickupLocationRepository pickupLocationRepository;
    private final CategoryService categoryService;

    @Autowired
    public ItemService(ItemRepository itemRepository, UserService userService, PickupLocationRepository pickupLocationRepository, CategoryService categoryService) {
        this.itemRepository = itemRepository;
        this.userService = userService;
        this.pickupLocationRepository = pickupLocationRepository;
        this.categoryService = categoryService;
    }

    public Item saveItem(ItemDTO form) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.findByUsername(username);
        Item item = new Item();
        PickupLocation pickupLocation = new PickupLocation();
        if (form.getCity() != null && form.getPostalCode() != null && form.getStreet() != null) {
            pickupLocation.setCity(form.getCity());
            pickupLocation.setPostalCode(form.getPostalCode());
            pickupLocation.setStreet(form.getStreet());
        }
        pickupLocation.setCity(user.getCity());
        pickupLocation.setPostalCode(user.getPostalCode());
        pickupLocation.setStreet(user.getStreet());
        item.setItemName(form.getItemName());
        item.setDescription(form.getDescription());
        item.setStartDate(LocalDateTime.parse(form.getStartDate()));
        item.setEndDate(LocalDateTime.parse(form.getEndDate()));
        item.setStartingPrice(form.getStartingPrice());
        item.setCategory(categoryService.findById(form.getCategory()));
        item.setSeller(user);
        item.setPickupLocationBid(pickupLocation);
        item.setSaleStatus(String.valueOf(SalesStatusCycle.CREATED));
        pickupLocationRepository.save(pickupLocation);

        return itemRepository.save(item);
    }

    public Item findByItemId(Long itemId) {
        return itemRepository.findByItemId(itemId);
    }
}
