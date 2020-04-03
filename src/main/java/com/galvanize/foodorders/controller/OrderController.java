package com.galvanize.foodorders.controller;

import com.galvanize.foodorders.entities.Order;
import com.galvanize.foodorders.entities.Status;
import com.galvanize.foodorders.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order){
        return ResponseEntity.ok(orderService.createOrder(order));
    }

    @GetMapping
    public ResponseEntity<ArrayList<Order>> getAllOrders(){
        ArrayList<Order> orders = orderService.getOrders();
        return ResponseEntity.ok(orders);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id){
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestParam Status status){
        Order order = orderService.getOrderById(id);
        order.setStatus(status);
        return ResponseEntity.ok(order);
    }

}
