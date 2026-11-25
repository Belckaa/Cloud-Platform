package com.cloudplatform.benchmark;

import com.cloudplatform.integration.v1.Address;
import com.cloudplatform.integration.v1.InventoryItem;
import com.cloudplatform.integration.v1.ReserveInventoryRequest;
import com.google.protobuf.Timestamp;

import java.nio.charset.StandardCharsets;
import java.time.Instant;

public class SerializationBenchmark {
    public static void main(String[] args) {
        String json = """
        {
          "orderId":"ORD-2026-00125",
          "customerId":"CUS-10025",
          "items":[
            {"productId":"PROD-001","quantity":2,"unitPrice":1299.99,"currency":"UAH"},
            {"productId":"PROD-002","quantity":1,"unitPrice":549.50,"currency":"UAH"},
            {"productId":"PROD-003","quantity":3,"unitPrice":199.00,"currency":"UAH"},
            {"productId":"PROD-004","quantity":1,"unitPrice":2499.00,"currency":"UAH"},
            {"productId":"PROD-005","quantity":2,"unitPrice":875.25,"currency":"UAH"}
          ],
          "shippingAddress":{
            "country":"UA","city":"Kherson","street":"Soborna",
            "building":"12A","postalCode":"73000"
          },
          "metadata":{
            "source":"web","campaign":"winter-2026","priority":"standard"
          },
          "requestedAt":"2026-11-25T16:00:00Z"
        }
        """.replaceAll("\\s+", "");

        ReserveInventoryRequest request =
            ReserveInventoryRequest.newBuilder()
                .setOrderId("ORD-2026-00125")
                .setCustomerId("CUS-10025")
                .addItems(InventoryItem.newBuilder()
                    .setProductId("PROD-001").setQuantity(2)
                    .setUnitPrice(1299.99).setCurrency("UAH"))
                .addItems(InventoryItem.newBuilder()
                    .setProductId("PROD-002").setQuantity(1)
                    .setUnitPrice(549.50).setCurrency("UAH"))
                .addItems(InventoryItem.newBuilder()
                    .setProductId("PROD-003").setQuantity(3)
                    .setUnitPrice(199.00).setCurrency("UAH"))
                .addItems(InventoryItem.newBuilder()
                    .setProductId("PROD-004").setQuantity(1)
                    .setUnitPrice(2499.00).setCurrency("UAH"))
                .addItems(InventoryItem.newBuilder()
                    .setProductId("PROD-005").setQuantity(2)
                    .setUnitPrice(875.25).setCurrency("UAH"))
                .setShippingAddress(Address.newBuilder()
                    .setCountry("UA")
                    .setCity("Kherson")
                    .setStreet("Soborna")
                    .setBuilding("12A")
                    .setPostalCode("73000"))
                .putMetadata("source", "web")
                .putMetadata("campaign", "winter-2026")
                .putMetadata("priority", "standard")
                .setRequestedAt(Timestamp.newBuilder()
                    .setSeconds(Instant.parse("2026-11-25T16:00:00Z").getEpochSecond())
                    .build())
                .build();

        int jsonSize = json.getBytes(StandardCharsets.UTF_8).length;
        int protobufSize = request.toByteArray().length;
        double reduction = (1.0 - (double) protobufSize / jsonSize) * 100.0;

        System.out.println("=== JSON vs Protobuf benchmark ===");
        System.out.println("Entity: 1 order, 5 items, address, metadata, timestamp");
        System.out.println("JSON UTF-8 size: " + jsonSize + " bytes");
        System.out.println("Protobuf binary size: " + protobufSize + " bytes");
        System.out.printf("Protobuf payload reduction: %.2f%%%n", reduction);
    }
}