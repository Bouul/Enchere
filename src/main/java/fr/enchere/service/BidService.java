package fr.enchere.service;

import fr.enchere.model.Bid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BidService {

    //USE
    List<Bid> getBids();
    List<Bid> getBidsByCategory(Long categoryId);
    List<Bid> getBidsByItemName(String itemName);
    List<Bid> getBidsByCategoryAndItemName(Long categoryId, String itemName);
    List<Bid> getBidsByUsername(String username);
    List<Bid> getWonBidsByUsername(String username);
    Bid findHighestBidByItemId(Long itemId);

    Page<Bid> getBidsPage(Pageable pageable);
    Page<Bid> getBidsByCategoryPage(Long categoryId, Pageable pageable);
    Page<Bid> getBidsByItemNamePage(String itemName, Pageable pageable);
    Page<Bid> getBidsByCategoryAndItemNamePage(Long categoryId, String itemName, Pageable pageable);
    Page<Bid> getBidsByUsernamePage(String username, Pageable pageable);
    Page<Bid> getWonBidsByUsernamePage(String username, Pageable pageable);
    Page<Bid> getByUserIdPage(Long userId, Pageable pageable);

}
