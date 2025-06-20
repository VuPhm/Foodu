package com.foodu.features.home.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.foodu.R;
import com.foodu.features.cart.data.CartItem;
import com.foodu.features.cart.data.CartRepository;
import com.foodu.features.home.data.model.Product;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Adapter for displaying products in the home screen RecyclerView.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private final Context context;
    private final List<Product> productList;
    // Format price in VND

    /**
     * Constructor for ProductAdapter.
     * @param productList Initial product list
     * @param context Context for launching activities
     */
    public ProductAdapter(List<Product> productList, Context context) {
        // Always use a mutable list to avoid ConcurrentModificationException
        this.productList = new ArrayList<>(productList != null ? productList : new ArrayList<>());
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);

        holder.name.setText(product.getName());
        holder.price.setText("$" + product.getPrice());

        // Load product image using Glide
        Glide.with(holder.image.getContext())
                .load(product.getImageUrl())
                .placeholder(R.drawable.placeholder_image)
                .into(holder.image);

        // Handle add to cart action
        holder.addToCart.setOnClickListener(v -> {
            CartItem item = new CartItem(
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    1,
                    product.getImageUrl()
            );
            item.setSelected(true);
            CartRepository.getInstance().addItem(item);
        });

        // Handle product item click to show details
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductViewActivity.class);
            intent.putExtra("name", product.getName());
            intent.putExtra("price", product.getPrice());
            intent.putExtra("imageUrl", product.getImageUrl());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    /**
     * Update the product list and refresh the UI.
     * @param products The new list of products to display
     */
    public void setData(List<Product> products) {
        this.productList.clear();
        if (products != null) {
            this.productList.addAll(products);
        }
        notifyDataSetChanged();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView name, price;
        ImageView image;
        Button addToCart;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.productName);
            price = itemView.findViewById(R.id.productPrice);
            image = itemView.findViewById(R.id.productImage);
            addToCart = itemView.findViewById(R.id.addToCartButton);
        }
    }
}
