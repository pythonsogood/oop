package org.pythonsogood.model;

import java.util.ArrayList;
import java.util.UUID;

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
	private UUID id;
	private String username;
	private String password_hash;
	private String email;
	private ArrayList<UUID> cart = new ArrayList<>();

	public User(String username, String password_hash, String email) {
		this.setId(UUID.randomUUID());
		this.setUsername(username);
		this.setPassword_hash(password_hash);
		this.setEmail(email);
	}

	public void addToCart(UUID product_id) {
		this.cart.add(product_id);
	}

	public void removeFromCart(UUID product_id) {
		if (!this.cart.contains(product_id)) {
			return;
		}
		this.cart.remove(product_id);
	}

	public boolean isInCart(UUID product_id) {
		return this.cart.contains(product_id);
	}

	public void setPassword(String password) {
		this.setPassword_hash(User.hashPassword(password));
	}

	static public String hashPassword(String password) {
		return User.bCrypt.hashToString(12, password.toCharArray());
	}

	public BCrypt.Result verifyPassword(String password) {
		return User.bCryptVerifier.verify(password.toCharArray(), this.password_hash);
	}
}
