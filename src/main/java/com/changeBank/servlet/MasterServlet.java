package com.changeBank.servlet;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.changeBank.controllers.LoginController;
import com.changeBank.controllers.UserController;

public class MasterServlet extends HttpServlet {
	
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
		
		int roleId = 0;
		int authUserId = 0;
		HttpSession ses = req.getSession(false);
		if(ses != null) {
			roleId = (int) ses.getAttribute("roleId");
			authUserId = (int) ses.getAttribute("userid");
		}
					
		final String URI = req.getRequestURI().replace("/rocp-project/api/", "");	
		final String METHOD = req.getMethod();
		String[] URIparts = URI.split("/");
				
		System.out.println(Arrays.toString(URIparts));
				
		switch(URIparts[0]) {
		case "users":
			if(METHOD.equals("GET")) {
				if(roleId == 1 || roleId == 2) {
					if(URIparts.length == 2) {
						uc.findById(req, res,Integer.parseInt(URIparts[1]));
					} else {
						uc.findAll(req, res);
					}
				}else {
					res.setStatus(401);		
				}
			}else if(METHOD.equals("POST")) {
				if(roleId == 1) {
					uc.createUser(req, res);
				}else {
					res.setStatus(401);		
				}				
			}else if(METHOD.equals("PUT")) {
					uc.updateUser(req, res, roleId, authUserId);
			}else {
				res.setStatus(405);	
			}
			break;
		case "login":
			if (METHOD.equals("POST")) {
				lc.login(req, res);
			}else {
				res.setStatus(405);			
			}			
			break;
		case "logout":
			if (METHOD.equals("POST")) {
				lc.logout(req, res);
			}else {
				res.setStatus(405);			
			}			
			break;
		}
	}
}