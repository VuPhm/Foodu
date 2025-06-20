package com.foodu.features.home.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.foodu.R;
import com.foodu.core.session.SessionManager;
import com.foodu.core.utils.LoginUtils;
import com.foodu.features.authentication.data.AuthRepository;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * HomeFragment - hiển thị trang chủ với danh sách sản phẩm, điều hướng đến giỏ hàng và lịch sử đơn hàng.
 */
@AndroidEntryPoint
public class HomeFragment extends Fragment {
    @Inject
    AuthRepository authRepository;
    @Inject
    SessionManager sessionManager;

    private HomeViewModel viewModel;
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private Button cartButton, orderHistoryButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        ImageView imgAvatar = view.findViewById(R.id.imgAvatar);

        imgAvatar.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            LoginUtils.requireLoginOr(
                    sessionManager,
                    navController,
                    () -> navController.navigate(R.id.action_homeFragment_to_profileFragment),
                    R.id.action_homeFragment_to_loginFragment
            );
        });


        // Khởi tạo RecyclerView và Adapter
        recyclerView = view.findViewById(R.id.rvPopular);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        productAdapter = new ProductAdapter(new ArrayList<>(), requireContext());
        recyclerView.setAdapter(productAdapter);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        );

        // Khởi tạo các nút
        cartButton = view.findViewById(R.id.cartButton);
        orderHistoryButton = view.findViewById(R.id.orderHistoryButton);

        // Điều hướng đến CartFragment khi nhấn nút giỏ hàng
        cartButton.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            LoginUtils.requireLoginOr(
                    sessionManager,
                    navController,
                    () -> navController.navigate(R.id.action_homeFragment_to_cartFragment),
                    R.id.action_homeFragment_to_loginFragment
            );
        });

        // Điều hướng đến OrderHistoryFragment khi nhấn nút lịch sử đơn hàng
        orderHistoryButton.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            LoginUtils.requireLoginOr(
                    sessionManager,
                    navController,
                    () -> navController.navigate(R.id.action_homeFragment_to_orderHistoryFragment),
                    R.id.action_homeFragment_to_loginFragment
            );

        });

        // Quan sát LiveData và cập nhật adapter
        viewModel.getProducts().observe(getViewLifecycleOwner(), products -> {
            productAdapter.setData(products);
        });
    }
}
