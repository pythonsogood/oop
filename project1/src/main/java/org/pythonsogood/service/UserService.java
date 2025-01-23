package org.pythonsogood.service;

import java.util.List;
import java.util.Optional;

import org.pythonsogood.model.User;
import org.pythonsogood.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
	@Autowired
	private final UserRepository userRepository;

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
}
