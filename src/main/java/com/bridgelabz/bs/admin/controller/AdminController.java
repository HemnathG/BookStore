package com.bridgelabz.bs.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.bridgelabz.bs.admin.dto.AdminDto;
import com.bridgelabz.bs.admin.model.Admin;
import com.bridgelabz.bs.admin.service.IAdminService;
import com.bridgelabz.bs.book.dto.BookDto;
import com.bridgelabz.bs.book.model.Order;
import com.bridgelabz.bs.user.dto.LoginDto;
import com.bridgelabz.bs.utility.Response;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
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
    public ResponseEntity<Response> forgetPasswordForAdmin(@RequestHeader String email, @RequestBody AdminDto adminDto){
        Admin admin = adminService.resetPassword(email, adminDto);
        Response response = new Response("Password changed successfully", admin);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PutMapping("/updateBook")
	public ResponseEntity<Response> editBook(@RequestHeader int id, @RequestBody BookDto bookdto ) {
		Response response = adminService.updateBook( id ,bookdto);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@DeleteMapping("/deleteBook")
	public ResponseEntity<Response> deleteNote(@RequestHeader int bookId) {
		Response response = adminService.deleteBook(bookId);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	@GetMapping("/historyOfUser")
	public ResponseEntity<Response> historyOfUser(@RequestHeader int id ) {
		Response response = adminService.historyOfUser(id);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	@GetMapping("/getPendingOrders")
	public ResponseEntity<Response> getPendingOrders(){
		List<Order> pendingOrders = adminService.getPendingOrders();
	    Response response = new Response("Get call for all pending orders successful", pendingOrders);
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}
    
}
