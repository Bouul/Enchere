package fr.enchere.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;
    private String lastName;
    private String firstName;
    private String email;
    private String phone;
    private String street;
    private String postalCode;
    private String city;
    private String password;
    private int credit;
    private boolean administrator;

    @OneToMany(mappedBy = "user")
    private List<Bid> bids;

    @OneToMany(mappedBy = "seller")
    private List<Item> itemsSold;

    @OneToMany(mappedBy = "buyer")
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
}
