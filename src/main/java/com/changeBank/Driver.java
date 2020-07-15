package com.changeBank;

import com.changeBank.models.accounts.Account;

import com.changeBank.models.accounts.AccountType;
import com.changeBank.models.users.Role;
import com.changeBank.models.users.User;
import com.changeBank.models.users.UserLoginDTO;
import com.changeBank.models.users.UserDTO;
import com.changeBank.repo.AccountDao;
import com.changeBank.repo.AccountTypeDao;
import com.changeBank.repo.RoleDao;
import com.changeBank.repo.UserDao;
import com.changeBank.services.UserService;

public class Driver {

	public static void main(String[] args) {
		
// NEW USER
//		UserDTO userData = new UserDTO("cbratton","1234","Creed","Bratton","cbratton@dundermifflin.com",3);
//		UserService userService = new UserService();
//		userService.CreateUser(userData);
		
// LOGIN	
//		UserLoginDTO login = new UserLoginDTO("tflenderson","12345");
//		UserService userService = new UserService();
//		userService.authenticate(login);
		
// UPDATE USER
		RoleDao roleDoa =  RoleDao.getInstance();
				
		UserDTO userData = new UserDTO();
		userData.setUserId(13);
		userData.setPassword("1234");
		userData.setLastName("Bratton");
		userData.setRole(2);
		UserService userService = new UserService();
		userService.updateUser(userData, roleDoa.getById(1));
		
		
		
//		UserDao userDao = UserDao.getInstance();
		
		//UserLoginDTO login = new UserLoginDTO("tflenderson","1234");
		//User user = userDao.authenticate(login);
		//LOOKUP USER
		//User user = userDao.findById(8);
		
		//NEW USER
//		RoleDao roleDao = RoleDao.getInstance();
//		Role role = roleDao.getById(3);
//		User user = new User("kmalone","1234","Kevin","Malone","kmalone@dundermifflin.com",role);
//		user = userDao.insert(user);
//		System.out.println(user);
		
		//Update user
		//user.setFirstName("TOBY");
		//user.setEmail("test@dundermifflin.com");
		
		//userDao.update(user);
		
		//if(user != null) {
		//	System.out.println(user.toString());
		//}
		
		//CREATE ACCOUNT
//		AccountTypeDao accountTypeDAO = AccountTypeDao.getInstance();
//		AccountType accountType =  accountTypeDAO.getById(2);
//		
//		int acctNbr = accountTypeDAO.getNextAccountNumber(accountType.getAccountTypeId());
//		Account account = new Account(1,acctNbr,accountType);
//		
//		AccountDao accountDAO = AccountDao.getInstance();
//		account = accountDAO.insert(account);
//		
//		System.out.println(account.toString());

	}
}