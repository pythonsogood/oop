package org.pythonsogood.controller;

import java.util.ArrayList;
import java.util.Optional;

import org.json.JSONObject;
import org.pythonsogood.dto.StoreCartDeleteDTO;
import org.pythonsogood.dto.StoreCartPutDTO;
import org.pythonsogood.dto.StorePayDTO;
import org.pythonsogood.dto.StoreProductPutDTO;
import org.pythonsogood.exceptions.BadCredentialsException;
import org.pythonsogood.exceptions.NotEnoughBalanceException;
import org.pythonsogood.exceptions.ProductAlreadyExistsException;
import org.pythonsogood.exceptions.ProductNotFoundException;
import org.pythonsogood.exceptions.RequestException;
import org.pythonsogood.model.Product;
import org.pythonsogood.model.User;
import org.pythonsogood.service.ProductService;
import org.pythonsogood.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/store")
public class StoreController extends AbstractRestController {
	@Value("${store.authorization.admin_token}")
	private String storeAuthorizationAdminToken;

	@Autowired
	private UserService userService;

	@Autowired
	private ProductService productService;

	@RequestMapping(value="/cart", method={RequestMethod.PUT}, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> cart_put(@Valid @RequestBody StoreCartPutDTO dto) throws BadCredentialsException, ProductNotFoundException {
		User user = this.userService.authorize(dto.getToken());
		Optional<Product> productOptional = this.productService.findById(Long.parseLong(dto.getProduct_id()));
		if (!productOptional.isPresent()) {
			throw new ProductNotFoundException(String.format("Product %d not found", dto.getProduct_id()));
		}
		Product product = productOptional.get();
		user.addToCart(product.getProduct_id());
		this.userService.save(user);
		JSONObject response = new JSONObject();
		response.put("message", "success");
		return ResponseEntity.status(HttpStatus.OK).body(response.toString());
	}

	@RequestMapping(value="/cart", method={RequestMethod.DELETE}, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> cart_delete(@Valid @RequestBody StoreCartDeleteDTO dto) throws BadCredentialsException, ProductNotFoundException {
		User user = this.userService.authorize(dto.getToken());
		Optional<Product> productOptional = this.productService.findById(Long.parseLong(dto.getProduct_id()));
		if (!productOptional.isPresent()) {
			throw new ProductNotFoundException(String.format("Product %d not found", dto.getProduct_id()));
		}
		Product product = productOptional.get();
		if (!user.isInCart(product.getProduct_id())) {
			throw new ProductNotFoundException(String.format("Product %d not in cart", dto.getProduct_id()));
		}
		user.removeFromCart(product.getProduct_id());
		this.userService.save(user);
		JSONObject response = new JSONObject();
		response.put("message", "success");
		return ResponseEntity.status(HttpStatus.OK).body(response.toString());
	}

	@RequestMapping(value="/cart", method={RequestMethod.GET}, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> cart_get(@RequestParam(value="token", required=true) String token) throws BadCredentialsException {
		User user = this.userService.authorize(token);
		JSONObject response = new JSONObject();
		response.put("message", "success");
		ArrayList<String> products = new ArrayList<>();
		for (Long product_id : user.getCart()) {
			Optional<Product> productOptional = this.productService.findById(product_id);
			if (productOptional.isPresent()) {
				Product product = productOptional.get();
				products.add(product.getName());
			}
		}
		response.put("cart", products);
		return ResponseEntity.status(HttpStatus.OK).body(response.toString());
	}

	@RequestMapping(value="/pay", method={RequestMethod.POST}, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> cart_pay(@Valid @RequestBody(required=true) StorePayDTO dto) throws BadCredentialsException, NotEnoughBalanceException, RequestException {
		User user = this.userService.authorize(dto.getToken());
		if (user.getCart().size() <= 0) {
			throw new RequestException("Cart is empty");
		}
		Double cost = this.productService.countUserCart(user);
		if (user.getBalance() < cost) {
			throw new NotEnoughBalanceException("Not enough balance");
		}
		JSONObject response = new JSONObject();
		response.put("message", "success");
		ArrayList<String> products = new ArrayList<>();
		for (Long product_id : user.getCart()) {
			Optional<Product> productOptional = this.productService.findById(product_id);
			if (productOptional.isPresent()) {
				Product product = productOptional.get();
				products.add(product.getName());
			}
		}
		user.setBalance(user.getBalance() - cost);
		user.clearCart();
		this.userService.save(user);
		return ResponseEntity.status(HttpStatus.OK).body(response.toString());
	}

	@RequestMapping(value="/products", method={RequestMethod.GET}, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> cart_get() {
		JSONObject response = new JSONObject();
		response.put("message", "success");
		ArrayList<JSONObject> products = new ArrayList<>();
		for (Product product : this.productService.findAll()) {
			JSONObject productData = new JSONObject();
			productData.put("id", product.getProduct_id());
			productData.put("name", product.getName());
			productData.put("description", product.getDescription());
			productData.put("price", product.getPrice());
			products.add(productData);
		}
		response.put("products", products);
		return ResponseEntity.status(HttpStatus.OK).body(response.toString());
	}

	@RequestMapping(value="/product", method={RequestMethod.PUT}, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> product_put(@Valid @RequestBody StoreProductPutDTO dto) throws BadCredentialsException, ProductAlreadyExistsException {
		if (!dto.getToken().equals(this.storeAuthorizationAdminToken)) {
			throw new BadCredentialsException("Invalid token");
		}
		Optional<Product> existingProduct = this.productService.findByName(dto.getName());
		if (existingProduct.isPresent()) {
			throw new ProductAlreadyExistsException(String.format("Product %s already exists", dto.getName()));
		}
		Product product = new Product(dto.getName(), dto.getDescription(), dto.getPrice());
		this.productService.save(product);
		JSONObject response = new JSONObject();
		response.put("message", "success");
		return ResponseEntity.status(HttpStatus.OK).body(response.toString());
	}

	@RequestMapping(value="/product", method={RequestMethod.DELETE}, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> product_delete(@Valid @RequestBody StoreCartDeleteDTO dto) throws BadCredentialsException, ProductNotFoundException {
		if (!dto.getToken().equals(this.storeAuthorizationAdminToken)) {
			throw new BadCredentialsException("Invalid token");
		}
		Optional<Product> productOptional = this.productService.findById(Long.parseLong(dto.getProduct_id()));
		if (!productOptional.isPresent()) {
			throw new ProductNotFoundException(String.format("Product %d not found", dto.getProduct_id()));
		}
		this.productService.deleteById(Long.parseLong(dto.getProduct_id()));
		JSONObject response = new JSONObject();
		response.put("message", "success");
		return ResponseEntity.status(HttpStatus.OK).body(response.toString());
	}
}
