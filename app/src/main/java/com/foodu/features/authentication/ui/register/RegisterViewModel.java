package com.foodu.features.authentication.ui.register;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.foodu.features.authentication.data.AuthRepository;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.foodu.features.authentication.data.UserModel;


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
                        FirebaseUser newUser = repo.getCurrentUser();
                        user.setValue(newUser);

                        if (newUser != null) {
                            // ✅ Tạo thông tin người dùng để lưu vào Firestore
                            UserModel newUserModel = new UserModel(
                                    newUser.getUid(),
                                    newUser.getEmail(),
                                    "", // Name rỗng
                                    ""  // Avatar URL rỗng
                            );

                            FirebaseFirestore.getInstance()
                                    .collection("users")
                                    .document(newUser.getUid())
                                    .set(newUserModel)
                                    .addOnSuccessListener(unused -> {
                                        // Thành công rồi, không cần làm gì thêm ở đây
                                    })
                                    .addOnFailureListener(e -> {
                                        errorMsg.setValue("Failed to save user profile: " + e.getMessage());
                                    });
                        }
                    } else {
                        errorMsg.setValue(task.getException() != null ?
                                task.getException().getMessage() : "Unknown error");
                    }
                });
    }

}
