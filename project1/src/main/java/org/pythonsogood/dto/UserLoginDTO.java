package org.pythonsogood.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserLoginDTO {
	@Valid

	private String username;

	@NotNull
	@NotBlank
	private String password;

	public boolean hasUsername() {
		return this.username != null && this.username.length() > 0;
	}
}
