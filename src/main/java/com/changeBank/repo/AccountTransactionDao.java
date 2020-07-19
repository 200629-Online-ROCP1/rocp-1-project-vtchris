package com.changeBank.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Set;

import com.changeBank.models.accounts.AccountTransaction;
import com.changeBank.utils.ConnectionUtil;

public class AccountTransactionDao implements IDao<AccountTransaction> {
		// This is a design pattern called a "singleton" where only one implementation 
		// of a class can exist at a time.
		// Not set up for multi-threads
		private static AccountTransactionDao repo = new AccountTransactionDao();
		private AccountTransactionDao() {}
		public static AccountTransactionDao getInstance() {
			return repo;
		}
		@Override
		public AccountTransaction insert(AccountTransaction t) {
			System.out.println("Inserting New Transaction");
			
			try(Connection conn = ConnectionUtil.getConnection()){
				String sql = "INSERT INTO account_transactions (account_id_fk,debit,credit,signed_amount,running_balance,status,memo,user_id_fk) "
						+ "VALUES (?,?,?,?,?,?,?,?);";
				PreparedStatement statement = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
				statement.setInt(1, t.getAccountId());
				statement.setFloat(2, t.getDebit());
				statement.setFloat(3, t.getCredit());
				statement.setFloat(4, t.getSignedAmount());
				statement.setFloat(5, t.getRunningBalance());
				statement.setInt(6, t.getStatus());
				statement.setString(7, t.getMemo());
				statement.setInt(8, t.getUserId());
			
				int rows = statement.executeUpdate();
				
				if(rows > 0) {
					ResultSet rs = statement.getGeneratedKeys();
					while(rs.next()) {	
						t.setTransactionId(rs.getInt(1));
					}
					return t;
				}else {
					return null;
				}
			
			}catch(SQLException e) {				
				System.out.println(e);							
			}
			return null;
			
		}
		@Override
		public boolean update(AccountTransaction t) {
			// TODO Auto-generated method stub
			return false;
		}
		@Override
		public boolean delete(AccountTransaction t) {
			// TODO Auto-generated method stub
			return false;
		}
		@Override
		public AccountTransaction findById(int id) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public Set<AccountTransaction> selectAll() {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public List<AccountTransaction> findAll() {
			// TODO Auto-generated method stub
			return null;
		}
		public boolean createTransaction() {
			// TODO Auto-generated method stub
			return false;
		}
}
