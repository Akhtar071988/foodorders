package com.galvanize.foodorders.service;

import com.galvanize.foodorders.OrderRepository;
import com.galvanize.foodorders.entities.Order;
import com.galvanize.foodorders.entities.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(Order order) {
        LocalDateTime localDate = LocalDateTime.now();
        order.setUpdatedAt(localDate);
        order.setCreatedAt(localDate);
        return orderRepository.save(order);
    }

    public ArrayList<Order> getOrders() {
        return (ArrayList<Order>) orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if(!order.isPresent()){
            throw new RuntimeException("Order id '"+id+"' not found");
        }
        return order.get();
    }

    public Order updateStatus(Long id, Status status) {
        Order order = getOrderById(id);
        order.setStatus(status);
        orderRepository.save(order);
        return order;
    }


    public Order updateNotes(Long id, String note) {
        Order order = getOrderById(id);
        order.setNote(note);
        orderRepository.save(order);
        return order;
    }

}
