package com.changeBank.models.accounts;

public class AccountStatus {
	
	private int statusId;
	private String status;

	public AccountStatus() {
		super();		
	}

	public AccountStatus(int accountStatusId, String accountStatus) {
		super();
		this.statusId = accountStatusId;
		this.status = accountStatus;
	}

	public int getAccountStatusId() {
		return statusId;
	}

	public void setAccountStatusId(int accountStatusId) {
		this.statusId = accountStatusId;
	}

	public String getAccountStatus() {
		return status;
	}

	public void setAccountStatus(String accountStatus) {
		this.status = accountStatus;
	}

	@Override
	public String toString() {
		return "AccountStatus [statusId=" + statusId + ", status=" + status + "]";
	}
		

}
