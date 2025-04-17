package fr.enchere.service;

import fr.enchere.model.PickupLocation;
import fr.enchere.repository.PickupLocationRepository;
import org.springframework.stereotype.Service;

@Service
public class PickupLocationService {

    private final PickupLocationRepository pickupLocationRepository;

    public PickupLocationService(PickupLocationRepository pickupLocationRepository) {
        this.pickupLocationRepository = pickupLocationRepository;
    }


    public PickupLocation findById(Long id) {
        return pickupLocationRepository.findById(id).orElse(null);
    }
}
