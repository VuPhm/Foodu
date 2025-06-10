package com.foodu.features.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.foodu.R;
import com.foodu.features.authentication.data.AuthRepository;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeFragment extends Fragment {
    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate layout with a simple welcome message
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tvWelcome = view.findViewById(R.id.tvWelcome);

        // Get current Firebase user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Display welcome message with user's email
        if (user != null && user.getEmail() != null) {
            tvWelcome.setText("Welcome, " + user.getEmail() + "!");
        } else {
            tvWelcome.setText("Welcome to Foodu!");
        }

        MaterialButton btnLogout = view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> {
            AuthRepository repo = new AuthRepository(FirebaseAuth.getInstance());
            repo.signOut();
            NavHostFragment.findNavController(this)
                    .navigate(R.id.loginFragment);
        });

    }
}
