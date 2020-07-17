package com.changeBank.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;

import com.changeBank.models.accounts.Account;
import com.changeBank.models.accounts.AccountStatus;
import com.changeBank.models.accounts.AccountType;
import com.changeBank.utils.ConnectionUtil;


public class AccountDao implements Dao<Account> {
	
	// This is a design pattern called a "singleton" where only one implementation 
	// of a class can exist at a time.
	// Not set up for multi-threads
	private static AccountDao repo = new AccountDao();
	private AccountDao() {}
	public static AccountDao getInstance() {
		return repo;
	}
	
	@Override
	public Account insert(Account account) {
		System.out.println("Inserting New Account");
		
		AccountTypeDao accountTypeDAO = AccountTypeDao.getInstance();
		account.setAcctNbr(accountTypeDAO.getNextAccountNumber(account.getType().getAccountTypeId()));
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "INSERT INTO accounts (user_id_fk,acct_nbr,balance,acct_status_id_fk,acct_typ_id_fk) "
					+ "VALUES (?,?,?,?,?);";
			PreparedStatement statement = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1,account.getUserId());
			statement.setInt(2,account.getAcctNbr());
			statement.setFloat(3,account.getBalance());
			//statement.setInt(4,account.getStatus().getAccountStatusId());
			statement.setInt(4,1);
			statement.setInt(5,account.getType().getAccountTypeId());
			
			//ResultSet resultSet = statement.executeUpate();
			int rows = statement.executeUpdate();
			
			if(rows > 0) {
				
				ResultSet keys = statement.getGeneratedKeys();
				
				while(keys.next()) {
					account = getById((int)keys.getInt(1));
				}
				
				keys.close();
		            
			}
			
			return account;

		}catch(SQLException e) {
			System.out.println(e);
		}
		return null;
	
	}
	@Override
	public boolean update(Account t) {
		// TODO Auto-generated method stub
		return false;
	}
	public Account updateStatus(Account account) {
		System.out.println("Updating Status");
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "UPDATE accounts SET acct_status_id_fk = ? WHERE account_id = ?";
			PreparedStatement statement = conn.prepareStatement(sql);			
			statement.setInt(1,account.getStatus().getAccountStatusId());
			statement.setInt(2,account.getAccountId());
			
			statement.executeUpdate();
			account = getById(account.getAccountId());
			return account;
		}catch(SQLException e) {
			System.out.println(e);
		}
		return null;		
	}
	@Override
	public boolean delete(Account t) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Account getById(int id) {
		System.out.println("Looking Up Account by id");
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM accounts WHERE account_id = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1,id);
			
			ResultSet result = statement.executeQuery();
			
			if(result.next()) {
				return new Account(
						result.getInt("account_id"), 
						result.getInt("user_id_fk"),
						result.getInt("acct_nbr"),
						result.getFloat("balance"),
						getAccountStatusById(result.getInt("acct_status_id_fk")),
						getAccountTypeById(result.getInt("acct_typ_id_fk")));
			}
			
		}catch(SQLException e) {
			System.out.println(e);
		}
		return null;
	}
	@Override
	public Set<Account> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private AccountType getAccountTypeById(int id) {
		AccountTypeDao accountTypeDao = AccountTypeDao.getInstance();
		AccountType accountType = accountTypeDao.getById(id);	
		return accountType;
	}
	
	private AccountStatus getAccountStatusById(int id) {
		AccountStatusDao accountStatusDao = AccountStatusDao.getInstance();
		AccountStatus accountStatus = accountStatusDao.getById(id);	
		return accountStatus;
	}

}
