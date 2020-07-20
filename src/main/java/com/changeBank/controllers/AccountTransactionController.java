package com.changeBank.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.NumberFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.changeBank.models.accounts.Account;
import com.changeBank.models.accounts.AccountTransactionDTO;
import com.changeBank.repo.AccountDao;
import com.changeBank.services.AccountTransactionService;
import com.changeBank.services.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AccountTransactionController {
	
	private static final AccountDao adao = AccountDao.getInstance();
	private static final AccountTransactionService ts = new AccountTransactionService();
	private static final MessageService ms = new MessageService();
	private static final ObjectMapper om = new ObjectMapper();
	

	public void createTransaction(HttpServletRequest req, HttpServletResponse res, int roleId, int authUserId) throws IOException {
		
		AccountTransactionDTO tdto = getAccountTransationDTO(req);
		Account a = adao.findById(tdto.accountId);
		tdto.account = a;
		tdto.userId = authUserId;
		
		// Only account owner or admin can do these functions
		if(roleId == 1 || authUserId == a.getUserId()) {
			// Account must be either OPEN or FROZEN
			if(a.getStatus().getAccountStatusId() == 2 || a.getStatus().getAccountStatusId() == 3 ) {
				// Only positive amounts are allowed
				if(tdto.amount > 0) {
					// Check for sufficient funds for Withdrawls and Transfers
					if(((tdto.type == 'W' || tdto.type == 'T') && a.getBalance() >= tdto.amount) || tdto.type == 'D') {
						if(tdto.type == 'D' || a.getStatus().getAccountStatusId() == 2) {
							
							if(tdto.type == 'T') {
								tdto.targetAccount = adao.findById(tdto.targetAccountId);
								tdto.acctNbr = a.getAcctNbr();
								//Confirm target account exists
								if(tdto.targetAccount == null) {
									res.setStatus(400);
									res.getWriter().println(om.writeValueAsString(ms.getMessageDTO("Transfer To account not found.")));
									return;
								//Confirm that target account can receive deposits								}else if				
								}else if(tdto.targetAccount.getStatus().getAccountStatusId() != 2 && tdto.targetAccount.getStatus().getAccountStatusId() != 3  ) {
									res.setStatus(400);
									res.getWriter().println(om.writeValueAsString(ms.getMessageDTO("Transfers not allowed to accounts with status of " + tdto.targetAccount.getStatus().getAccountStatus())));
									return;
								}
							}								
							
							if(ts.createTransaction(tdto)) {
								res.setStatus(201);
								NumberFormat formatter = NumberFormat.getCurrencyInstance();
								String money = formatter.format(tdto.amount);
								if(tdto.type == 'D') {
									res.getWriter().println(om.writeValueAsString(ms.getMessageDTO(money + " has been deposited to Account #" + a.getAcctNbr())));
								}else if(tdto.type == 'W') {
									res.getWriter().println(om.writeValueAsString(ms.getMessageDTO(money + " has been withdrawn from Account #" + a.getAcctNbr())));
								}else {
									res.getWriter().println(om.writeValueAsString(ms.getMessageDTO(money + " has been transfered from Account #" + a.getAcctNbr() + " to Account:" + tdto.targetAccount.getAcctNbr())));
								}
							}else {
								res.setStatus(500);
								res.getWriter().println(om.writeValueAsString(ms.getMessageDTO("Transaction could not be completed.")));
							}
						}else{
							// Not transactions are allowed on FROZEN accounts
							res.setStatus(400);
							res.getWriter().println(om.writeValueAsString(ms.getMessageDTO("Transaction not allowed for account status: " + a.getStatus().getAccountStatus())));
						}											
					}else {
						// Bad Request
						res.setStatus(400);
						res.getWriter().println(om.writeValueAsString(ms.getMessageDTO("Insufficient Funds.")));
					}					
				}else {
					// Bad Request
					res.setStatus(400);
					res.getWriter().println(om.writeValueAsString(ms.getMessageDTO("Only values greater than 0.00 are accepted.")));
				}
			}else {
				// No transactions are allowed on PENDING, CLOSED, DENIED accounts
				res.setStatus(400);
				res.getWriter().println(om.writeValueAsString(ms.getMessageDTO("Transaction not allowed for account status: " + a.getStatus().getAccountStatus())));
			}			
		}else {
			// Unauthorized
			res.setStatus(401);
		}				
	}

	private AccountTransactionDTO getAccountTransationDTO(HttpServletRequest req) throws IOException  {
		//System.out.println("Getting DTO from body");
		
		BufferedReader reader = req.getReader();
		StringBuilder s = new StringBuilder();
		String line = reader.readLine();

		while (line != null) {
			s.append(line);
			line = reader.readLine();
		}

		String body = new String(s);
		//System.out.println(body);
		
		return om.readValue(body, AccountTransactionDTO.class);
	}
}
