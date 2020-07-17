package com.changeBank;

import com.changeBank.models.accounts.Account;
import com.changeBank.models.accounts.AccountDTO;
import com.changeBank.models.accounts.AccountType;
import com.changeBank.models.users.Role;
import com.changeBank.models.users.User;
import com.changeBank.models.users.UserLoginDTO;
import com.changeBank.models.users.UserDTO;
import com.changeBank.repo.AccountDao;
import com.changeBank.repo.AccountTypeDao;
import com.changeBank.repo.RoleDao;
import com.changeBank.repo.UserDao;
import com.changeBank.services.AccountService;
import com.changeBank.services.UserService;

public class Driver {

	public static void main(String[] args) {
		
// NEW USER
//		UserDTO userData = new UserDTO("cbratton","1234","Creed","Bratton","cbratton@dundermifflin.com",3);
//		UserService userService = new UserService();
//		userService.CreateUser(userData);
		
// USER BY ID
		UserService userService = new UserService();
		User user = userService.findById(1);
		System.out.println(user.toString());
		
// LOGIN	
//		UserLoginDTO login = new UserLoginDTO("tflenderson","12345");
//		UserService userService = new UserService();
//		userService.authenticate(login);
		
// UPDATE USER
//		RoleDao roleDoa =  RoleDao.getInstance();
//				
//		UserDTO userData = new UserDTO();
//		userData.setUserId(13);
//		userData.setPassword("1234");
//		userData.setLastName("Bratton");
//		userData.setRole(2);
//		UserService userService = new UserService();
//		userService.updateUser(userData, roleDoa.getById(1));
		
// NEW ACCOUNT
//		AccountDTO accountData = new AccountDTO(1,2,0);
//		AccountService accountService = new AccountService();
//		accountService.CreateAccount(accountData);
		
// UPDATE ACCOUNT STATUS 
//		AccountDTO accountData = new AccountDTO(1,2);
//		AccountService accountService = new AccountService();
//		accountService.UpdateStatus(accountData);
		


	}
}