package com.bridgelabz.bs.admin.service;

import java.util.List;

import com.bridgelabz.bs.admin.dto.AdminDto;
import com.bridgelabz.bs.admin.model.Admin;
import com.bridgelabz.bs.book.dto.BookDto;
import com.bridgelabz.bs.book.model.Order;
import com.bridgelabz.bs.user.dto.LoginDto;
import com.bridgelabz.bs.utility.Response;

public interface IAdminService {
	
	Admin resetPassword(String email, AdminDto adminDto);

	Admin login(LoginDto loginDto);

	Response deleteBook(int id);

	Response historyOfUser(int id);

	Response updateBook(int bookId, BookDto bookdto);

	List<Order> getPendingOrders();

}
