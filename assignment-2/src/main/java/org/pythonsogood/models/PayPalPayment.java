package org.pythonsogood.models;

public class PayPalPayment {
	private String email;

	public PayPalPayment(String email) {
        this.email = email;
    }

	public String getEmail() {
		return email;
	}

	public void processPayment(double amount) {
		System.out.println(String.format("Processing PayPal payment for %f tenge", amount));
	}
}
