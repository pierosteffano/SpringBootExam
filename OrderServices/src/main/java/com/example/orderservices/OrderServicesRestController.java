package com.example.orderservices;

import com.example.orderservices.order.Order;

import java.net.URI;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
@RestController
public class OrderServicesRestController {

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/order")
    public ResponseEntity<Object> createOrder(@RequestBody Order order){
        order.setCreationDate(new Timestamp(System.currentTimeMillis()));
        order.setOrderStatus(OrderStatus.CREATED);
        order.setOrderTotal((float)order.getOrderItems().stream().mapToDouble(oi->oi.getQuantity()*oi.getProduct().getPrice()).sum());
        Order savedOrder = orderRepository.save(order);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{orderId}")
                .buildAndExpand(savedOrder.getOrderId()).toUri();

        return ResponseEntity.created(location).build();
    }


    @GetMapping("/orders")
    public List<Order> retrieveAllOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/order/{id}")
    public Order retrieveOrder(@PathVariable long id) {
        Optional<Order> order = orderRepository.findById(id);

        if (!order.isPresent())
            throw new OrderNotFoundException("id-" + id);

        return order.get();
    }
    @PostMapping("/getordersbycreationandshoperandstatus")
    public List<Order> retrieveOrders(@RequestBody Order p_order) {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues();
        Example<Order> exampleQuery = Example.of(p_order, matcher);
        List<Order> lst_orders = orderRepository.findAll(exampleQuery);
        //List<Order> lst_orders = orderRepository.findByCreationDateOrShopperRutOrOrderStatus(p_order.getCreationDate(),p_order.getShopperRut(),p_order.getOrderStatus());

        if (lst_orders.isEmpty())
            throw new OrderNotFoundException("No orders were found for given filters");

        return lst_orders;
    }
}
