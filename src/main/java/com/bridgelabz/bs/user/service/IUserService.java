package com.bridgelabz.bs.user.service;

import java.util.List;

import com.bridgelabz.bs.user.dto.LoginDto;
import com.bridgelabz.bs.user.dto.UserDto;
import com.bridgelabz.bs.user.model.User;

public interface IUserService {

	List<User> getAllUsers();

	User registerUser(UserDto userDto);

	User getUserById(String token);

	User editUser(String token, UserDto userDto);

	void deleteUser(String token);

	User login(LoginDto loginDto);

	User resetPassword(String email, UserDto userDto);

	User verifyUser(String token);

	void isEmpty(List<User> userList);

}
