package org.pythonsogood.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.pythonsogood.model.Product;
import org.pythonsogood.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	@Autowired
	private final ProductRepository productRepository;

	public List<Product> findAll() {
		return this.productRepository.findAll();
	}

	public Optional<Product> findById(UUID id) {
		return this.productRepository.findById(id);
	}

	public Optional<Product> findByName(String name) {
		List<Product> products = this.productRepository.findByName(name);
		return products.size() > 0 ? Optional.of(products.get(0)) : Optional.empty();
	}

	public Product save(Product product) {
		return this.productRepository.save(product);
	}

	public void delete(Product product) {
		this.productRepository.delete(product);
	}

	public void deleteById(UUID id) {
		this.productRepository.deleteById(id);
	}
}
