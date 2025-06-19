package com.foodu.features.cart.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.foodu.features.cart.data.CartItem;
import com.foodu.features.cart.data.CartRepository;

import java.util.ArrayList;
import java.util.List;

public class CartViewModel extends ViewModel {

    private final CartRepository repository = CartRepository.getInstance();
    private final MutableLiveData<List<CartItem>> cartItems = new MutableLiveData<>();

    public CartViewModel() {
        cartItems.setValue(repository.getCartItems());
    }

    public LiveData<List<CartItem>> getCartItems() {
        return cartItems;
    }

    public void addItem(CartItem item) {
        repository.addItem(item);
        cartItems.setValue(repository.getCartItems());
    }

    public void updateQuantity(CartItem item, int quantity) {
        repository.updateQuantity(item, quantity);
        cartItems.setValue(repository.getCartItems());
    }

    public void removeItem(CartItem item) {
        repository.removeItem(item);
        cartItems.setValue(repository.getCartItems());
    }

    public void removeSelectedItems() {
        repository.removeSelectedItems();
        cartItems.setValue(repository.getCartItems());
    }

    public double getTotal() {
        return repository.getTotal();
    }
    public void updateItem(CartItem item) {
        // Cập nhật lại toàn bộ danh sách để LiveData thông báo lại
        List<CartItem> updatedList = new ArrayList<>(cartItems.getValue());
        cartItems.setValue(updatedList);
    }
    public List<CartItem> getSelectedItems() {
        if (cartItems.getValue() == null) return new ArrayList<>();

        List<CartItem> selected = new ArrayList<>();
        for (CartItem item : cartItems.getValue()) {
            if (item.isSelected()) {
                selected.add(item);
            }
        }
        return selected;
    }


}
