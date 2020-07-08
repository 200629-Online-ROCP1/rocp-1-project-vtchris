package models.accounts;

public class AccountType {
	
	private int typeId;
	private String type;

	public AccountType() {
		super();
	}
	
	public AccountType(int accountTypeId, String accountTypeName) {
		super();
		this.typeId = accountTypeId;
		this.type = accountTypeName;
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

	
}
