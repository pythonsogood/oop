package org.pythonsogood.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserLoginDTO {
	@Valid

	@NotNull
	@NotBlank
	private String username;

	@NotNull
	@NotBlank
	private String password;
}
