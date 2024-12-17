package org.pythonsogood.assignment1;

import java.util.ArrayList;
import java.util.Scanner;

import org.pythonsogood.assignment1.models.Book;

public class Assignment1 {

    public static void main(String[] args) {
        ArrayList<Book> books = new ArrayList<>();

		books.add(new Book(1, "The Great Gatsby", "F. Scott Fitzgerald", false));
		books.add(new Book(2, "To Kill a Mockingbird", "Harper Lee"));
		books.add(new Book(3, "1984", "George Orwell"));
		books.add(new Book(4, "Pride and Prejudice", "Jane Austen", false));

		for (int i=0; i<books.size(); i++) {
			Book book = books.get(i);
			System.out.print(String.format("[%d] ", i));
			book.displayBookDetails();
		}

		Scanner scanner = new Scanner(System.in);
		System.out.println("Choose book by index: ");
		int index = scanner.nextInt();
		while (index < 0 || index >= books.size()) {
			System.out.println("Index is out of range!\nChoose book by index: ");
			index = scanner.nextInt();
		}
		scanner.close();

		Book my_book = books.get(index);
		my_book.borrowBook();
		System.out.println(String.format("Borrowed book availability: %s", my_book.isAvailable()));
		my_book.returnBook();

		for (Book book : books) {
			if (book.isAvailable()) {
				book.displayBookDetails();
			}
		}
    }
}