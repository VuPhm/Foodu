package com.foodu.features.order.ui;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.foodu.R;
import com.foodu.features.order.data.Order;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private final List<Order> orderList;

    public OrderAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.orderTotal.setText("Tổng: $" + order.getTotal());

        String formattedDate = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                .format(new Date(order.getCreatedAt()));
        holder.orderDate.setText("Ngày đặt: " + formattedDate);

        holder.orderItemCount.setText("Số sản phẩm: " + order.getItems().size());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderTotal, orderDate, orderItemCount;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderTotal = itemView.findViewById(R.id.orderTotal);
            orderDate = itemView.findViewById(R.id.orderDate);
            orderItemCount = itemView.findViewById(R.id.orderItemCount);
        }
    }
}
