package org.pythonsogood.controllers;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.pythonsogood.Models.User;
import org.pythonsogood.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
public class UserController {
	private final UserRepository userRepository;

	@Autowired
	private Algorithm userAuthorizationJwtAlgorithm;

	@Autowired
	private JWTVerifier userAuthorizationJwtVerifier;

	public UserController(final UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@PostMapping("/api/users/register")
	public String users_register(@RequestParam(value="username", required=true) String username, @RequestParam(value="password", required=true) String password, @RequestParam(value="email", required=true) String email) {
		User existing_user = userRepository.findByUsername(username);
		if (existing_user != null) {
			return "user already exists";
		}
		User user = new User(username, User.hashPassword(password), email);
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
		Date expires_at = new Date(now.getTime() + 15000);
		System.out.println(expires_at.toString() + " 1 " + now.toString());
		String token = JWT.create().withExpiresAt(expires_at).withSubject(user.getId().toString()).sign(this.userAuthorizationJwtAlgorithm);
		return token;
	}

	@PostMapping("/api/users/whoami")
	public String users_whoami(@RequestParam(value="token", required=true) String token) {
		User user;
		try {
			user = this.authorize(token);
		} catch (JWTVerificationException | NoSuchElementException e) {
			return e.getMessage();
		}
		return user.getUsername();
	}

	public User authorize(String token) throws JWTVerificationException {
		DecodedJWT jwt = this.userAuthorizationJwtVerifier.verify(token);
		String user_id_hex = jwt.getSubject();
		ObjectId user_id = new ObjectId(user_id_hex);
		return this.userRepository.findById(user_id).orElseThrow();
	}
}
