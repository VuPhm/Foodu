package com.foodu.features.authentication.data;

public class AuthResult {
    public final boolean success;
    public final String message;

    private AuthResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public static AuthResult success() {
        return new AuthResult(true, null);
    }

    public static AuthResult error(String msg) {
        return new AuthResult(false, msg);
    }
}
