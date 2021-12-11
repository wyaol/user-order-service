package com.thoughtworks.userorderservice.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.thoughtworks.userorderservice.client.CommodityServiceClient;
import com.thoughtworks.userorderservice.client.response.ClientResponse;
import com.thoughtworks.userorderservice.controller.request.OrderCreateRequest;
import com.thoughtworks.userorderservice.dto.Detail;
import com.thoughtworks.userorderservice.dto.OrderStatus;
import com.thoughtworks.userorderservice.repository.OrderRepository;
import com.thoughtworks.userorderservice.repository.entity.OrderEntity;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CommodityServiceClient commodityServiceClient;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateOrderSuccess() {
        List<Detail> details = List.of(
            Detail.builder().price(10).build(),
            Detail.builder().price(10).build(),
            Detail.builder().price(10).build()
        );

        when(orderRepository.save(any())).thenReturn(
            OrderEntity.builder().build()
        );
        when(commodityServiceClient.lockInventory(any())).thenReturn(
            new ClientResponse<>(0, "", details)
        );

        orderService.createOrder(OrderCreateRequest.builder()
            .foodIds(List.of(1, 2, 3))
            .build());

        verify(orderRepository).save(
            OrderEntity.builder()
                .deduction(0)
                .orderStatus(OrderStatus.CREATED)
                .totalPrice(30)
                .details(details)
                .build()
        );
    }
}