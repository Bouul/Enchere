package fr.enchere.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;
    private String itemName;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int startingPrice;
    private int salePrice;
    private String saleStatus;
    private String image;

    @ManyToOne
    @JoinColumn(name = "category")
    @JsonBackReference
    private Category category;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    @JsonBackReference
    private User seller;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @OneToMany(mappedBy = "item")
    @JsonBackReference
    private List<Bid> bids;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pickup_location")
    private PickupLocation pickupLocationBid;

    // Constructors

    public Item() {
        // Default constructor
    }

    public Item(Long itemId, String itemName, String description, LocalDateTime startDate, LocalDateTime endDate, int startingPrice, int salePrice, String saleStatus, Category category, User seller, User buyer, List<Bid> bids, PickupLocation pickupLocationBid) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startingPrice = startingPrice;
        this.salePrice = salePrice;
        this.saleStatus = saleStatus;
        this.category = category;
        this.seller = seller;
        this.buyer = buyer;
        this.bids = bids;
        this.pickupLocationBid = pickupLocationBid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) { this.bids = bids; }

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
