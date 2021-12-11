package com.thoughtworks.userorderservice.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Response {
    private static final Integer SUCCESS_CODE = 0;

    private Integer code = SUCCESS_CODE;
    private String msg = "";
}
