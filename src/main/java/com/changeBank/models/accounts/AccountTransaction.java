package com.changeBank.models.accounts;

import java.sql.Date;

public class AccountTransaction {
	
	private int		transactionId;
	private int 	accountId;
	private float	debit;			
	private float	credit;
	private float	signedAmount;
	private float	runningBalance;
	private String	status;
	private String	memo;
	private String	userId; //Initiated transaction
	private Date 	transactionDt;
	
	public AccountTransaction() {
		super();
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public float getDebit() {
		return debit;
	}

	public void setDebit(float debit) {
		this.debit = debit;
	}

	public float getCredit() {
		return credit;
	}

	public void setCredit(float credit) {
		this.credit = credit;
	}

	public float getSignedAmount() {
		return signedAmount;
	}

	public void setSignedAmount(float signedAmount) {
		this.signedAmount = signedAmount;
	}

	public float getRunningBalance() {
		return runningBalance;
	}

	public void setRunningBalance(float runningBalance) {
		this.runningBalance = runningBalance;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getTransactionDt() {
		return transactionDt;
	}

	public void setTransactionDt(Date transactionDt) {
		this.transactionDt = transactionDt;
	}
	
}
