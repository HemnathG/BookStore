package com.bridgelabz.bs.user.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.bridgelabz.bs.user.dto.UserDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	private String userName;
	private String email;
	private String password;
	private String address;
	private String token;
	boolean verified=false;

	public User(UserDto userDto){ 
		this.updateUser(userDto); 
	}
	
	public void updateUser(UserDto userDto){
		this.userName= userDto.userName;
	    this.email=userDto.email;
	    this.password=userDto.password;
	    this.address= userDto.address;
	}
}
