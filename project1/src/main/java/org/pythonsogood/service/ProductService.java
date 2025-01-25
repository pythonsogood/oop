package org.pythonsogood.service;

import java.util.List;
import java.util.Optional;

import org.pythonsogood.model.Product;
import org.pythonsogood.model.User;
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

	public Optional<Product> findById(Long id) {
		return this.productRepository.findById(id);
	}

	public Optional<Product> findByName(String name) {
		List<Product> products = this.productRepository.findByName(name);
		return products.size() > 0 ? Optional.of(products.get(0)) : Optional.empty();
	}

	public Product save(Product product) {
		System.out.println(String.format("SAVING PRODUCT WITH ID: %s", product.getProduct_id()));
		return this.productRepository.save(product);
	}

	public void delete(Product product) {
		this.productRepository.delete(product);
	}

	public void deleteById(Long id) {
		this.productRepository.deleteById(id);
	}

	public Double countUserCart(User user) {
		Double total = 0.0;
		for (Long product_id : user.getCart()) {
			Optional<Product> productOptional = this.findById(product_id);
			if (productOptional.isPresent()) {
				Product product = productOptional.get();
				total += product.getPrice();
			}
		}
		return total;
	}
}
