package com.foodu.features.order.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.foodu.features.order.data.Order;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class OrderRepository {
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private final FirebaseAuth auth = FirebaseAuth.getInstance();

    public LiveData<List<Order>> getOrders() {
        MutableLiveData<List<Order>> ordersLiveData = new MutableLiveData<>();
        String userId = auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : "guest";

        firestore.collection("orders")
                .whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener(query -> {
                    List<Order> orders = new ArrayList<>();
                    for (DocumentSnapshot doc : query.getDocuments()) {
                        Order order = doc.toObject(Order.class);
                        orders.add(order);
                    }
                    ordersLiveData.setValue(orders);
                });

        return ordersLiveData;
    }
}
