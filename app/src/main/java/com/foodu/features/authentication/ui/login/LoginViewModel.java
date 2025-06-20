// LoginViewModel.java
package com.foodu.features.authentication.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.foodu.features.authentication.data.AuthRepository;
import com.foodu.features.authentication.data.AuthResult;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LoginViewModel extends ViewModel {
    private final AuthRepository authRepository;
    private final MutableLiveData<AuthResult> loginResult = new MutableLiveData<>();

    @Inject
    public LoginViewModel(AuthRepository repo) {
        this.authRepository = repo;
    }

    public LiveData<AuthResult> getLoginResult() { return loginResult; }

    public void login(String email, String password) {
        authRepository.login(email, password).observeForever(result -> loginResult.setValue(result));
    }
}
