package org.pythonsogood.controller;

import java.util.Date;
import java.util.Optional;

import org.json.JSONObject;
import org.pythonsogood.dto.UserLoginDTO;
import org.pythonsogood.dto.UserRegistrationDTO;
import org.pythonsogood.exceptions.BadCredentialsException;
import org.pythonsogood.exceptions.UserAlreadyExistsException;
import org.pythonsogood.exceptions.UserNotFoundException;
import org.pythonsogood.model.User;
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

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController extends AbstractRestController {
	private final UserService userService;

	@Autowired
	private Algorithm userAuthorizationJwtAlgorithm;

	@Value("${user.authorization.jwt.duration}")
	protected Integer userAuthorizationJwtDuration;

	public UserController(final UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value="/register", method={RequestMethod.POST}, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> users_register(@Valid @RequestBody UserRegistrationDTO dto) throws UserAlreadyExistsException {
		Optional<User> existing_user = this.userService.findByUsername(dto.getUsername());
		if (existing_user.isPresent()) {
			throw new UserAlreadyExistsException(String.format("User %s already exists", dto.getUsername()));
		}
		User user = new User(dto.getUsername(), User.hashPassword(dto.getPassword()), dto.getEmail());
		this.userService.save(user);
		JSONObject response = new JSONObject();
		response.put("message", "success");
		return ResponseEntity.status(HttpStatus.OK).body(response.toString());
	}

	@RequestMapping(value="/login", method={RequestMethod.POST}, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> users_register(@Valid @RequestBody(required=true) UserLoginDTO dto) throws UserNotFoundException, BadCredentialsException {
		Optional<User> userOptional = this.userService.findByUsername(dto.getUsername());
		if (!userOptional.isPresent()) {
			throw new UserNotFoundException(String.format("User %s not found", dto.getUsername()));
		}
		User user = userOptional.get();
		BCrypt.Result password_result = user.verifyPassword(dto.getPassword());
		if (!password_result.verified) {
			throw new BadCredentialsException("Invalid password");
		}
		JSONObject response = new JSONObject();
		Date now = new Date();
		String token = JWT.create().withExpiresAt(new Date(now.getTime() + (this.userAuthorizationJwtDuration * 1000))).withSubject(user.getId().toString()).sign(this.userAuthorizationJwtAlgorithm);
		response.put("token", token);
		return ResponseEntity.status(HttpStatus.OK).body(response.toString());
	}

	@RequestMapping(value="/whoami", method={RequestMethod.GET}, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> users_whoami(@RequestParam(value="token", required=true) String token) throws BadCredentialsException {
		User user = this.userService.authorize(token);
		JSONObject response = new JSONObject();
		response.put("username", user.getUsername());
		return ResponseEntity.status(HttpStatus.OK).body(response.toString());
	}
}
