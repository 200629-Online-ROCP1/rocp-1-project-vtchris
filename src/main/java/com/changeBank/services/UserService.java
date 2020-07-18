package com.changeBank.services;

import java.util.List;

import com.changeBank.models.users.Role;
import com.changeBank.models.users.User;
import com.changeBank.models.users.UserDTO;
import com.changeBank.models.users.UserLoginDTO;
import com.changeBank.repo.RoleDao;
import com.changeBank.repo.UserDao;

public class UserService {
	
	private UserDao userDao = UserDao.getInstance();
	
	public User authenticate(UserLoginDTO userLoginData) {
		
		return userDao.authenticate(userLoginData);			
				
	}

	public boolean CreateUser(UserDTO userData) {
		
		RoleDao roleDao = RoleDao.getInstance();
		Role role = roleDao.getById(userData.role);
				
		UserDao userDao = UserDao.getInstance();
		User user = new User(
				userData.username,
				userData.newPassword,
				userData.firstName,
				userData.lastName,
				userData.email,
				role);
				
		user = userDao.insert(user);
		System.out.println(user);
		return true;
		
	}	
	
	public void updateUser(UserDTO userData, Role role) {
		
		
		User user = userDao.getById(userData.userId);
		
		// Update fields with new data if not null		
		if(userData.email != null) {
			user.setEmail(userData.email);
		}
				
		if(userData.password.length() > 3) {
			user.setPasswordNew(userData.password);
		}
		
		// Update the following fields if Admin only		
		if(role.getRoleId() == 1) {
			if(userData.firstName != null) {
				user.setFirstName(userData.firstName);
			}
			if(userData.lastName != null) {
				user.setLastName(userData.lastName);	
			}
			if(userData.role > 0) {
				RoleDao roleDao = RoleDao.getInstance(); 
				Role userRole = roleDao.getById(userData.role);
				user.setRole(userRole);
			}				
		}
		
		userDao.update(user);
		
		// Get the update user and display on console
		user = userDao.getById(userData.userId);
		System.out.println(user.toString());
		
	}
	
	public User findById(int id) {
		
		return userDao.getById(id);
		
	}
	
	public List<User> findAll(){
		
		return userDao.findAll();
		
	}
}
