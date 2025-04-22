package fr.enchere.repository;

import fr.enchere.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {

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

   @Query("""
    SELECT b 
    FROM Bid b
    JOIN FETCH b.item i
    JOIN FETCH i.category
    JOIN FETCH i.seller
    LEFT JOIN FETCH i.pickupLocationBid
    WHERE LOWER(i.itemName) LIKE LOWER(CONCAT('%', :itemName, '%'))
""")

   List<Bid> findByItemNameContainingIgnoreCase(@Param("itemName") String itemName);

   @Query("""
    SELECT b 
    FROM Bid b
    JOIN FETCH b.item i
    JOIN FETCH i.category
    JOIN FETCH i.seller
    LEFT JOIN FETCH i.pickupLocationBid
    WHERE i.category.categoryId = :categoryId 
    AND LOWER(i.itemName) LIKE LOWER(CONCAT('%', :itemName, '%'))
""")
   List<Bid> findByItemCategoryIdAndItemNameContainingIgnoreCase(
           @Param("categoryId") Long categoryId,
           @Param("itemName") String itemName);

   @Query("""
    SELECT b
    FROM Bid b
    JOIN FETCH b.item i
    JOIN FETCH i.category
    JOIN FETCH i.seller
    LEFT JOIN FETCH i.pickupLocationBid
    WHERE b.user.username = :username
""")
   List<Bid> findByUserUsername(@Param("username") String username);

   @Query("""
    SELECT b
    FROM Bid b
    JOIN FETCH b.item i
    JOIN FETCH i.category
    JOIN FETCH i.seller
    LEFT JOIN FETCH i.pickupLocationBid
    WHERE b.user.username = :username
    AND b.bidAmount = (
        SELECT MAX(b2.bidAmount)
        FROM Bid b2
        WHERE b2.item = i
    )
    AND i.endDate < CURRENT_TIMESTAMP
""")
   List<Bid> findWonBidsByUsername(@Param("username") String username);

   @Query("SELECT b FROM Bid b WHERE b.item.itemId = :itemId ORDER BY b.bidAmount DESC LIMIT 1")
   Bid findHighestBidByItemId(Long itemId);
}
