package com.changeBank;

import com.changeBank.models.users.Role;
import com.changeBank.models.users.User;
import com.changeBank.models.users.UserLoginDTO;
import com.changeBank.repo.RoleDao;
import com.changeBank.repo.UserDao;

public class Driver {

	public static void main(String[] args) {
				
		UserDao userDao = UserDao.getInstance();
		
		//UserLoginDTO login = new UserLoginDTO("tflenderson","1234");
		//User user = userDao.authenticate(login);
		//LOOKUP USER
		User user = userDao.findById(8);
		
		//NEW USER
//		RoleDao roleDao = RoleDao.getInstance();
//		Role role = roleDao.findById(3);
//		User user = new User("tflenderson","1234","Toby","Flenderson","tflenderson@dundermifflin.com",role);
//		dao.insert(user);
		
		//Update user
		user.setFirstName("TOBY");
		user.setEmail("test@dundermifflin.com");
		
		userDao.update(user);
		
		if(user != null) {
			System.out.println(user.toString());
		}
		

	}
}