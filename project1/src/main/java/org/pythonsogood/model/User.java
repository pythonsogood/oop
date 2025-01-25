package org.pythonsogood.model;

import java.util.ArrayList;
import java.util.List;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "users", indexes = {
	@Index(columnList = "id, username, email", unique = true)
}, uniqueConstraints = {
	@UniqueConstraint(columnNames = { "username", "email" }),
})
public class User {
	static private final BCrypt.Hasher bCrypt = BCrypt.withDefaults();
	static private final BCrypt.Verifyer bCryptVerifier = BCrypt.verifyer();

	@Id
	@SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
	private Long id;
	private String username;
	private String password_hash;
	private String email;
	private Double balance = 0.0;
	private List<Long> cart = new ArrayList<>();

	public User(String username, String password_hash, String email) {
		this.setUsername(username);
		this.setPassword_hash(password_hash);
		this.setEmail(email);
	}

	public void addToCart(Long product_id) {
		this.cart.add(product_id);
	}

	public void removeFromCart(Long product_id) {
		if (!this.cart.contains(product_id)) {
			return;
		}
		this.cart.remove(product_id);
	}

	public boolean isInCart(Long product_id) {
		return this.cart.contains(product_id);
	}

	public void clearCart() {
		this.cart.clear();
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
