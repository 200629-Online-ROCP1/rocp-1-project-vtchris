package com.changeBank.controllers;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.changeBank.models.users.User;
import com.changeBank.models.users.UserLoginDTO;
import com.changeBank.services.UserService;

public class LoginController {

	private static final UserService us = new UserService();
	private static final ObjectMapper om = new ObjectMapper();

	public void login(HttpServletRequest req, HttpServletResponse res) throws IOException {

		if (req.getMethod().equals("POST")) {
			BufferedReader reader = req.getReader();
			StringBuilder s = new StringBuilder();
			String line = reader.readLine();

			while (line != null) {
				s.append(line);
				line = reader.readLine();
			}

			String body = new String(s);
			System.out.println(body);

			UserLoginDTO l = om.readValue(body, UserLoginDTO.class);

			User u = us.authenticate(l);

			if (u != null) {
				// Creates new session if one doesn't exist
				HttpSession ses = req.getSession();
				ses.setAttribute("userid", u.getUserId());
				ses.setAttribute("authenticated", true);
				ses.setAttribute("role", u.getRole().getRoleId());
				res.setStatus(200);
				res.getWriter().println("Login Successful!");
			} else {
				// Returns session only if one already exists
				HttpSession ses = req.getSession(false);
				if (ses != null) {
					// This will throw out the session, the client will no longer have a session
					ses.invalidate();
				}
				res.setStatus(401);
				res.getWriter().println("Login Failed!");
			}

		} else {
			res.setStatus(405);
		}
	}

	public void logout(HttpServletRequest req, HttpServletResponse res) throws IOException {

		HttpSession ses = req.getSession(false);

		if (req.getMethod().equals("POST")) {
			if (ses != null) {
				ses.invalidate();
				res.setStatus(200);
				res.getWriter().println("You logged out!");
			} else {
				res.setStatus(400);
				res.getWriter().println("You must be logged in to log out!");
			}
		} else {
			res.setStatus(405);
		}

	}

}