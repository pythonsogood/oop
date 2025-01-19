package org.pythonsogood.Models;

import java.util.ArrayList;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("users")
public class User {
	@Id
	private ObjectId id;
	private String username;
	private String password_hash;
	private String email;
	private ArrayList<String> cars;

	public User(String username, String password_hash, String email) {
		this.username = username;
        this.password_hash = password_hash;
        this.email = email;
        this.cars = new ArrayList<>();
	}

	public ObjectId getId() {
        return id;
    }

	public String getUsername() {
        return username;
    }

	public String getEmail() {
		return email;
	}

	public String getPasswordHash() {
		return password_hash;
	}
}
