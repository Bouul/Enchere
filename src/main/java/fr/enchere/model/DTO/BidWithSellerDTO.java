package fr.enchere.model.DTO;

import java.time.LocalDateTime;

public class BidWithSellerDTO {
    private Long bidId;
    private LocalDateTime bidDate;
    private int bidAmount;
    private Long itemId;
    private String itemName;
    private LocalDateTime itemEndDate;
    private int itemStartingPrice;
    private String itemImage;
    private String sellerUsername;
    // constructeur
    public BidWithSellerDTO(
            Long bidId,
            LocalDateTime bidDate,
            int bidAmount,
            Long itemId,
            String itemName,
            LocalDateTime itemEndDate,
            int itemStartingPrice,
            String itemImage,
            String sellerUsername
    ) {
        this.bidId = bidId;
        this.bidDate = bidDate;
        this.bidAmount = bidAmount;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemEndDate = itemEndDate;
        this.itemStartingPrice = itemStartingPrice;
        this.itemImage = itemImage;
        this.sellerUsername = sellerUsername;
    }

    public Long getBidId() {
        return bidId;
    }

    public void setBidId(Long bidId) {
        this.bidId = bidId;
    }

    public String getSellerUsername() {
        return sellerUsername;
    }

    public void setSellerUsername(String sellerUsername) {
        this.sellerUsername = sellerUsername;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public int getItemStartingPrice() {
        return itemStartingPrice;
    }

    public void setItemStartingPrice(int itemStartingPrice) {
        this.itemStartingPrice = itemStartingPrice;
    }

    public LocalDateTime getItemEndDate() {
        return itemEndDate;
    }

    public void setItemEndDate(LocalDateTime itemEndDate) {
        this.itemEndDate = itemEndDate;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
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