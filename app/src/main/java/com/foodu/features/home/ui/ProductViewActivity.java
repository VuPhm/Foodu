package com.foodu.features.home.ui;


import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.foodu.R;
import com.foodu.features.cart.data.CartItem;
import com.foodu.features.cart.data.CartRepository;

public class ProductViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_product); // Dùng lại layout bạn có sẵn

        // Nhận dữ liệu từ intent
        String name = getIntent().getStringExtra("name");
        double price = getIntent().getDoubleExtra("price", 0.0);
        String imageUrl = getIntent().getStringExtra("imageUrl");

        // Liên kết với view
        TextView productName = findViewById(R.id.productName);
        TextView productPrice = findViewById(R.id.productPrice);
        ImageView productImage = findViewById(R.id.productImage);
        Button addToCart = findViewById(R.id.addToCartButton);

        // Hiển thị dữ liệu
        productName.setText(name);
        productPrice.setText("$" + price);
        Glide.with(this).load(imageUrl).into(productImage);

        // Xử lý thêm vào giỏ
        addToCart.setOnClickListener(v -> {
            CartItem item = new CartItem(null, name, price, 1, imageUrl);
            CartRepository.getInstance().addItem(item);
            Toast.makeText(this, "Đã thêm vào giỏ", Toast.LENGTH_SHORT).show();
        });
    }
}
