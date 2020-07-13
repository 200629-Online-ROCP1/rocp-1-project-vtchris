package com.changeBank.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;

import com.changeBank.models.accounts.Account;
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
	public boolean insert(Account account) {
		System.out.println("Inserting New Account");
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "INSERT INTO accounts (user_id_fk,acct_nbr,balance,acct_status_id_fk,acct_typ_id_fk) "
					+ "VALUES (?,?,?,?,?,?);";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1,account.getUserId());
			statement.setInt(2,account.getAcctNbr());
			statement.setDouble(3,account.getBalance());
			statement.setInt(4,account.getStatus().getAccountStatusId());
			statement.setInt(5,account.getType().getAccountTypeId());
			
			statement.executeQuery();
			
			return true;

		}catch(SQLException e) {
			System.out.println(e);
		}
		return false;
	
	}
	@Override
	public boolean update(Account t) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean delete(Account t) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Account findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Set<Account> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
