package com.foodu.features.authentication.data;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.Task;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AuthRepository {
    private final FirebaseAuth auth;

    @Inject
    public AuthRepository(FirebaseAuth auth) {
        this.auth = auth;
    }

    /**
     * Sign in with email & password.
     */
    public Task<AuthResult> login(String email, String password) {
        return auth.signInWithEmailAndPassword(email, password);
    }

    /** Register with email & password */
    public Task<AuthResult> register(String email, String password) {
        return auth.createUserWithEmailAndPassword(email, password);
    }

    /**
     * Get current signed-in user (or null).
     */
    public FirebaseUser getCurrentUser() {
        return auth.getCurrentUser();
    }

    /**
     * Sign out current user.
     */
    public void signOut() {
        auth.signOut();
    }
}
