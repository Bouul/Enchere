package fr.enchere.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bidId;
    private LocalDateTime bidDate;
    private int bidAmount;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "itemForSale")
    private ItemForSale itemForSale;

    // Constructors

    public Bid() {
        // Default constructor
    }

    public Bid(Long bidId, LocalDateTime bidDate, int bidAmount, User user, ItemForSale itemForSale) {
        this.bidId = bidId;
        this.bidDate = bidDate;
        this.bidAmount = bidAmount;
        this.user = user;
        this.itemForSale = itemForSale;
    }


    // Getters and setters

    public Long getBidId() {
        return bidId;
    }

    public void setBidId(Long bidId) {
        this.bidId = bidId;
    }

    public ItemForSale getItemForSale() {
        return itemForSale;
    }

    public void setItemForSale(ItemForSale itemForSale) {
        this.itemForSale = itemForSale;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(int bidAmount) {
        this.bidAmount = bidAmount;
    }

    public LocalDateTime getBidDate() {
        return bidDate;
    }

    public void setBidDate(LocalDateTime bidDate) {
        this.bidDate = bidDate;
    }
}
