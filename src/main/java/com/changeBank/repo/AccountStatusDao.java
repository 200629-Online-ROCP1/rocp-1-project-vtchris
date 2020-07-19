package com.changeBank.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import com.changeBank.models.accounts.AccountStatus;
import com.changeBank.utils.ConnectionUtil;

public class AccountStatusDao implements IDao<AccountStatus> {
		// This is a design pattern called a "singleton" where only one implementation 
		// of a class can exist at a time.
		// Not set up for multi-threads
		private static AccountStatusDao repo = new AccountStatusDao();
		private AccountStatusDao() {}
		public static AccountStatusDao getInstance() {
			return repo;
		}
		
		@Override
		public AccountStatus insert(AccountStatus t) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public boolean update(AccountStatus t) {
			// TODO Auto-generated method stub
			return false;
		}
		@Override
		public boolean delete(AccountStatus t) {
			// TODO Auto-generated method stub
			return false;
		}
		@Override
		public AccountStatus findById(int id) {
			System.out.println("Looking Up Account Status by id");
			
			try(Connection conn = ConnectionUtil.getConnection()){
				String sql = "SELECT * FROM account_status WHERE acct_status_id = ?";
				PreparedStatement statement = conn.prepareStatement(sql);
				statement.setInt(1,id);
				
				ResultSet result = statement.executeQuery();
				
				if(result.next()) {
					return new AccountStatus(
						result.getInt("acct_status_id"), 
						result.getString("acct_status"));							
				}
				
			}catch(SQLException e) {
				System.out.println(e);
			}
			return null;
		}
		@Override
		public Set<AccountStatus> selectAll() {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public List<AccountStatus> findAll() {
			// TODO Auto-generated method stub
			return null;
		}
}
