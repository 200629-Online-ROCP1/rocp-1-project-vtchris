package com.changeBank.services;

import com.changeBank.models.accounts.Account;
import com.changeBank.models.accounts.AccountTransaction;
import com.changeBank.models.accounts.AccountTransactionDTO;
import com.changeBank.repo.AccountTransactionDao;

public class AccountTransactionService {
	
	private static final AccountTransactionDao tdao = AccountTransactionDao.getInstance();

	public boolean createTransaction(AccountTransactionDTO tdto) {
		AccountTransaction t = new AccountTransaction();
		t.setAccountId(tdto.account.getAccountId());
		t.setStatus('A');
		t.setUserId(tdto.userId);
				
		if(tdto.type == 'D') {
			t.setCredit(tdto.amount);
			t.setDebit(0);
			if(tdto.memo == null) {
				t.setMemo("DEPOSIT");
			}else {
				t.setMemo(tdto.memo);
			}			
			t.setRunningBalance(tdto.account.getBalance() + tdto.amount);
			t.setSignedAmount(tdto.amount);	
			t.setType('D');
		}else if(tdto.type == 'W') {
			t.setCredit(0);
			t.setDebit(tdto.amount);
			t.setMemo("WITHDRAWL");
			t.setRunningBalance(tdto.account.getBalance() - tdto.amount);
			t.setSignedAmount(tdto.amount * -1);	
			t.setType('W');
		}else {			
			t.setTargetAccount(tdto.targetAccount);
			t.setAcctNbr(tdto.acctNbr);
			t.setCredit(0);
			t.setDebit(tdto.amount);
			t.setMemo("TRANSFER TO #" + tdto.targetAccount.getAcctNbr());
			t.setRunningBalance(tdto.account.getBalance() - tdto.amount);
			t.setSignedAmount(tdto.amount * -1);	
			t.setType('T');
		}
				
		if(tdao.insert(t) != null) {
			return true;
		}else {
			return false;
		}		
	
	}

	public boolean deleteAccountTransactions(Account a, int authUserId) {
		AccountTransaction t = new AccountTransaction();
		t.setAccountId(a.getAccountId());
		t.setUserId(authUserId);
		
		return tdao.deleteAccountTransactions(t);		
		
	}

}
