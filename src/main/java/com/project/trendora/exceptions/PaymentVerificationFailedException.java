package com.project.trendora.exceptions;

public class PaymentVerificationFailedException extends RuntimeException {
    public PaymentVerificationFailedException(String message) {
        super(message);
    }
}
