package fr.enchere.model;

import jakarta.persistence.*;

@Entity
public class ItemForSale {
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
    @JoinColumn(name = "pickupLocation")
    private PickupLocation pickupLocation;

    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "seller")
    private User seller;

    // Constructors

    public ItemForSale() {
        // Default constructor
    }

    public ItemForSale(Long itemId, String itemName, String description, String startDate, String endDate, int startingPrice, int salePrice, String saleStatus, PickupLocation pickupLocation, Category category, User seller) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startingPrice = startingPrice;
        this.salePrice = salePrice;
        this.saleStatus = saleStatus;
        this.pickupLocation = pickupLocation;
        this.category = category;
        this.seller = seller;
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

    public PickupLocation getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(PickupLocation pickupLocation) {
        this.pickupLocation = pickupLocation;
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
