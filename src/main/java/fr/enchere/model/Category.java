package fr.enchere.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    private String label;

    @OneToMany(mappedBy = "category")
    private List<ItemForSale> itemsForSale;


    // Constructors

    public Category() {
        // Default constructor
    }

    public Category(Long categoryId, String label, List<ItemForSale> itemsForSale) {
        this.categoryId = categoryId;
        this.label = label;
        this.itemsForSale = itemsForSale;
    }


    // Getters and setters

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public List<ItemForSale> getItemsForSale() {
        return itemsForSale;
    }

    public void setItemsForSale(List<ItemForSale> itemsForSale) {
        this.itemsForSale = itemsForSale;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
