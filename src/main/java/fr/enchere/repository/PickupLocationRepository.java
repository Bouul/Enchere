package fr.enchere.repository;

import fr.enchere.model.PickupLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PickupLocationRepository extends JpaRepository<PickupLocation, Long> {
}
