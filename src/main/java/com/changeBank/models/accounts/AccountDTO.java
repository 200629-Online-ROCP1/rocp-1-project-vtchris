package com.changeBank.models.accounts;

public class AccountDTO {
	private int accountId; 
	private int userId;
	private int acctNbr;
	private float balance; 
	private int status;
	private int type;
	
	public AccountDTO() {
		super();
	}

	public AccountDTO(int userId, int type, float balance) {
		super();
		this.userId = userId;
		this.balance = balance;
		this.type = type;
	}
	
	public AccountDTO(int accountId, int status) {
		super();
		this.accountId = accountId;
		this.status = status;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
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

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	

	

}
