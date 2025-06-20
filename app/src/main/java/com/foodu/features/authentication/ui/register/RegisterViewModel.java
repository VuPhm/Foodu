package com.foodu.features.authentication.ui.register;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.foodu.features.authentication.data.AuthRepository;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class RegisterViewModel extends ViewModel {

    private final AuthRepository authRepository;

    @Inject
    public RegisterViewModel(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public LiveData<FirebaseUser> register(String email, String password) {
        return authRepository.register(email, password);
    }
}
