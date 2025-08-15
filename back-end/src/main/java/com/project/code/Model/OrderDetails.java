package com.project.code.Model;                              // package

import java.time.LocalDateTime;                              // LocalDateTime for order date
import java.util.ArrayList;                                  // default list impl
import java.util.List;                                       // List for orderItems

import javax.persistence.*;                                   // JPA annotations
import com.fasterxml.jackson.annotation.JsonManagedReference; // handle JSON cycles

@Entity                                                     // marks as JPA entity
public class OrderDetails {                                 // class definition

    @Id                                                    // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)    // auto increment id
    private Long id;                                       // id field

    @ManyToOne                                             // many orders to one customer
    @JoinColumn(name = "customer_id")                      // FK column name
    @JsonManagedReference                                  // manage JSON side per spec
    private Customer customer;                             // customer who placed the order

    @ManyToOne                                             // many orders to one store
    @JoinColumn(name = "store_id")                         // FK column name
    @JsonManagedReference                                  // manage JSON side per spec
    private Store store;                                   // store where order was placed

    private Double totalPrice;                             // total price of the order

    private LocalDateTime date;                            // when the order was placed

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER) // one order has many items, eager load
    @JsonManagedReference                                   // prevent circular JSON on parent side
    private List<OrderItem> orderItems = new ArrayList<>(); // items in the order

    public OrderDetails() {                                // no args constructor
    }

    public OrderDetails(                                   // parameterized constructor
            Customer customer,                             // customer
            Store store,                                   // store
            Double totalPrice,                             // total price
            LocalDateTime date) {                          // date time
        this.customer = customer;                          // set customer
        this.store = store;                                // set store
        this.totalPrice = totalPrice;                      // set price
        this.date = date;                                  // set date
    }

    // getters and setters for all fields

    public Long getId() {                                  // get id
        return id;
    }

    public void setId(Long id) {                           // set id
        this.id = id;
    }

    public Customer getCustomer() {                        // get customer
        return customer;
    }

    public void setCustomer(Customer customer) {           // set customer
        this.customer = customer;
    }

    public Store getStore() {                              // get store
        return store;
    }

    public void setStore(Store store) {                    // set store
        this.store = store;
    }

    public Double getTotalPrice() {                        // get total price
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {         // set total price
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getDate() {                       // get date
        return date;
    }

    public void setDate(LocalDateTime date) {              // set date
        this.date = date;
    }

    public List<OrderItem> getOrderItems() {               // get items
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {// set items
        this.orderItems = orderItems;
    }
}
