package com.changeBank;

import com.changeBank.models.users.Role;
import com.changeBank.models.users.User;
import com.changeBank.repo.RoleDao;
import com.changeBank.repo.UserDao;

public class Driver {

	public static void main(String[] args) {
		
		RoleDao roleDao = RoleDao.getInstance();
		Role role = roleDao.findById(3);
				
		
		UserDao dao = UserDao.getInstance();
		//User user = dao.findById(2);
		User user = new User("omartin","6546","Oscar","Martinez","omartinez@dundermifflin.com",role);
		dao.insert(user);
		
		System.out.println(user.toString());

	}
}