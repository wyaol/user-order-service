package com.thoughtworks.userorderservice.service;

import com.thoughtworks.userorderservice.client.CommodityServiceClient;
import com.thoughtworks.userorderservice.client.response.ClientResponse;
import com.thoughtworks.userorderservice.dto.OrderDetail;
import com.thoughtworks.userorderservice.client.request.InventoryLockRequest;
import com.thoughtworks.userorderservice.controller.request.OrderCreateRequest;
import com.thoughtworks.userorderservice.dto.OrderStatus;
import com.thoughtworks.userorderservice.exception.InventoryShortageException;
import com.thoughtworks.userorderservice.repository.OrderRepository;
import com.thoughtworks.userorderservice.repository.entity.OrderEntity;
import com.thoughtworks.userorderservice.service.dto.OrderDTO;
import com.thoughtworks.userorderservice.util.ObjectMapperUtil;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private OrderRepository orderRepository;
    private CommodityServiceClient commodityServiceClient;

    private static final Map<Integer, Integer> COMMODITY_SERVICE_CODE_TO_SERVICE_CODE = Map.of(
        4008, 4008
    );
    private static final Integer COMMODITY_SERVICE_SUCCESS_CODE = 200;

    public OrderDTO createOrder(OrderCreateRequest orderCreateRequest) {

        ClientResponse<List<OrderDetail>> clientResponse = commodityServiceClient.lockInventory(
            InventoryLockRequest.builder().
                inventories(orderCreateRequest.getFoods())
                .build()
        );

        if (!COMMODITY_SERVICE_SUCCESS_CODE.equals(clientResponse.getCode())) {
            throw new InventoryShortageException(
                COMMODITY_SERVICE_CODE_TO_SERVICE_CODE.get(clientResponse.getCode()),
                clientResponse.getMsg());
        }

        List<OrderDetail> orderDetails = clientResponse.getData();

        OrderEntity orderEntity = orderRepository.save(
            OrderEntity.builder()
                .orderStatus(OrderStatus.CREATED)
                .deduction(0)
                .totalPrice(orderDetails.stream().map(it -> it.getPrice() * it.getCopies())
                    .reduce(0, Integer::sum))
                .orderDetails(orderDetails)
                .build()
        );
        return ObjectMapperUtil.convert(orderEntity, OrderDTO.class);
    }
}
