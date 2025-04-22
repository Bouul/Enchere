package fr.enchere.service;

import fr.enchere.model.Bid;
import fr.enchere.repository.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BidServiceImpl implements BidService {

    @Autowired
    private BidRepository bidRepository;


    public BidServiceImpl(BidRepository bidRepository) {
        this.bidRepository = bidRepository;
    }

    @Override
    public List<Bid> getBids() {
        return bidRepository.findAll();
    }

    @Override
    public List<Bid> getBidsByCategory(Long categoryId) {
        return bidRepository.findByItemCategoryId(categoryId);
    }

    @Override
    public Bid findByBidId(Long bidId) {
        return bidRepository.findByBidId(bidId);
    }

    @Override
    public List<Bid> getBidsByItemName(String itemName) {
        return bidRepository.findByItemNameContainingIgnoreCase(itemName);
    }

    @Override
    public List<Bid> getBidsByCategoryAndItemName(Long categoryId, String itemName) {
        return bidRepository.findByItemCategoryIdAndItemNameContainingIgnoreCase(categoryId, itemName);
    }

    @Override
    public Bid saveBid(Bid bid) {
        return bidRepository.save(bid);
    }
}
