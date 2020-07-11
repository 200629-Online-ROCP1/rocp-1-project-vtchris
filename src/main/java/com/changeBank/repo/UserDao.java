package com.changeBank.repo;

import com.changeBank.models.users.User;
import com.changeBank.utils.ConnectionUtil;
import com.changeBank.utils.HashUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import javax.security.auth.login.LoginException;

public class UserDao implements Dao<User> {	

	// This is a design pattern called a "singleton" where only one implementation 
	// of a class can exist at a time.
	// Not set up for multi-threads
	private static UserDao repo = new UserDao();
	private UserDao() {}
	public static UserDao getInstance() {
		return repo;
	}
	
	@Override
	public boolean insert(User user) {
		System.out.println("Inserting New User");
		
		user.setPassword(hashPassword(user.getPassword()));

		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "INSERT INTO users (first_name,last_name,username,pword,email,role_id_fk) "
					+ "VALUES (?,?,?,?,?,?);";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1,user.getFirstName().toUpperCase());
			statement.setString(2,user.getLastName().toUpperCase());
			statement.setString(3,user.getUsername().toLowerCase());
			statement.setString(4,user.getPassword());
			statement.setString(5,user.getEmail().toLowerCase());
			statement.setInt(6,user.getRole().getRoleId());

			statement.executeQuery();
			
			return true;

		}catch(SQLException e) {
			System.out.println(e);
		}
		return false;
	}

	@Override
	public boolean delete(User t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User findById(int id) {
		System.out.println("Looking Up User by id");
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM users WHERE user_id = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1,id);
			
			ResultSet result = statement.executeQuery();
			
			if(result.next()) {
				return new User(
						result.getInt("user_id"), 
						result.getString("username"),
						result.getString("first_name"), 
						result.getString("last_name"),
						result.getString("email"));
			}
			
		}catch(SQLException e) {
			System.out.println(e);
		}
		return null;		
	}

	@Override
	public Set<User> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public User login(UserLoginDTO login) {	
		System.out.println("Attempting to login");
		
		login.setPassword(hashPassword(login.getPassword()));
		
		try(Connection conn = ConnectionUtil.getConnection()){			
			String sql = "SELECT * FROM users WHERE username = ? AND pword = ?;";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1,login.getUsername());
			statement.setString(2,login.getPassword());
			
			ResultSet result = statement.executeQuery();
			
			if(result.next()) {
				return new User(
						result.getInt("user_id"), 
						result.getString("username"),
						result.getString("first_name"), 
						result.getString("last_name"),
						result.getString("email"));
			}else{
				throw new LoginException("Username or password do not match.");
			}
			
			
		}catch(SQLException e) {
			System.out.println(e);	
			
		}catch(LoginException e) {
			System.out.println(e);
		}
		return null;
	}
	
	private String hashPassword(String password) {
		
		try {
			password = HashUtil.getHash(password);
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;
		}
		
		return password;
	}

}
