package fr.enchere.service;

import fr.enchere.model.*;
import fr.enchere.model.DTO.ItemDTO;
import fr.enchere.repository.BidRepository;
import fr.enchere.repository.ItemRepository;
import fr.enchere.repository.PickupLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final UserService userService;
    private final PickupLocationRepository pickupLocationRepository;
    private final CategoryService categoryService;
    private final BidRepository bidRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository, UserService userService, PickupLocationRepository pickupLocationRepository, CategoryService categoryService, BidRepository bidRepository) {

        this.itemRepository = itemRepository;
        this.userService = userService;
        this.pickupLocationRepository = pickupLocationRepository;
        this.categoryService = categoryService;
        this.bidRepository = bidRepository;
    }

    public Item saveItem(ItemDTO form, MultipartFile photo) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.findByUsername(username);
        Item item = new Item();
        PickupLocation pickupLocation = new PickupLocation();
        if (form.getCity() != null && form.getPostalCode() != null && form.getStreet() != null) {
            pickupLocation.setCity(form.getCity());
            pickupLocation.setPostalCode(form.getPostalCode());
            pickupLocation.setStreet(form.getStreet());
        } else {
            pickupLocation.setCity(user.getCity());
            pickupLocation.setPostalCode(user.getPostalCode());
            pickupLocation.setStreet(user.getStreet());
        }
        item.setItemName(form.getItemName());
        item.setDescription(form.getDescription());
        item.setStartDate(LocalDateTime.parse(form.getStartDate()));
        item.setEndDate(LocalDateTime.parse(form.getEndDate()));
        item.setStartingPrice(form.getStartingPrice());
        item.setCategory(categoryService.findById(form.getCategory()));
        item.setSeller(user);
        item.setPickupLocationBid(pickupLocation);
        if (item.getStartDate().equals(LocalDateTime.now()) || item.getStartDate().isBefore(LocalDateTime.now())) {
            item.setSaleStatus(String.valueOf(SalesStatusCycle.IN_PROGRESS));
        } else {
            item.setSaleStatus(String.valueOf(SalesStatusCycle.CREATED));
        }
        Bid bid = new Bid();
        bid.setBidDate(LocalDateTime.parse(form.getStartDate()));
        bid.setItem(item);
        bid.setUser(user);
        bid.setBidAmount(form.getStartingPrice());
        pickupLocationRepository.save(pickupLocation);
        if (photo != null && !photo.isEmpty()) {
            try {
                String uploadDir = "C:\\Users\\glandry2023\\Desktop\\Enchere\\src\\main\\resources\\static\\uploads";
                String fileName = System.currentTimeMillis() + "_" + photo.getOriginalFilename();
                Path filePath = Paths.get(uploadDir, fileName);
                Files.copy(photo.getInputStream(), filePath);
                item.setImage(fileName); // On stocke juste le nom du fichier
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        itemRepository.save(item);
        bidRepository.save(bid);
        return item;
    }

    public Item updateItem(Item item) {
        Item existingItem = itemRepository.findById(item.getItemId()).orElse(null);
        if (existingItem != null) {
            existingItem.setItemName(item.getItemName());
            existingItem.setDescription(item.getDescription());
            existingItem.setStartDate(item.getStartDate());
            existingItem.setEndDate(item.getEndDate());
            existingItem.setStartingPrice(item.getStartingPrice());
            existingItem.setSalePrice(item.getSalePrice());
            existingItem.setSaleStatus(item.getSaleStatus());
            existingItem.setCategory(item.getCategory());
            return itemRepository.save(existingItem);
        }
        return null;
    }

    public Item findByItemId(Long itemId) {
        return itemRepository.findByItemId(itemId);
    }

    @Scheduled(fixedRate = 60000) // toutes les 60 secondes
    public void updateEndedAuctions() {
        List<Item> items = itemRepository.findAll();

        LocalDateTime now = LocalDateTime.now();

        for (Item item : items) {
            if (item.getEndDate().isBefore(now)
                    && "IN_PROGRESS".equals(item.getSaleStatus())) {
                item.setSaleStatus("AUCTION_ENDED");
                item.setBuyer(bidRepository.findHighestBidByItemId(item.getItemId()).getUser());
                itemRepository.save(item);
                System.out.println("Article " + item.getItemId() + " mis à jour en AUCTION_ENDED.");
            }
            if (item.getStartDate().isBefore(now)
                    && "CREATED".equals(item.getSaleStatus())) {
                item.setSaleStatus("IN_PROGRESS");
                itemRepository.save(item);
                System.out.println("Article " + item.getItemId() + " mis à jour en IN_PROGRESS.");
            }
        }
    }
}
