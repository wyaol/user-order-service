package com.thoughtworks.userorderservice.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.thoughtworks.userorderservice.dto.Detail;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDTO {
    private List<Detail> details;
    private Integer totalPrice;
    private Integer deduction;
    private OrderStatus orderStatus;
}
