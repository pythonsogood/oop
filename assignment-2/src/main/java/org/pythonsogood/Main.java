package org.pythonsogood;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.pythonsogood.models.Admin;
import org.pythonsogood.models.CreditCardPayment;
import org.pythonsogood.models.Customer;
import org.pythonsogood.models.Order;
import org.pythonsogood.models.PayPalPayment;
import org.pythonsogood.models.Product;

public class Main {
	public static ArrayList<Admin> admins = new ArrayList<>();
	public static ArrayList<Customer> customers = new ArrayList<>();
	public static HashMap<Integer, CreditCardPayment> payments_credit_cards = new HashMap<>();
	public static HashMap<Integer, PayPalPayment> payments_paypal = new HashMap<>();
	private static Random random = new Random();

    public static void main(String[] args) {

		Admin admin = new Admin("admin", "admin@pythonsoogood.org");
		Admin pythonsogood = new Admin("pythonsogood", "pythonsogood@pythonsoogood.org");

		Product apple = new Product("Apple", 250, Main.random.nextInt(50, 100));
		Product potato = new Product("Potato", 350, Main.random.nextInt(50, 100));
		Product book = new Product("Book", 500, Main.random.nextInt(15, 25));
		Product chair = new Product("Chair", 5000, Main.random.nextInt(30, 50));
		Product pen = new Product("Pen", 100, Main.random.nextInt(30, 50));
		Product pencil = new Product("Pencil", 150, Main.random.nextInt(30, 50));
		Product headphones = new Product("Headphones", 1500, Main.random.nextInt(25, 50));
		Product speaker = new Product("Speaker", 900, Main.random.nextInt(25, 50));
		Product microphone = new Product("Microphone", 1300, Main.random.nextInt(25, 50));
		Product phonecase = new Product("Phone case", 800, Main.random.nextInt(15, 30));

		Product table = new Product("Table", 5000, Main.random.nextInt(15, 25));

		admin.addProduct(apple);
		admin.addProduct(potato);
		admin.addProduct(book);
		admin.addProduct(chair);
		admin.addProduct(pen);
		admin.addProduct(pencil);
		pythonsogood.addProduct(headphones);
		pythonsogood.addProduct(speaker);
		pythonsogood.addProduct(microphone);
		pythonsogood.addProduct(phonecase);

        Main.admins.add(admin);
        Main.admins.add(pythonsogood);

		Main.customers.add(new Customer("Abay", "abay@mail.kz", "010000"));
		Main.customers.add(new Customer("Eskendir", "eskendir@mail.kz", "010010"));
		Main.customers.add(new Customer("Vasiliy", "vasiliy@mail.ru", "050000"));
		Main.customers.add(new Customer("Peter", "peter@mail.ru", "050002"));
		Main.customers.add(new Customer("John", "john@gmail.com", "050004"));

		ArrayList<Product> products = Main.getProducts();

		for (Customer customer : Main.customers) {
			for (int i=0; i<Main.random.nextInt(8); i++) {
				int productId = Main.random.nextInt(products.size());
				Product product = products.get(productId);

				int quantity = Main.random.nextInt(1, 5);

				try {
					Order order = new Order(customer, product, quantity);

					if (Main.random.nextBoolean()) {
						PayPalPayment payment = Main.getCustomerPayPal(customer);
						payment.processPayment(order.getTotalPrice());
					} else {
						CreditCardPayment payment = Main.getCustomerCreditCard(customer);
                        payment.processPayment(order.getTotalPrice());
					}

					order.completeOrder();
				} catch (Exception e) {
					System.out.println(String.format("Error creating order for customer %s: %s", customer.getName(), e.getMessage()));
				}
			}
		}

		admin.addProduct(table);
		admin.updateStock(pen.getProductId(), 0);
		admin.removeProduct(pencil);

		Customer random_customer = Main.customers.get(Main.random.nextInt(Main.customers.size()));
		try {
			Order order = new Order(random_customer, pen, 1);
		} catch (Exception e) {
			System.out.println(String.format("Error creating order for customer %s: %s", random_customer.getName(), e.getMessage()));
		}

		System.out.println("Customer list:");

		for (Customer customer : Main.customers) {
			customer.displayDetails();
		}

		System.out.println("Admin list:");

		for (Admin _admin : Main.admins) {
			_admin.displayDetails();
		}

		System.out.println("Product list:");

		for (Product product : Main.getProducts()) {
			product.displayDetails();
		}

		System.out.println("Order list:");

		for (Customer customer : Main.customers) {
			for (Order order : customer.getOrders()) {
				order.displayOrderDetails();
			}
		}
    }

	public static ArrayList<Product> getProducts() {
		ArrayList<Product> products = new ArrayList<Product>();

		for (Admin admin : Main.admins) {
			products.addAll(admin.getProducts());
		}

		return products;
	}

	public static PayPalPayment getCustomerPayPal(Customer customer) {
		if (Main.payments_paypal.containsKey(customer.getUserId())) {
			return Main.payments_paypal.get(customer.getUserId());
		}

		PayPalPayment payPal = new PayPalPayment(customer.getEmail());
		Main.payments_paypal.put(customer.getUserId(), payPal);

		return payPal;
	}

	public static CreditCardPayment getCustomerCreditCard(Customer customer) {
		if (Main.payments_credit_cards.containsKey(customer.getUserId())) {
            return Main.payments_credit_cards.get(customer.getUserId());
        }

        CreditCardPayment creditCard = new CreditCardPayment(String.format("%d %d %d %d", Main.random.nextInt(1000, 9999), Main.random.nextInt(1000, 9999), Main.random.nextInt(1000, 9999), Main.random.nextInt(1000, 9999)), customer.getName());
        Main.payments_credit_cards.put(customer.getUserId(), creditCard);

        return creditCard;
	}
}