package org.pythonsogood.assignment1;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import org.pythonsogood.assignment1.models.Book;

public class Assignment1 {

    public static void main(String[] args) {
		Random random = new Random();

        ArrayList<Book> books = new ArrayList<>();

		books.add(new Book(1, "Abay Joly", "M. Auezov", random.nextBoolean()));
		books.add(new Book(2, "Koshpendyler", "I. Esenberlin", random.nextBoolean()));
		books.add(new Book(3, "Qara Sozder", "A. Kunanbaev", random.nextBoolean()));
		books.add(new Book(4, "Qan men Ter", "A. Nurpeisov", random.nextBoolean()));

		boolean isAnyBookAvailable = false;
		for (Book book : books) {
			if (book.isAvailable()) {
				isAnyBookAvailable = true;
				break;
			}
		}

		if (!isAnyBookAvailable) {
			books.get(random.nextInt(0, books.size())).setAvailable(true);
		}

		for (int i=0; i<books.size(); i++) {
			Book book = books.get(i);
			System.out.print(String.format("[%d] ", i));
			book.displayBookDetails();
		}

		Scanner scanner = new Scanner(System.in);
		System.out.print("Choose book by index: ");
		int index = scanner.nextInt();
		while (index < 0 || index >= books.size()) {
			System.out.print("Index is out of range!\nChoose book by index: ");
			index = scanner.nextInt();
		}
		scanner.close();

		Book my_book = books.get(index);
		try {
			my_book.borrowBook();
			System.out.println(String.format("%s availability now is: %s", my_book.getTitle(), my_book.isAvailable()));
			my_book.returnBook();
		} catch (IllegalStateException e) {
			System.out.println(e.getMessage());
		}

		System.out.println("Available books:");
		for (Book book : books) {
			if (book.isAvailable()) {
				book.displayBookDetails();
			}
		}
    }
}