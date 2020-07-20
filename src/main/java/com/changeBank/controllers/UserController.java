package com.changeBank.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.changeBank.models.users.User;
import com.changeBank.models.users.UserDTO;
import com.changeBank.repo.UserDao;
import com.changeBank.services.MessageService;
import com.changeBank.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserController {
	
	private static final MessageService ms = new MessageService();
	private static final ObjectMapper om = new ObjectMapper();
	private static final UserDao ud = UserDao.getInstance();
	private static final UserService us = new UserService();
	

	public void createUser(HttpServletRequest req, HttpServletResponse res) throws IOException {
		
		BufferedReader reader = req.getReader();
		StringBuilder s = new StringBuilder();
		String line = reader.readLine();

		while (line != null) {
			s.append(line);
			line = reader.readLine();
		}

		String body = new String(s);
		//System.out.println(body);
		
		UserDTO udto = om.readValue(body, UserDTO.class);
		
		// Make sure the username and email address are not already in use.
		if(ud.findByUsername(udto.username) != null) {
			res.setStatus(400);
			res.getWriter().println(om.writeValueAsString(ms.getMessageDTO("Username not available, please try again.")));
		}else if(ud.findByEmail(udto.email) != null) {
			res.setStatus(400);
			res.getWriter().println(om.writeValueAsString(ms.getMessageDTO("Email address already used, please use another.")));
		}
		else {
			User u = us.CreateUser(udto);
			if(u != null) {								
				udto = getUserDTO(u);				
				res.setStatus(201);
				res.getWriter().println(om.writeValueAsString(udto));				
			}else {
				res.setStatus(400);
				res.getWriter().println(om.writeValueAsString(ms.getMessageDTO("User not created.")));
			}
		}
		
	}
	public void findAll(HttpServletRequest req, HttpServletResponse res) throws IOException {
		
		List<User> users = us.findAll();
		
		List<UserDTO> usdto = new ArrayList<>();
		
				
		users.forEach(u -> usdto.add(getUserDTO(u)));
		
		
		res.setStatus(200);
		res.getWriter().println(om.writeValueAsString(usdto));
		
	}
	public void findById(HttpServletRequest req, HttpServletResponse res, int id) throws IOException {
		
		User u = us.findById(id);
		
		if(u != null) {
			UserDTO udto = getUserDTO(u);
			res.setStatus(200);
			res.getWriter().println(om.writeValueAsString(udto));
		}else {
			res.setStatus(204);
		}
		
		
	}
	private UserDTO getUserDTO(User u) {
	
			UserDTO udto = new UserDTO();
			udto.userId = u.getUserId();
			udto.username = u.getUsername();				
			udto.newPassword = null;
			udto.firstName = u.getFirstName();
			udto.lastName = u.getLastName();
			udto.email = u.getEmail();
			udto.roleId = u.getRole().getRoleId();
			udto.role = u.getRole();
			
			return udto;
						
	}
	private UserDTO getUserDTO(HttpServletRequest req) throws IOException {
		//System.out.println("Getting DTO from body");
		
		BufferedReader reader = req.getReader();
		StringBuilder s = new StringBuilder();
		String line = reader.readLine();

		while (line != null) {
			s.append(line);
			line = reader.readLine();
		}

		String body = new String(s);
		System.out.println(body);
		
		return om.readValue(body, UserDTO.class);
	}
	public void updateUser(HttpServletRequest req, HttpServletResponse res, int roleId, int authUserId) throws IOException {
		
		UserDTO udto = getUserDTO(req);
				
	    User u = us.updateUser(udto, roleId, authUserId);
		
	    if(u != null) {
	    	
	    	udto = getUserDTO(u);
	    	res.setStatus(200);
			res.getWriter().println(om.writeValueAsString(udto));
	    	
	    }else {
	    	res.setStatus(401);
	    }
		
	}
}