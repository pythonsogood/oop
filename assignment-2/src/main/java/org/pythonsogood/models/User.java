package org.pythonsogood.models;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class User {
	static AtomicInteger userIdGenerator = new AtomicInteger(0);

	private int userId;
	private String name;
	private String email;

	public User(int userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

	public User(String name, String email) {
		this(User.userIdGenerator.incrementAndGet(), name, email);
	}

	public int getUserId() {
        return userId;
    }

	public String getName() {
        return name;
    }

	public String getEmail() {
        return email;
    }

	public void displayDetails() {}
}
