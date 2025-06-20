package com.foodu.features.authentication.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.foodu.R;
import com.foodu.features.authentication.ui.login.LoginViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginFragment extends Fragment {
    private LoginViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText etEmail = view.findViewById(R.id.etEmail);
        EditText etPassword = view.findViewById(R.id.etPassword);
        Button btnLogin = view.findViewById(R.id.btnLogin);
        ProgressBar progressBar = view.findViewById(R.id.progressBar);
        TextView tvError = view.findViewById(R.id.tvError);

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòsng nhập đủ email và mật khẩu", Toast.LENGTH_SHORT).show();
                return;
            }
            viewModel.login(email, password);
        });

        viewModel.getLoginResult().observe(getViewLifecycleOwner(), result -> {
            progressBar.setVisibility(View.GONE);
            if (result.success) {
                // Chuyển sang Home, vd NavController
                NavController navController = NavHostFragment.findNavController(this);
                navController.navigate(R.id.action_loginFragment_to_homeFragment);
            } else {
                tvError.setVisibility(View.VISIBLE);
                tvError.setText(result.message);
            }
        });
        TextView tvRegister = view.findViewById(R.id.tvRegister);
        tvRegister.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_loginFragment_to_registerFragment);
        });

    }
}
