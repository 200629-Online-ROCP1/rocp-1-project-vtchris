package com.changeBank.controllers;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.changeBank.models.users.UserDTO;
import com.changeBank.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserController {
	
	private static final UserService us = new UserService();
	private static final ObjectMapper om = new ObjectMapper();

	public void createUser(HttpServletRequest req, HttpServletResponse res) throws IOException {
		
		BufferedReader reader = req.getReader();
		StringBuilder s = new StringBuilder();
		String line = reader.readLine();

		while (line != null) {
			s.append(line);
			line = reader.readLine();
		}

		String body = new String(s);
		System.out.println(body);
		
		UserDTO udto = om.readValue(body, UserDTO.class);
		if(us.CreateUser(udto)) {
			res.setStatus(201);
			res.getWriter().println("User was created.");
		}else {
			res.setStatus(409);
			res.getWriter().println("User not created.");
		}
		
		
	}
}

//int id = Integer.parseInt(URIparts[1]);
//User u = us.findById(id);
//res.setStatus(200);
////This ObjectMapper (om) will take the object (a) and convert it to a JSON object String
//String json = om.writeValueAsString(u);
//res.getWriter().println(json);

//List<User> users = us.findAll();
//res.setStatus(200);
//res.getWriter().println(om.writeValueAsString(users));