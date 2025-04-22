package fr.enchere.service;

import fr.enchere.model.Bid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BidService {

    List<Bid> getBids();
    Bid findByBidId(Long bidId);
    ServiceResponse<Bid> createBid(Bid bid);
    List<Bid> getBidsByItemName(String itemName);
    List<Bid> getBidsByCategoryAndItemName(Long categoryId, String itemName);
    Bid updateBid(Bid bid);
    void deleteBid(Long bidId);
    Bid findHighestBidByItemId(Long itemId);
    List<Bid> getBidsByCategory(Long categoryId);
}
