package com.bridgelabz.bs.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class LoginDto {
	 public String email;
	 public String password;
}
