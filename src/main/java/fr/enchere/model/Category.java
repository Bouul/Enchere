package fr.enchere.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    private String label;

    @OneToMany(mappedBy = "category")
    @JsonManagedReference
    private List<Item> items;

    public Category() {
    }

    public Category(Long categoryId, String label, List<Item> items) {
        this.categoryId = categoryId;
        this.label = label;
        this.items = items;
    }


    // Getters and setters

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public List<Item> getItemsForSale() {
        return items;
    }

    public void setItemsForSale(List<Item> items) {
        this.items = items;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
