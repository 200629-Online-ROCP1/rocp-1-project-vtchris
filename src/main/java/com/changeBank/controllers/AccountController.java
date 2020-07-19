package com.changeBank.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.changeBank.models.accounts.Account;
import com.changeBank.models.accounts.AccountDTO;
import com.changeBank.models.users.User;
import com.changeBank.models.users.UserDTO;
import com.changeBank.repo.AccountDao;
import com.changeBank.services.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AccountController {
	
	private static final AccountService as = new AccountService();
	private static final AccountDao ad = AccountDao.getInstance();
	private static final ObjectMapper om = new ObjectMapper();
	
	public void createAccount(HttpServletRequest req, HttpServletResponse res, int roleId, int authUserId) throws IOException {
		
		AccountDTO adto = getAccountDTO(req);
		
		if(roleId == 1 || roleId == 2 || authUserId == adto.userId) {
			Account a = as.createAccount(adto);
			if(a != null) {
				adto = getAccountDTO(a);
				res.setStatus(201);
				res.getWriter().println(om.writeValueAsString(adto));
			}else {
				res.setStatus(400);
				res.getWriter().println("Account not created.");
			}
		}else {
			res.setStatus(401);
		}
	}
	
	public void findAll(HttpServletRequest req, HttpServletResponse res) throws IOException {
		List<Account> accounts = as.findAll();		
		List<AccountDTO> asdto = new ArrayList<>();
						
		accounts.forEach(a -> asdto.add(getAccountDTO(a)));
				
		res.setStatus(200);
		res.getWriter().println(om.writeValueAsString(asdto));
		
	}	
	
	public void updateAccount(HttpServletRequest req, HttpServletResponse res) throws IOException {
		
		AccountDTO adto = getAccountDTO(req);
		
		Account a = as.updateAccount(adto);
		if(a != null) {
			adto = getAccountDTO(a);
			res.setStatus(200);
			res.getWriter().println(om.writeValueAsString(adto));
		}else {
			res.setStatus(400);
			res.getWriter().println("Account not created.");
		}		
	}
	
	private AccountDTO getAccountDTO(HttpServletRequest req) throws IOException {
		System.out.println("Getting DTO from body");
		
		BufferedReader reader = req.getReader();
		StringBuilder s = new StringBuilder();
		String line = reader.readLine();

		while (line != null) {
			s.append(line);
			line = reader.readLine();
		}

		String body = new String(s);
		System.out.println(body);
		
		return om.readValue(body, AccountDTO.class);
	}
	
	private AccountDTO getAccountDTO(Account a) {
		
		AccountDTO adto = new AccountDTO();
		adto.accountId = a.getAccountId();	
		adto.acctNbr = a.getAcctNbr();
		adto.balance = a.getBalance();
		adto.statusId = a.getStatus().getAccountStatusId();
		adto.status = a.getStatus();
		adto.typeId = a.getType().getAccountTypeId();
		adto.type = a.getType();
		adto.userId = a.getUserId();
		adto.user = getUserDTO(a.getUser());
				
		return adto;
					
	}
	
	private UserDTO getUserDTO(User u) {
		
		UserDTO udto = new UserDTO();
		udto.email = u.getEmail();
		udto.firstName = u.getFirstName();
		udto.lastName = u.getLastName();
		udto.roleId = u.getRole().getRoleId();
		udto.role = u.getRole();
		udto.userId = u.getUserId();
		udto.username = u.getUsername();
		
		return udto;
		
	}

	
}