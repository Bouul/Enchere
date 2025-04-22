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
  
     @Override
     public List<Bid> getBidsByUsername(String username) {
        return bidRepository.findByUserUsername(username);
    }

    @Override
    public List<Bid> getWonBidsByUsername(String username) {
        return bidRepository.findWonBidsByUsername(username);
    }

    @Override
    public ServiceResponse<Bid> createBid(Bid bid) {
        // Vérification que l'enchère n'est pas null
        if (bid == null) {
            return ServiceResponse.buildResponse(
                    ServiceConstant.CD_ERR_NOT_FOUND,
                    "error.bid.null",
                    null
            );
        }

        // Vérification que l'enchère a un montant valide
        if (bid.getBidAmount() <= 0) {
            return ServiceResponse.buildResponse(
                    ServiceConstant.CD_ERR_NOT_FOUND,
                    "error.bid.amount.invalid",
                    bid
            );
        }

        // Autres vérifications...

        try {
            bidRepository.save(bid);
            return ServiceResponse.buildResponse(
                    ServiceConstant.CD_SUCCESS,
                    "success.bid.created",
                    bid
            );
        } catch (Exception e) {
            return ServiceResponse.buildResponse(
                    ServiceConstant.CD_ERR_TCH,
                    "error.bid.save.failed",
                    bid
            );
        }
    }



    @Override
    public Bid updateBid(Bid bid) {
        return null;
    }

    @Override
    public void deleteBid(Long bidId) {

    }

    @Override
    public Bid findHighestBidByItemId(Long itemId) {
        return bidRepository.findHighestBidByItemId(itemId);
    }
}
