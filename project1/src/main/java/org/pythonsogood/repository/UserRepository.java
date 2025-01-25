package org.pythonsogood.repository;

import java.util.List;

import org.pythonsogood.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	List<User> findByUsername(String username);

	List<User> findByEmail(String email);
}
