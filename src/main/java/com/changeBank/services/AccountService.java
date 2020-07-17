package com.changeBank.services;

import com.changeBank.models.accounts.Account;
import com.changeBank.models.accounts.AccountDTO;
import com.changeBank.models.accounts.AccountType;
import com.changeBank.repo.AccountDao;
import com.changeBank.repo.AccountStatusDao;
import com.changeBank.repo.AccountTypeDao;

public class AccountService {
	
	public void CreateAccount(AccountDTO accountData) {
		
		AccountTypeDao accountTypeDAO = AccountTypeDao.getInstance();
		AccountType accountType =  accountTypeDAO.getById(accountData.getType());
		AccountDao accountDAO = AccountDao.getInstance();
		Account account = new Account(accountData.getUserId(),accountType);
		account = accountDAO.insert(account);
		System.out.println(account.toString());
		
	}
	
//	public void CreateAccountTransaction(AccountTransactionDTO accountTransactionData) {
//		
//	}
	
	public void UpdateStatus(AccountDTO accountData) {
		
		AccountStatusDao accountStatusDao = AccountStatusDao.getInstance();
		AccountDao accountDAO = AccountDao.getInstance();
		Account account = new Account(accountData.getAccountId(),accountStatusDao.getById(accountData.getStatus()));
		account = accountDAO.updateStatus(account);
		System.out.println(account.toString());
		
	}
	


}
