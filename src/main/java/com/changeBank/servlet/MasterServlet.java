package com.changeBank.servlet;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.changeBank.models.users.User;
import com.changeBank.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MasterServlet extends HttpServlet {
	
	private static final ObjectMapper om = new ObjectMapper();
	private static final UserService us = new UserService();
	
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
		String[] URIparts = URI.split("/");
		
		System.out.println(Arrays.toString(URIparts));
		
		switch(URIparts[0]) {
		case "user":
			if(req.getMethod().equals("GET")) {
				if(URIparts.length == 2) {
					int id = Integer.parseInt(URIparts[1]);
					User u = us.findById(id);
					res.setStatus(200);
					//This ObjectMapper (om) will take the object (a) and convert it to a JSON object String
					//res.getWriter().println(om.writeValueAsString(a)); 
					String json = om.writeValueAsString(u);
					res.getWriter().println(json);
				}
			}
		}
		
	}

}
