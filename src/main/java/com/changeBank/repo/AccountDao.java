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
	private static final AccountDao repo = new AccountDao();
	private static final AccountStatusDao asdao = AccountStatusDao.getInstance();
	private static final AccountTypeDao atdao = AccountTypeDao.getInstance();
	private static final UserDao udao = UserDao.getInstance();
	
	private AccountDao() {}
	public static AccountDao getInstance() {
		return repo;
	}
	
	public boolean deleteAccountById(Account a, int authUserId) {
		//First, copy account to archive
		try(Connection conn = ConnectionUtil.getConnection();){
			String sql = "INSERT INTO archive_accounts "
					+ "(account_id,user_id_fk, acct_nbr,balance,"
					+ "acct_status_id_fk,acct_typ_id_fk,created_at,deleted_by) "
					+ "SELECT  account_id,user_id_fk,acct_nbr,balance, "
					+ "acct_status_id_fk,acct_typ_id_fk,created_at, ? " 
					+ "FROM accounts "
					+ "WHERE account_id = ?;";						
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1,authUserId);
			statement.setInt(2,a.getAccountId());
			
			int iCount = statement.executeUpdate();
			
			sql = 	"DELETE "
					+ "FROM accounts "
					+ "WHERE account_id = ?;";						
			statement = conn.prepareStatement(sql);
			statement.setInt(1,a.getAccountId());
			
			int dCount = statement.executeUpdate();
			
//			System.out.println(iCount);
//			System.out.println(dCount);
			
			//Did you delete the same # of transactions as you moved to archive
			return iCount == dCount;
							
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public Account insert(Account account) {
		//System.out.println("Inserting New Account");
				
		account.setAcctNbr(atdao.getNextAccountNumber(account.getType().getAccountTypeId()));
		
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
	
	// Connection is passed to this method to make sure it is include in transaction/rollback
	protected boolean updateBalance(Connection conn, int id, float bal) {
		//System.out.println("Updating Balance");
		//Connection conn = ConnectionUtil.getConnection()
		try{
			String sql = "UPDATE accounts SET balance = ? WHERE account_id = ?;";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setFloat(1, bal);
			statement.setInt(2, id);
			
			// Test Transaction rollback
			//int test = 10/0;
			
			int rows = statement.executeUpdate();
			
			if(rows > 0) {
				return true;
			}else {
				return false;
			}			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public Account updateStatus(Account account) {
		//System.out.println("Updating Status");
		
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
	public boolean delete(Account t, int id) {
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
			String sql = "SELECT * FROM accounts WHERE account_id = ? ORDER BY acct_nbr;";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1,id);
			
			ResultSet result = statement.executeQuery();
			
			while(result.next()) {
				return new Account(
						result.getInt("account_id"), 
						result.getInt("user_id_fk"),
						findUserById(result.getInt("user_id_fk")),
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
	public List<Account>  findAllInterestBearingAccounts() {
		
		List<Account> accounts = new ArrayList<>();
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql =  "SELECT account_id "
						+ "FROM account_types t "
						+ "INNER JOIN accounts a ON t.acct_typ_id = a.acct_typ_id_fk "
						+ "WHERE acct_rate > 0 AND balance > 0;";
			PreparedStatement statement = conn.prepareStatement(sql);
			
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()) {
				accounts.add(this.findById(rs.getInt("account_id")));
			}
			
			return accounts;
			
		}catch(SQLException e) {
			System.out.println(e);
		}
	
		return null;
		
	}
	
	public List<Account>  findAllByStatusId(int id) {
		//System.out.println("Looking Up Account by Status id");
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM accounts WHERE acct_status_id_fk = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1,id);
			
			List<Account> accounts = new ArrayList<>();
			
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()) {
				Account a = new Account(
						rs.getInt("account_id"), 
						rs.getInt("user_id_fk"),
						this.findUserById(rs.getInt("user_id_fk")),
						rs.getInt("acct_nbr"),
						rs.getFloat("balance"),
						this.findAccountStatusById(rs.getInt("acct_status_id_fk")),
						this.findAccountTypeById(rs.getInt("acct_typ_id_fk")));
				
				accounts.add(a);
						
			}
			return accounts;
			
		}catch(SQLException e) {
			System.out.println(e);
		}
		return null;
	}
	
	public List<Account> findAllByUserId(int id) {
		System.out.println("Looking Up Account by User id");
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM accounts WHERE user_id_fk = ? ORDER BY acct_nbr;";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1,id);
			
			List<Account> accounts = new ArrayList<>();
			
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()) {
				Account a = new Account(
						rs.getInt("account_id"), 
						rs.getInt("user_id_fk"),
						this.findUserById(rs.getInt("user_id_fk")),
						rs.getInt("acct_nbr"),
						rs.getFloat("balance"),
						this.findAccountStatusById(rs.getInt("acct_status_id_fk")),
						this.findAccountTypeById(rs.getInt("acct_typ_id_fk")));
				
				accounts.add(a);
						
			}
			return accounts;
			
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
		AccountType accountType = atdao.findById(id);	
		return accountType;
	}
	
	private AccountStatus findAccountStatusById(int id) {
		AccountStatus accountStatus = asdao.findById(id);	
		return accountStatus;
	}
	
	private User findUserById(int id) {
		return udao.findById(id);
	}
	
		
}