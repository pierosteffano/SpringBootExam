package com.example.orderservices;
import com.example.orderservices.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
    List<Order> findByCreationDateOrShopperRutOrOrderStatus(Timestamp creationDate, String shopperRut, String orderStatus);

}
