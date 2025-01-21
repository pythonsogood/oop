package org.pythonsogood.Models;

import java.util.ArrayList;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import at.favre.lib.crypto.bcrypt.BCrypt;

@Document("users")
public class User {
	static private final BCrypt.Hasher bCrypt = BCrypt.withDefaults();
	static private final BCrypt.Verifyer bCryptVerifier = BCrypt.verifyer();

	@Id
	private ObjectId id;
	private String username;
	private String password_hash;
	private String email;
	private ArrayList<String> cars;

	public User(String username, String password_hash, String email) {
		this.username = username;
        this.email = email;
		this.password_hash = password_hash;
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

	private void setPasswordHash(String password_hash) {
		this.password_hash = password_hash;
	}

	public void setPassword(String password) {
		this.setPasswordHash(User.hashPassword(password));
	}

	static public String hashPassword(String password) {
		String password_hash = User.bCrypt.hashToString(12, password.toCharArray());
		return password_hash;
	}

	public BCrypt.Result verifyPassword(String password) {
		return User.bCryptVerifier.verify(password.toCharArray(), this.password_hash);
	}
}
