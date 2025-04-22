package fr.enchere.service;

import fr.enchere.model.Bid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BidService {

    //USE
    List<Bid> getBids();
    List<Bid> getBidsByCategory(Long categoryId);
    Bid findByBidId(Long bidId);
    List<Bid> getBidsByItemName(String itemName);
    List<Bid> getBidsByCategoryAndItemName(Long categoryId, String itemName);
    List<Bid> getBidsByUsername(String username);
    List<Bid> getWonBidsByUsername(String username);
}
