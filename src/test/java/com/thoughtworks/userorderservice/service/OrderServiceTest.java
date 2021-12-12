package com.thoughtworks.userorderservice.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.thoughtworks.userorderservice.client.CommodityServiceClient;
import com.thoughtworks.userorderservice.client.response.ClientResponse;
import com.thoughtworks.userorderservice.controller.request.OrderCreateRequest;
import com.thoughtworks.userorderservice.dto.OrderDetail;
import com.thoughtworks.userorderservice.dto.OrderItem;
import com.thoughtworks.userorderservice.dto.OrderStatus;
import com.thoughtworks.userorderservice.exception.InventoryShortageException;
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
        List<OrderDetail> orderDetails = List.of(
            OrderDetail.builder().price(10).copies(1).build(),
            OrderDetail.builder().price(10).copies(1).build(),
            OrderDetail.builder().price(10).copies(2).build()
        );

        when(orderRepository.save(any())).thenReturn(
            OrderEntity.builder().build()
        );
        when(commodityServiceClient.lockInventory(any())).thenReturn(
            new ClientResponse<>(0, "", orderDetails)
        );

        orderService.createOrder(new OrderCreateRequest(
            List.of(
                new OrderItem(1, 1),
                new OrderItem(2, 1)
            )
        ), 123, 124);

        verify(orderRepository).save(
            OrderEntity.builder()
                .userid(123)
                .merchantId(124)
                .deduction(0)
                .orderStatus(OrderStatus.CREATED)
                .totalPrice(40)
                .orderDetails(orderDetails)
                .build()
        );
    }

    @Test
    void shouldThrowExceptionWhenClientCodeIsNot200() {
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(
            List.of(
                new OrderItem(124, 1)
            )
        );

        when(orderRepository.save(any())).thenReturn(
            OrderEntity.builder().build()
        );
        when(commodityServiceClient.lockInventory(any())).thenReturn(
            new ClientResponse<>(4008, "Inventory is not enough", null)
        );

        assertThrows(InventoryShortageException.class,
            () -> orderService.createOrder(orderCreateRequest, 123, 124));
    }
}