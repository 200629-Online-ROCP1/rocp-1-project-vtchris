package com.changeBank.services;

import com.changeBank.models.users.Role;
import com.changeBank.models.users.User;
import com.changeBank.models.users.UserDTO;
import com.changeBank.models.users.UserLoginDTO;
import com.changeBank.repo.RoleDao;
import com.changeBank.repo.UserDao;

public class UserService {
	
	public void authenticate(UserLoginDTO userLoginData) {
		
		UserDao userDao = UserDao.getInstance();
		
		try {
			User user = userDao.authenticate(userLoginData);
			System.out.println(user.toString());
		}catch (Exception e){
			System.out.println(e);
		}
				
	}

	public void CreateUser(UserDTO userData) {
		
		RoleDao roleDao = RoleDao.getInstance();
		Role role = roleDao.getById(userData.getRole());
				
		UserDao userDao = UserDao.getInstance();
		User user = new User(
				userData.getUsername(),
				userData.getPassword(),
				userData.getFirstName(),
				userData.getLastName(),
				userData.getEmail(),
				role);
				
		user = userDao.insert(user);
		System.out.println(user);
		
	}	
	
	public void updateUser(UserDTO userData, Role role) {
		
		UserDao userDao = UserDao.getInstance();
		User user = userDao.getById(userData.getUserId());
		
		// Update fields with new data if not null		
		if(userData.getEmail() != null) {
			user.setEmail(userData.getEmail());
		}
				
		if(userData.getPassword().length() > 3) {
			user.setPasswordNew(userData.getPassword());
		}
		
		// Update the following fields if Admin only		
		if(role.getRoleId() == 1) {
			if(userData.getFirstName() != null) {
				user.setFirstName(userData.getFirstName());
			}
			if(userData.getLastName() != null) {
				user.setLastName(userData.getLastName());	
			}
			if(userData.getRole() > 0) {
				RoleDao roleDao = RoleDao.getInstance(); 
				Role userRole = roleDao.getById(userData.getRole());
				user.setRole(userRole);
			}				
		}
		
		userDao.update(user);
		
		// Get the update user and display on console
		user = userDao.getById(userData.getUserId());
		System.out.println(user.toString());
		
	}
}
