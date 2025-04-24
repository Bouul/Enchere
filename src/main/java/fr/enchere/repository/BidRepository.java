package fr.enchere.repository;

import fr.enchere.model.Bid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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
          AND i.saleStatus!= 'RETRAIT'
              AND i.saleStatus!= 'CREATED'
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
    AND i.saleStatus!= 'RETRAIT'
    AND i.saleStatus!= 'CREATED'
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
    AND i.saleStatus!= 'RETRAIT'
    AND i.saleStatus!= 'CREATED'
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
    AND i.saleStatus!= 'RETRAIT'
    AND i.saleStatus!= 'CREATED'
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
    AND i.endDate < :currentDate
    AND i.saleStatus!= 'RETRAIT'
    AND i.saleStatus!= 'CREATED'
""")
   List<Bid> findWonBidsByUsername(
           @Param("username") String username,
           @Param("currentDate") LocalDateTime currentDate);

   @Query("SELECT b FROM Bid b WHERE b.item.itemId = :itemId ORDER BY b.bidAmount DESC LIMIT 1")
   Bid findHighestBidByItemId(Long itemId);


   @Query(value = """
    SELECT b
    FROM Bid b
    JOIN FETCH b.item i
    JOIN FETCH i.category
    JOIN FETCH i.seller
    LEFT JOIN FETCH i.pickupLocationBid
   WHERE i.saleStatus!= 'RETRAIT'
      AND i.saleStatus!= 'CREATED'
    """,
           countQuery = "SELECT COUNT(b) FROM Bid b")
   Page<Bid> findAllWithPagination(Pageable pageable);

   @Query(value = """
    SELECT b
    FROM Bid b
    JOIN FETCH b.item i
    JOIN FETCH i.category
    JOIN FETCH i.seller
    LEFT JOIN FETCH i.pickupLocationBid
    WHERE i.category.categoryId = :categoryId
    AND i.saleStatus!= 'RETRAIT'
    AND i.saleStatus!= 'CREATED'
""",
           countQuery = "SELECT COUNT(b) FROM Bid b JOIN b.item i WHERE i.category.categoryId = :categoryId")
   Page<Bid> findByItemCategoryIdPage(@Param("categoryId") Long categoryId, Pageable pageable);

   @Query(value = """
    SELECT b
    FROM Bid b
    JOIN FETCH b.item i
    JOIN FETCH i.category
    JOIN FETCH i.seller
    LEFT JOIN FETCH i.pickupLocationBid
    WHERE LOWER(i.itemName) LIKE LOWER(CONCAT('%', :itemName, '%'))
    AND i.saleStatus!= 'RETRAIT'
    AND i.saleStatus!= 'CREATED'
""",
           countQuery = "SELECT COUNT(b) FROM Bid b JOIN b.item i WHERE LOWER(i.itemName) LIKE LOWER(CONCAT('%', :itemName, '%'))")
   Page<Bid> findByItemNameContainingIgnoreCasePage(@Param("itemName") String itemName, Pageable pageable);

   @Query(value = """
    SELECT b
    FROM Bid b
    JOIN FETCH b.item i
    JOIN FETCH i.category
    JOIN FETCH i.seller
    LEFT JOIN FETCH i.pickupLocationBid
    WHERE i.category.categoryId = :categoryId
    AND LOWER(i.itemName) LIKE LOWER(CONCAT('%', :itemName, '%'))
    AND i.saleStatus!= 'RETRAIT'
    AND i.saleStatus!= 'CREATED'
""",
           countQuery = "SELECT COUNT(b) FROM Bid b JOIN b.item i WHERE i.category.categoryId = :categoryId AND LOWER(i.itemName) LIKE LOWER(CONCAT('%', :itemName, '%'))")
   Page<Bid> findByItemCategoryIdAndItemNameContainingIgnoreCasePage(@Param("categoryId") Long categoryId, @Param("itemName") String itemName, Pageable pageable);

   @Query(value = """
    SELECT b
    FROM Bid b
    JOIN FETCH b.item i
    JOIN FETCH i.category
    JOIN FETCH i.seller
    LEFT JOIN FETCH i.pickupLocationBid
    WHERE b.user.username = :username
    AND i.saleStatus != 'RETRAIT'
    AND i.saleStatus != 'CREATED'
    AND b.bidAmount = (
        SELECT MAX(b2.bidAmount)
        FROM Bid b2
        WHERE b2.item = b.item
        AND b2.user.username = :username
    )
""",
           countQuery = "SELECT COUNT(b) FROM Bid b WHERE b.user.username = :username")
   Page<Bid> findByUserUsernamePage(@Param("username") String username, Pageable pageable);

   @Query(value = """
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
    AND i.endDate < :currentDate
    AND i.saleStatus!= 'RETRAIT'
    AND i.saleStatus!= 'CREATED'
""",
           countQuery = "SELECT COUNT(b) FROM Bid b JOIN b.item i WHERE b.user.username = :username AND b.bidAmount = (SELECT MAX(b2.bidAmount) FROM Bid b2 WHERE b2.item = i) AND i.endDate < :currentDate")
   Page<Bid> findWonBidsByUsernamePage(@Param("username") String username, @Param("currentDate") LocalDateTime currentDate, Pageable pageable);

    @Query(value = """
     SELECT b
     FROM Bid b
     JOIN FETCH b.item i
     JOIN FETCH i.category
     JOIN FETCH i.seller
     WHERE i.seller.userId = :userId
     AND i.saleStatus!= 'RETRAIT'
     """,
    countQuery = "SELECT COUNT(b) FROM Bid b JOIN b.item i WHERE b.user.userId = :userId")
    Page<Bid> findByUserIdPage(@Param("userId") Long userId, Pageable pageable);

}
