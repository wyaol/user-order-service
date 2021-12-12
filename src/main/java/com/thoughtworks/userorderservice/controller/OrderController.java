package com.thoughtworks.userorderservice.controller;

import com.thoughtworks.userorderservice.controller.request.OrderCreateRequest;
import com.thoughtworks.userorderservice.controller.response.Response;
import com.thoughtworks.userorderservice.exception.BusinessException;
import com.thoughtworks.userorderservice.exception.ServiceErrorException;
import com.thoughtworks.userorderservice.service.OrderService;
import com.thoughtworks.userorderservice.service.dto.OrderDTO;
import feign.RetryableException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<OrderDTO> createOrder(
        @RequestHeader Integer userId,
        @RequestHeader Integer merchantId,
        @RequestBody OrderCreateRequest orderCreateRequest
    ) {
        return Response.success(orderService.createOrder(orderCreateRequest, userId, merchantId));
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response<Object> handlerBusinessException(BusinessException businessException) {
        return new Response<>(businessException.getCode(), businessException.getMsg(), null);
    }

    @ExceptionHandler({ServiceErrorException.class, RetryableException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response<Object> handlerBusinessException(Exception e) {
        return new Response<>(5000, "unknown error", null);
    }
}
