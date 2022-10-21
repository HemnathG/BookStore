package com.bridgelabz.bs.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bs.admin.dto.AdminDto;
import com.bridgelabz.bs.admin.model.Admin;
import com.bridgelabz.bs.admin.service.IAdminService;
import com.bridgelabz.bs.user.dto.LoginDto;
import com.bridgelabz.bs.utility.Response;

@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
    private IAdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<Response> adminLogin(@RequestBody LoginDto loginDto){
        Admin admin = adminService.login(loginDto);
        if (admin != null){
            Response response = new Response("Login Successful", admin);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            Response response = new Response("Login Failed!", "Password is incorrect!");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }
    @PutMapping("/forgetPassword")
    public ResponseEntity<Response> forgetPasswordForAdmin(@RequestParam String email, @RequestBody AdminDto adminDto){
        Admin admin = adminService.resetPassword(email, adminDto);
        Response response = new Response("Password changed successfully", admin);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
