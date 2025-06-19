package com.foodu.features.order.data;

import com.foodu.features.cart.data.CartItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order {
    private String userId;
    private double total;
    private long createdAt;
    private List<CartItem> items;

    public Order() {} // Required for Firestore

    public Order(String userId, double total, long createdAt, List<CartItem> items) {
        this.userId = userId;
        this.total = total;
        this.createdAt = createdAt;
        this.items = items;
    }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }

    public List<CartItem> getItems() { return items; }
    public void setItems(List<CartItem> items) { this.items = items; }

    // Optional: toMap if you want to send manually to Firestore
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("total", total);
        map.put("createdAt", createdAt);
        map.put("items", items);
        return map;
    }
}