package org.pythonsogood.models;

import java.util.List;

public class Admin extends User {
	private List<Product> products;

	public Admin(int userId, String name, String email) {
        super(userId, name, email);
    }

	public Admin(String name, String email) {
        super(name, email);
    }

	public List<Product> getProducts() {
		return this.products;
	}

	public void addProduct(Product product) {
		this.products.add(product);
	}

	public void removeProduct(String productId) {
        this.products.removeIf((Product product) -> (product.getProductId() == productId));
    }

	public void removeProduct(Product product) {
        this.removeProduct(product.getProductId());
    }

	public void updateStock(String productId, int newStock) {
		for (Product product : this.products) {
			if (product.getProductId() == productId) {
				product.setStock(newStock);
			}
		}
	}

	public void updateStock(Product product, int newStock) {
		this.updateStock(product.getProductId(), newStock);
	}

	public void displayDetails() {
		System.out.println(String.format("ID: %d | NAME: %s | EMAIL: %s", this.getUserId(), this.getName(), this.getEmail()));
		System.out.println("Products:");
		if (this.getProducts().isEmpty()) {
			System.out.println("No products placed yet.");
		} else {
			for (Product product : this.getProducts()) {
				product.displayDetails();
			}
		}
	}
}
