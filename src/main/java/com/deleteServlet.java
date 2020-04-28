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

public class deleteServlet  extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setDateHeader("Expires", 0);
		response.setContentType("text/html");
		HttpSession session = request.getSession(false);
		PrintWriter out = response.getWriter();
		
		out.print("<form action=\"/delete\" method=\"post\" > <input type =\"search\" placeholder=\"type name to delete\" name=\"searchKey\" required> <input type =\"submit\" value=\"delete\"> </form>");
		
		out.print("<form action=\"/welcome\" method=\"get\" > <input type =\"submit\" value=\"Logout\"> </form>");
		out.print("<form action=\"/profile\" method=\"get\" > <input type =\"submit\" value=\"Profile\"> </form>");

		

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setDateHeader("Expires", 0);
		response.setContentType("text/html");
		HttpSession session = request.getSession(false);
		PrintWriter out = response.getWriter();
		String uid = session.getAttribute("uid").toString();
		String uname=session.getAttribute("uname").toString();
        String deleteName=request.getParameter("searchKey");
		try {
		
		
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		

		

			Query query = new Query("Contacts").addFilter("UserId", FilterOperator.EQUAL, uid).addFilter("Name", FilterOperator.EQUAL, deleteName);
			 
			Entity contact=datastore.prepare(query).asSingleEntity();
			if(contact==null) {
				throw new Exception("Contact:"+deleteName+" is not present!");
			}
			datastore.delete(contact.getKey());
			response.sendRedirect("/profile");
		} catch (Exception e) {
			out.print("<h3>" + e + " Try again!</h3><br>");
			out.print("<form action=\"/welcome\" method=\"get\" > <input type =\"submit\" value=\"Logout\"> </form>");
			out.print("<form action=\"/profile\" method=\"get\" > <input type =\"submit\" value=\"Profile\"> </form>");

		}
	}
	
	
	
}
