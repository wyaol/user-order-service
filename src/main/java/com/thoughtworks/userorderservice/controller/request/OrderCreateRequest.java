package com.thoughtworks.userorderservice.controller.request;

import com.thoughtworks.userorderservice.dto.OrderItem;
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
    private List<OrderItem> foods;
}
