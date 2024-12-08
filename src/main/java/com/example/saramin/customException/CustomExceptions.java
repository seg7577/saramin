package com.example.saramin.customException;

public class CustomExceptions {
    public static class BadRequestException extends CustomException {
        public BadRequestException(String message) {
            super(message, 400);
        }
    }

    public static class UnauthorizedException extends CustomException {
        public UnauthorizedException(String message) {
            super(message, 401);
        }
    }
}