package fr.enchere.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotNull
    @Size(min = 2, max = 20, message = "2 caractères minimum, 20 maximum")
    private String username;

    @NotNull
    @Size(min = 2, max = 20, message = "2 caractères minimum, 20 maximum")
    private String lastName;

    @NotNull
    @Size(min = 2, max = 20, message = "2 caractères minimum, 20 maximum")
    private String firstName;

    @NotNull
    @Size(min = 7, max = 100, message = "2 caractères minimum, 100 maximum")
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[a-zA-Z]{2,}$", message = "Adresse email invalide")
    private String email;

    @NotNull
    @Size(min = 10, max = 13, message = "10 caractères minimum, 13 maximum")
    private String phone;

    @NotNull
    @Size(min = 2, max = 50, message = "2 caractères minimum, 50 maximum")
    private String street;

    @NotNull
    @Size(min = 4, max = 5, message = "4 caractères minimum, 5 maximum")
    private String postalCode;

    @NotNull
    @Size(min = 2, max = 50, message = "2 caractères minimum, 50 maximum")
    private String city;

    @NotNull
    @Size(min = 8, max = 254, message = "8 caractères minimum")
    private String password;

    private int credit;

    private boolean administrator;

    private boolean active = true; // Par défaut, le compte est actif

    private String resetToken;

    private LocalDateTime resetTokenExpiry;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Bid> bids;

    @OneToMany(mappedBy = "seller")


    @JsonManagedReference
    private List<Item> itemsSold;

    @OneToMany(mappedBy = "buyer")
    @JsonIgnore
    private List<Item> itemsBought;


    //Consqtructors
    public User() {
        // Default constructor
    }

    public User(Long userId, List<Item> itemsBought, List<Item> itemsSold, List<Bid> bids, boolean administrator, int credit, String password, String city, String postalCode, String street, String phone, String email, String firstName, String lastName, String username) {
        this.userId = userId;
        this.itemsBought = itemsBought;
        this.itemsSold = itemsSold;
        this.bids = bids;
        this.administrator = administrator;
        this.credit = credit;
        this.password = password;
        this.city = city;
        this.postalCode = postalCode;
        this.street = street;
        this.phone = phone;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
    }

    public List<Item> getItemsSold() {
        return itemsSold;
    }

    public void setItemsSold(List<Item> itemsSold) {
        this.itemsSold = itemsSold;
    }

    public List<Item> getItemsBought() {
        return itemsBought;
    }

    public void setItemsBought(List<Item> itemsBought) {
        this.itemsBought = itemsBought;
    }

    public List<Bid> getBids() {
        return bids;
    }

    public void setBids(List<Bid> bids) {
        this.bids = bids;
    }

    public boolean isAdministrator() {
        return administrator;
    }

    public void setAdministrator(boolean administrator) {
        this.administrator = administrator;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public LocalDateTime getResetTokenExpiry() {
        return resetTokenExpiry;
    }

    public void setResetTokenExpiry(LocalDateTime resetTokenExpiry) {
        this.resetTokenExpiry = resetTokenExpiry;
    }

    /**
     * Calcule le pourcentage de complétion du profil utilisateur
     * en vérifiant quels champs sont renseignés
     * @return pourcentage de complétion (0-100)
     */
    public int calculateProfileCompletion() {
        int completedFields = 0;
        int totalFields = 8; // Nombre total de champs à vérifier
        
        if (username != null && !username.isEmpty()) completedFields++;
        if (lastName != null && !lastName.isEmpty()) completedFields++;
        if (firstName != null && !firstName.isEmpty()) completedFields++;
        if (email != null && !email.isEmpty()) completedFields++;
        if (phone != null && !phone.isEmpty()) completedFields++;
        if (street != null && !street.isEmpty()) completedFields++;
        if (postalCode != null && !postalCode.isEmpty()) completedFields++;
        if (city != null && !city.isEmpty()) completedFields++;
        
        return (completedFields * 100) / totalFields;
    }
    
    /**
     * Retourne le nombre total d'enchères créées par l'utilisateur
     * @return nombre d'enchères créées
     */
    public int getCreatedAuctionsCount() {
        return itemsSold != null ? itemsSold.size() : 0;
    }
    
    /**
     * Retourne le nombre d'enchères remportées par l'utilisateur
     * @return nombre d'enchères remportées
     */
    public int getWonAuctionsCount() {
        return itemsBought != null ? itemsBought.size() : 0;
    }
    
    /**
     * Retourne le nombre d'enchères actives (en cours) de l'utilisateur
     * @return nombre d'enchères actives
     */
    public int getActiveAuctionsCount() {
        if (itemsSold == null) return 0;

        LocalDateTime currentDate = LocalDateTime.now();

        return (int) itemsSold.stream()
                .filter(item -> {
                    LocalDateTime endDate = item.getEndDate();
                    return endDate != null && endDate.isAfter(currentDate);
                })
                .count();
    }
}

