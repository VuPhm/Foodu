package com.foodu.features.cart.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.foodu.R;
import com.foodu.features.cart.data.CartItem;
import com.foodu.features.cart.viewmodel.CartViewModel;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private final CartViewModel cartViewModel;
    private List<CartItem> cartItemList = new ArrayList<>();

    public CartAdapter(CartViewModel cartViewModel) {
        this.cartViewModel = cartViewModel;
    }

    public void submitList(List<CartItem> items) {
        cartItemList = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItemList.get(position);

        holder.name.setText(item.getName());
        holder.price.setText("$" + item.getPrice());
        holder.quantity.setText("Quantity: " + item.getQuantity());

        Glide.with(holder.image.getContext())
                .load(item.getImageUrl())
                .placeholder(R.drawable.loading_text_overlay)
                .into(holder.image);

        holder.checkSelect.setOnCheckedChangeListener(null);
        holder.checkSelect.setChecked(item.isSelected());
        holder.checkSelect.setOnCheckedChangeListener((buttonView, isChecked) -> {
            item.setSelected(isChecked);
            cartViewModel.updateItem(item); // Cập nhật lại ViewModel
        });

        holder.increaseBtn.setOnClickListener(v -> {
            int newQuantity = item.getQuantity() + 1;
            cartViewModel.updateQuantity(item, newQuantity);
        });

        holder.decreaseBtn.setOnClickListener(v -> {
            int newQuantity = item.getQuantity() - 1;
            if (newQuantity <= 0) {
                cartViewModel.removeItem(item);
            } else {
                cartViewModel.updateQuantity(item, newQuantity);
            }
        });

        holder.removeBtn.setOnClickListener(v -> cartViewModel.removeItem(item));
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, quantity;
        ImageView image;
        CheckBox checkSelect;
        Button increaseBtn, decreaseBtn, removeBtn;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cartItemName);
            price = itemView.findViewById(R.id.cartItemPrice);
            quantity = itemView.findViewById(R.id.cartItemQuantity);
            image = itemView.findViewById(R.id.cartItemImage);
            checkSelect = itemView.findViewById(R.id.checkSelect);
            increaseBtn = itemView.findViewById(R.id.btnIncrease);
            decreaseBtn = itemView.findViewById(R.id.btnDecrease);
            removeBtn = itemView.findViewById(R.id.btnRemove);
        }
    }
}
