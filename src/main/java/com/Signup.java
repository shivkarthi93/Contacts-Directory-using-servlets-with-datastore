package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query.FilterOperator;

public class Signup extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setDateHeader("Expires", 0);
		response.setContentType("text/html");
		HttpSession session = request.getSession(false);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Entity user;
		PrintWriter out = response.getWriter();
		try {
			if (session == null) {
				String uname = request.getParameter("uname");
				String psw = request.getParameter("psw");
				String cpsw = request.getParameter("cpsw");
				Query qs = new Query("Users").addFilter("username", FilterOperator.EQUAL, uname);
				PreparedQuery pq = datastore.prepare(qs);

				user = pq.asSingleEntity();
				if (user != null) {
					throw new Exception("Username is already exists!");

				}

				if (cpsw.equals(psw)) {
					String uid=Integer.toString((uname+psw).hashCode());
					 user = new Entity("Users",uid);
					user.setProperty("username", uname);
					user.setProperty("password", psw);

					datastore.put(user);
					out.print("contact is created!");
					response.sendRedirect("/profile");
				}

				else

				{
					throw new Exception("Password is not same wrong !");

				}

			} else {
				response.sendRedirect("/profile");
			}
		} catch (Exception e) {
			out.print("<h3>"+e + " Try again!</h3><br>");
			out.print("<form action=\"/Welcome.html\" > <input type =\"submit\" value=\"Login\"> </form>");
			out.print("<form action=\"/Signup.html\" > <input type =\"submit\" value=\"Signup\"> </form>");
		}

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

	}
}
