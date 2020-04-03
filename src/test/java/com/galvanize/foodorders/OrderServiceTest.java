package com.galvanize.foodorders;

import com.galvanize.foodorders.entities.Order;
import com.galvanize.foodorders.entities.Status;
import com.galvanize.foodorders.service.OrderService;
import javafx.animation.Animation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class OrderServiceTest {

    @Autowired
    OrderService orderService;

    @Test
    void createOrderTest() throws Exception {
        Order newOrder = new Order("Hungry Man Jr", "Burger, double meat, extra pickles, doughnut buns, diet coke");
        newOrder.setStatus(Status.PENDING);
        newOrder.setNote("New Note");
        newOrder.setCreatedAt(LocalDateTime.now());
        newOrder.setUpdatedAt(LocalDateTime.now());
        Order order = orderService.createOrder(newOrder);
        Order savedOrder = orderService.getOrderById(order.getId());
        assertEquals(savedOrder.getNote(), "New Note");
    }
    @Test
    void getAllOrdersTest() throws Exception {
        List<Order> orders = orderService.getOrders();
        assertNotNull(orders.size() > 0);
    }
    @Test
    void getOrderByIdTest() throws Exception {
        Order newOrder = new Order("Hungry Man Jr", "Burger, double meat, extra pickles, doughnut buns, diet coke");
        Order order = orderService.createOrder(newOrder);
        Order sameOrder = orderService.getOrderById(order.getId());
        assertEquals(order.getId(), sameOrder.getId());
    }
    @Test
    void updateOrderStatusTest() throws Exception {
        Order newOrder = new Order("Hungry Man Jr", "Burger, double meat, extra pickles, doughnut buns, diet coke");
        Order order = orderService.createOrder(newOrder);
        orderService.updateStatus(order.getId(), Status.CANCELLED);
        assertEquals(Status.CANCELLED, order.getStatus());
    }
    @Test
    void updateOrderNoteTest() throws Exception {
        String note = "Test Note";
        Order newOrder = new Order("Hungry Man Jr", "Burger, double meat, extra pickles, doughnut buns, diet coke");
        Order order = orderService.createOrder(newOrder);
        orderService.updateNotes(order.getId(), note);
        assertEquals(note, order.getNote());
    }

}