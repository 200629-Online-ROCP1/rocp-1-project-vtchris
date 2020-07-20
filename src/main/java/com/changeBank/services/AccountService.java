package com.changeBank.services;

import java.util.List;

import com.changeBank.models.accounts.Account;
import com.changeBank.models.accounts.AccountDTO;
import com.changeBank.models.accounts.AccountType;
import com.changeBank.repo.AccountDao;
import com.changeBank.repo.AccountStatusDao;
import com.changeBank.repo.AccountTypeDao;

public class AccountService {
	
	private AccountDao adao = AccountDao.getInstance();
	private AccountStatusDao asdao = AccountStatusDao.getInstance();
	private AccountTypeDao atdao = AccountTypeDao.getInstance();
	
	public Account createAccount(AccountDTO adto) {
		
		AccountType accountType =  atdao.findById(adto.typeId);
		Account a = new Account(adto.userId,accountType);
		return adao.insert(a);
		
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