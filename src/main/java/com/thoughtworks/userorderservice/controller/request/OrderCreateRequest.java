package com.thoughtworks.userorderservice.controller.request;

import com.thoughtworks.userorderservice.dto.OrderStatus;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderCreateRequest {
    private List<Integer> foodIds;
    private Integer totalPrice;
    private Integer deduction;
    private OrderStatus orderStatus;
}
