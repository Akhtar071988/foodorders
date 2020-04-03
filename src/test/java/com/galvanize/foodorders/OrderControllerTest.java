package com.galvanize.foodorders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.foodorders.entities.Order;
import com.galvanize.foodorders.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.util.ArrayList;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    OrderService orderService;

    ObjectMapper mapper = new ObjectMapper();

    private Order order;

    @BeforeEach
    void setup() throws JsonProcessingException {
        order = new Order("Sami Akhtar", "Buffalo Burger Combo with Sweet Potato Fries and Chocolate Milkshake");
        order.setId(1L);
    }

    @Test
    void createOrderTest() throws Exception {
        String json = mapper.writeValueAsString(order);
        when(orderService.createOrder(ArgumentMatchers.any(Order.class))).thenReturn(order);
        mvc.perform(post("/api/orders").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(order.getId()))
                .andExpect((jsonPath("$.customerName").value(order.getCustomerName())))
                .andExpect((jsonPath("$.description").value(order.getDescription())))
                .andExpect((jsonPath("$.note").value(order.getNote())));

    }

    @Test
    void getAllOrdersTest() throws Exception {
        Order order1 = new Order("Sami Akhtar", "Chicken and Waffles with Waffle Fies");
        ArrayList<Order> orders = new ArrayList<>();
        orders.add(order);
        orders.add(order1);
        when(orderService.getOrders()).thenReturn(orders);
        mvc.perform(get("/api/orders")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(orders.size())));
    }

    @Test
    void getOrderByIdTest() throws Exception {
        when(orderService.getOrderById(order.getId())).thenReturn(order);
        mvc.perform(get("/api/orders/"+order.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.customerName").value(order.getCustomerName()));
    }

    @Test
    void updateOrderTest() throws Exception {
        when(orderService.getOrderById(order.getId())).thenReturn(order);
        when(orderService.createOrder(order)).thenReturn(order);
        String orderUrl = "/api/orders/"+order.getId();
        mvc.perform(patch(orderUrl)
                .param("status", "COMPLETED")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is("COMPLETED")));
    }

}