package com.bridgelabz.bs.book.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.bs.book.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
	@Modifying
    @Transactional
    @Query(value = "UPDATE order_details SET cancel=true WHERE order_id=:id", nativeQuery = true)
    void updateCancel(int id);

    @Query(value = "SELECT * FROM order_details WHERE user_id=:id", nativeQuery = true)
    List<Order> findAllByUserId(int id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE order_details SET order_status='COMPLETED' WHERE order_id=:id", nativeQuery = true)
    void updateOrderStatus(int id);

    @Query(value = "SELECT * FROM order_details WHERE order_status='PENDING'", nativeQuery = true)
    List<Order> findPendingOrders();
}
