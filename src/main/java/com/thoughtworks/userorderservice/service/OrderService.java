package com.thoughtworks.userorderservice.service;

import com.thoughtworks.userorderservice.client.CommodityServiceClient;
import com.thoughtworks.userorderservice.client.request.InventoryLockRequest;
import com.thoughtworks.userorderservice.controller.request.OrderCreateRequest;
import com.thoughtworks.userorderservice.dto.Detail;
import com.thoughtworks.userorderservice.dto.OrderStatus;
import com.thoughtworks.userorderservice.repository.OrderRepository;
import com.thoughtworks.userorderservice.repository.entity.OrderEntity;
import com.thoughtworks.userorderservice.service.dto.OrderDTO;
import com.thoughtworks.userorderservice.utils.ObjectMapperUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private OrderRepository orderRepository;
    private CommodityServiceClient commodityServiceClient;

    public OrderDTO createOrder(OrderCreateRequest orderCreateRequest) {

        List<Detail> details = commodityServiceClient.lockInventory(
            InventoryLockRequest.builder().ids(orderCreateRequest.getFoodIds()).build()
        ).getData();

        OrderEntity orderEntity = orderRepository.save(
            OrderEntity.builder()
                .orderStatus(OrderStatus.CREATED)
                .deduction(0)
                .totalPrice(details.stream().map(Detail::getPrice).reduce(0, Integer::sum))
                .details(details)
                .build()
        );
        return ObjectMapperUtil.convert(orderEntity, OrderDTO.class);
    }
}
