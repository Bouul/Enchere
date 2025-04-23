package fr.enchere.service;

import fr.enchere.model.Bid;
import fr.enchere.repository.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BidServiceImpl implements BidService {

    private final BidRepository bidRepository;


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
    public List<Bid> getBidsByItemName(String itemName) {
        return bidRepository.findByItemNameContainingIgnoreCase(itemName);
    }

    @Override
    public List<Bid> getBidsByCategoryAndItemName(Long categoryId, String itemName) {
        return bidRepository.findByItemCategoryIdAndItemNameContainingIgnoreCase(categoryId, itemName);
    }
  
     @Override
     public List<Bid> getBidsByUsername(String username) {
        return bidRepository.findByUserUsername(username);
    }

    @Override
    public List<Bid> getWonBidsByUsername(String username) {
        return bidRepository.findWonBidsByUsername(username, LocalDateTime.now());
    }

    @Override
    public Bid findHighestBidByItemId(Long itemId) {
        return bidRepository.findHighestBidByItemId(itemId);
    }

    @Override
    public Page<Bid> getBidsPage(Pageable pageable) {
        return bidRepository.findAllWithPagination(pageable);
    }

    @Override
    public Page<Bid> getBidsByCategoryPage(Long categoryId, Pageable pageable) {
        return bidRepository.findByItemCategoryIdPage(categoryId, pageable);
    }

    @Override
    public Page<Bid> getBidsByItemNamePage(String itemName, Pageable pageable) {
        return bidRepository.findByItemNameContainingIgnoreCasePage(itemName, pageable);
    }

    @Override
    public Page<Bid> getBidsByCategoryAndItemNamePage(Long categoryId, String itemName, Pageable pageable) {
        return bidRepository.findByItemCategoryIdAndItemNameContainingIgnoreCasePage(categoryId, itemName, pageable);
    }

    @Override
    public Page<Bid> getBidsByUsernamePage(String username, Pageable pageable) {
        return bidRepository.findByUserUsernamePage(username, pageable);
    }

    @Override
    public Page<Bid> getWonBidsByUsernamePage(String username, Pageable pageable) {
        return bidRepository.findWonBidsByUsernamePage(username, LocalDateTime.now(), pageable);
    }

}
