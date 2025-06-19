package com.foodu.features.cart.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.foodu.R;
import com.foodu.features.cart.data.CartItem;
import com.foodu.features.cart.viewmodel.CartViewModel;
import com.foodu.features.payment.ui.PaymentActivity;
import com.google.gson.Gson;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    private CartViewModel cartViewModel;
    private CartAdapter cartAdapter;
    private TextView totalTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.recyclerCart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartAdapter(cartViewModel);
        recyclerView.setAdapter(cartAdapter);

        totalTextView = findViewById(R.id.totalPrice);

        cartViewModel.getCartItems().observe(this, items -> {
            cartAdapter.submitList(items);
            totalTextView.setText("Total: $" + cartViewModel.getTotal());
        });

        Button checkoutButton = findViewById(R.id.checkoutButton);
        checkoutButton.setOnClickListener(v -> {
            List<CartItem> selectedItems = cartViewModel.getSelectedItems();
            if (selectedItems.isEmpty()) {
                Toast.makeText(this, "Vui lòng chọn ít nhất 1 sản phẩm để thanh toán", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(CartActivity.this, PaymentActivity.class);
            intent.putExtra("selectedItems", new Gson().toJson(selectedItems));  // Gửi danh sách đã chọn
            startActivity(intent);
        });
    }
}
