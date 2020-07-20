package com.changeBank.models.accounts;

import java.sql.Date;

public class AccountTransaction {
	
	private int		transactionId;
	private int 	accountId;
	private int 	acctNbr;
	private char	type;
	private float	debit;			
	private float	credit;
	private float	signedAmount;
	private float	runningBalance;
	private char	status;
	private String	memo;
	private int		userId; //Initiated transaction
	private Date 	transactionDt;
	private Account	targetAccount; //Only Populated for type=T transfers
	
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

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getTransactionDt() {
		return transactionDt;
	}

	public void setTransactionDt(Date transactionDt) {
		this.transactionDt = transactionDt;
	}

	public char getType() {
		return type;
	}

	public void setType(char type) {
		this.type = type;
	}

	public Account getTargetAccount() {
		return targetAccount;
	}

	public void setTargetAccount(Account targetAccount) {
		this.targetAccount = targetAccount;
	}

	public int getAcctNbr() {
		return acctNbr;
	}

	public void setAcctNbr(int acctNbr) {
		this.acctNbr = acctNbr;
	}

	@Override
	public String toString() {
		return "AccountTransaction [transactionId=" + transactionId + ", accountId=" + accountId + ", acctNbr="
				+ acctNbr + ", type=" + type + ", debit=" + debit + ", credit=" + credit + ", signedAmount="
				+ signedAmount + ", runningBalance=" + runningBalance + ", status=" + status + ", memo=" + memo
				+ ", userId=" + userId + ", transactionDt=" + transactionDt + ", targetAccount=" + targetAccount + "]";
	}	
	
}