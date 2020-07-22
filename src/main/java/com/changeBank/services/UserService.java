package com.changeBank.services;

import java.util.List;

import com.changeBank.models.accounts.Account;
import com.changeBank.models.users.Role;
import com.changeBank.models.users.User;
import com.changeBank.models.users.UserDTO;
import com.changeBank.models.users.UserLoginDTO;
import com.changeBank.repo.RoleDao;
import com.changeBank.repo.UserDao;

public class UserService {
	
	private static final AccountService as = new AccountService();
	private static final AccountTransactionService ts = new AccountTransactionService();
	private static final RoleDao rdao = RoleDao.getInstance();
	private static final UserDao udao = UserDao.getInstance();
	
	public User authenticate(UserLoginDTO uldto) {
		
		return udao.authenticate(uldto);		
		
	}

	public User CreateUser(UserDTO udto) {
				
		Role role = rdao.findById(udto.roleId);
				
		User user = new User(
				udto.username,
				udto.newPassword,
				udto.firstName,
				udto.lastName,
				udto.email,
				role);
				
		return udao.insert(user);
		
	}	
	
	public boolean deleteUser(UserDTO udto, List<Account> accounts) {
		
		User u = new User();
		u.setUserId(udto.userId);
		u.setAuthUserId(udto.authUserId);
				
		if(accounts != null) {
			for(Account a: accounts) {				
				if(ts.deleteAccountTransactions(a, udto.authUserId)) {
					if(!as.deleteAccountById(a, udto.authUserId)){
						return false;
					}
				}else {
					return false;
				}
			}
		}
		
		return udao.delete(u, udto.authUserId);		
		
	}
		
	public List<User> findAll(){
		
		return udao.findAll();
		
	}
	
	public User findByEmail(String email) {
		return udao.findByEmail(email);
	}
	
	public User findById(int id) {
		
		return udao.findById(id);
		
	}
	
	public User findByUsername(String username) {
		return udao.findByUsername(username);
	}
	
	public User updateUser(UserDTO udto, int roleId, int authUserId) {
				
		User user = udao.findById(udto.userId);
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
				Role userRole = rdao.findById(udto.roleId);
				user.setRole(userRole);
				isChanging = true;
			}		
		}
		
		if(isChanging) {
			if(udao.update(user)) {
				return udao.findById(udto.userId);
			}else {
				return null;
			}
		}
				
		return null;				
	}
	
}
