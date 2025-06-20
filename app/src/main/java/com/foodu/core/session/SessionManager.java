package com.foodu.core.session;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.foodu.features.authentication.data.AuthRepository;

/**
 * Handles user session and login checks.
 */
@Singleton
public class SessionManager {
    private final AuthRepository authRepository;

    @Inject
    public SessionManager(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    /**
     * Returns true if the user is logged in.
     */
    public boolean isLoggedIn() {
        return authRepository.isLoggedIn();
    }

    /**
     * Returns user email if logged in, null otherwise.
     */
    public String getUserEmail() {
        return authRepository.getCurrentUserEmail();
    }

    /**
     * Logout.
     */
    public void logout() {
        authRepository.logout();
    }

}
