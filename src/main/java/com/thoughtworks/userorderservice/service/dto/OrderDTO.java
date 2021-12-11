package com.thoughtworks.userorderservice.service.dto;

import com.thoughtworks.userorderservice.dto.Detail;
import com.thoughtworks.userorderservice.dto.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderDTO {
    private Detail[] details;
    private Integer totalPrice;
    private Integer deduction;
    private OrderStatus orderStatus;
}
