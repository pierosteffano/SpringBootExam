package com.example.orderservices.order;

import java.net.URI;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Api(value="/order",description="Order services",produces ="application/json")
@RestController
@RequestMapping("/order")
public class OrderServicesRestController {

    static final Logger logger = LoggerFactory.getLogger(OrderServicesRestController.class);
    @Autowired
    private OrderRepository orderRepository;
    @Value( "${notifications.host:localhost}" )
    private String notificationsHost;
    @Value( "${notifications.port:8082}" )
    private String notificationsPort;

    @ApiOperation(value="create order",response=ResponseEntity.class)
    @ApiResponses(value={
            @ApiResponse(code=201,message="Order Created",response=ResponseEntity.class),
    })
    @PostMapping("/create")
    public ResponseEntity<Object> createOrder(@RequestBody Order order){
        order.setCreationDate(new Timestamp(System.currentTimeMillis()));
        order.setOrderStatus(OrderStatus.CREATED);
        order.setOrderTotal((float)order.getOrderItems().stream().mapToDouble(oi->oi.getQuantity()*oi.getProduct().getPrice()).sum());
        logger.info("Saving Order: {}",order.getOrderId());
        Order savedOrder = orderRepository.save(order);
        logger.info("Order Saved: {}",savedOrder.getOrderId());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{orderId}")
                .buildAndExpand(savedOrder.getOrderId()).toUri();
        final String uri = "http://".concat(notificationsHost).concat(":").concat(notificationsPort).concat("/sendnotification");

        RestTemplate restTemplate = new RestTemplate();
        String orderId=savedOrder.getOrderId().toString();
        String email=savedOrder.getShopperEmail();

        NotificationRequest request=new NotificationRequest(orderId,email);
        logger.info("Sending Notification: {}",request.toString());
        String result =restTemplate.postForObject(uri,request,String.class);
        logger.info("Notification sent: {}",result);
        return ResponseEntity.created(location).build();
    }

    @ApiOperation(value="find all orders",response=List.class)
    @ApiResponses(value={
            @ApiResponse(code=201,message="Orders foud",response=List.class),
    })
    @GetMapping("/findall")
    public List<Order> retrieveAllOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/byId/{id}")
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
