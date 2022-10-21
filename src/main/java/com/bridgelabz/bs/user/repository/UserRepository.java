package com.bridgelabz.bs.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bridgelabz.bs.user.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	@Query(value = "SELECT * FROM user WHERE email=:email AND password=:password", nativeQuery = true)
    User loginUser(String email, String password);
    
	User getUserByEmail(String email);
    
	@Query(value = "SELECT * FROM user WHERE user_id=:userId", nativeQuery = true)
    User getUserById(int userId);
}
