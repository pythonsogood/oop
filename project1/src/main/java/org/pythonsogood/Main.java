package org.pythonsogood;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;


@SpringBootApplication
public class Main extends SpringBootServletInitializer {
	@Value("${user.authorization.jwt.secret}")
	protected String userAuthorizationJwtSecret;

    public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
    }

	@Bean
	public Algorithm userAuthorizationJwtAlgorithm() {
		return Algorithm.HMAC256(this.userAuthorizationJwtSecret);
	}

	@Bean
	public JWTVerifier userAuthorizationJwtVerifier() {
		return JWT.require(this.userAuthorizationJwtAlgorithm()).build();
	}
}