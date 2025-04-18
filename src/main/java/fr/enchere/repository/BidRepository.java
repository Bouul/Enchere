package fr.enchere.repository;

import fr.enchere.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {
   Bid findByBidId(Long bidId);
   @Query("""
      SELECT b 
      FROM Bid b
       JOIN FETCH b.item i
       JOIN FETCH i.category
       JOIN FETCH i.seller
       LEFT JOIN FETCH i.pickupLocationBid
      WHERE i.category.categoryId = :categoryId
    """)
   List<Bid> findByItemCategoryId(@Param("categoryId") Long categoryId);
}
