package org.pythonsogood.model;

import java.util.ArrayList;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "users", indexes = {
	@Index(columnList = "id, username, email", unique = true)
})
public class User {
	static private final BCrypt.Hasher bCrypt = BCrypt.withDefaults();
	static private final BCrypt.Verifyer bCryptVerifier = BCrypt.verifyer();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String username;
	private String password_hash;
	private String email;

	public User(String username, String password_hash, String email) {
		this.setUsername(username);
		this.setPassword_hash(password_hash);
		this.setEmail(email);
	}

	public void setPassword(String password) {
		this.setPassword_hash(User.hashPassword(password));
	}

	static public String hashPassword(String password) {
		System.out.println(String.format("PASS: %s", password));
		return User.bCrypt.hashToString(12, password.toCharArray());
	}

	public BCrypt.Result verifyPassword(String password) {
		System.out.println(String.format("CHECK: %s", password));
		return User.bCryptVerifier.verify(password.toCharArray(), this.password_hash);
	}
}
