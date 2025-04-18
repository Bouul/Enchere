package fr.enchere.service;

import fr.enchere.model.Bid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BidService {

    List<Bid> getBids();
    Bid findByBidId(Long bidId);
    ServiceResponse<Bid> createBid(Bid bid);
    Bid updateBid(Bid bid);
    void deleteBid(Long bidId);
    Bid findHighestBidByItemId(Long itemId);
}
