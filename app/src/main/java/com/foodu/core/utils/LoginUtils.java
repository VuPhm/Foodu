package com.foodu.core.utils;

import androidx.navigation.NavController;

import com.foodu.core.session.SessionManager;

/**
 * Utility to require login for protected actions.
 */
public class LoginUtils {
    /**
     * Check login status and handle navigation.
     *
     * @param sessionManager SessionManager instance (injected vào Fragment/Activity)
     * @param navController NavController để điều hướng
     * @param actionIfLoggedIn Code sẽ chạy nếu đã đăng nhập (lambda)
     * @param loginDirectionId ID điều hướng đến login (ví dụ: R.id.action_xxx_to_loginFragment)
     */
    public static void requireLoginOr(
            SessionManager sessionManager,
            NavController navController,
            Runnable actionIfLoggedIn,
            int loginDirectionId
    ) {
        if (sessionManager.isLoggedIn()) {
            actionIfLoggedIn.run();
        } else {
            navController.navigate(loginDirectionId);
        }
    }
}
