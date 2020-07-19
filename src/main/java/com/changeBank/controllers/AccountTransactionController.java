package com.changeBank.controllers;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.changeBank.models.accounts.Account;
import com.changeBank.models.accounts.AccountTransaction;
import com.changeBank.models.accounts.AccountTransactionDTO;
import com.changeBank.models.app.MessageDTO;
import com.changeBank.repo.AccountDao;
import com.changeBank.services.AccountTransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AccountTransactionController {
	
	private static final AccountTransactionService ts = new AccountTransactionService(); 
	private static final ObjectMapper om = new ObjectMapper();
	private static final  AccountDao adao = AccountDao.getInstance();

	public void createTransaction(HttpServletRequest req, HttpServletResponse res, int roleId, int authUserId) throws IOException {
		
		AccountTransactionDTO tdto = getAccountTransationDTO(req);
		Account a = adao.findById(tdto.accountId);
		tdto.account = a;
		tdto.userId = authUserId;
		//AccountTransaction t = new AccountTransaction();
		
		// Only account owner or admin can do these functions
		if(roleId == 1 || authUserId == a.getUserId()) {
			// Account must be either OPEN or FROZEN
			if(a.getStatus().getAccountStatusId() == 2 || a.getStatus().getAccountStatusId() == 3 ) {
				// Only positive amounts are allowed
				if(tdto.amount > 0) {
					// Check for sufficient funds for Withdrawls and Transfers
					if(((tdto.type == 'W' || tdto.type == 'T') && a.getBalance() >= tdto.amount) || tdto.type == 'D') {
						if(tdto.type == 'D' || a.getStatus().getAccountStatusId() == 2) {
							if(ts.createTransaction(tdto)) {
								res.setStatus(201);
								if(tdto.type == 'D') {
									res.getWriter().println(getMessageDTO("$" + tdto.amount + " has been deposited to Account #" + a.getAcctNbr()));
								}else if(tdto.type == 'W') {
									res.getWriter().println(getMessageDTO("$" + tdto.amount + " has been withdrawn from Account #" + a.getAcctNbr()));
								}else {
									res.getWriter().println(getMessageDTO("$" + tdto.amount + " has been transfered from Account #" + a.getAcctNbr() + " to Account:" + tdto.targetAccountId));
								}
							}else {
								res.setStatus(500);
								res.getWriter().println(getMessageDTO("Transaction could not be completed."));
							}
						}else{
							// Not transactions are allowed on FROZEN accounts
							res.setStatus(400);
							res.getWriter().println("Transaction not allowed for account status: " + a.getStatus().getAccountStatus());
						}											
					}else {
						// Bad Request
						res.setStatus(400);
						res.getWriter().println("Insufficient Funds.");
					}					
				}else {
					// Bad Request
					res.setStatus(400);
					res.getWriter().println("Only values greater than 0.00 are accepted.");
				}
			}else {
				// No transactions are allowed on PENDING, CLOSED, DENIED accounts
				res.setStatus(400);
				res.getWriter().println("Transaction not allowed for account status: " + a.getStatus().getAccountStatus());
			}
			
			
		}else {
			// Unauthorized
			res.setStatus(401);
		}
		
		
	}

	private AccountTransactionDTO getAccountTransationDTO(HttpServletRequest req) throws IOException  {
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
		
		return om.readValue(body, AccountTransactionDTO.class);
	}
	
	private MessageDTO getMessageDTO(String m) {
		
		MessageDTO mdto = new MessageDTO();
		mdto.message = m;
		
		return mdto;
		
	}

}
