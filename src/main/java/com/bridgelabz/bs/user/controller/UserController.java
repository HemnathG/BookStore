package com.bridgelabz.bs.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bs.user.dto.LoginDto;
import com.bridgelabz.bs.user.dto.UserDto;
import com.bridgelabz.bs.user.model.User;
import com.bridgelabz.bs.user.service.IUserService;
import com.bridgelabz.bs.utility.Response;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
    private IUserService userService;

    @GetMapping("/getAllUsers")
    public ResponseEntity<Response> getUserData(){
        List<User> userList = userService.getAllUsers();
        Response response = new Response("Get call successful", userList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PostMapping("/addUser")
    public ResponseEntity<Response> createNewUser(@RequestBody UserDto userDto){
        User user = userService.registerUser(userDto);
        Response response = new Response("User registered successfully", user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/verify")
    public ResponseEntity<Response> verifyUser(@RequestHeader String token){
        Response response = new Response("User verified successfully", userService.verifyUser(token));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/getUserById")
    public ResponseEntity<Response> getUserById(@RequestHeader String token){
        User user = userService.getUserById(token);
        Response response = new Response("Get call for id successful", user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PutMapping("/editUser")
    public ResponseEntity<Response> editUser(@RequestBody UserDto userDto, @RequestHeader String token){
        User user = userService.editUser(token, userDto);
        Response response = new Response("User data updated successfully", user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @DeleteMapping("/deleteUser")
    public ResponseEntity<Response> deleteUser(@RequestHeader String token){
        userService.deleteUser(token);
        Response response = new Response("Deleted Successfully", "User deleted!");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PostMapping("/login")
    public ResponseEntity<Response> userLogin(@RequestBody LoginDto loginDto){
        User user = userService.login(loginDto);
        if (user != null){
            Response response = new Response("Login Successful", user);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            Response response = new Response("Login Failed!", "Email or password is incorrect!");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }
    
    @PutMapping("/forgetPassword")
    public ResponseEntity<Response> forgetPassword(@RequestParam String email, @RequestBody UserDto userDto){
        User user = userService.resetPassword(email, userDto);
        Response response = new Response("Password changed successfully", user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
