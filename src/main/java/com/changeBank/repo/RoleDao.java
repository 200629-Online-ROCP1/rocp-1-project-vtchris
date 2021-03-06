package com.changeBank.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.changeBank.models.users.Role;
import com.changeBank.utils.ConnectionUtil;

public class RoleDao implements IDao<Role> {
	
	// This is a design pattern called a "singleton" where only one implementation 
	// of a class can exist at a time.
	// Not set up for multi-threads
	private static RoleDao repo = new RoleDao();
	private RoleDao() {}
	public static RoleDao getInstance() {
		return repo;
	}

	@Override
	public Role insert(Role t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Role t, int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Role findById(int id) {
		//System.out.println("Looking Up Role by id");
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM roles WHERE role_id = ?;";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1,id);
			
			ResultSet rs = statement.executeQuery();
			
			if(rs.next()) {
				return new Role(
						rs.getInt("role_id"), 					
						rs.getString("role_name"));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean update(Role t) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public List<Role> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
