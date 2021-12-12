package com.thoughtworks.userorderservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.userorderservice.controller.request.OrderCreateRequest;
import com.thoughtworks.userorderservice.exception.InventoryShortageException;
import com.thoughtworks.userorderservice.exception.ThirdServiceException;
import com.thoughtworks.userorderservice.service.OrderService;
import com.thoughtworks.userorderservice.service.dto.OrderDTO;
import feign.Request;
import feign.Request.HttpMethod;
import feign.RetryableException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class OrderControllerTest {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    void shouldCreateOrderSuccess() throws Exception {
        when(orderService.createOrder(any())).thenReturn(OrderDTO.builder().build());

        mockMvc.perform(post("/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                objectMapper.writeValueAsString(
                    OrderCreateRequest.builder()
                        .foods(List.of())
                        .build()
                )
            ))
            .andExpect(status().isCreated());
    }

    @Test
    void shouldReturn400WhenCreateOrderFailWithInventoryShortage() throws Exception {
        when(orderService.createOrder(any()))
            .thenThrow(new InventoryShortageException(4008, "Inventory is not enough"));

        mockMvc.perform(post("/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                objectMapper.writeValueAsString(
                    OrderCreateRequest.builder()
                        .foods(List.of())
                        .build()
                )
            ))
            .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn500WhenCreateOrderFailWithThirdServiceError() throws Exception {
        when(orderService.createOrder(any())).thenThrow(new ThirdServiceException());

        mockMvc.perform(post("/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                objectMapper.writeValueAsString(
                    OrderCreateRequest.builder()
                        .foods(List.of())
                        .build()
                )
            ))
            .andExpect(status().is5xxServerError());
    }

    @Test
    void shouldReturn500WhenCreateOrderFailWithThirdServiceTimeOut() throws Exception {
        when(orderService.createOrder(any()))
            .thenThrow(new RetryableException(500, "service connection refused", null, null, Request
                .create(HttpMethod.GET, "", Map.of(), null, Charset.defaultCharset())));

        mockMvc.perform(post("/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                objectMapper.writeValueAsString(
                    OrderCreateRequest.builder()
                        .foods(List.of())
                        .build()
                )
            ))
            .andExpect(status().is5xxServerError());
    }

}