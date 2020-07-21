package com.changeBank.models.accounts;

public class AccountType {
	
	private int typeId;
	private String type;
	private float acctRate;
	
	public AccountType() {
		super();
	}
	
	public AccountType(int accountTypeId, String accountTypeName, float acctRate) {
		super();
		this.typeId = accountTypeId;
		this.type = accountTypeName;
		this.acctRate = acctRate;
	}
	
	public float getAcctRate() {
		return acctRate;
	}

	public void setAcctRate(float acctRate) {
		this.acctRate = acctRate;
	}

	public int getAccountTypeId() {
		return typeId;
	}

	public void setAccountTypeId(int accountTypeId) {
		this.typeId = accountTypeId;
	}

	public String getAccountTypeName() {
		return type;
	}

	public void setAccountTypeName(String accountTypeName) {
		this.type = accountTypeName;
	}

	@Override
	public String toString() {
		return "AccountType [typeId=" + typeId + ", type=" + type + ", acctRate=" + acctRate + "]";
	}

	
}
