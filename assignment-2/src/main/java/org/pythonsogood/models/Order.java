package org.pythonsogood.models;

import java.util.UUID;

import org.pythonsogood.enums.OrderStatus;

public class Order {
	private String orderId;
	private Customer customer;
	private Product product;
	private int quantity = 1;
	private double totalPrice;
	private OrderStatus status = OrderStatus.Placed;

	public Order(String orderId, Customer customer, Product product, int quantity, double totalPrice, OrderStatus status) {
		this.orderId = orderId;
        this.customer = customer;
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.status = status;
		this.placeOrder();
	}

	public Order(Customer customer, Product product, int quantity, double totalPrice, OrderStatus status) {
        this(UUID.randomUUID().toString(), customer, product, quantity, totalPrice, status);
    }

	public Order(String orderId, Customer customer, Product product, int quantity, double totalPrice) {
        this(orderId, customer, product, quantity, totalPrice, OrderStatus.Placed);
    }

	public Order(Customer customer, Product product, int quantity, double totalPrice) {
        this(UUID.randomUUID().toString(), customer, product, quantity, totalPrice);
    }

	public Order(String orderId, Customer customer, Product product, int quantity) {
        this(orderId, customer, product, quantity, product.getPrice() * quantity, OrderStatus.Placed);
    }

	public Order(Customer customer, Product product, int quantity) {
        this(UUID.randomUUID().toString(), customer, product, quantity);
    }

	public String getOrderId() {
        return orderId;
    }

	public Customer getCustomer() {
        return customer;
    }

	public Product getProduct() {
        return product;
    }

	public int getQuantity() {
        return quantity;
    }

	public double getTotalPrice() {
        return totalPrice;
    }

	public OrderStatus getStatus() {
        return status;
    }

	private void placeOrder() {
		this.getProduct().reduceStock();
		this.status = OrderStatus.Placed;
		this.customer.placeOrder(this);
	}

	public void cancelOrder() {
		this.getProduct().increaseStock();
		this.status = OrderStatus.Cancelled;
		this.customer.cancelOrder(this);
	}

	public void completeOrder() {
		this.status = OrderStatus.Completed;
	}

	public void displayOrderDetails() {
		System.out.println(String.format("ORDER ID: %s | PRODUCT: %s | QUANTITY: %d | TOTAL PRICE: %f | STATUS: %s", this.getOrderId(), this.getProduct().getName(), this.getQuantity(), this.getTotalPrice(), this.getStatus()));
	}
}
