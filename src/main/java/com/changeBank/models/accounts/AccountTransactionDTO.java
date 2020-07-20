package com.changeBank.models.accounts;

public class AccountTransactionDTO {
	
	public int accountId;
	public int acctNbr;
	public Account account;
	public float amount;
	public char type; //D = Deposit ,W = Withdrawl,T = Transfer
	public int targetAccountId;
	public Account	targetAccount; //Only Populated for type=T transfers
	public int userId;
	
}
