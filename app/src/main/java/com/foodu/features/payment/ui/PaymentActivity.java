package com.foodu.features.payment.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.foodu.R;
import com.foodu.features.cart.data.CartItem;
import com.foodu.features.payment.viewmodel.PaymentViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class PaymentActivity extends AppCompatActivity {

    private PaymentViewModel viewModel;
    private TextView totalView;
    private Button payButton;
    private List<CartItem> selectedItems;  // Lưu danh sách nhận từ intent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        totalView = findViewById(R.id.paymentTotal);
        payButton = findViewById(R.id.btnPay);
        RecyclerView recycler = findViewById(R.id.recyclerSelectedItems);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        viewModel = new ViewModelProvider(this).get(PaymentViewModel.class);

        // 🔽 Nhận danh sách item từ intent
        String json = getIntent().getStringExtra("selectedItems");
        selectedItems = new Gson().fromJson(json, new TypeToken<List<CartItem>>(){}.getType());

        recycler.setAdapter(new PaymentItemAdapter(selectedItems));
        totalView.setText("Tổng tiền: $" + viewModel.getTotal(selectedItems));

        payButton.setOnClickListener(v -> {
            if (selectedItems.isEmpty()) {
                Toast.makeText(this, "Chưa chọn món nào!", Toast.LENGTH_SHORT).show();
                return;
            }
            viewModel.placeOrder(selectedItems);
        });

        viewModel.getPaymentSuccess().observe(this, success -> {
            if (success != null && success) {
                Toast.makeText(this, "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();
                finish();
            } else if (success != null) {
                Toast.makeText(this, "Lỗi khi thanh toán!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
