package com.project.code.Model;                           // Package declaration, keeps the class inside the Model package

import com.fasterxml.jackson.annotation.JsonBackReference; // Prevents circular JSON serialization for bidirectional relations
import jakarta.persistence.*;                              // JPA annotations for entity mapping

@Entity                                                    // Marks this class as a JPA entity mapped to a table named 'inventory' by default
public class Inventory {

    @Id                                                   // Declares primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)   // Uses auto increment identity column
    private Long id;                                      // Unique identifier for the inventory entry

    @ManyToOne                                            // Many inventory rows can reference one product
    @JoinColumn(name = "product_id")                      // Foreign key column to Product
    @JsonBackReference("inventory-product")               // Breaks JSON cycle on the product side
    private Product product;                              // The associated product

    @ManyToOne                                            // Many inventory rows can reference one store
    @JoinColumn(name = "store_id")                        // Foreign key column to Store
    @JsonBackReference("inventory-store")                 // Breaks JSON cycle on the store side
    private Store store;                                  // The store where this inventory is located

    private Integer stockLevel;                           // Current stock level at the store

    public Inventory() {                                  // No args constructor required by JPA
    }

    public Inventory(Product product,                     // Convenience constructor to initialize mandatory fields
                     Store store,
                     Integer stockLevel) {
        this.product = product;                           // Set product
        this.store = store;                               // Set store
        this.stockLevel = stockLevel;                     // Set initial stock
    }

    public Long getId() {                                 // Getter for id
        return id;
    }

    public void setId(Long id) {                          // Setter for id
        this.id = id;
    }

    public Product getProduct() {                         // Getter for product
        return product;
    }

    public void setProduct(Product product) {             // Setter for product
        this.product = product;
    }

    public Store getStore() {                             // Getter for store
        return store;
    }

    public void setStore(Store store) {                   // Setter for store
        this.store = store;
    }

    public Integer getStockLevel() {                      // Getter for stockLevel
        return stockLevel;
    }

    public void setStockLevel(Integer stockLevel) {       // Setter for stockLevel
        this.stockLevel = stockLevel;
    }
}
