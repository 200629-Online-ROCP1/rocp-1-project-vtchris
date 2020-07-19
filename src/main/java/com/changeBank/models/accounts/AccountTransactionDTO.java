package com.changeBank.models.accounts;

public class AccountTransactionDTO {
	
	public int accountId;
	public Account account;
	public float amount;
	public char type; //D = Deposit ,W = Withdrawl,T = Transfer
	public int targetAccountId;
	public int userId;

}
