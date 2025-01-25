package org.pythonsogood.service;

import java.util.List;
import java.util.Optional;

import org.pythonsogood.exceptions.BadCredentialsException;
import org.pythonsogood.model.User;
import org.pythonsogood.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	@Autowired
	private final UserRepository userRepository;

	@Autowired
	private JWTVerifier userAuthorizationJwtVerifier;

	public List<User> findAll() {
		return this.userRepository.findAll();
	}

	public Optional<User> findById(Long id) {
		return this.userRepository.findById(id);
	}

	public Optional<User> findByUsername(String username) {
		List<User> users = this.userRepository.findByUsername(username);
		return users.size() > 0 ? Optional.of(users.get(0)) : Optional.empty();
	}

	public User findByEmail(String email) {
		List<User> users = this.userRepository.findByEmail(email);
		return users.size() > 0 ? users.get(0) : null;
	}

	public User save(User user) {
		return this.userRepository.save(user);
	}

	public void delete(User user) {
		this.userRepository.delete(user);
	}

	public void deleteById(Long id) {
		this.userRepository.deleteById(id);
	}

	public User authorize(String token) throws BadCredentialsException {
		try {
			DecodedJWT jwt = this.userAuthorizationJwtVerifier.verify(token);
			Long user_id = Long.parseLong(jwt.getSubject());
			return this.findById(user_id).orElseThrow(() -> new BadCredentialsException("user not found"));
		} catch (JWTVerificationException e) {
			throw new BadCredentialsException(e.getMessage());
		}
	}
}
