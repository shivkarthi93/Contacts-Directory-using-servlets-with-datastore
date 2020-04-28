package com;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query.FilterOperator;

public class Welcome extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setDateHeader("Expires", 0);
		response.setContentType("text/html");
		HttpSession session = request.getSession(false);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Entity user;
		PrintWriter out = response.getWriter();
		if (session != null) {
			
			session.invalidate();
			out.print("<h4> you succesfully logged out</h4>");
			response.sendRedirect("Welcome.html");
			
		} else {
			response.sendRedirect("/profile");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setDateHeader("Expires", 0);
		response.setContentType("text/html");
		HttpSession session = request.getSession(false);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Entity user;
		PrintWriter out = response.getWriter();
		if (session == null) {
			String uname = request.getParameter("uname");
			String psw = request.getParameter("psw");

			try {
				Query userQuery= new Query("Users").addFilter("username", FilterOperator.EQUAL, uname);
				user=datastore.prepare(userQuery).asSingleEntity();
				if(user==null) {
					throw new Exception("Username is not exist!");
				}
				if (user.getProperty("password").equals(psw)) {

					session = request.getSession();
					session.setAttribute("uid", user.getKey().toString());
					session.setAttribute("uname", uname);
					response.sendRedirect("/profile");
				}

				else

				{
					throw new Exception("Password is not correct!");

				}
			} catch (Exception e) {
				out.print("<h3>"+e+" Try again!</h3><br>");
				out.print("<a href=\"Welcome.html\" > Login </a>");

			}

		} else {
			response.sendRedirect("/profile");
		}
	}
}