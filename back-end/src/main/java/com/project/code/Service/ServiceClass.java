package com.project.code.Service;

import com.project.code.Model.Inventory;
import com.project.code.Model.Product;
import com.project.code.Repo.InventoryRepository;
import com.project.code.Repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceClass {

    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;

    @Autowired
    public ServiceClass(ProductRepository productRepository, 
                      InventoryRepository inventoryRepository) {
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
    }

    /**
     * Validates if an inventory record exists for a product-store combination
     * @param inventory The inventory to validate
     * @return false if inventory exists, true otherwise
     */
    public boolean validateInventory(Inventory inventory) {
        Inventory existingInventory = inventoryRepository.findByProductIdandStoreId(
            inventory.getProduct().getId(), 
            inventory.getStore().getId()
        );
        return existingInventory == null;
    }

    /**
     * Validates if a product with the same name already exists
     * @param product The product to validate
     * @return false if product with same name exists, true otherwise
     */
    public boolean validateProduct(Product product) {
        Product existingProduct = productRepository.findByName(product.getName());
        return existingProduct == null;
    }

    /**
     * Validates if a product exists by ID
     * @param id The product ID to validate
     * @return true if product exists, false otherwise
     */
    public boolean validateProductId(long id) {
        Product product = productRepository.findById(id);
        return product != null;
    }

    /**
     * Retrieves inventory record for a product-store combination
     * @param inventory The inventory to search for
     * @return The found inventory record or null if not found
     */
    public Inventory getInventoryId(Inventory inventory) {
        return inventoryRepository.findByProductIdandStoreId(
            inventory.getProduct().getId(),
            inventory.getStore().getId()
        );
    }
}
