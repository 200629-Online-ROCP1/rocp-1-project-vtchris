package com.changeBank.models.accounts;

import com.changeBank.models.users.UserDTO;

public class AccountDTO {
	public int accountId; 	
	public int acctNbr;
	public float balance; 
	public int statusId;
	public AccountStatus status;
	public int typeId;	
	public AccountType type;	
	public int userId;
	public UserDTO user;
}