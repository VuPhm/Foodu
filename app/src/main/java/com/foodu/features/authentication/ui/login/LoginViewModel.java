package com.foodu.features.authentication.ui.login;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.foodu.features.authentication.data.LoginRepository;
import com.google.firebase.auth.FirebaseUser;

import dagger.hilt.android.lifecycle.HiltViewModel;

import javax.inject.Inject;

@HiltViewModel
public class LoginViewModel extends ViewModel {
    private final LoginRepository repo;
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final MutableLiveData<String> errorMsg = new MutableLiveData<>();
    private final MutableLiveData<FirebaseUser> user = new MutableLiveData<>();

    @Inject
    public LoginViewModel(LoginRepository repo) {
        this.repo = repo;
    }

    public LiveData<Boolean> getLoading() {
        return isLoading;
    }

    public LiveData<String> getErrorMessage() {
        return errorMsg;
    }

    public LiveData<FirebaseUser> getUser() {
        return user;
    }

    /**
     * Thực hiện login, cập nhật trạng thái loading/error/user.
     */
    public void login(String email, String password) {
        isLoading.setValue(true);
        repo.login(email, password)
                .addOnCompleteListener(task -> {
                    isLoading.setValue(false);
                    if (task.isSuccessful()) {
                        user.setValue(repo.getCurrentUser());
                    } else {
                        errorMsg.setValue(task.getException() != null
                                ? task.getException().getMessage()
                                : "Unknown error");
                    }
                });
    }
}
