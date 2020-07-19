package com.changeBank.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.changeBank.models.accounts.Account;
import com.changeBank.models.accounts.AccountStatus;
import com.changeBank.models.accounts.AccountType;
import com.changeBank.models.users.User;
import com.changeBank.utils.ConnectionUtil;


public class AccountDao implements IDao<Account> {
	
	// This is a design pattern called a "singleton" where only one implementation 
	// of a class can exist at a time.
	// Not set up for multi-threads
	private static AccountDao repo = new AccountDao();
	private UserDao udao = UserDao.getInstance();
	
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
			statement.setInt(4,1);
			statement.setInt(5,account.getType().getAccountTypeId());
			
			int rows = statement.executeUpdate();
			
			if(rows > 0) {
				
				ResultSet keys = statement.getGeneratedKeys();
				
				while(keys.next()) {
					account = findById((int)keys.getInt(1));
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
			account = findById(account.getAccountId());
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
	public List<Account> findAll() {
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM accounts ORDER BY acct_nbr;";
			Statement statement = conn.createStatement();
			
			List<Account> accounts = new ArrayList<>();
						
			ResultSet rs = statement.executeQuery(sql);
			
			while(rs.next()) {
				Account a = new Account();
				a.setAccountId(rs.getInt("account_id"));
				a.setAcctNbr(rs.getInt("acct_nbr"));
				a.setBalance(rs.getFloat("balance"));
				a.setStatus(findAccountStatusById(rs.getInt("acct_status_id_fk")));
				a.setType(findAccountTypeById(rs.getInt("acct_typ_id_fk")));
				a.setUser(findUserById(rs.getInt("user_id_fk")));
				System.out.println(a.getUser().toString());
				
				accounts.add(a);
			}
			
			return accounts;
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Account findById(int id) {
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
						findAccountStatusById(result.getInt("acct_status_id_fk")),
						findAccountTypeById(result.getInt("acct_typ_id_fk")));
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
	
	private AccountType findAccountTypeById(int id) {
		AccountTypeDao accountTypeDao = AccountTypeDao.getInstance();
		AccountType accountType = accountTypeDao.findById(id);	
		return accountType;
	}
	
	private AccountStatus findAccountStatusById(int id) {
		AccountStatusDao accountStatusDao = AccountStatusDao.getInstance();
		AccountStatus accountStatus = accountStatusDao.findById(id);	
		return accountStatus;
	}
	
	private User findUserById(int id) {
		return udao.findById(id);
	}
	
	
}
