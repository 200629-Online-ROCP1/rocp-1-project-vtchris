package com.changeBank.models.accounts;

public class Account {
	private int accountId; // primary key
	private int userId;
	private int acctNbr;
	private double balance;  // not null
	private AccountStatus status;
	private AccountType type;
		
	public Account() {
		super();
	}	

	public Account(int userId, AccountType type) {
		super();
		this.userId = userId;
		this.type = type;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getAcctNbr() {
		return acctNbr;
	}

	public void setAcctNbr(int acctNbr) {
		this.acctNbr = acctNbr;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public AccountStatus getStatus() {
		return status;
	}

	public void setStatus(AccountStatus status) {
		this.status = status;
	}

	public AccountType getType() {
		return type;
	}

	public void setType(AccountType type) {
		this.type = type;
	}

}
