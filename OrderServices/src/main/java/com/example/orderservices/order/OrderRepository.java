package com.example.orderservices.order;
import com.example.orderservices.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

    List<Order> findByCreationDateGreaterThanEqual(Date creationDate);

    List<Order> findByShopperRut(String shopperRut);

    List<Order> findByOrderStatus(String orderStatus);

    List<Order> findByOrderStatusAndCreationDate(String orderStatus, Date creationDate);

    List<Order> findByOrderStatusAndShopperRut(String orderStatus, String shopperRut);

    List<Order> findByCreationDateGreaterThanEqualAndShopperRut(Date creationDate, String shopperRut);

    List<Order> findByCreationDateGreaterThanEqualAndShopperRutAndOrderStatus(Date orderStatus, String creationDate, String orderStatus1);
}
