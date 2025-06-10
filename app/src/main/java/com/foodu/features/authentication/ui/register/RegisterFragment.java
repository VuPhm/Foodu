// features/authentication/ui/register/RegisterFragment.java
package com.foodu.features.authentication.ui.register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.foodu.R;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.button.MaterialButton;
import android.widget.ProgressBar;
import androidx.navigation.fragment.NavHostFragment;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RegisterFragment extends Fragment {
    private RegisterViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        TextInputEditText etEmail = view.findViewById(R.id.etEmail);
        TextInputEditText etPassword = view.findViewById(R.id.etPassword);
        MaterialButton btnRegister = view.findViewById(R.id.btnRegister);
        MaterialButton btnGoLogin = view.findViewById(R.id.btnGoLogin);
        ProgressBar progress = view.findViewById(R.id.progressBar);

        btnRegister.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String pass  = etPassword.getText().toString().trim();
            viewModel.register(email, pass);
        });

        btnGoLogin.setOnClickListener(v ->
                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_register_to_login)
        );

        viewModel.getLoading().observe(getViewLifecycleOwner(),
                loading -> progress.setVisibility(loading ? View.VISIBLE : View.GONE)
        );

        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), msg -> {
            if (msg != null) Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        });

        viewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                NavHostFragment.findNavController(this)
                        .navigate(R.id.action_register_to_home);
            }
        });
    }
}
