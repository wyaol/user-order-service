package com.thoughtworks.userorderservice.client;

import com.thoughtworks.userorderservice.client.request.InventoryLockRequest;
import com.thoughtworks.userorderservice.client.response.ClientResponse;
import com.thoughtworks.userorderservice.dto.Detail;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name="commodity-service", url = "${services.commodity-service.url}")
public interface CommodityServiceClient {

    @PostMapping(value = "/inventory-locks")
    ClientResponse<List<Detail>> lockInventory(InventoryLockRequest inventoryLockRequest);

}
