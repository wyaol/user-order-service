package com.thoughtworks.userorderservice.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.thoughtworks.userorderservice.dto.OrderDetail;
import com.thoughtworks.userorderservice.dto.OrderStatus;
import com.thoughtworks.userorderservice.repository.entity.OrderEntity;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void shouldCreateOrderSuccess() {
        OrderEntity orderEntity1 = OrderEntity.builder()
            .deduction(0)
            .orderDetails(List.of(OrderDetail.builder()
                .name("name")
                .picUrl("picUrl")
                .price(2)
                .build()))
            .orderStatus(OrderStatus.CREATED)
            .totalPrice(30)
            .build();
        OrderEntity orderEntity = orderRepository.save(
            orderEntity1
        );
        assertEquals(orderEntity, orderEntity1);
    }
}