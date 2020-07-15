package com.changeBank.repo;

import com.changeBank.models.users.Role;
import com.changeBank.models.users.User;
import com.changeBank.models.users.UserLoginDTO;
import com.changeBank.utils.ConnectionUtil;
import com.changeBank.utils.HashUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
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
	public User insert(User user) {
		System.out.println("Inserting New User");
		
		user.setPassword(hashPassword(user.getPassword()));

		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "INSERT INTO users (first_name,last_name,username,pword,email,role_id_fk) "
					+ "VALUES (?,?,?,?,?,?);";
			PreparedStatement statement = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			statement.setString(1,user.getFirstName().toUpperCase());
			statement.setString(2,user.getLastName().toUpperCase());
			statement.setString(3,user.getUsername().toLowerCase());
			statement.setString(4,user.getPassword());
			statement.setString(5,user.getEmail().toLowerCase());
			statement.setInt(6,user.getRole().getRoleId());

			int rows = statement.executeUpdate();
			
			if(rows > 0) {
				ResultSet rs = statement.getGeneratedKeys();
				
				while(rs.next()) {					
					User newUser = new User();
					newUser = getById(rs.getInt(1));
					newUser.setRole(user.getRole());
					return newUser;
				}
			}else {
				return null;
			}
			
			return user;

		}catch(SQLException e) {
			System.out.println(e);
		}
		return null;
	}

	@Override
	public boolean delete(User t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User getById(int id) {
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
						result.getString("pword"),
						result.getString("first_name"), 
						result.getString("last_name"),
						result.getString("email"),
						getRoleById(result.getInt("role_id_fk")));
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
	
	public User authenticate(UserLoginDTO login)  {	
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
						result.getString("pword"), 						
						result.getString("first_name"), 
						result.getString("last_name"),
						result.getString("email"),
						getRoleById(result.getInt("role_id_fk")));
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
	@Override
	public boolean update(User user) {
		System.out.println("Updating");
		
		if(user.getPasswordNew().length() > 2 ) {
			user.setPassword(hashPassword(user.getPasswordNew()));
		}
		
		
		try(Connection conn = ConnectionUtil.getConnection()){			
			String sql = "UPDATE users SET first_name = ?, last_name = ?, pword = ?, email = ?, role_id_fk = ? WHERE user_id = ?;";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1,user.getFirstName().toUpperCase());
			statement.setString(2,user.getLastName().toUpperCase());
			statement.setString(3,user.getPassword());
			statement.setString(4,user.getEmail().toLowerCase());
			statement.setInt(5,user.getRole().getRoleId());
			statement.setInt(6,user.getUserId());
			
			statement.executeUpdate();		
			
			return true;
						
		}catch(SQLException e) {
			System.out.println(e);	
			
		}
		return false;
	}
	private Role getRoleById(int id) {
		RoleDao roleDao = RoleDao.getInstance();
		Role role = roleDao.getById(id);	
		return role;
	}

}
