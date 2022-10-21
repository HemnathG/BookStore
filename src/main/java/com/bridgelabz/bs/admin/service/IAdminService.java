package com.bridgelabz.bs.admin.service;

import com.bridgelabz.bs.admin.dto.AdminDto;
import com.bridgelabz.bs.admin.model.Admin;
import com.bridgelabz.bs.user.dto.LoginDto;

public interface IAdminService {
	
	Admin resetPassword(String email, AdminDto adminDto);

	Admin login(LoginDto loginDto);

}
