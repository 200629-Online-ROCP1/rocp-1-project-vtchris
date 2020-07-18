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

	public User CreateUser(UserDTO userData) {
		
		RoleDao roleDao = RoleDao.getInstance();
		Role role = roleDao.findById(userData.roleId);
				
		UserDao userDao = UserDao.getInstance();
		User user = new User(
				userData.username,
				userData.newPassword,
				userData.firstName,
				userData.lastName,
				userData.email,
				role);
				
		return userDao.insert(user);
		
	}	
	
	public User updateUser(UserDTO udto, int roleId, int authUserId) {
				
		User user = userDao.findById(udto.userId);
		boolean isChanging = false;
		
		// Update fields with new data if not null	
		if(roleId == 1 || roleId == 2 || authUserId == udto.userId) {
			if(udto.email != null && udto.email != "" && !user.getEmail().equals(udto.email)) {
				user.setEmail(udto.email);
				isChanging = true;
			}
			if(udto.newPassword != null && udto.newPassword.length() > 3) {
				user.setPasswordNew(udto.newPassword);
				isChanging = true;
			}			
		}
		
		// Update the following fields if Admin only		
		if(roleId == 1) {
			if(udto.firstName != null && udto.firstName != "" && !user.getFirstName().equals(udto.firstName)) {
				user.setFirstName(udto.firstName);
				isChanging = true;
			}
			if(udto.lastName != null && udto.lastName != "" && !user.getLastName().equals(udto.lastName)) {
				user.setLastName(udto.lastName);	
				isChanging = true;
			}
			if(udto.roleId > 0 && user.getRole().getRoleId() != udto.roleId) {
				RoleDao roleDao = RoleDao.getInstance(); 
				Role userRole = roleDao.findById(udto.roleId);
				user.setRole(userRole);
				isChanging = true;
			}		
		}
		
		if(isChanging) {
			if(userDao.update(user)) {
				return userDao.findById(udto.userId);
			}else {
				return null;
			}
		}
				
		return null;				
	}
	
	public User findById(int id) {
		
		return userDao.findById(id);
		
	}
	
	public List<User> findAll(){
		
		return userDao.findAll();
		
	}
}
