package com.example.saramin.util;

import com.example.saramin.entity.dto.Authentication.RegisterForm;

import java.util.regex.Pattern;

public class FormValidator {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private static final String PHONENUMBER_REGEX = "^010-\\d{4}-\\d{4}$";

    public static boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches(PHONENUMBER_REGEX);
    }

    public static boolean isAnyFieldNull(RegisterForm registerForm) {
        return registerForm.getEmail() == null || registerForm.getPassword() == null || registerForm.getUsername() == null || registerForm.getPhoneNumber() == null;
    }
}
