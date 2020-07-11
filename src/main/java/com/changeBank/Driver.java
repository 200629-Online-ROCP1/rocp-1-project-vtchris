package com.changeBank;

import com.changeBank.models.users.Role;
import com.changeBank.models.users.User;
import com.changeBank.repo.RoleDao;
import com.changeBank.repo.UserDao;
import com.changeBank.repo.UserLoginDTO;

public class Driver {

	public static void main(String[] args) {
				
		UserDao dao = UserDao.getInstance();
		
		UserLoginDTO login = new UserLoginDTO("tflenderson","12344");
		User user = dao.login(login);
		//LOOKUP USER
		//User user = dao.findById(2);
		
		//NEW USER
//		RoleDao roleDao = RoleDao.getInstance();
//		Role role = roleDao.findById(3);
//		User user = new User("tflenderson","1234","Toby","Flenderson","tflenderson@dundermifflin.com",role);
//		dao.insert(user);
		
		if(user != null) {
			System.out.println(user.toString());
		}
		

	}
}