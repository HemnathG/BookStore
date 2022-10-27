package com.bridgelabz.bs.admin.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.bs.exception.CustomException;
import com.bridgelabz.bs.user.dto.LoginDto;
import com.bridgelabz.bs.user.model.User;
import com.bridgelabz.bs.user.repository.UserRepository;
import com.bridgelabz.bs.utility.Response;
import com.bridgelabz.bs.admin.dto.AdminDto;
import com.bridgelabz.bs.admin.model.Admin;
import com.bridgelabz.bs.admin.repository.AdminRepository;
import com.bridgelabz.bs.book.dto.BookDto;
import com.bridgelabz.bs.book.model.Book;
import com.bridgelabz.bs.book.model.Order;
import com.bridgelabz.bs.book.repository.BookRepository;
import com.bridgelabz.bs.book.repository.OrderRepository;

@Service
public class AdminService implements IAdminService{
	@Autowired
    private AdminRepository adminRepository;
	
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderRepository orderRepository;


    @Override
    public Admin login(LoginDto loginDto) {
        Admin admin = adminRepository.getAdminByEmail(loginDto.email);
        if (admin != null) {
            admin = adminRepository.loginAdmin(loginDto.email, loginDto.password);
            return admin;
        }
        else throw new CustomException("Email is incorrect!");
    }

    @Override
    public Admin resetPassword(String email, AdminDto adminDto) {
        Admin admin = adminRepository.getAdminByEmail(email);
        if (admin != null){
            admin.setPassword(adminDto.password);
            return adminRepository.save(admin);
        }
        else throw new CustomException("Email is incorrect!");
    }
    
	@Override
	public Response deleteBook(int id) {
		Optional<Admin> findAdmin = adminRepository.findById(id);
		if (!findAdmin.isPresent()) {
			throw new CustomException(String.format("Book has already Deleted from the Database !!!"));
		}
		bookRepository.deleteById(id);
		Response response = new Response("The response message : Note has been deleted Sucessfully !!!",
				id);
		return response;
	}
	
	@Override
	public Response historyOfUser(int id) {
		Optional<User> isPresent = userRepository.findById(id);
		if (isPresent.isPresent()) {
			List <Order> orderData = orderRepository.findAllByUserId(id);
			Response response = new Response(
					"The response message : History of a particular user from the database",
					orderData);
			return response;
		} else {
			return null;
		}
	}
	
	@Override
	public Response updateBook(int id, BookDto bookdto) {
		Book book = getBookById(id);
		book.updateBookDetails(bookdto);         
		Response response = new Response("The response message : Book Information Sucessfully updated to the DataBase by the Admin", bookRepository.save(book));
		return response;
	}
	
    public Book getBookById(int id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new CustomException("Book with id "+id+" not found!"));
    }

	@Override
    public List<Order> getPendingOrders() {
        List<Order> orderList = orderRepository.findPendingOrders();
        if (orderList.isEmpty())
            throw new CustomException("There are no pending orders!");
        return orderList;
    }
}
