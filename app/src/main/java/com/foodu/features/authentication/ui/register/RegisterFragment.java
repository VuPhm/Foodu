// com.foodu.features.authentication.ui.register.RegisterFragment.java
package com.foodu.features.authentication.ui.register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.foodu.R;
import com.google.firebase.auth.FirebaseUser;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RegisterFragment extends Fragment {

    private RegisterViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        EditText edtEmail = view.findViewById(R.id.etEmail);
        EditText edtPassword = view.findViewById(R.id.etPassword);
        Button btnRegister = view.findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> {
            String email = edtEmail.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            viewModel.register(email, password).observe(getViewLifecycleOwner(), firebaseUser -> {
                if (firebaseUser != null) {
                    // Đăng ký thành công → chuyển sang home hoặc profile
                    NavController navController = NavHostFragment.findNavController(this);
                    navController.navigate(R.id.action_registerFragment_to_homeFragment);
                } else {
                    Toast.makeText(requireContext(), "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                }
            });
        });

        TextView tvLogin = view.findViewById(R.id.tvLogin);
        tvLogin.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_registerFragment_to_loginFragment);
        });

    }
}
