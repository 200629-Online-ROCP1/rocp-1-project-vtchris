package com.changeBank.services;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import com.changeBank.models.accounts.Account;
import com.changeBank.models.accounts.AccountDTO;
import com.changeBank.models.accounts.AccountTransactionDTO;
import com.changeBank.models.accounts.AccountType;
import com.changeBank.repo.AccountDao;
import com.changeBank.repo.AccountStatusDao;
import com.changeBank.repo.AccountTypeDao;

public class AccountService {
	
	private AccountDao adao = AccountDao.getInstance();
	private AccountStatusDao asdao = AccountStatusDao.getInstance();
	private AccountTransactionService ts = new AccountTransactionService();
	private AccountTypeDao atdao = AccountTypeDao.getInstance();
	private NumberFormat fpercent = NumberFormat.getPercentInstance();
	private NumberFormat fcurrency = NumberFormat.getCurrencyInstance();
	
	public Account createAccount(AccountDTO adto) {
		
		AccountType accountType =  atdao.findById(adto.typeId);
		Account a = new Account(adto.userId,accountType);
		return adao.insert(a);
		
	}
	
	public String createInterest(int authUserId) {
		
		AccountTransactionDTO tdto = new AccountTransactionDTO();
		List<Account> accounts = adao.findAllInterestBearingAccounts();
		float totalInterest = 0;
				
		for(Account a: accounts) {			
			float rate = a.getType().getAcctRate()/12;
			float interest = a.getBalance() * rate;
			String rateString = fpercent.format(a.getType().getAcctRate());
						
			tdto.memo = "INTEREST ( " + rateString + " APR )";
			tdto.account = a;
			tdto.amount = interest;
			tdto.userId = authUserId;
			tdto.type = 'D';
			ts.createTransaction(tdto);
			
			totalInterest += interest;
		}
		
		if(totalInterest > 0) {
			return fcurrency.format(totalInterest);
		}else {
			return null;
		}
		
	}
	
	public List<Account> findAll() {
		return adao.findAll();
	}
	
	public Account findById(int id) {
		return adao.findById(id);
	}
	
	public List<Account> findAllByStatusId(int id) {
		return adao.findAllByStatusId(id);
	}
	
	public List<Account> findAllByUserId(int id) {
		return adao.findAllByUserId(id);
	}
	
	public Account updateAccount(AccountDTO adto) {
		
		Account a = adao.findById(adto.accountId);
		
		if(a.getStatus().getAccountStatusId() == adto.statusId) {
			return null;
		}else {
			a.setStatus(asdao.findById(adto.statusId));
			return adao.updateStatus(a);
		}		
		
	}
}