package com.project.code.Repo;

import com.project.code.Model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    /**
     * Finds inventory record by product ID and store ID
     * @param productId The product ID to search for
     * @param storeId The store ID to search for
     * @return Inventory entity if found, null otherwise
     */
    @Query("SELECT i FROM Inventory i WHERE i.product.id = :productId AND i.store.id = :storeId")
    Inventory findByProductIdandStoreId(Long productId, Long storeId);

    /**
     * Finds all inventory records for a specific store
     * @param storeId The store ID to search for
     * @return List of inventory records for the specified store
     */
    List<Inventory> findByStore_Id(Long storeId);

    /**
     * Deletes all inventory records for a specific product ID
     * @param productId The product ID for which to delete inventory records
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM Inventory i WHERE i.product.id = :productId")
    void deleteByProductId(Long productId);

    /**
     * Finds inventory records by product ID
     * @param productId The product ID to search for
     * @return List of inventory records for the specified product
     */
    List<Inventory> findByProduct_Id(Long productId);
}
