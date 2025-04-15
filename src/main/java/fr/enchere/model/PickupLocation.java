package fr.enchere.model;

import jakarta.persistence.*;

@Entity
public class PickupLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long locationId;
    private String street;
    private String postalCode;
    private String city;

    // Constructors

    public PickupLocation() {
        // Default constructor
    }

    public PickupLocation(Long locationId, String street, String postalCode, String city) {
        this.locationId = locationId;
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
    }



    // Getters and setters

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}