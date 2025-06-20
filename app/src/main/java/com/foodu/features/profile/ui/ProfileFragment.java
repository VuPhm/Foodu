package com.foodu.features.profile.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import com.foodu.R;
import com.foodu.features.authentication.data.AuthRepository;
import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProfileFragment extends Fragment {

    @Inject
    AuthRepository authRepository;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvEmail = view.findViewById(R.id.tvEmail);
        Button btnLogout = view.findViewById(R.id.btnLogout);

        // Hiển thị email người dùng nếu đã đăng nhập
        String email = authRepository.getCurrentUserEmail();
        tvEmail.setText(email != null ? email : "Không có thông tin tài khoản");

        btnLogout.setOnClickListener(v -> {
            authRepository.logout();
            // Quay về Home sau khi đăng xuất
            NavController navController = NavHostFragment.findNavController(this);
            navController.popBackStack(R.id.homeFragment, false);
        });
    }
}
