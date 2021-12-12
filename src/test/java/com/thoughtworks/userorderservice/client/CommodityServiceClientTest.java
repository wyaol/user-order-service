package com.thoughtworks.userorderservice.client;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.http.trafficlistener.ConsoleNotifyingWiremockNetworkTrafficListener;
import com.thoughtworks.userorderservice.client.request.InventoryLockRequest;
import com.thoughtworks.userorderservice.client.response.ClientResponse;
import com.thoughtworks.userorderservice.dto.OrderDetail;
import com.thoughtworks.userorderservice.dto.OrderItem;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableFeignClients(basePackages = "com.thoughtworks.userorderservice.client.*")
class CommodityServiceClientTest {

    private static final WireMockConfiguration wireMockConfiguration = WireMockConfiguration
        .wireMockConfig().networkTrafficListener(
            new ConsoleNotifyingWiremockNetworkTrafficListener()).port(8001);
    private static final WireMockServer wireMockServer = new WireMockServer(wireMockConfiguration);

    @Autowired
    private CommodityServiceClient commodityServiceClient;

    @BeforeAll
    static void startWireMock() {
        wireMockServer.start();
    }

    @AfterAll
    static void stopWireMock() {
        wireMockServer.stop();
    }

    @Test
    void shouldLockInventorySuccess() {
        ClientResponse<List<OrderDetail>> listClientResponse = commodityServiceClient
            .lockInventory(InventoryLockRequest.builder().inventories(List.of(new OrderItem(123, 1))).build());
        assertEquals("name", listClientResponse.getData().get(0).getName());
        assertEquals(123, listClientResponse.getData().get(0).getId());
        assertEquals("http://127.0.0.1", listClientResponse.getData().get(0).getPicUrl());
        assertEquals(30, listClientResponse.getData().get(0).getPrice());
        assertEquals(1, listClientResponse.getData().get(0).getCopies());
    }
}