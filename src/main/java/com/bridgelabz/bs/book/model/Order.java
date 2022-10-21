package com.bridgelabz.bs.book.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Table(name = "order_details")
@Entity
public class Order {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;
	private int userId;
    private int orderQty;
    private String address;
    private double orderPrice;
    private LocalDate orderedAt;
    private boolean cancel;
    
    @OneToMany(fetch = FetchType.LAZY,orphanRemoval=true)
    private List<Cart> cart;
    
    @ManyToMany
    private List<Book> book;
    
    @Enumerated(EnumType.STRING)
    private Status orderStatus;
    
    public Order(int userId, String address, List<Cart> cart, List<Book> book, LocalDate orderedAt, int totalOrderQty, double totalPrice, boolean cancel, Status orderStatus) {
        this.userId=userId;
        this.cart=cart;
        this.orderedAt=orderedAt;
        this.orderQty=totalOrderQty;
        this.orderPrice=totalPrice;
        this.cancel=cancel;
        this.orderStatus = orderStatus;
        this.address=address;
        this.book=book;
    }
}
