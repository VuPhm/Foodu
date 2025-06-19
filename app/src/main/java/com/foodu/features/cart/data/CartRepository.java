package com.foodu.features.cart.data;

import java.util.ArrayList;
import java.util.List;

public class CartRepository {
    private static CartRepository instance;
    private final List<CartItem> cartItems;

    private CartRepository() {
        cartItems = new ArrayList<>();
    }

    public static synchronized CartRepository getInstance() {
        if (instance == null) {
            instance = new CartRepository();
        }
        return instance;
    }

    public void addItem(CartItem item) {
        for (CartItem i : cartItems) {
            if (i.getProductId().equals(item.getProductId())) {
                i.setQuantity(i.getQuantity() + item.getQuantity());
                return;
            }
        }
        cartItems.add(item);
    }

    public List<CartItem> getCartItems() {
        return new ArrayList<>(cartItems);
    }

    public double getTotal() {
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }

    public void clearCart() {
        cartItems.clear();
    }

    public void updateQuantity(CartItem item, int quantity) {
        item.setQuantity(quantity);
    }

    public void removeItem(CartItem item) {
        cartItems.remove(item);
    }

    public void removeSelectedItems() {
        cartItems.removeIf(CartItem::isSelected);
    }
}