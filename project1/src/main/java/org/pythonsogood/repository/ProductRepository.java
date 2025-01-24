package org.pythonsogood.repository;

import java.util.List;
import java.util.UUID;

import org.pythonsogood.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, UUID> {
	List<Product> findByName(String name);
}
