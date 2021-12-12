package com.thoughtworks.userorderservice.client.request;

import com.thoughtworks.userorderservice.dto.OrderItem;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class InventoryLockRequest {
    private List<OrderItem> inventories;
}
