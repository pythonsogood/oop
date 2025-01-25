package org.pythonsogood.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "products", indexes = {
	@Index(columnList = "product_id, name", unique = true),
}, uniqueConstraints = {
	@UniqueConstraint(columnNames = "name"),
})
public class Product {
	@Id
	@SequenceGenerator(name = "product_sequence", sequenceName = "product_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_sequence")
	private Long product_id;
	private String name;
	private String description;
	private Double price;

	public Product(String name, String description, Double price) {
		this.setName(name);
		this.setDescription(description);
		this.setPrice(price);
	}
}
