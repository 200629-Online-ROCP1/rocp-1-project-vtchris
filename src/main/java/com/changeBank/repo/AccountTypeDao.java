package com.changeBank.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import com.changeBank.models.accounts.AccountType;
import com.changeBank.utils.ConnectionUtil;

public class AccountTypeDao implements Dao<AccountType> {
	
	// This is a design pattern called a "singleton" where only one implementation 
	// of a class can exist at a time.
	// Not set up for multi-threads
	private static AccountTypeDao repo = new AccountTypeDao();
	private AccountTypeDao() {}
	public static AccountTypeDao getInstance() {
		return repo;
	}

	@Override
	public boolean insert(AccountType t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(AccountType t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(AccountType t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public AccountType findById(int id) {
		System.out.println("Looking Up Account Type by id");
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM accountTypes WHERE acct_typ_id = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1,id);
			
			ResultSet result = statement.executeQuery();
			
			if(result.next()) {
				return new AccountType(
					result.getInt("acct_typ_id"), 
					result.getString("acct_typ_name"),
					result.getDouble("acct_rate"));					
			}
			
		}catch(SQLException e) {
			System.out.println(e);
		}
		return null;	
	}

	@Override
	public Set<AccountType> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
