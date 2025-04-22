package fr.enchere.service;

import fr.enchere.model.Bid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BidService {

    List<Bid> getBids();
    Bid findByBidId(Long bidId);
    Bid saveBid(Bid bid);
    List<Bid> getBidsByItemName(String itemName);
    List<Bid> getBidsByCategoryAndItemName(Long categoryId, String itemName);
    List<Bid> getBidsByCategory(Long categoryId);
}
