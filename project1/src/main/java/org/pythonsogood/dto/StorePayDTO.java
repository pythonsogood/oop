package org.pythonsogood.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StorePayDTO {
	@Valid

	@NotNull
	@NotBlank
	private String token;
}
