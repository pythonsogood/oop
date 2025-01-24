package org.pythonsogood.repository;

import java.util.List;
import java.util.UUID;

import org.pythonsogood.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {
	List<User> findByUsername(String username);

	List<User> findByEmail(String email);
}
