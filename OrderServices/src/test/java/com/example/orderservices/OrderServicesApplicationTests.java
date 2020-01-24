package com.example.orderservices;

import com.example.orderservices.order.Order;
import com.example.orderservices.order.OrderItem;
import com.example.orderservices.order.OrderStatus;
import com.example.orderservices.order.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class OrderServicesApplicationTests {

	@Autowired
	protected MockMvc mvc;

	@Test
	void contextLoads() {
	}
	@Test
	@org.junit.jupiter.api.Order(1)
	public void testCreateOrder() throws Exception {
		Order order = new Order();
		order.setShopperRut("24175297-6");
		order.setShopperEmail("pierosteffano@gmail.com");
		Set<OrderItem> orderItems=new HashSet<OrderItem>(2);
		orderItems.add(createOrderItem(1));
		orderItems.add(createOrderItem(2));

		order.setOrderItems(orderItems);
		System.out.println("mvc::" + mvc);
		MockHttpServletRequestBuilder post = MockMvcRequestBuilders
				.post("/order/create");
		post.content(asJsonString(order));
		post.contentType(MediaType.APPLICATION_JSON);
		MockHttpServletRequestBuilder accept = post.accept(MediaType.APPLICATION_JSON);
		System.out.println("accept:" + accept);
		ResultActions resultActions = mvc.perform(accept);
		System.out.println("resultActions:" + resultActions);
		resultActions.andDo(print());
		resultActions.andExpect(status().isCreated());
	}
	@Test
	@org.junit.jupiter.api.Order(2)
	public void testFindAllOrders() throws Exception {
		mvc.perform( MockMvcRequestBuilders
				.get("/order/findall")
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.orders").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.orders[*].orderId").isNotEmpty());
	}
	@Test
	@org.junit.jupiter.api.Order(3)
	public void testFindOrdersById() throws Exception {
		mvc.perform( MockMvcRequestBuilders
				.get("/order/byId/{id}",(int)1)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.orderId").value((int)1));
	}


	@Test
	@org.junit.jupiter.api.Order(4)
	public void testGetbycreationandshoperandstatus() throws Exception {
		Order order = new Order();
		order.setShopperRut("24175297-6");
		order.setCreationDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2020-01-21 15:18:06"));
		order.setOrderStatus(OrderStatus.CREATED);
		mvc.perform( MockMvcRequestBuilders
				.post("/order/getbycreationandshoperandstatus")
				.content(asJsonString(order))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.orders").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.orders[*].orderId").isNotEmpty());

	}
		private OrderItem createOrderItem(Integer productId) {
		OrderItem orderItem1=new OrderItem();
		orderItem1.setQuantity((short)2);
		Product product1=new Product();
		product1.setProductId(productId);
		product1.setName("Producto-" + productId);
		product1.setPrice(5000f);
		orderItem1.setProduct(product1);
		return orderItem1;
	}

	public static String asJsonString(final Object obj) {
		try {
			String ret = new ObjectMapper().writeValueAsString(obj);
			System.out.println("ret:"+ ret);
			return ret;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
