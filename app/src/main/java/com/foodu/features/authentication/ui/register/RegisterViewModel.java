package com.foodu.features.authentication.ui.register;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.foodu.features.authentication.data.AuthRepository;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class RegisterViewModel extends ViewModel {
    private final AuthRepository repo;
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final MutableLiveData<String> errorMsg = new MutableLiveData<>();
    private final MutableLiveData<FirebaseUser> user = new MutableLiveData<>();

    @Inject
    public RegisterViewModel(AuthRepository repo) {
        this.repo = repo;
    }

    public LiveData<Boolean> getLoading() { return isLoading; }
    public LiveData<String> getErrorMessage() { return errorMsg; }
    public LiveData<FirebaseUser> getUser() { return user; }

    /** Register user with email and password */
    public void register(String email, String password) {
        isLoading.setValue(true);
        repo.register(email, password)
                .addOnCompleteListener(task -> {
                    isLoading.setValue(false);
                    if (task.isSuccessful()) {
                        user.setValue(repo.getCurrentUser());
                    } else {
                        errorMsg.setValue(task.getException() != null ?
                                task.getException().getMessage() : "Unknown error");
                    }
                });
    }
}
