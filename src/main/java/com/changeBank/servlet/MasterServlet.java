package com.changeBank.servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.changeBank.controllers.LoginController;
import com.changeBank.controllers.UserController;
import com.changeBank.models.users.User;
import com.changeBank.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MasterServlet extends HttpServlet {
	
	private static final ObjectMapper om = new ObjectMapper();
	private static final UserService us = new UserService();
	private static final LoginController lc = new LoginController();
	private static final UserController uc = new UserController();
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException,IOException {
		System.out.println("In MasterServlet doGet");
		router(req,res);		
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException,IOException {
		System.out.println("In MasterServlet doPost");
		router(req,res);
	}
	
	protected void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException,IOException {
		System.out.println("In MasterServlet doPut");
		router(req,res);
	}
	
	protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException,IOException {
		System.out.println("In MasterServlet doDelete");
		router(req,res);
	}
	
	protected void router(HttpServletRequest req, HttpServletResponse res) throws ServletException,IOException {
		res.setContentType("application/json");
		// this will set the default res to not found, we will change later if the request was successful
		res.setStatus(404);
		
		final String URI = req.getRequestURI().replace("/rocp-project/api/", "");	
		final String METHOD = req.getMethod();
		String[] URIparts = URI.split("/");
		
		System.out.println(Arrays.toString(URIparts));
		System.out.println(METHOD.equals("GET"));
		
		
		switch(URIparts[0]) {
		case "user":
			if(METHOD.equals("GET")) {
				if(URIparts.length == 2) {

				} else {

				}
			}else if(METHOD.equals("POST")) {
				uc.createUser(req, res);
			}			
			break;
		case "login":
			lc.login(req, res);
			break;
		case "logout":
			lc.logout(req, res);
			break;
		}
		
		
	}

}
