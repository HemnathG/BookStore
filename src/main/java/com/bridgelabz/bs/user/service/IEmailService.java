package com.bridgelabz.bs.user.service;

import javax.mail.MessagingException;

import com.bridgelabz.bs.user.model.Email;

public interface IEmailService {

	String sendMail(Email email) throws MessagingException;

	String getLink(String token);

}
