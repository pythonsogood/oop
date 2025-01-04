package org.pythonsogood;

import java.util.ArrayList;
import java.util.Random;

import org.pythonsogood.models.Admin;
import org.pythonsogood.models.Customer;
import org.pythonsogood.models.Product;

public class Main {
	public static ArrayList<Admin> admins = new ArrayList<Admin>();
	public static ArrayList<Customer> customers = new ArrayList<Customer>();

    public static void main(String[] args) {
		Random random = new Random();

		Admin admin = new Admin("admin", "admin@pythonsoogood.org");
		Admin pythonsogood = new Admin("pythonsogood", "pythonsogood@pythonsoogood.org");

		admin.addProduct(new Product("Apple", 1.5, random.nextInt(50, 100)));
		admin.addProduct(new Product("Potato", 2.15, random.nextInt(50, 100)));

        Main.admins.add(admin);
        Main.admins.add(pythonsogood);

		Main.customers.add(new Customer("Abay", "abay@mail.kz", "010000"));
		Main.customers.add(new Customer("Eskendir", "eskendir@mail.kz", "010010"));
		Main.customers.add(new Customer("Vasiliy", "vasiliy@mail.ru", "050000"));
		Main.customers.add(new Customer("Peter", "peter@mail.ru", "050002"));
		Main.customers.add(new Customer("John", "john@gmail.com", "050004"));
    }

	public static ArrayList<Product> getProducts() {
		ArrayList<Product> products = new ArrayList<Product>();

		for (Admin admin : Main.admins) {
			products.addAll(admin.getProducts());
		}

		return products;
	}
}