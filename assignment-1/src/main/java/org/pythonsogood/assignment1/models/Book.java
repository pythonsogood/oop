package org.pythonsogood.assignment1.models;

public class Book {
	private int bookId;
	private String title;
	private String author;
	private boolean isAvailable = true;

	public Book() {}

	public Book(int id, String title, String author) {
		this.bookId = id;
		this.title = title;
		this.author = author;
	}

	public Book(int id, String title, String author, boolean isAvailable) {
		this.bookId = id;
		this.title = title;
		this.author = author;
		this.isAvailable = isAvailable;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public void displayBookDetails() {
		System.out.println(String.format("%d | %s | %s | %s", bookId, title, author, isAvailable ? "available" : "not available"));
	}

	public void borrowBook() {
		if (!isAvailable) {
			throw new IllegalStateException("Book is not available now!");
		}
		this.setAvailable(false);
	}

	public void returnBook() {
		if (isAvailable) {
			throw new IllegalStateException("Book is not borrowed!");
		}
		this.setAvailable(true);
	}
}
