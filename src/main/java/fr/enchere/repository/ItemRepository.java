package fr.enchere.repository;

import fr.enchere.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    Item findByItemId(Long itemId);

    Item findByItemName(String itemName);

    Item findByDescription(String description);

    Item findByStartingPrice(Double startingPrice);

    Item findByEndDate(String endDate);

}
