package fr.enchere.service;

import fr.enchere.model.Bid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BidService {

    //USE
    List<Bid> getBids();
    Bid findByBidId(Long bidId);
    Bid saveBid(Bid bid);
    List<Bid> getBidsByItemName(String itemName);
    List<Bid> getBidsByCategoryAndItemName(Long categoryId, String itemName);
    List<Bid> getBidsByUsername(String username);
    List<Bid> getWonBidsByUsername(String username);
    Bid findHighestBidByItemId(Long itemId);

    //NO USE
    Bid updateBid(Bid bid);
    void deleteBid(Long bidId);
    List<Bid> getBidsByCategory(Long categoryId);
}
