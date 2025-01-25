package org.pythonsogood.repository;

import java.util.List;

import org.pythonsogood.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
	List<Product> findByName(String name);
}
