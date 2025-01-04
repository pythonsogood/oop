package org.pythonsogood.models;

import java.util.UUID;

public class Product {
	private String productId;
	private String name;
	private double price;
	private int stock;

	public Product(String productId, String name, double price, int stock) {
		this.productId = productId;
        this.name = name;
        this.price = price;
        this.stock = stock;
	}

	public Product(String name, double price, int stock) {
		this(UUID.randomUUID().toString(), name, price, stock);
	}

	public String getProductId() {
        return productId;
    }

	public String getName() {
        return name;
    }

	public double getPrice() {
        return price;
    }

	public int getStock() {
        return stock;
    }

	public void setStock(int stock) {
        this.stock = stock;
    }

	public void reduceStock(int quantity) {
		if (stock < quantity) {
			throw new ArithmeticException("Not enough stock to reduce");
        }
		stock -= quantity;
	}

	public void reduceStock() {
		reduceStock(1);
	}

	public void increaseStock(int quantity) {
        stock += quantity;
    }

	public void increaseStock() {
        increaseStock(1);
    }

	public void displayDetails() {
		System.out.println(String.format("PRODUCT ID: %s | NAME: %s | PRICE: %f | STOCK: %d", this.getProductId(), this.getName(), this.getPrice(), this.getStock()));
	}
}
