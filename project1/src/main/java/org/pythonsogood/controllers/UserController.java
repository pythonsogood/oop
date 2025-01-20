package org.pythonsogood.controllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.pythonsogood.Models.User;
import org.pythonsogood.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
public class UserController {
	private final UserRepository userRepository;
	@Value("${user.authorization.jwt.secret}")
	protected String jwtSecret;
	private final Algorithm jwtAlgorithm = Algorithm.HMAC256(this.jwtSecret);

	public UserController(final UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@PostMapping("/api/users/register")
	public String users_register(@RequestParam(value="username", required=true) String username, @RequestParam(value="password", required=true) String password, @RequestParam(value="email", required=true) String email) {
		User existing_user = userRepository.findByUsername(username);
		if (existing_user != null) {
			return "user already exists";
		}
		User user = new User(username, password, email);
		this.userRepository.save(user);
		return "success";
	}

	@PostMapping("/api/users/login")
	public String users_register(@RequestParam(value="username", required=true) String username, @RequestParam(value="password", required=true) String password) {
		User user = this.userRepository.findByUsername(username);
		if (user == null) {
			return "user not found";
		}
		BCrypt.Result password_result = user.verifyPassword(password);
		if (!password_result.verified) {
			return "wrong password";
		}
		Date now = new Date();
		Date expires_at = new Date(now.getTime() + 1000 * 60 * 60 * 24);
		String token = JWT.create().withExpiresAt(expires_at).withIssuer(user.getId().toString()).sign(jwtAlgorithm);
		return token;
	}

	@PostMapping("/api/users/whoami")
	public String users_whoami(@RequestParam(value="token", required=true) String token) {
		User user = this.authorize(token);
		if (user == null) {
			return "user not found";
		}
		return user.getUsername();
	}

	public User authorize(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			String user_id_hex = jwt.getIssuer();
			ObjectId user_id = new ObjectId(user_id_hex);
			return this.userRepository.findById(user_id).orElse(null);
		} catch (JWTDecodeException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
}
