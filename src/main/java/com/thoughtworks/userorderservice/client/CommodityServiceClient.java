package com.thoughtworks.userorderservice.client;

import com.thoughtworks.userorderservice.client.request.InventoryLockRequest;
import com.thoughtworks.userorderservice.client.response.ClientResponse;
import com.thoughtworks.userorderservice.dto.Detail;
import java.util.List;

public interface CommodityServiceClient {

    ClientResponse<List<Detail>> lockInventory(InventoryLockRequest inventoryLockRequest);

}
