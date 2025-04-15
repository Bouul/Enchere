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
    @JoinColumn(name = "item")
    private Item item;

    // Constructors

    public Bid() {
        // Default constructor
    }

    public Bid(Long bidId, LocalDateTime bidDate, int bidAmount, User user, Item item) {
        this.bidId = bidId;
        this.bidDate = bidDate;
        this.bidAmount = bidAmount;
        this.user = user;
        this.item = item;
    }


    // Getters and setters

    public Long getBidId() {
        return bidId;
    }

    public void setBidId(Long bidId) {
        this.bidId = bidId;
    }

    public Item getItemForSale() {
        return item;
    }

    public void setItemForSale(Item item) {
        this.item = item;
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
