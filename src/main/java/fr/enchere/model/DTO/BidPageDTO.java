package fr.enchere.model.DTO;

import fr.enchere.model.Bid;
import java.util.List;

public class BidPageDTO {
    private List<BidWithSellerDTO> bids;
    private int totalPages;
    private int currentPage;
    private long totalItems;

    public BidPageDTO(List<BidWithSellerDTO> bids, int totalPages, int currentPage, long totalItems) {
        this.bids = bids;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.totalItems = totalItems;
    }

    public List<BidWithSellerDTO> getBids() { return bids; }
    public void setBids(List<BidWithSellerDTO> bids) { this.bids = bids; }
    public int getTotalPages() { return totalPages; }
    public void setTotalPages(int totalPages) { this.totalPages = totalPages; }
    public int getCurrentPage() { return currentPage; }
    public void setCurrentPage(int currentPage) { this.currentPage = currentPage; }
    public long getTotalItems() { return totalItems; }
    public void setTotalItems(long totalItems) { this.totalItems = totalItems; }
}