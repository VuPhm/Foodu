package com.foodu.features.home.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.foodu.R;
import com.foodu.features.cart.ui.CartActivity;
import com.foodu.features.order.ui.OrderHistoryActivity;
import com.foodu.features.home.data.Product;
import com.foodu.features.home.data.ProductRepository;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private Button cartButton, orderHistoryButton;
    private final ProductRepository productRepository = new ProductRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recyclerViewProducts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productRepository.getProducts().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                productAdapter = new ProductAdapter(products, HomeActivity.this);
                recyclerView.setAdapter(productAdapter);
            }
        });

        cartButton = findViewById(R.id.cartButton);
        cartButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, CartActivity.class);
            startActivity(intent);
        });

        // Nút xem lịch sử đơn hàng
        orderHistoryButton = findViewById(R.id.orderHistoryButton);
        orderHistoryButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, OrderHistoryActivity.class);
            startActivity(intent);
        });
    }
}
