package org.pythonsogood.models;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User {
	private List<Order> orders = new ArrayList<>();
	private String shippingAddress;

	public Customer(int userId, String name, String email, String shippingAddress) {
        super(userId, name, email);
        this.shippingAddress = shippingAddress;
    }

	public Customer(String name, String email, String shippingAddress) {
        super(name, email);
        this.shippingAddress = shippingAddress;
    }

	public List<Order> getOrders() {
        return orders;
    }

	public String getShippingAddress() {
        return shippingAddress;
    }

	public void placeOrder(Order order) {
		this.orders.add(order);
	}

	public void cancelOrder(Order order) {
		order.cancelOrder();
		this.orders.remove(order);
	}

	public void displayDetails() {
		System.out.println(String.format("CUSTOMER ID: %d | NAME: %s | EMAIL: %s | SHIPPING ADDRESS: %s", this.getUserId(), this.getName(), this.getEmail(), this.getShippingAddress()));
		System.out.println("Orders:");
		if (this.getOrders().isEmpty()) {
			System.out.println("No orders placed yet.");
		} else {
			for (Order order : this.getOrders()) {
				order.displayOrderDetails();
			}
		}
	}
}
