package com.changeBank.controllers;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.changeBank.models.users.User;
import com.changeBank.models.users.UserDTO;
import com.changeBank.models.users.UserLoginDTO;
import com.changeBank.services.MessageService;
import com.changeBank.services.UserService;

public class LoginController {

	private static final MessageService ms = new MessageService();
	private static final ObjectMapper om = new ObjectMapper();
	private static final UserService us = new UserService();

	public void login(HttpServletRequest req, HttpServletResponse res) throws IOException {

		BufferedReader reader = req.getReader();
		StringBuilder s = new StringBuilder();
		String line = reader.readLine();

		while (line != null) {
			s.append(line);
			line = reader.readLine();
		}

		String body = new String(s);
		//System.out.println(body);

		UserLoginDTO l = om.readValue(body, UserLoginDTO.class);

		User u = us.authenticate(l);
				
		if (u != null) {
			// Creates new session if one doesn't exist
			HttpSession ses = req.getSession();
			ses.setAttribute("userid", u.getUserId());
			ses.setAttribute("username", u.getUsername());
			ses.setAttribute("authenticated", true);
			ses.setAttribute("roleId", u.getRole().getRoleId());
			
			UserDTO udto = new UserDTO();
			udto.userId = u.getUserId();
			udto.username = u.getUsername();		
			udto.firstName = u.getFirstName();
			udto.lastName = u.getLastName();
			udto.email =u.getEmail();	
			udto.roleId = u.getRole().getRoleId();
			udto.role = u.getRole();
			
			res.setStatus(200);
			res.getWriter().println(om.writeValueAsString(udto));
		} else {
			// Returns session only if one already exists
			HttpSession ses = req.getSession(false);
			if (ses != null) {
				// This will throw out the session, the client will no longer have a session
				ses.invalidate();
			}
			res.setStatus(400);
			res.getWriter().println(om.writeValueAsString(ms.getMessageDTO("Invalid Credentials")));
		}
	}

	public void logout(HttpServletRequest req, HttpServletResponse res) throws IOException {

		HttpSession ses = req.getSession(false);
		
		if (ses != null) {
			String username = (String) ses.getAttribute("username");
			ses.invalidate();
			res.setStatus(200);
			res.getWriter().println(om.writeValueAsString(ms.getMessageDTO("You have successfully logged out " + username)));
		} else {
			res.setStatus(400);
			res.getWriter().println(om.writeValueAsString(ms.getMessageDTO("There was no user logged into the session")));
		}
	}
}