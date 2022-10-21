package com.bridgelabz.bs.admin.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class AdminDto {
	@Email(message = "Email is invalid")
	public String email;
	@NotBlank(message = "Password should not be empty")
	public String password;
}
