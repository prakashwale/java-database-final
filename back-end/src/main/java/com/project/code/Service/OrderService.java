package com.project.code.Service;

import com.project.code.DTO.PlaceOrderRequestDTO;
import com.project.code.Model.*;
import com.project.code.Repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Transactional
    public void saveOrder(PlaceOrderRequestDTO placeOrderRequest) {
        // 1. Retrieve or Create Customer
        Customer customer = customerRepository.findByEmail(placeOrderRequest.getCustomerEmail());
        if (customer == null) {
            customer = new Customer();
            customer.setEmail(placeOrderRequest.getCustomerEmail());
            customer.setFirstName(placeOrderRequest.getCustomerFirstName());
            customer.setLastName(placeOrderRequest.getCustomerLastName());
            customer.setPhoneNumber(placeOrderRequest.getCustomerPhone());
            customer = customerRepository.save(customer);
        }

        // 2. Retrieve Store
        Store store = storeRepository.findById(placeOrderRequest.getStoreId())
                .orElseThrow(() -> new RuntimeException("Store not found with id: " + placeOrderRequest.getStoreId()));

        // 3. Create OrderDetails
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setCustomer(customer);
        orderDetails.setStore(store);
        orderDetails.setTotalPrice(placeOrderRequest.getTotalPrice());
        orderDetails.setOrderDate(LocalDateTime.now());
        orderDetails = orderDetailsRepository.save(orderDetails);

        // 4. Process Order Items
        placeOrderRequest.getItems().forEach(item -> {
            // Find product
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + item.getProductId()));

            // Find and update inventory
            Inventory inventory = inventoryRepository.findByProductIdandStoreId(item.getProductId(), placeOrderRequest.getStoreId());
            if (inventory == null) {
                throw new RuntimeException("Inventory not found for product: " + item.getProductId() + " in store: " + placeOrderRequest.getStoreId());
            }
            if (inventory.getStockLevel() < item.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }
            inventory.setStockLevel(inventory.getStockLevel() - item.getQuantity());
            inventoryRepository.save(inventory);

            // Create and save order item
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(orderDetails);
            orderItem.setProduct(product);
            orderItem.setQuantity(item.getQuantity());
            orderItem.setUnitPrice(item.getUnitPrice());
            orderItemRepository.save(orderItem);
        });
    }
}
