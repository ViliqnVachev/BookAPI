package com.rest.RestAPI.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class AuthorDTO {
	@ApiModelProperty(hidden = true)
	private Long id;

	@NotNull
	@NotBlank
	private String name;

	public AuthorDTO() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
