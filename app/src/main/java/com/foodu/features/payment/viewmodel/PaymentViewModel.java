package com.foodu.features.payment.viewmodel;

import android.util.Log;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.LiveData;

import com.foodu.features.cart.data.CartItem;
import com.foodu.features.cart.data.CartRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.foodu.features.order.data.Order;


import java.util.ArrayList;
import java.util.List;

public class PaymentViewModel extends ViewModel {

    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final CartRepository cartRepo = CartRepository.getInstance();

    private final MutableLiveData<Boolean> paymentSuccess = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public LiveData<Boolean> getPaymentSuccess() { return paymentSuccess; }
    public LiveData<Boolean> getIsLoading() { return isLoading; }

    public List<CartItem> getSelectedItems() {
        List<CartItem> all = cartRepo.getCartItems();
        List<CartItem> selected = new ArrayList<>();
        for (CartItem item : all) {
            if (item.isSelected()) {
                selected.add(item);
            }
        }
        return selected;
    }


    public double getTotal(List<CartItem> selectedItems) {
        double total = 0;
        for (CartItem item : selectedItems) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }


    public void placeOrder(List<CartItem> selectedItems) {
        String userId = auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : "guest";
        double total = getTotal(selectedItems);
        long timestamp = System.currentTimeMillis();

        Order order = new Order(userId, total, timestamp, selectedItems);

        isLoading.setValue(true);
        firestore.collection("orders")
                .add(order)
                .addOnSuccessListener(docRef -> {
                    Log.d("PaymentViewModel", "Order placed successfully: " + docRef.getId());
                    isLoading.setValue(false);
                    cartRepo.removeSelectedItems();
                    paymentSuccess.setValue(true);
                })
                .addOnFailureListener(e -> {
                    Log.e("PaymentViewModel", "Order failed", e);
                    isLoading.setValue(false);
                    paymentSuccess.setValue(false);
                });
    }
}
