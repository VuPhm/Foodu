package com.foodu.features.authentication.data;

// com.foodu.features.authentication.data.UserModel
public class UserModel {
    private String uid;
    private String email;
    private String name;
    private String avatarUrl;

    public UserModel() {}  // Required for Firestore

    public UserModel(String uid, String email, String name, String avatarUrl) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.avatarUrl = avatarUrl;
    }

    // Getters & setters
    public String getUid() { return uid; }
    public String getEmail() { return email; }
    public String getName() { return name; }
    public String getAvatarUrl() { return avatarUrl; }
}
