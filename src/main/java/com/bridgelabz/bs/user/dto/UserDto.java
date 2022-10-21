package com.bridgelabz.bs.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class UserDto {
	@Pattern(regexp = "^[A-Z][a-zA-Z]{2,}(\\s[a-zA-Z]+)*$", message = "User name invalid")
    public String userName;
    @Email(message = "Email is invalid")
    public String email;
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?\\d)(?=.*?[a-z])(?!.*[<>`])" +
            "(?=[^.,!@$&_]*[.,:;'!@$&_][^.,!@$&_]*$).{8,}$", message = "Password is invalid")
    public String password;
    @NotBlank(message = "Address should not be empty")
    public String address;
}
