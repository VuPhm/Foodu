package com.foodu.features.home.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.foodu.features.home.data.model.Product;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ProductRepository {

    @Inject
    public ProductRepository() {}

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public LiveData<List<Product>> getProducts() {
        MutableLiveData<List<Product>> liveData = new MutableLiveData<>();

        db.collection("products").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Product> products = new ArrayList<>();
                for (DocumentSnapshot doc : task.getResult()) {
                    Product product = doc.toObject(Product.class);
                    if (product != null) {
                        product.setId(doc.getId()); // set ID từ document
                        products.add(product);
                    }
                }
                liveData.setValue(products);
            } else {
                liveData.setValue(new ArrayList<>()); // hoặc null nếu muốn báo lỗi
            }
        });

        return liveData;
    }
}
