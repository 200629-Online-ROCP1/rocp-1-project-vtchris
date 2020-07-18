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
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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
		
		user.setPassword(hashPassword(user.getPassword().trim()));

		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "INSERT INTO users (first_name,last_name,username,pword,email,role_id_fk) "
					+ "VALUES (?,?,?,?,?,?);";
			PreparedStatement statement = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			statement.setString(1,user.getFirstName().trim().toUpperCase());
			statement.setString(2,user.getLastName().trim().toUpperCase());
			statement.setString(3,user.getUsername().trim().toLowerCase());
			statement.setString(4,user.getPassword());
			statement.setString(5,user.getEmail().trim().toLowerCase());
			statement.setInt(6,user.getRole().getRoleId());

			int rows = statement.executeUpdate();
			
			if(rows > 0) {
				ResultSet rs = statement.getGeneratedKeys();
				
				while(rs.next()) {					
					User newUser = new User();
					newUser = findById(rs.getInt(1));
					newUser.setRole(user.getRole());
					return newUser;
				}
			}else {
				return null;
			}
			
		}catch(SQLException e) {
			if(e.getSQLState().equals("23505")) {
				System.out.println(e.getLocalizedMessage());	
			}else {
				System.out.println(e);
			}			
		}
		return null;
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
	
	public User findByEmail(String email) {
		System.out.println("Looking Up User by email");
		
		email = email.trim().toLowerCase();
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM users WHERE email = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1,email);
		
			ResultSet rs = statement.executeQuery();
			
			if(rs.next()) {
				return getPopulatedModel(rs);
			}
			
		}catch(SQLException e) {
			System.out.println(e);
		}
		return null;	
		
	}
	
	public User findByUsername(String username) {
		System.out.println("Looking Up User by username");
		
		username = username.trim().toLowerCase();
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM users WHERE username = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1,username);
			
			ResultSet rs = statement.executeQuery();
			
			if(rs.next()) {
				return getPopulatedModel(rs);
			}
			
		}catch(SQLException e) {
			System.out.println(e);
		}
		return null;	
		
	}
	
	public List<User> findAll(){
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM users ORDER BY last_name, first_name;";
			Statement statement = conn.createStatement();
			
			List<User> users = new ArrayList<>();
						
			ResultSet rs = statement.executeQuery(sql);
			
			while(rs.next()) {
				User u = new User();
				u.setUserId(rs.getInt("user_id"));
				u.setFirstName(rs.getString("first_name"));
				u.setLastName(rs.getString("last_name"));
				u.setUsername(rs.getString("username"));
				u.setPassword(rs.getString("pword"));
				u.setEmail(rs.getString("email"));
				u.setRole(getRoleById(rs.getInt("role_id_fk")));
				
				users.add(u);
			}
			
			return users;
			
		}catch(SQLException e) {
			e.printStackTrace();
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
		
		login.password = hashPassword(login.password);
		
		try(Connection conn = ConnectionUtil.getConnection()){			
			String sql = "SELECT * FROM users WHERE username = ? AND pword = ?;";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1,login.username);
			statement.setString(2,login.password);
			
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
		
		if(user.getPasswordNew() != null && user.getPasswordNew().length() > 3 ) {
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
		Role role = roleDao.findById(id);	
		return role;
	}
	private User getPopulatedModel(ResultSet rs) {
			
			try {
				return new User(
						rs.getInt("user_id"), 
						rs.getString("username"),
						rs.getString("pword"),
						rs.getString("first_name"), 
						rs.getString("last_name"),
						rs.getString("email"),
						getRoleById(rs.getInt("role_id_fk")));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
				
	}
}