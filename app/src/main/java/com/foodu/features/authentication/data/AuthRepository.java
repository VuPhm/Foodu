// AuthRepository.java
package com.foodu.features.authentication.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.foodu.features.authentication.data.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AuthRepository {
    private final FirebaseAuth firebaseAuth;

    @Inject
    public AuthRepository(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    public LiveData<AuthResult> login(String email, String password) {
        MutableLiveData<AuthResult> result = new MutableLiveData<>();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> result.setValue(AuthResult.success()))
                .addOnFailureListener(e -> result.setValue(AuthResult.error(e.getMessage())));
        return result;
    }

    public boolean isLoggedIn() {
        return firebaseAuth.getCurrentUser() != null;
    }

    public LiveData<FirebaseUser> register(String email, String password) {
        MutableLiveData<FirebaseUser> result = new MutableLiveData<>();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> result.setValue(firebaseAuth.getCurrentUser()))
                .addOnFailureListener(e -> result.setValue(null));
        return result;
    }
    /**
     * Get the email of the current user.
     * @return email or null if not logged in.
     */
    public String getCurrentUserEmail() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        return user != null ? user.getEmail() : null;
    }

    /**
     * Log out the current user.
     */
    public void logout() {
        firebaseAuth.signOut();
    }

}
