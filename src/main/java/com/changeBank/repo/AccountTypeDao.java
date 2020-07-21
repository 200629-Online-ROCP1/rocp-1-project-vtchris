package com.changeBank.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import com.changeBank.models.accounts.AccountType;
import com.changeBank.utils.ConnectionUtil;

public class AccountTypeDao implements IDao<AccountType> {
	
	// This is a design pattern called a "singleton" where only one implementation 
	// of a class can exist at a time.
	// Not set up for multi-threads
	private static AccountTypeDao repo = new AccountTypeDao();
	private AccountTypeDao() {}
	public static AccountTypeDao getInstance() {
		return repo;
	}

	@Override
	public AccountType insert(AccountType t) {
		// TODO Auto-generated method stub
		return null;
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
		//System.out.println("Looking Up Account Type by id");
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM account_types WHERE acct_typ_id = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1,id);
			
			ResultSet rs = statement.executeQuery();
			
			if(rs.next()) {
				return new AccountType(
					rs.getInt("acct_typ_id"), 
					rs.getString("acct_typ_name"),
					rs.getFloat("acct_rate"));					
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;	
	}

	@Override
	public Set<AccountType> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int getNextAccountNumber(int acct_typ_id) {		
		//System.out.println("Looking Up next Account Number");
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "UPDATE account_types "
					+ "SET acct_last_nbr = acct_last_nbr + 1 "  
					+ "WHERE acct_typ_id = ? "
					+ "RETURNING acct_last_nbr;";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, acct_typ_id);
			
			ResultSet rs = statement.executeQuery();
			
			if(rs.next()) {
				return rs.getInt("acct_last_nbr");			
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return 0;	
		
	}
	@Override
	public List<AccountType> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
