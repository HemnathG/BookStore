package com.bridgelabz.bs.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.bs.exception.CustomException;
import com.bridgelabz.bs.user.dto.LoginDto;
import com.bridgelabz.bs.admin.dto.AdminDto;
import com.bridgelabz.bs.admin.model.Admin;
import com.bridgelabz.bs.admin.repository.AdminRepository;

@Service
public class AdminService implements IAdminService{
	@Autowired
    private AdminRepository adminRepository;

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
}
