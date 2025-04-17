package fr.enchere.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;
    private String itemName;
    private String description;
    private String startDate;
    private String endDate;
    private int startingPrice;
    private int salePrice;
    private String saleStatus;

    @ManyToOne
    @JoinColumn(name = "category")
    @JsonBackReference
    private Category category;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pickup_location")
    private PickupLocation pickupLocationBid;

    // Constructors

    public Item() {
        // Default constructor
    }

    public Item(Long itemId, PickupLocation pickupLocationBid, User buyer, User seller, Category category, String saleStatus, int salePrice, int startingPrice, String endDate, String startDate, String description, String itemName) {
        this.itemId = itemId;
        this.pickupLocationBid = pickupLocationBid;
        this.buyer = buyer;
        this.seller = seller;
        this.category = category;
        this.saleStatus = saleStatus;
        this.salePrice = salePrice;
        this.startingPrice = startingPrice;
        this.endDate = endDate;
        this.startDate = startDate;
        this.description = description;
        this.itemName = itemName;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public PickupLocation getPickupLocationBid() {
        return pickupLocationBid;
    }

    public void setPickupLocationBid(PickupLocation pickupLocationBid) {
        this.pickupLocationBid = pickupLocationBid;
    }

    // Getters and setters

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(String saleStatus) {
        this.saleStatus = saleStatus;
    }

    public int getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(int salePrice) {
        this.salePrice = salePrice;
    }

    public int getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(int startingPrice) {
        this.startingPrice = startingPrice;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
