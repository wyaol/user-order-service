package com.thoughtworks.userorderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderDetail {
    private Integer id;
    private String name;
    private Integer price;
    private String picUrl;
    private Integer copies;
}
