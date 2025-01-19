package org.pythonsogood.controlles;

import java.util.List;

import org.pythonsogood.Models.User;
import org.pythonsogood.repositories.UserRepository;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
public class UserController {
	private final UserRepository userRepository;
	// private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(16);
	private final BCrypt.Hasher bCrypt = BCrypt.withDefaults();

	public UserController(final UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@PostMapping("/api/users/register")
	public String users_register(@RequestParam(value="username", required=true) String username, @RequestParam(value="password", required=true) String password, @RequestParam(value="email", required=true) String email) {
		User existing_user = userRepository.findByUsername(username);
		if (existing_user != null) {
			return "user already exists";
		}
		// String password_hash = this.bCryptPasswordEncoder.encode(password);
		String password_hash = this.bCrypt.hashToString(12, password.toCharArray());
		User user = new User(username, password_hash, email);
		this.userRepository.save(user);
		return "success";
	}

	@PostMapping("/api/users/login")
	public String users_register(@RequestParam(value="username", required=true) String username, @RequestParam(value="password", required=true) String password) {
		User user = this.userRepository.findByUsername(username);
		if (user == null) {
			return "user not found";
		}
		BCrypt.Result password_result =
		// if (!this.bCryptPasswordEncoder.matches(password, user.getPasswordHash())) {
			return "wrong password";
		}
		return "success";
	}
}
