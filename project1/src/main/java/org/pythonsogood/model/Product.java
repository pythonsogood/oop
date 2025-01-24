package org.pythonsogood.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "products", indexes = {
	@Index(columnList = "product_id, name", unique = true)
})
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private UUID product_id;
	private String name;
	private String description;
	private Double price;

	public Product(String name, String description, Double price) {
		this.setProduct_id(UUID.randomUUID());
		this.setName(name);
		this.setDescription(description);
		this.setPrice(price);
	}
}
