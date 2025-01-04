package org.pythonsogood.models;

import org.pythonsogood.interfaces.Payment;

public class CreditCardPayment implements Payment {
	private String cardNumber;
	private String cardHolderName;

	public CreditCardPayment(String cardNumber, String cardHolderName) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
    }

	public String getCardNumber() {
        return cardNumber;
    }

	public String getCardHolderName() {
        return cardHolderName;
    }

	public void processPayment(double amount) {
		System.out.println(String.format("Processing credit card payment for %d ₸", amount));
	}
}
