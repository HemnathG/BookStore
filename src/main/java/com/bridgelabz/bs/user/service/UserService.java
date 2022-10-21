package com.bridgelabz.bs.user.service;

import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.bs.exception.CustomException;
import com.bridgelabz.bs.user.dto.LoginDto;
import com.bridgelabz.bs.user.dto.UserDto;
import com.bridgelabz.bs.user.model.Email;
import com.bridgelabz.bs.user.model.User;
import com.bridgelabz.bs.user.repository.UserRepository;
import com.bridgelabz.bs.utility.TokenUtility;

@Service
public class UserService implements IUserService{
	
	@Autowired
    private UserRepository repository;
	
    @Autowired
    private TokenUtility tokenUtility;
    
    @Autowired
    private IEmailService emailService;

    public UserService() {
    }
    
    @Override
    public void isEmpty(List<User> userList){
        if(userList.isEmpty())
            throw new CustomException("User List is empty!");
    }
    
    @Override
    public List<User> getAllUsers() {
        List<User> userList = repository.findAll();
        isEmpty(userList);
        return userList;
    }
    
    @Override
    public User registerUser(UserDto userDto) {
        User user = new User(userDto);
        repository.save(user);
        String token = tokenUtility.createToken(user.getUserId());
        user.setToken(token);
        Email email = new Email(user.getEmail(), "User registered successfully", user.getUserName()+" => "+emailService.getLink(token));
        try {
            emailService.sendMail(email);
        } catch (MessagingException e) {
            throw new CustomException(e.getMessage());
        }
        return repository.save(user);
    }
    
    @Override
    public User getUserById(String token) {
        int id = tokenUtility.decodeToken(token);
        return repository.findById(id)
                .orElseThrow(() -> new CustomException("User with not found!"));
    }
    
    @Override
    public User editUser(String token, UserDto userDto) {
        User user = this.getUserById(token);
        user.updateUser(userDto);
        return repository.save(user);
    }
    
    @Override
    public void deleteUser(String token) {
        User user = this.getUserById(token);
        repository.delete(user);

    }
    
    @Override
    public User login(LoginDto loginDto) {
        User user = repository.loginUser(loginDto.email, loginDto.password);
        return user;
    }
    
    @Override
    public User resetPassword(String email, UserDto userDto) {
        User user = repository.getUserByEmail(email);
        if (user != null){
            user.setPassword(userDto.password);
            return repository.save(user);
        }
        else throw new CustomException("User with email "+email+" not found!");
    }
    
    @Override
    public User verifyUser(String token) {
        User user = this.getUserById(token);
        user.setVerified(true);
        return repository.save(user);
    }
}
