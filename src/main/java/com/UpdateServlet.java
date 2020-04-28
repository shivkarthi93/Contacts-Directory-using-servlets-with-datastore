package com;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;

public class UpdateServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setDateHeader("Expires", 0);
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		
		out.println(
				"<form action = \"/update\" method =\"post\">Name to be update:<input type =\"search\" placeholder=\"type name to update\" name=\"searchKey\" required> <br> Name:   <input type=\"text\" name=\"name\" ><br>Email:   <input type=\"text\" name=\"email\" ><br>Number:   <input type=\"text\" name=\"number\" ><br>Address:   <input type=\"text\" name=\"address\" ><br><input type=\"submit\" value=\"Update\">"
						+ "</form>");
		out.print("<form action=\"/welcome\" method=\"get\" > <input type =\"submit\" value=\"Logout\"> </form>");
		out.print("<form action=\"/profile\" method=\"get\" > <input type =\"submit\" value=\"Profile\"> </form>");

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String name;
		String number;
		String email;
		String address;

		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setDateHeader("Expires", 0);
		response.setContentType("text/html");
		HttpSession session = request.getSession(false);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Entity user;
		PrintWriter out = response.getWriter();
		String uid = session.getAttribute("uid").toString();
		String uname=session.getAttribute("uname").toString();
        String key=request.getParameter("searchKey");
		name = request.getParameter("name");
		number = request.getParameter("number");
		email = request.getParameter("email");
		address = request.getParameter("address");
		try {


			Query query = new Query("Contacts").addFilter("UserId", FilterOperator.EQUAL, uid).addFilter("Name", FilterOperator.EQUAL, key);
			 
			Entity contact=datastore.prepare(query).asSingleEntity();
			if(contact==null) {
				throw new Exception("Contact:"+key+" is not present!");
			}
			
			name = request.getParameter("name");
			number = request.getParameter("number");
			email = request.getParameter("email");
			address = request.getParameter("address");
			contact.setProperty("Name", name);
			contact.setProperty("Number", number);
			contact.setProperty("Email", email);
			contact.setProperty("Address", address);
			datastore.put(contact);
				out.println("contacts updated!");
				response.sendRedirect("/profile");

			

		} catch (Exception e) {
			out.print("<h4>" + e + " Try again!!</h4>");
			out.print("<form action=\"/AddContact.html\" > <input type =\"submit\" value=\"Add contact\"> </form>");
			out.print("<form action=\"/welcome\" method=\"get\" > <input type =\"submit\" value=\"Logout\"> </form>");
		}
	}
}