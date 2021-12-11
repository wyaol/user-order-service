package com.thoughtworks.userorderservice.client.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClientResponse<T> {
    private Integer code;
    private String msg;
    private T data;
}
